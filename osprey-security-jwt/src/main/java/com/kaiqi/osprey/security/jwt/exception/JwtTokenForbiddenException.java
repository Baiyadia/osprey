package com.kaiqi.osprey.security.jwt.exception;

/**
 * @author newex-team
 * @date 2018-05-04
 */
public class JwtTokenForbiddenException extends RuntimeException {
    public JwtTokenForbiddenException() {
        this("User is forbidden");
    }

    /**
     * @param msg
     */
    public JwtTokenForbiddenException(final String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param t
     */
    public JwtTokenForbiddenException(final String msg, final Throwable t) {
        super(msg, t);
    }
}
