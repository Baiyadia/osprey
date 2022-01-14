package com.kaiqi.osprey.security.csrf;

/**
 * @author wangs
 * @date 2018-05-04
 */
public class CsrfInterceptException extends RuntimeException {
    public CsrfInterceptException() {
        this("csrf check Intercepted");
    }

    /**
     * @param msg
     */
    public CsrfInterceptException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param t
     */
    public CsrfInterceptException(String msg, Throwable t) {
        super(msg, t);
    }
}
