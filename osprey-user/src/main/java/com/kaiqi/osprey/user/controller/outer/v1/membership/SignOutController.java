package com.kaiqi.osprey.user.controller.outer.v1.membership;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.security.jwt.util.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户注销
 *
 * @author wangs
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/osprey/users/membership/sign-out")
@Api(value = "sign-out", tags = "用户注销")
public class SignOutController {

    /**
     * 用户注销
     */
    @ApiOperation("用户注销")
    @GetMapping(value = "")
    public ResponseResult logout(HttpServletRequest request) {
        try {
            JwtTokenUtils.clearSession(request);
            return ResultUtil.success();
        } catch (Exception e) {
            log.error("unknown error = {}", e);
            return ResultUtil.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }
}
