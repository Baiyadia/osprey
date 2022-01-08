package com.kaiqi.osprey.security.jwt.aop;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.consts.AppEnvConsts;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.security.jwt.enums.JwtSecurityErrorCode;
import com.kaiqi.osprey.security.jwt.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局安全相关异常处理器
 *
 * @author wangs
 * @date 2017/12/09
 **/
@Slf4j
@Order(1)
@RestControllerAdvice
@ConditionalOnProperty(
        prefix = "newex.security.jwt.exception-advice", name = "enabled", matchIfMissing = true
)
public class JwtSecurityExceptionAdvice {

    /**
     * 没有找到token,未授权
     */
    @Order(1)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtTokenNotFoundException.class)
    public ResponseResult handleJwtTokenNotFoundException(JwtTokenNotFoundException e,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) {
        log.warn("JwtTokenNotFoundException App:{},Url:{},Message:{}",
                AppEnvConsts.APP_NAME_ITEM, request.getRequestURL(), e.getMessage());
        return ResultUtil.failure(JwtSecurityErrorCode.TOKEN_UNAUTHORIZED);
    }

    /**
     * token过期
     */
    @Order(2)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseResult handleJwtTokenExpiredException(JwtTokenExpiredException e,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response) {
        log.warn("JwtTokenExpiredException App:{},Url:{},Message:{}",
                AppEnvConsts.APP_NAME_ITEM, request.getRequestURL(), e.getMessage());
        return ResultUtil.failure(JwtSecurityErrorCode.TOKEN_EXPIRATION);
    }

    /**
     * 登录session过期
     */
    @Order(3)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SessionExpiredException.class)
    public ResponseResult handleSessionExpiredException(SessionExpiredException e,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        log.warn("SessionExpiredException App:{},Url:{},Message:{}",
                AppEnvConsts.APP_NAME_ITEM, request.getRequestURL(), e.getMessage());
        return ResultUtil.failure(JwtSecurityErrorCode.TOKEN_EXPIRATION);
    }

    /**
     * token验证失败
     */
    @Order(4)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtTokenInvalidException.class)
    public ResponseResult handleJwtTokenInvalidException(JwtTokenInvalidException e,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response) {
        log.warn("JwtTokenInvalidException App:{},Url:{},Message:{}",
                AppEnvConsts.APP_NAME_ITEM, request.getRequestURL(), e.getMessage());
        return ResultUtil.failure(JwtSecurityErrorCode.TOKEN_INVALID);
    }

    /**
     * token被禁用
     */
    @Order(5)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(JwtTokenForbiddenException.class)
    public ResponseResult handleJwtTokenForbiddenException(JwtTokenForbiddenException e,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response) {
        log.warn("JwtTokenForbiddenException App:{},Url:{},Message:{}",
                AppEnvConsts.APP_NAME_ITEM, request.getRequestURL(), e.getMessage());
        return ResultUtil.failure(JwtSecurityErrorCode.TOKEN_FORBIDDEN);
    }

    /**
     * 非正常访问
     */
    @Order(6)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AbnormalAccessException.class)
    public ResponseResult handleAbnormalAccessException(AbnormalAccessException e,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        log.warn("AbnormalAccessException App:{},Url:{},Message:{}",
                AppEnvConsts.APP_NAME_ITEM, request.getRequestURL(), e.getMessage());
        return ResultUtil.failure(JwtSecurityErrorCode.TOKEN_ABNORMAL_ACCESS);
    }

    /**
     * 业务已经冻结
     */
    @Order(7)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BusinessFrozenException.class)
    public ResponseResult handleBusinessFrozenException(BusinessFrozenException e,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        log.warn("BusinessFrozenException App:{},Url:{},Message:{}",
                AppEnvConsts.APP_NAME_ITEM, request.getRequestURL(), e.getMessage());
        return ResultUtil.failure(JwtSecurityErrorCode.TOKEN_BIZ_FROZEN);
    }
}