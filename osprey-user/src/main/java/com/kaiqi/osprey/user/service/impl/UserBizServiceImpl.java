package com.kaiqi.osprey.user.service.impl;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.entity.WebInfo;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.exception.OspreyBizException;
import com.kaiqi.osprey.common.util.IdWorker;
import com.kaiqi.osprey.common.util.MobileUtil;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.security.jwt.util.JwtTokenUtils;
import com.kaiqi.osprey.service.criteria.UserExample;
import com.kaiqi.osprey.service.criteria.UserSettingsExample;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.domain.UserSettings;
import com.kaiqi.osprey.service.service.UserLoginRecordService;
import com.kaiqi.osprey.service.service.UserService;
import com.kaiqi.osprey.service.service.UserSettingsService;
import com.kaiqi.osprey.user.domain.UserDetails;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.enums.UserStatusEnum;
import com.kaiqi.osprey.user.model.AccessTokenResVO;
import com.kaiqi.osprey.user.model.RegisterReqVO;
import com.kaiqi.osprey.user.model.UserProfileVO;
import com.kaiqi.osprey.user.service.JwtTokenService;
import com.kaiqi.osprey.user.service.OperatorFacadeService;
import com.kaiqi.osprey.user.service.UserBizService;
import com.kaiqi.osprey.user.util.NicknameUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * 用户通知事件表 服务实现
 *
 * @author newex-team
 * @date 2018-07-28
 */
@Slf4j
@Service
public class UserBizServiceImpl implements UserBizService {

    @Resource
    private UserService userService;
    @Resource
    private UserSettingsService userSettingsService;
    @Resource
    private UserLoginRecordService userLoginRecordService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private OperatorFacadeService operatorFacadeService;

    @Override
    public boolean updateUserProtocol(long userId) {
        UserSettingsExample example = new UserSettingsExample();
        example.createCriteria().andUserIdEqualTo(userId);
        UserSettings settings = UserSettings.builder().userId(userId).protocolAuthFlag(UserStatusEnum.USER_AGREE_PROTOCOL.getStatus()).build();
        int result = userSettingsService.editByExample(settings, example);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setTradePassword(String encryptedPass, Long userId, String encodePath) {
        User user = new User();
        user.setUserId(userId);
        user.setTradePasswordCryptoHash(encryptedPass);
//        user.setCryptoPath(encodePath);
        user.setTradePassUpdate(new Date());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(userId);
        userService.editByExample(user, userExample);

        UserSettings userSettings = new UserSettings();
        userSettings.setTradePasswordSetFlag(UserStatusEnum.USER_TRADE_PASSWORD_ALREADY_SET.getStatus());
        userSettings.setUserId(userId);
        UserSettingsExample settingsExample = new UserSettingsExample();
        settingsExample.createCriteria().andUserIdEqualTo(userId);
        userSettingsService.editByExample(userSettings, settingsExample);
        return true;
    }

    @Override
    public boolean isNewDevice(String deviceId, Long userId) {
        if (ObjectUtils.isEmpty(deviceId) || ObjectUtils.isEmpty(userId)) {
            return true;
        }
        return ObjectUtils.isEmpty(userLoginRecordService.getByUidDeviceId(userId, deviceId));
    }

    @Override
    public ResponseResult register(RegisterReqVO reqVO, WebInfo webInfo, BusinessTypeEnum businessTypeEnum) throws OspreyBizException {
        /**
         * 查邀请用户
         */
        if (StringUtils.isNotBlank(reqVO.getInviteCode())) {
            User inviteUser = userService.getByOpenId(reqVO.getInviteCode().toUpperCase());
            if (Objects.isNull(inviteUser)) {
                return ResultUtil.failure(ErrorCodeEnum.USER_INVITE_CODE_NOT_FOUND);
            }
        }

        /**
         * 持久化user、settings
         */
        User user = genNewUser(reqVO, webInfo, businessTypeEnum);
        UserSettings settings = genDefaultSettings(user.getUserId(), businessTypeEnum);
        try {
            userService.add(user);
            userSettingsService.add(settings);
            log.info("register reqVO {}  userId {} success", reqVO, user.getUserId());
        } catch (Exception e) {
            log.info("register reqVO:{} failure {}", reqVO, e);
            throw new OspreyBizException(ErrorCodeEnum.REGISTER_SAVE_FAILED);
        }

        /**
         * 生成jwt 颁发令牌
         */
        UserDetails userDetails = genUserDetails(user, settings, webInfo);
        JwtUserDetails jwtUserDetails = jwtTokenService.createJwtUserDetails(userDetails);
        UserProfileVO profile = UserProfileVO.builder()
                                             .openId(user.getOpenId())
                                             .email(user.getEmail())
                                             .mobile(user.getMobile())
                                             .protocolAuthFlag(settings.getProtocolAuthFlag())
                                             .registerType(user.getRegisterType())
                                             .subNotifyFlag(settings.getSubNotifyFlag())
                                             .tradePasswordFlag(settings.getTradePasswordSetFlag())
                                             .username(user.getNickname())
                                             .tradePassUpdate(user.getTradePassUpdate())
//                                             .isAddressVisible(settings.getIsAddressVisible())
                                             .contactCount(user.getContactCount())
                                             .build();
        AccessTokenResVO token = AccessTokenResVO.builder()
                                                 .accessToken(JwtTokenUtils.generateTokenAndCreateSession(jwtUserDetails, webInfo))
                                                 .refreshToken("")
                                                 .profile(profile)
                                                 .build();
        /**
         * 安全记录
         */
        operatorFacadeService.recordLoginEvent(token.getAccessToken(), userDetails, BusinessTypeEnum.USER_REGISTER_TYPE_MOBILE, webInfo);
        return ResultUtil.success(token);
    }

    /**
     * 新用户构建
     */
    private User genNewUser(RegisterReqVO reqVO, WebInfo webInfo, BusinessTypeEnum businessTypeEnum) {
        Date now = new Date();
        User user = User.builder()
                        .userId(IdWorker.nextId())
//                        .password(passwordEncoder.encode(reqVO.getPassword()))
                        .parentId(0L)
                        .password(reqVO.getPassword())
                        .antiPhishingCode(NicknameUtil.antiPhishingCode())
                        .areaCode(reqVO.getAreaCode())
                        .regIp(webInfo.getIpAddress())
                        .openId(String.valueOf(NicknameUtil.getOpenId()))
                        .createTime(now)
                        .updateTime(now)
                        .tradePassUpdate(now)
                        .contactCount(0)
                        .status(UserStatusEnum.USER_STATUS_NORMAL.getStatus())
                        .build();
        if (BusinessTypeEnum.USER_REGISTER_TYPE_MOBILE.getType().equals(businessTypeEnum.getType())) {
            user.setMobile(reqVO.getMobile());
            user.setRegisterType(BusinessTypeEnum.USER_REGISTER_TYPE_MOBILE.getType());
            user.setNickname(MobileUtil.getScreenPhoneNumber(reqVO.getMobile()));
        } else {
            user.setEmail(reqVO.getEmail());
            user.setRegisterType(BusinessTypeEnum.USER_REGISTER_TYPE_EMAIL.getType());
            user.setNickname(MobileUtil.getScreenEmailNumber(reqVO.getEmail()));
        }
        return user;
    }

    /**
     * 默认配置构建
     */
    private UserSettings genDefaultSettings(Long userId, BusinessTypeEnum businessTypeEnum) {
        Date now = new Date();
        UserSettings settings = UserSettings
                .builder()
                .userId(userId)
                .loginAuthFlag(UserStatusEnum.USER_LOGIN_AUTH_FLAG_NO.getStatus())
                .googleAuthFlag(UserStatusEnum.USER_GOOGLE_SETTING_STATUS_NO.getStatus())
                .loginPwdStrength(UserStatusEnum.USER_PWD_STRENGTH_OK.getStatus())
                .protocolAuthFlag(UserStatusEnum.USER_REJECT_PROTOCOL.getStatus())
                .subNotifyFlag(UserStatusEnum.USER_SUBNOTIFY_FLAG_NO.getStatus())
                .tradePasswordSetFlag(UserStatusEnum.USER_TRADE_PASSWORD_NO_SET.getStatus())
                .createTime(now)
                .updateTime(now)
                .build();
        if (BusinessTypeEnum.USER_REGISTER_TYPE_MOBILE.getType().equals(businessTypeEnum.getType())) {
            settings.setMobileAuthFlag(UserStatusEnum.USER_MOBILE_SETTING_STATUS_OK.getStatus());
            settings.setEmailAuthFlag(UserStatusEnum.USER_EMAIL_SETTING_STATUS_NO.getStatus());
        } else {
            settings.setMobileAuthFlag(UserStatusEnum.USER_MOBILE_SETTING_STATUS_NO.getStatus());
            settings.setEmailAuthFlag(UserStatusEnum.USER_EMAIL_SETTING_STATUS_OK.getStatus());
        }
        return settings;
    }

    /**
     * create simple userDetails object
     *
     * @param user
     * @param settings
     * @return
     */
    private UserDetails genUserDetails(User user, UserSettings settings, WebInfo webInfo) {
        UserDetails userDetails = new UserDetails();
        BeanUtils.copyProperties(user, userDetails);
        BeanUtils.copyProperties(settings, userDetails);
        userDetails.setLastLoginIp(webInfo.getIpAddress());
        userDetails.setDeviceId(webInfo.getDeviceId());
        return userDetails;
    }
}