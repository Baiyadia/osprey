package com.kaiqi.osprey.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.kaiqi.osprey.common.consts.NoticeSendLogConsts;
import com.kaiqi.osprey.common.util.DateUtil;
import com.kaiqi.osprey.common.util.StringUtil;
import com.kaiqi.osprey.service.criteria.UserNoticeRecordExample;
import com.kaiqi.osprey.service.dao.UserNoticeRecordRepository;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.domain.UserNoticeRecord;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.enums.RegisterTypeEnum;
import com.kaiqi.osprey.user.model.DeviceVerifyResVO;
import com.kaiqi.osprey.user.service.UserNoticeService;
import com.kaiqi.osprey.user.util.EmailTokenUtils;
import com.kaiqi.osprey.user.util.VerificationCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;

/**
 * 用户通知事件表 服务实现
 *
 * @author newex-team
 * @date 2018-07-28
 */
@Slf4j
@Service
public class UserNoticeServiceImpl implements UserNoticeService {

    @Autowired
    private UserNoticeRecordRepository userNoticeRecordRepos;

//    @Autowired
//    private MessageClient messageClient;

    @Override
    public DeviceVerifyResVO sendNoticeByUserName(String userName, String locale, BusinessTypeEnum businessTypeEnum, int businessCode,
                                                  User user, String deviceId, HashMap<String, String> param) {
        DeviceVerifyResVO result = new DeviceVerifyResVO();
        if (StringUtil.isEmail(userName)) {
            boolean sendResult = sendEmail(locale, businessTypeEnum, businessCode, userName, user.getUserId(), param, deviceId);
            result.setEmail(userName);
            result.setSendType(RegisterTypeEnum.EMAIL.getType());
            result.setSendSuccess(sendResult);
        } else if (StringUtil.isMobile(userName)) {
            boolean sendResult = sendSMS(locale, businessTypeEnum, businessCode, userName, user.getAreaCode(), user.getUserId(), param, deviceId);
            result.setAreaCode(user.getAreaCode());
            result.setMobile(userName);
            result.setSendType(RegisterTypeEnum.MOBILE.getType());
            result.setSendSuccess(sendResult);
        }
        return result;
    }

    @Override
    public boolean sendSMS(String locale, BusinessTypeEnum businessTypeEnum, int businessCode, String mobile, Integer areaCode, long userId,
                           HashMap<String, String> params, String deviceId) {
        params.put("loginTime", DateUtil.getFormatDate(System.currentTimeMillis()));
        int result;
        String target = areaCode + mobile;
        UserNoticeRecordExample nsExample = new UserNoticeRecordExample();
        UserNoticeRecordExample.Criteria criteria = nsExample.createCriteria()
                                                             .andNoticeTypeEqualTo(businessTypeEnum.getType())
                                                             .andChannelEqualTo(NoticeSendLogConsts.NOTICE_TYPE_PHONE)
                                                             .andStatusEqualTo(NoticeSendLogConsts.STATUS_NEW)
                                                             .andTargetEqualTo(target);
        if (userId > 0) {
            criteria.andUserIdEqualTo(userId);
        }
        UserNoticeRecord userNoticeRecord = userNoticeRecordRepos.selectOneByExample(nsExample);
        if (!ObjectUtils.isEmpty(userNoticeRecord)) {
            if (new Date().compareTo(userNoticeRecord.getExpireTime()) > 0) {
                userNoticeRecord.setStatus(NoticeSendLogConsts.STATUS_OVERDUE);
                result = userNoticeRecordRepos.updateById(userNoticeRecord);
                if (result <= 0) {
                    log.error("MessageSendService sendSMS, msgCode OVERDUE and update status falied!");
                    return false;
                }
                userNoticeRecord = null;
            }
        }
        if (ObjectUtils.isEmpty(userNoticeRecord)) {
            String randNumber = VerificationCodeUtils.getRandNumber(6);
            log.info("send sms code is {}", randNumber);
            params.put("code", randNumber);
            userNoticeRecord = new UserNoticeRecord();
            userNoticeRecord.setToken(EmailTokenUtils.createEmailToken(userId, businessTypeEnum.getType(), -1));
            userNoticeRecord.setStatus(NoticeSendLogConsts.STATUS_NEW);
            userNoticeRecord.setChannel(NoticeSendLogConsts.NOTICE_TYPE_PHONE);
            userNoticeRecord.setTarget(target);
            userNoticeRecord.setUserId(userId);
            userNoticeRecord.setNoticeType(businessTypeEnum.getType());
            userNoticeRecord.setCreateTime(new Date());
            userNoticeRecord.setAreaCode(areaCode);
            userNoticeRecord.setParams(JSON.toJSONString(params));
            userNoticeRecord.setUpdateTime(new Date());
            userNoticeRecord.setExpireTime(businessTypeEnum.getExpireTime(new Date()));
            userNoticeRecord.setDeviceId(deviceId);
            result = userNoticeRecordRepos.insert(userNoticeRecord);
            if (result <= 0) {
                log.error("MessageSendService sendSMS, save noticeSendLog falied!");
                return false;
            }

        }

//        //TODO 模板id
//        MessageReqDTO messageReq = MessageReqDTO.builder()
//                                                .templateCode(businessTypeEnum.getMobileCode())
//                                                .countryCode(String.valueOf(areaCode))
//                                                .locale(locale.toLowerCase())
//                                                .mobile(mobile)
//                                                .params(userNoticeRecord.getParams())
//                                                .brokerId(BrokerIdConsts.COIN_SAFE)
//                                                .build();
//        ResponseResult resResult = messageClient.send(messageReq);
//        log.info("sendSMS sendResult: params {}， result {},locale={}", userNoticeRecord, JSON.toJSONString(resResult), locale);
//        return resResult != null && resResult.getCode() == 0;
        return true;
    }

    @Override
    public boolean sendEmail(String locale, BusinessTypeEnum businessTypeEnum, int businessCode, String email, long userId, HashMap<String,
            String> params, String deviceId) {
        log.error("[SENDMAIL] businessTypeEnum={} businessCode={} email={} userId={} deviceId={}", businessTypeEnum.getType(), businessCode, email, userId, deviceId);
        params.put("loginTime", DateUtil.getFormatDate(System.currentTimeMillis()));
        int result;
        UserNoticeRecordExample nsExample = new UserNoticeRecordExample();
        UserNoticeRecordExample.Criteria criteria = nsExample.createCriteria()
                                                             .andNoticeTypeEqualTo(businessTypeEnum.getType())
                                                             .andChannelEqualTo(NoticeSendLogConsts.NOTICE_TYPE_EMAIL)
                                                             .andStatusEqualTo(NoticeSendLogConsts.STATUS_NEW)
                                                             .andTargetEqualTo(email);
        if (userId > 0) {
            criteria.andUserIdEqualTo(userId);
        }
        UserNoticeRecord userNoticeRecord = userNoticeRecordRepos.selectOneByExample(nsExample);
        log.error("[SENDMAIL] record={}", userNoticeRecord);
        //如果过了3分钟
        if (userNoticeRecord != null && new Date().after(userNoticeRecord.getExpireTime())) {
            log.error("[SENDMAIL] >max");
            userNoticeRecord.setStatus(NoticeSendLogConsts.STATUS_OVERDUE);
            result = userNoticeRecordRepos.updateById(userNoticeRecord);
            if (result <= 0) {
                log.error("UserNoticeEventServiceImpl sendEmail, emailCode OVERDUE and update status falied!");
                return false;
            }
            log.error("[SENDMAIL]  update old record");
            userNoticeRecord = null;
        }

        if (userNoticeRecord == null) {
            if (businessCode == NoticeSendLogConsts.BUSINESS_CODE) {
                params.put("code", VerificationCodeUtils.getRandNumber(6));
            }

            userNoticeRecord = new UserNoticeRecord();
            userNoticeRecord.setToken(EmailTokenUtils.createEmailToken(userId, businessTypeEnum.getType(), 30));
            userNoticeRecord.setStatus(NoticeSendLogConsts.STATUS_NEW);
            userNoticeRecord.setChannel(NoticeSendLogConsts.NOTICE_TYPE_EMAIL);
            userNoticeRecord.setTarget(email);
            userNoticeRecord.setUserId(userId);
            userNoticeRecord.setCreateTime(new Date());
            userNoticeRecord.setParams(JSON.toJSONString(params));
            userNoticeRecord.setExpireTime(businessTypeEnum.getExpireTime(new Date()));
            userNoticeRecord.setDeviceId(deviceId);
            userNoticeRecord.setUpdateTime(new Date());
            userNoticeRecord.setNoticeType(businessTypeEnum.getType());
            result = userNoticeRecordRepos.insert(userNoticeRecord);
            if (result <= 0) {
                log.error("UserNoticeEventServiceImpl sendEmail, save noticeSendLog falied!");
                return false;
            }
        }

//        // 邮件发送
//        MessageReqDTO messageReq = MessageReqDTO.builder()
//                                                .templateCode(businessTypeEnum.getEmailCode())
//                                                .locale(locale.toLowerCase())
//                                                .email(email)
//                                                .params(userNoticeRecord.getParams())
//                                                .brokerId(BrokerIdConsts.COIN_SAFE)
//                                                .build();
//        ResponseResult responseResult = messageClient.send(messageReq);
//        log.info("[SENDMAIL] sendResult: params {}， result {},locale={}", userNoticeRecord, JSON.toJSONString(responseResult), locale);
//        return responseResult.getCode() == 0;
        return true;
    }
}