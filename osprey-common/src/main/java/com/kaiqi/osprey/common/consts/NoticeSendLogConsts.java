package com.kaiqi.osprey.common.consts;

public class NoticeSendLogConsts {

    /**
     * 过期
     */
    public static final int STATUS_OVERDUE = -1;

    /**
     * 新创建
     */
    public static final int STATUS_NEW = 0;

    /**
     * 已经使用过
     */
    public static final int STATUS_USED = 1;

    /**
     * CHANNEL=手机
     */
    public static final int NOTICE_TYPE_PHONE = 1;

    /**
     * CHANNEL=邮箱
     */
    public static final int NOTICE_TYPE_EMAIL = 2;

    /**
     * 验证码发送相关
     */
    public static final int BUSINESS_CODE = 1;

    /**
     * 用户通知
     */
    public static final int BUSINESS_NOTIFICATION = 2;
}
