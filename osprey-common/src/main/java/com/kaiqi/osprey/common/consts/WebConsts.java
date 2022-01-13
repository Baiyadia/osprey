package com.kaiqi.osprey.common.consts;

/**
 * Http请求相关常量
 *
 * @author wangs
 * @date 2017/12/09
 **/
public class WebConsts {

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
    /**
     * 设备ID
     */
    public static final String USER_AGENT = "user-agent";
}
