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
}
