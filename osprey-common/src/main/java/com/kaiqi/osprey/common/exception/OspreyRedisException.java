package com.kaiqi.osprey.common.exception;

import lombok.Getter;

@Getter
public class OspreyRedisException extends RuntimeException {

    public OspreyRedisException(String message) {
        super(message);
    }

    public OspreyRedisException(Throwable e) {
        super(e);
    }

    public OspreyRedisException(String message, Throwable cause) {
        super(message, cause);
    }
}
