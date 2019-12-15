package com.kaiqi.osprey.common.commons.enums;

public enum ErrorCodeEnum {
    /* error code of user */
    /**
     * 用户已经登录
     */
    USER_ALREADY_LANDED_ERROR(1001, 400, "用户已经登录"),
    /**
     * 用户名或者密码错误
     */
    LOGIN_LOGINNAME_OR_PASSWORD_ERROR(1002, 400, "用户名或者密码错误"),
    /**
     * 用户输入手机格式错误
     */
    USER_MOBILE_FORMAT_ERROR(1003, 400, "用户输入手机格式错误"),

    /**
     * 注册国家不支持
     */
    USER_REGISTER_COUNTRY_UNSUPPORT(1004, 400, "注册国家不支持"),
    /**
     * 邮箱或手机号已经被使用
     */
    REGISTER_USER_EXIST(1005, 400, "邮箱或手机号已经被使用"),

    /**
     * 创建用户失败
     */
    REGISTER_SAVE_FAILED(1006, 400, "创建用户失败"),
    /**
     * 短信验证码错误
     */
    SMS_CODE_VERIFY_ERROR(1007, 400, "短信验证码错误"),
    /**
     * 邮箱验证码错误
     */
    EMAIL_CODE_VERIFY_ERROR(1008, 400, "邮箱验证码错误"),
    /**
     * 发送短信验证码失败
     */
    SMS_CODE_SEND_FAIL(1009, 400, "发送短信验证码失败"),
    /**
     * 业务不支持
     */
    BUSINESS_SEND_CODE_UNSUPPORT(1010, 400, "业务不支持"),
    /**
     * 邮箱发送失败
     */
    EMAIL_CODE_SEND_FAIL(1011, 400, "邮箱发送失败"),
    /**
     * 用户拒绝同意协议
     */
    USER_REJECT_PROTOCOL(1012, 400, "用户拒绝同意协议"),
    /**
     * 用户同意协议失败
     */
    USER_AGREE_PROTOCOL_UPDATE_ERROR(1013, 400, "用户同意协议失败"),
    /**
     * 用户交易密码已经设置
     */
    USER_TRADE_PASSWORD_ALREADY_SET(1014, 400, "用户交易密码已经设置"),
    /**
     * 用户未登录
     */
    USER_NO_LOGIN(1015, 400, "用户未登录"),
    /**
     * 设置交易密码错误
     */
    SET_TRADE_PASSWORD_ERROR(1016, 400, "设置交易密码错误"),
    /**
     * 用户新设备登录验证
     */
    USER_NEW_DEVICE_LOGIN_VERIFY(1018, 400, "用户新设备登录验证"),
    /**
     * 新设备登录验证码错误
     */
    USER_NEW_DEVICE_LOGIN_CODE_ERROR(1020, 400, "新设备登录验证码错误"),
    /**
     * 查询用户信息失败
     */
    USER_CENTER_GET_PROFILE_ERROR(1022, 400, "查询用户信息失败"),
    /**
     * 用户提交反馈失败
     */
    USER_SUBMIT_FEEDBACK_FAILURE(1023, 400, "用户提交反馈失败"),
    /**
     * 版本发布记录为空
     */
    VERSION_PUBLISH_RECORD_EMPTY(1024, 400, "版本发布记录为空"),
    /**
     * 用户设置昵称失败
     */
    USER_SET_NICKNAME_ERROR(1025, 400, "用户设置昵称失败"),
    /**
     * 用户联系人不存在
     */
    USER_CONTACT_ADDRESS_NO_EXIST(1026, 400, "用户联系人不存在"),
    /**
     * 用户联系人地址更新失败
     */
    USER_CONTACT_ADDRESS_UPDATE_ERROR(1027, 400, "用户联系人地址更新失败"),
    /**
     * 用户联系人地址删除失败
     */
    USER_CONTACT_ADDRESS_DELETE_ERROR(1028, 400, "用户联系人地址删除失败"),
    /**
     * 交易密码输入错误
     */
    TRADE_PASSWORD_CHECK_ERROR(1029, 400, "交易密码输入错误"),
    /**
     * 设置钱包path失败
     */
    SET_WALLET_PATH_ERROR(1030, 400, "设置钱包path失败"),
    /**
     * 刷新token失败
     */
    REFRESH_TOKEN_FAILURE(1031, 400, "刷新token失败"),
    /**
     * 验证码错误
     */
    COMMON_VERIFICATION_CODE_ERROR(1033, 400, "验证码错误"),
    /**
     * 缺少必要参数
     */
    COMMON_ILLEGALITY_ACCESS(1034, 400, "缺少必要参数"),
    /**
     * 用户不存在
     */
    COMMON_USER_NOT_EXIST(1035, 400, "用户不存在"),
    /**
     * 参数为空
     */
    COMMON_PARAMS_EMPTY(1036, 400, "参数为空"),
    /**
     * 注册密码不相等
     */
    REGISTER_PASSWORD_NOT_EQUAL(1037, 400, "注册密码不相等"),
    /**
     * 密码设置失败
     */
    PWD_SETTING_ERROR(1038, 400, "密码设置失败"),
    /**
     * 需要图片验证码
     */
    NEED_IMAGE_VERIFICATION(1039, 400, "需要图片验证码"),
    /**
     * 注册密码太简单
     */
    REGISTER_PASSWORD_SIMPLE(1040, 400, "注册密码太简单"),
    /**
     * 图片验证码错误
     */
    IMAGE_CODE_CHECK_ERROR(1041, 400, "图片验证码错误"),
    /**
     * email格式错误
     */
    EMAIL_FORMAT_ERROR(1042, 400, "email格式错误"),
    /**
     * 操作超时
     */
    COMMON_OPERATE_TIMEOUT(1043, 400, "操作超时"),
    USER_SUBMIT_FEEDBACK_LIMIT(1044, 400, ""),

    REQUEST_DATA_FORMAT_ERROR(1045, 400, "请求数据解析失败"),
    /**
     * 手机号已绑定
     */
    MOBILE_HAS_BIND(1046, 400, "手机号已绑定"),
    /**
     * 与原手机号相同
     */
    MOBILE_SAME_OLD(1047, 400, "与原手机号相同"),
    /**
     * 登录密码错误
     */
    LOGIN_PASSWORD_ERROR(1048, 400, "登录密码错误"),

    /**
     * 邮箱已绑定
     */
    EMAIL_HAS_BIND(1049, 400, "邮箱已绑定"),
    /**
     * 与原邮箱相同
     */
    EMAIL_SAME_OLD(1050, 400, "与原邮箱相同"),

    /**
     * 邀请码未查到
     */
    USER_INVITE_CODE_NOT_FOUND(1055, 400, "邀请码未查到"),

    /**
     * 用户好友设置地址不可见
     */
    USER_FRIEND_SET_CANT_VISIBLE(1056, 400, "用户好友设置地址不可见"),
    /**
     * 用户未绑定手机
     */
    USER_MOBILE_NOT_BIND(1057, 400, "用户未绑定手机"),
    /**
     * 用户未绑定邮箱
     */
    USER_EMAIL_NOT_BIND(1058, 400, "用户未绑定邮箱"),
    /**
     * 旧手机验证码校验失败
     */
    USER_OLD_SMS_CODE_VERIFY_ERROR(1059, 400, "旧手机验证码校验失败"),
    /**
     * 旧邮箱验证码校验失败
     */
    USER_OLD_EMAIL_CODE_VERIFY_ERROR(1060, 400, "旧邮箱验证码校验失败"),

    /**
     * 参数校验异常
     */
    ARGUMENT_VALID_EXCEPTION(1000, 400, "参数校验异常"),
    /* error code of user */

    /**
     * 交易发送异常
     */
    TRADE_SEND_ERROR(9997, 400, "交易发送异常"),

    /**
     * 事务异常
     */
    TRANSACTION_ERROR(9998, 400, "事务异常"),

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(9999, 400, "未知错误");

    private int code;
    private int httpStatus;
    private String msg;

    ErrorCodeEnum(int code, int httpStatus, String msg) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    public static ErrorCodeEnum parseByCode(int code) {
        ErrorCodeEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ErrorCodeEnum errorCodeEnum = var1[var3];
            if (errorCodeEnum.getCode() == code) {
                return errorCodeEnum;
            }
        }

        throw new RuntimeException(String.valueOf(code));
    }

    public int getCode() {
        return this.code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMsg();
    }
}
