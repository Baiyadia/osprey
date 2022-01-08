package com.kaiqi.osprey.security.jwt.exception;

/**
 * @author wangs
 * @date 2018-05-04
 */
public class JwtTokenInvalidException extends RuntimeException {
    public JwtTokenInvalidException() {
        this("Token is invalid");
    }
    /**
     * @param msg
     */
    public JwtTokenInvalidException(final String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param t
     */
    public JwtTokenInvalidException(final String msg, final Throwable t) {
        super(msg, t);
    }
}
