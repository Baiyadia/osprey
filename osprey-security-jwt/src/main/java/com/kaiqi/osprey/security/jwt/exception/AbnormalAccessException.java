package com.kaiqi.osprey.security.jwt.exception;

/**
 * @author newex-team
 * @date 2018-05-04
 */
public class AbnormalAccessException extends RuntimeException {
    public AbnormalAccessException() {
        this("abnormal access,current request's ip or device id is changed");
    }
    /**
     * @param msg
     */
    public AbnormalAccessException(final String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param t
     */
    public AbnormalAccessException(final String msg, final Throwable t) {
        super(msg, t);
    }
}
