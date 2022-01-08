package com.kaiqi.osprey.security.jwt.enums;

import com.kaiqi.osprey.common.commons.enums.ErrorCode;

/**
 * @author wangs
 * @date 2017/12/29
 */
public enum JwtSecurityErrorCode implements ErrorCode {
    TOKEN_EXPIRATION(401, "error.code.security.800"),
    TOKEN_INVALID(401, "error.code.security.801"),
    TOKEN_FORBIDDEN(403, "error.code.security.803"),
    TOKEN_ABNORMAL_ACCESS(401, "error.code.security.804"),
    TOKEN_BIZ_FROZEN(403, "error.code.security.805"),
    TOKEN_UNAUTHORIZED(403, "error.code.security.806");

    private final int code;
    private final String message;

    JwtSecurityErrorCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
//        return LocaleUtils.getMessage(this.message);
        return this.message;
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMsg();
    }
}
