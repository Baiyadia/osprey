package com.kaiqi.osprey.security.jwt.model;

public class JwtConsts {
    /**
     * 正常登录状用户
     */
    public static final int STATUS_NORMAL = 0;
    /**
     * 不存在的用户
     */
    public static final int STATUS_NOT_EXISTS = -1;
    /**
     * 需要二次验证
     */
    public static final int STATUS_TWO_FACTOR = 2;
    /**
     * httpRequest中存储的当前登录用户对象属性名称
     */
    public static final String JWT_CURRENT_USER = "jwt_current_user";
    /**
     * httpRequest中存储的当前登录用户ID属性名称
     */
    public static final String JWT_CURRENT_USER_ID = "jwt_current_user_id";
    /**
     * cookie中存储token的属性名
     */
    public static final String TOKEN = "token";
    /**
     * http header中存储设备唯一编号的属性名
     */
    public static final String X_DEV_ID = "x-dev-id";
}
