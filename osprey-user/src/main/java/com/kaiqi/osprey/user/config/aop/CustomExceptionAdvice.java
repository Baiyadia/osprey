package com.kaiqi.osprey.user.config.aop;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wangs
 */
@Slf4j
@ResponseBody
@RestControllerAdvice
public class CustomExceptionAdvice {

    /**
     * 200 -
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Throwable.class)
    public ResponseResult handleNewExBizException(Throwable e) {
        log.error("throwable error console {}", e);
        if (e instanceof HttpMessageNotWritableException) {
            return ResultUtil.failure(ErrorCodeEnum.REQUEST_DATA_FORMAT_ERROR);
        }
        return ResultUtil.failure(ErrorCodeEnum.UNKNOWN_ERROR);
    }
}
