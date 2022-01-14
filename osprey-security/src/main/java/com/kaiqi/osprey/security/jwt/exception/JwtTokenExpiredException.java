package com.kaiqi.osprey.security.jwt.exception;

/**
 * jwt token 过期异常
 *
 * @author wangs
 * @date 2017/12/29
 */
public class JwtTokenExpiredException extends RuntimeException {
    public JwtTokenExpiredException() {
        this("Token is Expired");
    }
    /**
     * @param msg
     */
    public JwtTokenExpiredException(final String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param t
     */
    public JwtTokenExpiredException(final String msg, final Throwable t) {
        super(msg, t);
    }
}
