package com.kaiqi.osprey.user.controller.inner.v1;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.util.BeanCopyUtil;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.service.UserService;
import com.kaiqi.osprey.user.model.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangs
 */
@Slf4j
@RestController
@RequestMapping("/v1/osprey/inner/users")
@Api(value = "user", tags = "对内用户服务")
public class InnerUserController {

    @Autowired
    private UserService userService;

    @ApiOperation("uid查用户信息")
    @RequestMapping("/getByUserIds")
    public ResponseResult<List<UserInfoVO>> getUserByIds(@RequestParam("userIds") List<Long> userIds) {
        try {
            List<User> users = userService.getByUserIds(userIds);
            return ResultUtil.success(BeanCopyUtil.copyListProperties(users, UserInfoVO::new));
        } catch (Exception e) {
            log.error("getUserByIds error", e);
            return ResultUtil.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @ApiOperation("openId查用户信息")
    @RequestMapping("/getByOpenIds")
    ResponseResult<List<UserInfoVO>> getUserByOpenIds(@RequestParam("openIds") List<String> openIds) {
        try {
            List<User> users = userService.getByOpenIds(openIds);
            return ResultUtil.success(BeanCopyUtil.copyListProperties(users, UserInfoVO::new));
        } catch (Exception e) {
            log.error("getUserByOpenIds error", e);
            return ResultUtil.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }
}
