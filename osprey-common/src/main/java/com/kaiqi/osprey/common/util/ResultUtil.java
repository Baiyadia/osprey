package com.kaiqi.osprey.common.util;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCode;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.exception.OspreyBizException;

/**
 * @author wangs
 * @title: ResultUtil
 * @package com.kaiqi.osprey.util
 * @description: TODO
 * @date 2019-06-01 22:15
 */
public class ResultUtil {

    public static ResponseResult failure() {
        return failure(ErrorCodeEnum.UNKNOWN_ERROR);
    }

    public static ResponseResult failure(OspreyBizException e) {
        return failure(e.getErrorCode());
    }

    public static ResponseResult failure(ErrorCode errorCode) {
        return new ResponseResult(errorCode.getCode(), errorCode.getMsg(), null);
    }

    /**
     * @param <T>
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    /**
     * @param data
     * @param <T>
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> success(T data) {
        return build(0, "success", data);
    }

    /**
     * @param errorCode
     * @param data
     * @param <T>
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> failure(ErrorCode errorCode, T data) {
        return build(errorCode.getCode(), errorCode.getMsg(), data);
    }

    /**
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> build(int code, String msg, T data) {
        return new ResponseResult<>(code, msg, data);
    }

}
