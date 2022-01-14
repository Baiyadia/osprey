package com.kaiqi.osprey.security;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.kaiqi.osprey.security.csrf.CsrfInterceptor;
import com.kaiqi.osprey.security.jwt.JwtInterceptor;
import com.kaiqi.osprey.security.jwt.crypto.AesJwtTokenCryptoProvider;
import com.kaiqi.osprey.security.jwt.crypto.JwtTokenCryptoProvider;
import com.kaiqi.osprey.security.jwt.model.JwtConfig;
import com.kaiqi.osprey.security.jwt.token.JwtTokenProvider;
import com.kaiqi.osprey.security.xss.XssFilter;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author wangs
 * @date 2017/11/20
 */
@Order(99)
@Configuration
@ConditionalOnClass({ XssFilter.class, CsrfInterceptor.class, JwtInterceptor.class })
@EnableConfigurationProperties({ SecurityProperties.class })
public class SecurityAutoConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private static String CSRF_INTERCEPTOR_BEAN_NAME = "csrfInterceptor";
    private static String JWT_INTERCEPTOR_BEAN_NAME = "jwtInterceptor";
    private ApplicationContext applicationContext;
    private SecurityProperties securityProperties;

    public SecurityAutoConfiguration(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    /**
     * 配置XssFilter
     *
     * @return FilterRegistrationBean
     */
    @Bean
    @ConditionalOnProperty(prefix = "osprey.security.xss", name = "enabled", matchIfMissing = true)
    public FilterRegistrationBean xssFilterRegistrationBean() {
        String[] urlPatterns = Iterables.toArray(Splitter.on(",")
                                                         .omitEmptyStrings()
                                                         .trimResults()
                                                         .split(securityProperties.getXss().getUrlPatterns()), String.class);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new XssFilter());
        registrationBean.addInitParameter(XssFilter.FILTER_POLICY, securityProperties.getXss().getPolicy());
        registrationBean.addInitParameter(XssFilter.EXCLUDE_URL_PATTERNS, securityProperties.getXss().getExcludeUrlPatterns());
        registrationBean.addUrlPatterns(urlPatterns);
        registrationBean.setName("xssFilter");
        return registrationBean;
    }

    /**
     * 配置Csrf Interceptor
     *
     * @return CsrfInterceptor
     */
    @Bean
    @ConditionalOnProperty(prefix = "osprey.security.csrf", name = "enabled", matchIfMissing = true)
    public CsrfInterceptor csrfInterceptor() {
        return new CsrfInterceptor(securityProperties.getCsrf().getRefererPattern());
    }

    /**
     * 配置jwt Interceptor,默认开启
     *
     * @return JwtInterceptor
     */
    @Bean
    @ConditionalOnClass(JwtInterceptor.class)
    @ConditionalOnProperty(prefix = "osprey.security.jwt",
            name = { "interceptor", "enabled" }, matchIfMissing = true)
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor(jwtTokenProvider());
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(JwtInterceptor.class)
    @ConditionalOnProperty(prefix = "osprey.security.jwt", name = "enabled", matchIfMissing = true)
    public JwtTokenProvider jwtTokenProvider() {
        SecurityProperties.Jwt jwt = securityProperties.getJwt();

        JwtConfig jwtConfig = new JwtConfig();
        jwtConfig.setRequestHeaderName(jwt.getRequestHeaderName());
        jwtConfig.setCryptoKey(jwt.getCryptoKey());
        jwtConfig.setIssuer(jwt.getIssuer());
        jwtConfig.setSecret(jwt.getSecret());
        jwtConfig.setExpiration(jwt.getExpiration());

        JwtTokenCryptoProvider cryptoProvider = new AesJwtTokenCryptoProvider();
        return new JwtTokenProvider(jwtConfig, cryptoProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public HandlerMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (applicationContext.containsBean(CSRF_INTERCEPTOR_BEAN_NAME)) {
            String[] csrfPathPatterns = Iterables.toArray(Splitter.on(",")
                                                                  .omitEmptyStrings()
                                                                  .trimResults()
                                                                  .split(securityProperties.getCsrf().getExcludePathPatterns()), String.class);
            registry.addInterceptor(csrfInterceptor()).excludePathPatterns(csrfPathPatterns);
        }

        if (applicationContext.containsBean(JWT_INTERCEPTOR_BEAN_NAME)) {
            String[] jwtPathPatterns = Iterables.toArray(Splitter.on(",")
                                                                 .omitEmptyStrings()
                                                                 .trimResults()
                                                                 .split(securityProperties.getJwt().getExcludePathPatterns()), String.class);
            registry.addInterceptor(jwtInterceptor()).excludePathPatterns(jwtPathPatterns);
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
