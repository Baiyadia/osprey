package com.kaiqi.osprey.exception;

import com.kaiqi.osprey.commons.enums.ErrorCodeEnum;
import lombok.Getter;

@Getter
public class OspreyBizException extends RuntimeException {

    private int code;
    private ErrorCodeEnum errorCode;

    public OspreyBizException() {
        super();
    }

    public OspreyBizException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.code = errorCodeEnum.getCode();
        this.errorCode = errorCodeEnum;
    }

    public OspreyBizException(ErrorCodeEnum errorCodeEnum, Throwable cause) {
        super(errorCodeEnum.getMsg(), cause);
    }

    public static void validateSqlResultWithValue(int affectedRows, int assetRows) {
        validateSqlResult(affectedRows, assetRows, ErrorCodeEnum.TRANSACTION_ERROR);
    }

    public static void validateSqlResult(int affectedRows) {
        validateSqlResult(affectedRows, affectedRows, ErrorCodeEnum.TRANSACTION_ERROR);
    }

    public static void validateSqlResult(int affectedRows, int assetRows, ErrorCodeEnum errorCode) {
        if (affectedRows <= 0 || affectedRows != assetRows) {
            throw new OspreyBizException(errorCode);
        }
    }
}
