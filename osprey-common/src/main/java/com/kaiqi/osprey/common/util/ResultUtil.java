package com.kaiqi.osprey.common.util;

import com.kaiqi.osprey.common.commons.ResponseResult;
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

    public static ResponseResult success(Object data) {
        return new ResponseResult("success", 0, "", data);
    }

    public static ResponseResult failure() {
        return failure(ErrorCodeEnum.UNKNOWN_ERROR);
    }

    public static ResponseResult failure(OspreyBizException e) {
        return failure(e.getErrorCode());
    }

    public static ResponseResult failure(ErrorCodeEnum errorCodeEnum) {
        return new ResponseResult("fail", errorCodeEnum.getCode(), errorCodeEnum.getMsg(), null);
    }

}
