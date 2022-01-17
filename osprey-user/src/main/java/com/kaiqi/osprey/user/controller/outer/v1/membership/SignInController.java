package com.kaiqi.osprey.user.controller.outer.v1.membership;

import com.google.common.collect.Maps;
import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.entity.WebInfo;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.consts.NoticeSendLogConsts;
import com.kaiqi.osprey.common.util.LocaleUtil;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.common.util.StringUtil;
import com.kaiqi.osprey.common.util.WebUtil;
import com.kaiqi.osprey.security.jwt.util.HttpSessionUtils;
import com.kaiqi.osprey.security.jwt.util.JwtTokenUtils;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.domain.UserLoginRecord;
import com.kaiqi.osprey.service.domain.UserSettings;
import com.kaiqi.osprey.service.service.UserLoginRecordService;
import com.kaiqi.osprey.service.service.UserService;
import com.kaiqi.osprey.service.service.UserSettingsService;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.enums.UserStatusEnum;
import com.kaiqi.osprey.user.model.AccessTokenResVO;
import com.kaiqi.osprey.user.model.DeviceVerifyResVO;
import com.kaiqi.osprey.user.model.LoginReqVO;
import com.kaiqi.osprey.user.service.AppCacheService;
import com.kaiqi.osprey.user.service.CheckCodeService;
import com.kaiqi.osprey.user.service.UserBizService;
import com.kaiqi.osprey.user.service.UserNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * 用户登录
 *
 * @author wangs
 */
@Slf4j
@RestController
@RequestMapping("/v1/osprey/users/membership/sign-in")
public class SignInController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserBizService userBizService;
    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private CheckCodeService checkCodeService;
    @Autowired
    private UserNoticeService userNoticeService;
    @Autowired
    private AppCacheService appCacheService;
    @Autowired
    private UserLoginRecordService userLoginRecordService;

    /**
     * 用户登录
     */
    @PostMapping("")
    public ResponseResult login(@RequestBody @Valid LoginReqVO reqVO, HttpServletRequest request) {
        try {
            /**
             * 获取登录信息
             */
            WebInfo webInfo = WebUtil.getWebInfo(request);
            log.info("user sign in username: {} webInfo: {} is sign in ", reqVO.getUsername(), webInfo);
            /**
             * 检查重复登录
             */
            long userId = HttpSessionUtils.getUserId(request);
            if (userId >= 0) {
                log.error("sign in user has login userId{}", userId);
                return ResultUtil.failure(ErrorCodeEnum.USER_ALREADY_LANDED_ERROR);
            }
            /**
             * 查用户信息
             */
            User user = userService.getByUserName(reqVO.getUsername());
            if (ObjectUtils.isEmpty(user)) {
                log.error("user sign in failure username not exist {} ", reqVO);
                return ResultUtil.failure(ErrorCodeEnum.LOGIN_NAME_OR_PASSWORD_ERROR);
            }
            /**
             * 验证是否需要图片验证码
             */
            if (StringUtils.isEmpty(reqVO.getVerificationCode())) {
                ErrorCodeEnum checkImageResult = appCacheService.loginCheckImageCode(user.getUserId(), reqVO.getImageCode(), reqVO.getSerialNO());
                if (!ObjectUtils.isEmpty(checkImageResult)) {
                    return ResultUtil.failure(checkImageResult);
                }
            }
            /**
             * 校验密码
             */
//            if (!passwordEncoder.matches(reqVO.getPassword(), user.getPassword())) {
            if (!StringUtils.equals(reqVO.getPassword(), user.getPassword())) {
                if (!StringUtils.isEmpty(reqVO.getSerialNO())) {
                    appCacheService.deleteImageVerificationCode(reqVO.getSerialNO());
                }
                appCacheService.addPwdErrorTimes(user, request, webInfo.getDeviceId());
                log.error("user sign in failure! password no match req {} db {}", reqVO.getPassword(), "");
                return ResultUtil.failure(ErrorCodeEnum.LOGIN_NAME_OR_PASSWORD_ERROR);
            }
            /**
             * 校验用户状态
             */
            if (user.getStatus() != UserStatusEnum.USER_STATUS_NORMAL.getStatus()) {
                log.error("user sign in failure user status is not normal status: {}", user.getStatus());
            }
            /**
             * 新设备登录检查
             */
            ResponseResult checkIsNewDevice = checkNewDevice(reqVO, request, webInfo.getIpAddress(), webInfo.getDeviceId(), user);
            if (checkIsNewDevice.isFail()) {
                log.error("new Device login need verify user= {}", user.getUserId());
                return checkIsNewDevice;
            }
            /**
             * 查询最后一次登录的token，清除登录会话记录
             */
            UserLoginRecord lastLoginRecord = userLoginRecordService.getLastLoginRecord(user.getUserId());
            if (!ObjectUtils.isEmpty(lastLoginRecord)) {
                String oldToken = lastLoginRecord.getToken();
                String oldDevId = lastLoginRecord.getDeviceId();
                JwtTokenUtils.clearSession(user.getUserId());
                log.info("user login delete old token user={},device={},newDevice={},token={}", user.getUserId(), oldDevId, webInfo.getDeviceId(), oldToken);
            }
            /**
             * 颁发令牌
             */
            UserSettings settings = userSettingsService.getByUserId(user.getUserId());
            AccessTokenResVO token = userBizService.issueToken(user, settings, webInfo, BusinessTypeEnum.USER_REGISTER_TYPE_MOBILE);
            return ResultUtil.success(token);
        } catch (Exception e) {
            log.error("sign in unknown error = {}", e);
            return ResultUtil.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 新设备登录检查
     * 需校验新设备校验验证码
     */
    private ResponseResult checkNewDevice(LoginReqVO reqVO, HttpServletRequest request, String ipAddress, String deviceId, User user) {
        boolean isNewDevice = userBizService.isNewDevice(deviceId, user.getUserId());
        if (!isNewDevice) {
            return ResultUtil.success();
        }

        if (StringUtils.isBlank(reqVO.getVerificationCode())) {
            HashMap<String, String> param = Maps.newHashMap();
            param.put("ip", ipAddress);
            DeviceVerifyResVO noticeResult = userNoticeService.sendNoticeByUserName(
                    reqVO.getUsername(),
                    LocaleUtil.getLocale(request),
                    StringUtil.isEmail(reqVO.getUsername()) ? BusinessTypeEnum.USER_LOGIN_TYPE_EMAIL : BusinessTypeEnum.USER_LOGIN_TYPE_MOBILE,
                    NoticeSendLogConsts.BUSINESS_CODE,
                    user,
                    deviceId,
                    param);
            log.info("user sign in new device login send notice result = {} userId = {}", noticeResult, user.getUserId());
            return ResultUtil.failure(ErrorCodeEnum.USER_NEW_DEVICE_LOGIN_VERIFY, noticeResult);
        } else {
            ResponseResult<?> checkCodeResult = checkCodeService.checkCode(
                    user.getUserId(),
                    StringUtil.isEmail(reqVO.getUsername()) ? user.getEmail() : user.getAreaCode() + user.getMobile(),
                    reqVO.getVerificationCode(),
                    StringUtil.isEmail(reqVO.getUsername()) ? BusinessTypeEnum.USER_LOGIN_TYPE_EMAIL : BusinessTypeEnum.USER_LOGIN_TYPE_MOBILE,
                    StringUtil.isEmail(reqVO.getUsername()) ? 2 : 1);
            if (checkCodeResult.getCode() != 0) {
                return ResultUtil.failure(ErrorCodeEnum.USER_NEW_DEVICE_LOGIN_CODE_ERROR);
            }
        }
        return ResultUtil.success();
    }
}
