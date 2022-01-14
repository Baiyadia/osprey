package com.kaiqi.osprey.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wangs
 * @date 2017/11/20
 */
@ConfigurationProperties(prefix = "osprey.security")
public class SecurityProperties {

    private Xss xss;
    private Csrf csrf;
    private Jwt jwt = new Jwt();

    public Xss getXss() {
        return this.xss;
    }

    public void setXss(Xss xss) {
        this.xss = xss;
    }

    public Csrf getCsrf() {
        return this.csrf;
    }

    public void setCsrf(Csrf csrf) {
        this.csrf = csrf;
    }

    public Jwt getJwt() {
        return this.jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    public static class Xss {

        private String policy = "owasp";
        private String urlPatterns = "/v2/*";
        private String excludeUrlPatterns = "";

        /**
         * 获取需要XssFilter过滤策略
         * 默认为{@link XssFilterPolicyEnum.OWASP }
         *
         * @return 过滤策略
         * @see XssFilterPolicyEnum
         */
        public String getPolicy() {
            return this.policy;
        }

        /**
         * 设置需要XssFilter过滤策略
         *
         * @param policy XssFilter过滤策略
         */
        public void setPolicy(String policy) {
            this.policy = policy;
        }

        /**
         * 获取需要XssFilter的url pattern(ant pattern 表达式)
         * 多个pattern用英文逗号(,)分隔
         *
         * @return url pattern(ant pattern 表达式)
         * @see org.springframework.util.AntPathMatcher
         */
        public String getUrlPatterns() {
            return this.urlPatterns;
        }

        /**
         * 设置需要XssFilter的url pattern(ant pattern 表达式)
         * 多个pattern用英文逗号(,)分隔
         *
         * @param urlPatterns url pattern(ant pattern 表达式)
         * @see org.springframework.util.AntPathMatcher
         */
        public void setUrlPatterns(String urlPatterns) {
            this.urlPatterns = urlPatterns;
        }

        /**
         * 获取排出XssFilter的url pattern(ant pattern 表达式)
         * 多个pattern用英文逗号(,)分隔
         *
         * @return url pattern(ant pattern 表达式)
         * @see org.springframework.util.AntPathMatcher
         */
        public String getExcludeUrlPatterns() {
            return this.excludeUrlPatterns;
        }

        /**
         * 设置排出XssFilter的url pattern(ant pattern 表达式)
         * 多个pattern用英文逗号(,)分隔
         *
         * @param excludeUrlPatterns url pattern(ant pattern 表达式)
         * @see org.springframework.util.AntPathMatcher
         */
        public void setExcludeUrlPatterns(String excludeUrlPatterns) {
            this.excludeUrlPatterns = excludeUrlPatterns;
        }
    }

    public static class Csrf {

        private String refererPattern = "all";
        private String excludePathPatterns = "";

        /**
         * 获取可以通过csrf的http header referer url pattern(正则表达式）
         * 默认值为"all"表示全部通过
         *
         * @return http header referer url pattern(正则表达式）
         */
        public String getRefererPattern() {
            return this.refererPattern;
        }

        /**
         * 设置可以通过csrf的http header referer url pattern(正则表达式）
         *
         * @param refererPattern http header referer pattern(正则表达式）
         */
        public void setRefererPattern(String refererPattern) {
            this.refererPattern = refererPattern;
        }

        /**
         * 获取排出csrf的url pattern(ant pattern 表达式)
         * 多个pattern用英文逗号(,)分隔
         *
         * @return url pattern(ant pattern 表达式)
         * @see org.springframework.util.AntPathMatcher
         */
        public String getExcludePathPatterns() {
            return this.excludePathPatterns;
        }

        /**
         * 设置排出csrf的url pattern(ant pattern 表达式)
         * 多个pattern用英文逗号(,)分隔
         *
         * @param excludePathPatterns 排出csrf的url pattern(ant pattern 表达式)
         */
        public void setExcludePathPatterns(String excludePathPatterns) {
            this.excludePathPatterns = excludePathPatterns;
        }
    }

    /**
     * Json Web Token(JWT)配置项
     */
    public static class Jwt {

        private String requestHeaderName = "X-Authorization";
        private String issuer = "newex";
        private String secret;
        private String cryptoKey;
        private String excludePathPatterns = "";
        private long expiration = 604800;
        private String cryptoAlgorithm = "AES";

        /**
         * 获取httpRequest Header中存放jwt token的名称
         * (默认为"Authorization")
         *
         * @return requestHeaderName
         */
        public String getRequestHeaderName() {
            return this.requestHeaderName;
        }

        /**
         * 设置httpRequest Header中存放jwt token的名称
         *
         * @param requestHeaderName httpRequest Header中存放jwt token的名称
         */
        public void setRequestHeaderName(String requestHeaderName) {
            this.requestHeaderName = requestHeaderName;
        }

        /**
         * 获取颁发机构(默认为"newex")
         *
         * @return 颁发机构
         */
        public String getIssuer() {
            return this.issuer;
        }

        /**
         * 设置颁发机构
         *
         * @param issuer 颁发机构
         */
        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        /**
         * 获取TOKEN过期时间或刷新时间
         * (默认为"604800"相当于7天有效期)
         *
         * @return TOKEN过期时间或刷新时间
         */
        public long getExpiration() {
            return this.expiration;
        }

        /**
         * 设置TOKEN过期时间或刷新时间
         *
         * @param expiration TOKEN过期时间或刷新时间
         */
        public void setExpiration(long expiration) {
            this.expiration = expiration;
        }

        /**
         * 获取jwt签名密钥
         *
         * @return jwt签名密钥
         */
        public String getSecret() {
            return this.secret;
        }

        /**
         * 设置jwt签名密钥
         *
         * @param secret jwt签名密钥
         */
        public void setSecret(String secret) {
            this.secret = secret;
        }

        /**
         * 获取JWT playload内容加密密钥(默认为AES算法）
         *
         * @return JWT playload内容加密密钥
         */
        public String getCryptoKey() {
            return this.cryptoKey;
        }

        /**
         * 设置 JWT playload内容加密密钥(默认为AES算法）
         *
         * @param cryptoKey JWT playload内容加密密钥
         */
        public void setCryptoKey(String cryptoKey) {
            this.cryptoKey = cryptoKey;
        }

        /**
         * 获取排出jwt拦截的url pattern(ant pattern 表达式)
         * 多个pattern用英文逗号(,)分隔
         *
         * @return url pattern(ant pattern 表达式)
         * @see org.springframework.util.AntPathMatcher
         */
        public String getExcludePathPatterns() {
            return this.excludePathPatterns;
        }

        /**
         * 设置排出jwt拦截的url pattern(ant pattern 表达式)
         * 多个pattern用英文逗号(,)分隔
         *
         * @param excludePathPatterns 排出csrf的url pattern(ant pattern 表达式)
         */
        public void setExcludePathPatterns(String excludePathPatterns) {
            this.excludePathPatterns = excludePathPatterns;
        }

        /**
         * 获取JWT playload内容加密算法名称(默认为AES）
         *
         * @return
         */
        public String getCryptoAlgorithm() {
            return this.cryptoAlgorithm;
        }

        /**
         * 设置JWT playload内容加密算法名称(默认为AES）
         *
         * @param cryptoAlgorithm
         */
        public void setCryptoAlgorithm(String cryptoAlgorithm) {
            this.cryptoAlgorithm = cryptoAlgorithm;
        }
    }
}
