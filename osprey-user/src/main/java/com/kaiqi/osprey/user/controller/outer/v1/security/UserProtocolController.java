package com.kaiqi.osprey.user.controller.outer.v1.security;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.consts.SwitchConsts;
import com.kaiqi.osprey.common.session.model.SessionInfo;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.security.jwt.token.JwtTokenUtils;
import com.kaiqi.osprey.security.jwt.util.HttpSessionUtils;
import com.kaiqi.osprey.user.service.UserBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangs
 */
@RestController
@Slf4j
@RequestMapping("/v1/osprey/users/security/user-protocol")
public class UserProtocolController {

    @Autowired
    private UserBizService userBizService;

    /**
     * 更新用户协议
     *
     * @param isAgree 1同意 0拒绝
     * @param request http request
     * @return
     */
    @RequestMapping("/update")
    public ResponseResult protocolUpdate(@RequestParam("isAgree") Integer isAgree, HttpServletRequest request) {
        if (!isAgree.equals(SwitchConsts.ON)) {
            return ResultUtil.failure(ErrorCodeEnum.USER_REJECT_PROTOCOL);
        }
        long userId = HttpSessionUtils.getUserId(request);
        boolean result = userBizService.updateUserProtocol(userId);
        if (result) {
            SessionInfo session = JwtTokenUtils.getSession(userId);
            session.setProtocolAuthFlag(SwitchConsts.ON);
            JwtTokenUtils.updateSession(session);
            return ResultUtil.success();
        }
        return ResultUtil.failure(ErrorCodeEnum.USER_AGREE_PROTOCOL_UPDATE_ERROR);
    }

}
