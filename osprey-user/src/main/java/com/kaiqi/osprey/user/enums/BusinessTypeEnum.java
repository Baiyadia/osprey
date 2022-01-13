package com.kaiqi.osprey.user.enums;

import lombok.Getter;

import java.util.Calendar;
import java.util.Date;

/**
 * 类型enum
 *
 * @author wangs
 */
@Getter
public enum BusinessTypeEnum {

    /**
     * 用户注册类型
     * 1.发送手机验证码 2.发送邮箱验证码
     * <p>
     * 用户通过手机注册
     */
    USER_REGISTER_TYPE_MOBILE(1, "SMS_CS_REGISTER_VERIFICATION", null, 15),
    /**
     * 用户通过邮箱注册
     */
    USER_REGISTER_TYPE_EMAIL(2, null, "MAIL_CS_REGISTER_VERIFICATION", 15),
    /**
     * 用户登录
     */
    USER_LOGIN(3, null, "", -1),
    /**
     * 用户通过手机登录 重新发送验证码
     */
    USER_LOGIN_TYPE_MOBILE(4, "SMS_CS_NEW_DEVICE_LOGIN_VERIFICATION", null, 15),
    /**
     * 用户通过邮箱登录  发送验证码
     */
    USER_LOGIN_TYPE_EMAIL(5, null, "MAIL_CS_NEW_DEVICE_LOGIN_VERIFICATION", 15),
    /**
     * 用户登录时输入密码错误超限时 邮件提醒
     */
    USER_LOGIN_PASS_MAIL_ERROR_NOTIFY(6, null, "MAIL_CS_PASSWORD_ERROR", 30),
    /**
     * 登录新设备短信提醒 短信提醒
     */
    NEW_DEVICE_LOGIN_SMS_NOTIFY(7, "SMS_CS_LOGIN_SUCCESS", null, 30),
    /**
     * 登录新设备邮箱提醒 邮件提醒
     */
    NEW_DEVICE_LOGIN_EMAIL_NOTIFY(8, null, "MAIL_CS_LOGIN_SUCCESS", 30),
    /**
     * 用户重置密码 发送邮箱
     */
    USER_RESET_PASSWORD_EMAIL(9, null, "MAIL_CS_RESET_PASSWORD", 15),
    /**
     * 用户重置密码 发送短信
     */
    USER_RESET_PASSWORD_MOBILE(10, "SMS_CS_RESET_PASSWORD", null, 15),

    /**
     * 用户登录密码错误 短信通知
     */
    USER_LOGIN_PASS_SMS_ERROR_NOTIFY(11, "SMS_CS_PASSWORD_ERROR", null, 30),

    /**
     * 用户更改手机号   "SMS_CS_CHANGE_MOBILE_SEND_NEW
     */
    USER_CHANGE_MOBILE(12, "SMS_CS_CHANGE_MOBILE_SEND_NEW", null, 30),
    /**
     * 用户更改邮箱  "MAIL_CS_CHANGE_EMAIL_SEND_NEW
     */
    USER_CHANGE_EMAIL(13, null, "MAIL_CS_CHANGE_EMAIL_SEND_NEW", 30),

    /**
     * 用户重置图形验证码发送短信 "SMS_CS_RESET_GRAPH_MOBILE
     */
    USER_RESET_GRAPH_MOBILE(14, "SMS_CS_RESET_GRAPH_MOBILE", null, 30),
    /**
     * 用户重置图形验证码发送邮箱 "MAIL_CS_RESET_GRAPH_EMAIL
     */
    USER_RESET_GRAPH_EMAIL(15, null, "MAIL_CS_RESET_GRAPH_EMAIL", 30),

    /**
     * 用户更改手机号   "SMS_CS_CHANGE_MOBILE
     */
    USER_CHANGE_MOBILE_SEND_OLD(16, "SMS_CS_CHANGE_MOBILE", null, 30),
    /**
     * 用户更改邮箱  "MAIL_CS_CHANGE_EMAIL
     */
    USER_CHANGE_EMAIL_SEND_OLD(17, null, "MAIL_CS_CHANGE_EMAIL", 30),

    ;

    private Integer type;
    private String mobileCode;
    private String emailCode;
    /**
     * 过期时间
     */
    private int expireTime;

    BusinessTypeEnum(Integer type, String mobileCode, String emailCode, int expireTime) {
        this.type = type;
        this.mobileCode = mobileCode;
        this.emailCode = emailCode;
        this.expireTime = expireTime;
    }

    public static BusinessTypeEnum getBehavior(Integer behavior) {
        if (behavior <= 0) {
            return null;
        }
        for (BusinessTypeEnum businessTypeEnum : BusinessTypeEnum.values()) {
            if (businessTypeEnum.getType().equals(behavior)) {
                return businessTypeEnum;
            }
        }
        return null;
    }

    public Date getExpireTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, this.expireTime);
        return calendar.getTime();
    }
}
