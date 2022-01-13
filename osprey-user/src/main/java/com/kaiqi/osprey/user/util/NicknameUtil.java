package com.kaiqi.osprey.user.util;

/**
 * @author wangs
 */
public class NicknameUtil {

    public static final String NICKNAME_PRE = "CSVIP";
    public static final String OPENID_PRE = "20";
    public static final String ANTI_PHISHING = "www.osprey.com";

    public static String getNickName() {
        return NICKNAME_PRE + VerificationCodeUtils.getRandNumber(4);
    }

    public static Long getOpenId() {
        return Long.parseLong(OPENID_PRE + VerificationCodeUtils.getRandNumber(6));
    }

    public static String antiPhishingCode() {
        return ANTI_PHISHING;
    }
}
