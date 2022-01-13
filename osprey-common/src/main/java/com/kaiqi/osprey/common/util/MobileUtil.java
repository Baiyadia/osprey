package com.kaiqi.osprey.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MobileUtil {

    /**
     * 手机号加密
     *
     * @param phoneNo 手机号码
     * @return
     */
    public static String getScreenPhoneNumber(String phoneNo) {
        return MobileUtil.getScreenPhoneNumber(3, phoneNo, 4);
    }

    /**
     * 邮箱加密
     *
     * @param email youxiang
     * @return
     */
    public static String getScreenEmailNumber(String email) {
        return MobileUtil.getScreenEmailNumber(3, email, 4);
    }

    public static void main(String[] args) {
        System.out.println(getScreenEmailNumber("602884291@qq.com"));
    }

    /**
     * 获取手机号后四位
     *
     * @param mobile
     * @return
     */
    public static String getMobileSuffix(String mobile, int len) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }

        if (mobile.length() < len) {
            return mobile;
        }

        return mobile.substring(mobile.length() - len);
    }

    /**
     * 手机号加密
     *
     * @param prefixLength 前缀长度
     * @param phoneNo      手机号码
     * @param suffixLength 后缀长度
     * @return
     */
    public static String getScreenPhoneNumber(int prefixLength, String phoneNo, int suffixLength) {
        String result = "";
        if (StringUtils.isNotBlank(phoneNo)) {
            int length = phoneNo.length();
            String prefix = phoneNo.substring(0, prefixLength);
            int i = length - (prefixLength + suffixLength);
            StringBuilder stringBuilder = new StringBuilder();

            Stream.iterate("*", item -> "*").limit(i).forEach(event -> {
                stringBuilder.append(event);
            });
            String body = stringBuilder.toString();
            String suffix = phoneNo.substring(prefixLength + suffixLength, length);
            result = prefix + body + suffix;
        }
        return result;
    }

    /**
     * 手机号加密
     *
     * @param prefixLength 前缀长度
     * @param phoneNo      手机号码
     * @param suffixLength 后缀长度
     * @return
     */
    public static String getScreenEmailNumber(int prefixLength, String phoneNo, int suffixLength) {
        String result = "";
        if (StringUtils.isNotBlank(phoneNo)) {
            int length = phoneNo.length();
            String prefix = phoneNo.substring(0, prefixLength);
            int i = 4;
            StringBuilder stringBuilder = new StringBuilder();

            Stream.iterate("*", item -> "*").limit(i).forEach(event -> {
                stringBuilder.append(event);
            });
            String body = stringBuilder.toString();
            String suffix = phoneNo.substring(prefixLength + suffixLength, length);
            result = prefix + body + suffix;
        }
        return result;
    }

    /**
     * 根据国家代码和手机号 判断手机号是否有效
     *
     * @param phoneNumber
     * @param countryCode
     * @return true|false
     */
    public static boolean checkPhoneNumber(String phoneNumber, Integer countryCode) {
        if (StringUtils.isEmpty(phoneNumber) || ObjectUtils.isEmpty(countryCode)) {
            return false;
        }
        if ("86".equals(countryCode)) {
            boolean isChinaPhone = MobileUtil.isChinaPhone(phoneNumber);
            if (!isChinaPhone) {
                return false;
            }
        }
//        PhoneNumber pn = new PhoneNumber();
//        pn.setCountryCode(ccode);
//        pn.setNationalNumber(phone);
//        return PhoneNumberUtil.getInstance().isValidNumber(pn);
        return StringUtil.isMobile(phoneNumber);
    }

    public static boolean isDigit(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }

    /**
     * 中国电信号段 133、149、153、173、177、180、181、189、199
     * 中国联通号段 130、131、132、145、155、156、166、175、176、185、186
     * 中国移动号段 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     * 其他号段
     * 14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
     * 虚拟运营商
     * 电信：1700、1701、1702
     * 移动：1703、1705、1706
     * 联通：1704、1707、1708、1709、171
     * 卫星通信：1349
     *
     * @param phone
     * @return
     */
    private static boolean isChinaPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }
}
