package com.kaiqi.osprey.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangs
 * @date 2017/12/09
 **/
@Slf4j
public class StringUtil {

    private static final Pattern IP_PATTERN = Pattern.compile(
            "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|"
                    + "(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|"
                    + "([1-9]\\d)|(\\d))");

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^([a-zA-Z0-9_\\-\\.\\+]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))"
                    + "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

    private static final Pattern MOBILE_PATTERN = Pattern.compile(
            "^((176)|(13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

    /**
     * ip校验
     *
     * @param s
     * @return 格式是否正确
     */
    public static boolean isIpAddress(String s) {
        Matcher m = IP_PATTERN.matcher(s);
        return m.matches();
    }

    /**
     * email校验
     *
     * @param email
     * @return 格式是否正确
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 手机号校验
     *
     * @param mobiles
     * @return 格式是否正确
     */
    public static boolean isMobile(String mobiles) {
        if (StringUtils.isEmpty(mobiles)) {
            return false;
        }
        return MOBILE_PATTERN.matcher(mobiles).matches();
    }

    /**
     * 按位取值，返回指定位的值<br>
     *
     * @param value
     * @param bit   指定位序号，从1开始
     * @return
     */
    public static int getBinaryIndex(Long value, int bit) {
        return (int) (value >> (bit - 1) & 1);
    }

    /**
     * 是否为允许的文件扩展名
     *
     * @param fileName 文件名
     * @return true|false
     */
    public static boolean isAllowedFileExtension(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        String[] allowFileExts = { ".jpg", ".jpeg", ".png", ".pdf", ".gif", ".bmp" };
        return StringUtils.endsWithAny(fileName.toLowerCase(), allowFileExts);
    }

    /**
     * 是否为不允许的文件扩展名
     *
     * @param fileName 文件名
     * @return true|false
     */
    public static boolean isNotAllowedFileExtension(String fileName) {
        return !isAllowedFileExtension(fileName);
    }

    /**
     * @param str1
     * @param str2
     * @return
     */
    public static boolean notEqualsIgnoreCase(String str1, String str2) {
        return !StringUtils.equalsIgnoreCase(str1, str2);
    }

    /**
     * @param str1
     * @param str2
     * @return
     */
    public static boolean notEquals(String str1, String str2) {
        return !StringUtils.equals(str1, str2);
    }

    /**
     * @param str1
     * @param str2
     * @return
     */
    public static boolean notEqualsIgnoreCaseWithTrim(String str1, String str2) {
        return notEqualsIgnoreCase(StringUtils.trim(str1), StringUtils.trim(str2));
    }

    /**
     * @param str1
     * @param str2
     * @return
     */
    public static boolean notEqualsWithTrim(String str1, String str2) {
        return notEquals(StringUtils.trim(str1), StringUtils.trim(str2));
    }

    /**
     * 手机加*
     *
     * @param
     * @return
     */
    public static String getStarMobile(String realMobile) {
        return realMobile;
    }

    /**
     * 邮箱加*
     *
     * @param
     * @return
     */
    public static String getStarEmail(String realMobile) {
        return realMobile;
    }
}
