package com.kaiqi.osprey.user.service.impl;

import com.google.common.collect.Maps;
import com.kaiqi.osprey.common.commons.entity.WebInfo;
import com.kaiqi.osprey.common.consts.NoticeSendLogConsts;
import com.kaiqi.osprey.service.domain.UserLoginRecord;
import com.kaiqi.osprey.service.service.UserLoginRecordService;
import com.kaiqi.osprey.user.domain.UserDetails;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.service.OperatorFacadeService;
import com.kaiqi.osprey.user.service.UserBizService;
import com.kaiqi.osprey.user.service.UserNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

/**
 * @author wangs
 */
@Slf4j
@Service
public class OperatorFacadeServiceImpl implements OperatorFacadeService {

    @Autowired
    private UserLoginRecordService userLoginRecordService;

    @Autowired
    private UserNoticeService userNoticeService;

    @Autowired
    private UserBizService userBizService;

    @Async
    @Override
    public void recordLoginEvent(String token, UserDetails userDetails, BusinessTypeEnum userRegisterTypeMobile, WebInfo webInfo) {
        boolean isEmpty = userBizService.isNewDevice(webInfo.getDeviceId(), userDetails.getUserId());
        if (isEmpty) {
            HashMap<String, String> param = Maps.newHashMap();
            param.put("ip", webInfo.getIpAddress());

            if (StringUtils.isNotBlank(userDetails.getEmail())) {
                try {
                    boolean sendEmailResult = userNoticeService.sendEmail(webInfo.getLocale(), BusinessTypeEnum.NEW_DEVICE_LOGIN_EMAIL_NOTIFY,
                            NoticeSendLogConsts.BUSINESS_NOTIFICATION, userDetails.getEmail(), userDetails.getUserId(), param, webInfo.getDeviceId());
                    log.info("recordLoginEvent sendEmailResult = {} userId = {} email = {}", sendEmailResult, userDetails.getUserId(), userDetails.getEmail());
                } catch (Exception e) {
                    log.error("recordLoginEvent new Device login sendEmailResult failure userDetail={} e={}", userDetails, e);
                }
            }

            if (userDetails.getRegisterType().equals(BusinessTypeEnum.USER_REGISTER_TYPE_MOBILE.getType())) {
                try {
                    boolean sendEmailResult = userNoticeService.sendSMS(webInfo.getLocale(), BusinessTypeEnum.NEW_DEVICE_LOGIN_SMS_NOTIFY,
                            NoticeSendLogConsts.BUSINESS_NOTIFICATION, userDetails.getMobile(), userDetails.getAreaCode(), userDetails.getUserId(), param, webInfo.getDeviceId());
                    log.info("recordLoginEvent sendSMS = {} userId = {} mobile = {}", sendEmailResult, userDetails.getUserId(), userDetails.getMobile());
                } catch (Exception e) {
                    log.error("recordLoginEvent new Device login sendSMS failure userDetail={} e={}", userDetails, e);
                }
            }
        }

        UserLoginRecord loginRecord = UserLoginRecord
                .builder()
                .userId(userDetails.getUserId())
                .token(token)
                .deviceId(webInfo.getDeviceId())
                .ipAddress(webInfo.getIpAddress())
                .region("")
                .lastLoginIp(userDetails.getLastLoginIp())
                .userAgent(webInfo.getUserAgent())
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        userLoginRecordService.add(loginRecord);
    }

}
