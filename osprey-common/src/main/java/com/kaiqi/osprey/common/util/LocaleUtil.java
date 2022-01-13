package com.kaiqi.osprey.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.i18n.CookieLocaleResolver;
//import org.springframework.web.servlet.i18n.SessionLocaleResolver;
//import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangs
 * @date 2017/12/09
 **/
@Slf4j
@Configuration
public class LocaleUtil {

    public static String getLocale(HttpServletRequest request) {
        return "zh-cn";
    }

//    /**
//     * 请求终端
//     */
//    public static String SPIDER = "spider";
//    public static String ANDROID = "android";
//    public static String IPHONE = "iphone";
//    public static String IOS = "ios";
//
//    /**
//     * 请求头User-Agent
//     */
//    public static String USER_AGENT = "User-Agent";
//
//    /**
//     * 语言
//     */
//    public static String[] ENGLISH = { "en-us", "en_us" };
//    public static String[] CHINA = { "zh-cn", "zh_cn", "zh-hans", "zh_hans" };
//    public static String[] CHINA_TW = { "zh-tw", "zh_tw", "zh-hant", "zh_hant" };
//    public static String[] CHINA_HK = { "zh-hk", "zh_hk" };
//    public static String[] KOREAN = { "ko-kr", "ko_kr" };
//    public static String[] JAPAN = { "ja-jp", "ja_jp" };
//    public static String[] SINGAPORE = { "zh-sg", "zh_sg" };
//    public static String[] TRADITIONAL_CHINESE = { "zh-tw", "zh_tw", "zh-hk", "zh_hk", "zh-hant", "zh_hant" };
//
//    /**
//     * 地区与语言列表
//     */
//    private static String[][] REGION_LANGS = {
//            ENGLISH, CHINA, CHINA_HK, KOREAN, JAPAN, CHINA_TW, SINGAPORE
//    };
//
//    /**
//     * 匹配手机端的UA中的本地化语言
//     */
//    private static Pattern LOCALE_PATTERN = Pattern.compile("locale=(.*)$", Pattern.CASE_INSENSITIVE);
//
//    private static char UNDERLINE = '_';
//    private static char DASH = '-';
//
//    private static LocaleResolver localeResolver;
//    private static MessageSource messageSource;
//
//    @Autowired
//    public LocaleUtil(LocaleResolver localeResolver, MessageSource messageSource) {
//        LocaleUtil.localeResolver = localeResolver;
//        LocaleUtil.messageSource = messageSource;
//    }
//
//    /**
//     * 根据当前request对象中的locale(Header的Accept属性)初始化系统国际化语言区域环境
//     *
//     * @param request  当前请求对象
//     * @param response 当前响应对象
//     */
//    public static void setInitLocale(HttpServletRequest request, HttpServletResponse response) {
//        Locale locale = request.getLocale();
//
//        if (localeResolver instanceof CookieLocaleResolver) {
//            CookieLocaleResolver cookieLocaleResolver = (CookieLocaleResolver) localeResolver;
//            Cookie cookie = WebUtils.getCookie(request, cookieLocaleResolver.getCookieName());
//            if (cookie == null) {
//                setLocale(request, response, locale,
//                        "Init CookieLocaleResolver locale url :{},country:{},lang:{}");
//                return;
//            }
//            log.debug("CookieLocaleResolver locale name:{} ,value:{}", cookie.getName(), cookie.getValue());
//        }
//
//        if (localeResolver instanceof SessionLocaleResolver) {
//            Locale sessionLocale = (Locale) WebUtils.getRequiredSessionAttribute(
//                    request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
//            if (sessionLocale == null) {
//                setLocale(request, response, locale,
//                        "Init SessionLocaleResolver locale url :{}, country:{},lang:{}");
//                return;
//            }
//            log.debug("SessionLocaleResolver Locale: {}", sessionLocale.toLanguageTag());
//        }
//    }
//
//    /**
//     * @param request
//     * @param response
//     * @param locale
//     * @param format
//     */
//    private static void setLocale(HttpServletRequest request, HttpServletResponse response,
//                                  Locale locale,
//                                  String format) {
//        setLocale(locale.toString(), request, response);
//        log.debug(format, request.getRequestURL().toString(), locale.getCountry(), locale.toLanguageTag());
//    }
//
//    /**
//     * 设置国际化语言区域环境
//     *
//     * @param lang     国际化语言名称
//     * @param request  当前请求对象
//     * @param response 当前响应对象
//     */
//    public static void setLocale(String lang, HttpServletRequest request, HttpServletResponse response) {
//        lang = StringUtils.defaultIfEmpty(lang, request.getLocale().toString()).toLowerCase();
//        for (String[] languages : REGION_LANGS) {
//            if (StringUtils.containsAny(lang, languages)) {
//                localeResolver.setLocale(request, response, Locale.forLanguageTag(languages[0]));
//                return;
//            }
//        }
//        localeResolver.setLocale(request, response, Locale.US);
//    }
//
//    /**
//     * 设置国际化语言区域环境
//     * 自动判断多请求端(web,android,ios,spider)
//     *
//     * @param request
//     * @param response
//     */
//    public static void setLocale(HttpServletRequest request, HttpServletResponse response) {
//        //如果当前请求已经带上本地化语言的cookie（即已经设置了当前语言环境）
//        //就不再次进行设置
//        Cookie cookie = WebUtils.getCookie(request, getLocaleCookieName());
//        if (cookie != null) {
//            return;
//        }
//
//        String userAgent = StringUtils.defaultIfBlank(
//                request.getHeader(USER_AGENT), StringUtils.EMPTY).toLowerCase();
//
//        log.debug("USER_AGENT :{}", userAgent);
//
//        if (StringUtils.containsAny(userAgent, SPIDER, ANDROID, IOS, IPHONE)) {
//            Matcher matcher = LOCALE_PATTERN.matcher(userAgent);
//            if (matcher.find()) {
//                String lang = matcher.group(1);
//                setLocale(lang, request, response);
//            } else {
//                localeResolver.setLocale(request, response, Locale.US);
//            }
//            return;
//        }
//        setInitLocale(request, response);
//    }
//
//    /**
//     * @param code 对应messages配置的key.
//     * @return String
//     */
//    public static String getMessage(String code) {
//        return getMessage(code, null);
//    }
//
//    /**
//     * @param code 对应messages配置的key.
//     * @param args 数组参数.
//     * @return String
//     */
//    public static String getMessage(String code, Object[] args) {
//        return getMessage(code, args, "");
//    }
//
//    /**
//     * @param code           对应messages配置的key.
//     * @param args           数组参数.
//     * @param defaultMessage 没有设置key的时候的默认值.
//     * @return String
//     */
//    public static String getMessage(String code, Object[] args, String defaultMessage) {
//        Locale locale = LocaleContextHolder.getLocale();
//
//        log.debug("Message Locale tag:{},value:{}", locale.toLanguageTag(), locale.toString());
//
//        return messageSource.getMessage(code, args, defaultMessage, locale);
//    }
//
////    /**
////     * 获取指定前辍对应的所有的Message集合
////     *
////     * @param codePrefixes code前辍
////     * @return Map[Key, Value]
////     */
////    public static Map<String, String> getMessages(String... codePrefixes) {
////        Locale locale = LocaleContextHolder.getLocale();
////        return ((CustomResourceBundleMessageSource) messageSource).getMessages(locale, codePrefixes);
////    }
//
//    /**
//     * 从当前请求cookie中获取请求本地化(locale)语言设置值
//     *
//     * @param request @see #HttpServletRequest
//     * @return 返回Locale对象的toLanguageTag名称
//     */
//    public static String getLocale(HttpServletRequest request) {
//        return getLocale(request, getLocaleCookieName());
//    }
//
//    /**
//     * 从当前请求cookie中获取请求本地化(locale)语言设置值
//     *
//     * @param request    @see #HttpServletRequest
//     * @param cookieName locale cookie 名称
//     * @return 返回Locale对象的toString名称
//     */
//    public static String getLocale(HttpServletRequest request, String cookieName) {
//        Cookie localeCookie = WebUtils.getCookie(request, cookieName);
//        String lang = Locale.SIMPLIFIED_CHINESE.toString();
//        return localeCookie != null ? StringUtils.replaceChars(localeCookie.getValue(), DASH, UNDERLINE) : lang;
//    }
//
//    /**
//     * locale是否为繁体中文语言地区
//     *
//     * @param lang locale
//     * @return true|false
//     */
//    public static boolean isTraditionalChinese(String lang) {
//        return StringUtils.equalsAnyIgnoreCase(lang, TRADITIONAL_CHINESE);
//    }
//
//    /**
//     * 获取多语言cookie名称
//     *
//     * @return 多语言cookie名称
//     */
//    public static String getLocaleCookieName() {
//        if (localeResolver instanceof CookieLocaleResolver) {
//            return ((CookieLocaleResolver) localeResolver).getCookieName();
//        }
//        return StringUtils.EMPTY;
//    }
}
