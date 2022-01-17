package com.kaiqi.osprey.user.controller.outer.v1.security;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.entity.WebInfo;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.common.util.WebUtil;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.security.jwt.token.JwtTokenProvider;
import com.kaiqi.osprey.security.jwt.util.JwtTokenUtils;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.domain.UserLoginRecord;
import com.kaiqi.osprey.service.service.UserLoginRecordService;
import com.kaiqi.osprey.service.service.UserService;
import com.kaiqi.osprey.user.model.AccessTokenResVO;
import com.kaiqi.osprey.user.model.UserProfileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author wangs
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/osprey/users/security/oauth")
public class RefreshTokenController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserLoginRecordService userLoginRecordService;
    @Autowired
    private UserService userService;

    /**
     * 刷新token
     */
    @GetMapping(value = "/refresh")
    public ResponseResult refresh(HttpServletRequest request) {
        try {
            /**
             * token验证
             */
            String token = request.getHeader(jwtTokenProvider.getJwtConfig().getRequestHeaderName());
            JwtUserDetails user = JwtTokenUtils.getCurrentLoginUserFromToken(request);
            if (!jwtTokenProvider.validateToken(token, user)) {
                return ResultUtil.failure(ErrorCodeEnum.REFRESH_TOKEN_FAILURE);
            }
            /**
             * 生成新token
             */
            String refreshedToken = jwtTokenProvider.refreshToken(token);
            User dbUser = userService.getById(user.getUserId());
            UserProfileVO profile = UserProfileVO.builder()
                                                 .openId(user.getOpenId())
                                                 .email(user.getEmail())
                                                 .mobile(user.getMobile())
                                                 .protocolAuthFlag(user.getProtocolAuthFlag())
                                                 .registerType(dbUser.getRegisterType())
//                                                     .subNotifyFlag(user.getSubNotifyFlag())
                                                 .tradePasswordFlag(user.getTradePasswordSetFlag())
                                                 .username(user.getUsername())
                                                 .tradePassUpdate(dbUser.getTradePassUpdate())
//                                                     .isAddressVisible(setting.getIsAddressVisible())
                                                 .contactCount(dbUser.getContactCount())
                                                 .build();
            AccessTokenResVO newToken = AccessTokenResVO.builder()
                                                        .accessToken(token)
                                                        .refreshToken(refreshedToken)
                                                        .profile(profile)
                                                        .build();
            /**
             * 每次获取token都要入库
             */
            WebInfo webInfo = WebUtil.getWebInfo(request);
            UserLoginRecord loginRecord = UserLoginRecord.builder()
                                                         .userId(user.getUserId())
                                                         .token(token)
                                                         .deviceId(webInfo.getDeviceId())
                                                         .ipAddress(webInfo.getIpAddress())
                                                         .region("")
                                                         .lastLoginIp(webInfo.getIpAddress())
                                                         .userAgent(webInfo.getUserAgent())
                                                         .createTime(new Date())
                                                         .updateTime(new Date())
                                                         .build();
            userLoginRecordService.add(loginRecord);
            /**
             * 清除旧token session 关系，创建新关系
             */
            JwtTokenUtils.clearSession(user.getUserId());
            JwtTokenUtils.createSession(user, newToken.getRefreshToken());
            return ResultUtil.success(newToken);
        } catch (Exception e) {
            log.error("refreshToken fail {}", e);
            return ResultUtil.failure(ErrorCodeEnum.REFRESH_TOKEN_FAILURE);
        }
    }
}
