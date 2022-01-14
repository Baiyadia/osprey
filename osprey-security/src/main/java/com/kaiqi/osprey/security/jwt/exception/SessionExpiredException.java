package com.kaiqi.osprey.security.jwt.exception;

/**
 * jwt token 过期异常
 *
 * @author wangs
 * @date 2017/12/29
 */
public class SessionExpiredException extends RuntimeException {
    public SessionExpiredException() {
        this("Session is Expired");
    }

    /**
     * @param msg
     */
    public SessionExpiredException(final String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param t
     */
    public SessionExpiredException(final String msg, final Throwable t) {
        super(msg, t);
    }
}
