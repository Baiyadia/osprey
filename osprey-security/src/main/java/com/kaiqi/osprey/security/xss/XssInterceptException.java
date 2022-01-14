package com.kaiqi.osprey.security.xss;

/**
 * @author wangs
 * @date 2018-05-04
 */
public class XssInterceptException extends RuntimeException {
    public XssInterceptException() {
        this("xss check Intercepted");
    }

    /**
     * @param msg
     */
    public XssInterceptException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param t
     */
    public XssInterceptException(String msg, Throwable t) {
        super(msg, t);
    }
}
