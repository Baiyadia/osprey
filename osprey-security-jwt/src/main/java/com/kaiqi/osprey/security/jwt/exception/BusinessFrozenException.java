package com.kaiqi.osprey.security.jwt.exception;

/**
 * @author newex-team
 * @date 2018-05-04
 */
public class BusinessFrozenException extends RuntimeException {
    public BusinessFrozenException() {
        this("Business is frozen");
    }

    /**
     * @param msg
     */
    public BusinessFrozenException(final String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param t
     */
    public BusinessFrozenException(final String msg, final Throwable t) {
        super(msg, t);
    }
}
