package com.kaiqi.osprey.commons.enums;

public enum ErrorCodeEnum {

    /**
     * 参数校验异常
     */
    ARGUMENT_VALID_EXCEPTION(1000, 400, "参数校验异常"),
    /* error code of user */

    /**
     * 交易发送异常
     */
    TRADE_SEND_ERROR(9997, 400, "交易发送异常"),

    /**
     * 事务异常
     */
    TRANSACTION_ERROR(9998, 400, "事务异常"),

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(9999, 400, "未知错误");

    private int code;
    private int httpStatus;
    private String msg;

    ErrorCodeEnum(int code, int httpStatus, String msg) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    public static ErrorCodeEnum parseByCode(int code) {
        ErrorCodeEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ErrorCodeEnum errorCodeEnum = var1[var3];
            if (errorCodeEnum.getCode() == code) {
                return errorCodeEnum;
            }
        }

        throw new RuntimeException(String.valueOf(code));
    }

    public int getCode() {
        return this.code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMsg();
    }
}
