package com.kaiqi.osprey.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.consts.NoticeSendLogConsts;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.common.util.StringUtil;
import com.kaiqi.osprey.service.criteria.UserNoticeRecordExample;
import com.kaiqi.osprey.service.domain.UserNoticeRecord;
import com.kaiqi.osprey.service.service.UserNoticeRecordService;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.service.CheckCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author wangs
 */
@Service
@Slf4j
public class CheckCodeServiceImpl implements CheckCodeService {

    @Autowired
    private UserNoticeRecordService noticeRecordService;

    @Override
    public ResponseResult<?> checkCode(long userId, String loginName, String verificationCode, BusinessTypeEnum businessTypeEnum, Integer verifyType) {
        if (verifyType == 1) {
            return checkMobileCode(userId, loginName, verificationCode, businessTypeEnum);
        }
        if (verifyType == 2) {
            return checkEmailCode(userId, loginName, verificationCode, businessTypeEnum);
        }
        return ResultUtil.failure(ErrorCodeEnum.BUSINESS_SEND_CODE_UNSUPPORT);
    }

    @Override
    public ResponseResult<?> checkMobileCode(long userId, String loginName, String verificationCode, BusinessTypeEnum businessTypeEnum) {
        UserNoticeRecord userNoticeRecord = noticeRecordService.getLatestRecord(userId, loginName, NoticeSendLogConsts.NOTICE_TYPE_PHONE, businessTypeEnum.getType());
        // 校验正确性
        if (ObjectUtils.isEmpty(userNoticeRecord)
                || StringUtil.notEquals(verificationCode, JSON.parseObject(userNoticeRecord.getParams()).getString("code"))) {
            return ResultUtil.failure(ErrorCodeEnum.SMS_CODE_VERIFY_ERROR);
        }

        UserNoticeRecord updateBean = new UserNoticeRecord();
        // 校验有效期
        if (new Date().after(userNoticeRecord.getExpireTime())) {
            updateBean.setId(userNoticeRecord.getId());
            updateBean.setStatus(NoticeSendLogConsts.STATUS_OVERDUE);
            noticeRecordService.editById(updateBean);
            return ResultUtil.failure(ErrorCodeEnum.SMS_CODE_VERIFY_ERROR);
        }

        updateBean.setId(userNoticeRecord.getId());
        updateBean.setStatus(NoticeSendLogConsts.STATUS_USED);
        long effect = noticeRecordService.editById(updateBean);
        if (effect <= 0) {
            return ResultUtil.failure(ErrorCodeEnum.SMS_CODE_VERIFY_ERROR);
        }
        return ResultUtil.success();
    }

    @Override
    public ResponseResult<?> checkEmailCode(long userId, String loginName, String verificationCode, BusinessTypeEnum businessTypeEnum) {
        log.error("[checkCode] {}", verificationCode);
        UserNoticeRecordExample example = new UserNoticeRecordExample();
        UserNoticeRecordExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId)
                .andNoticeTypeEqualTo(businessTypeEnum.getType())
                .andChannelEqualTo(NoticeSendLogConsts.NOTICE_TYPE_EMAIL)
                .andStatusEqualTo(NoticeSendLogConsts.STATUS_NEW);
        if (!StringUtils.isEmpty(loginName)) {
            criteria.andTargetEqualTo(loginName);
        }
        example.setOrderByClause(" id desc");
        log.info("query params: userId={} noticeType={} channel={} status={} target={} ", userId, businessTypeEnum.getType(), NoticeSendLogConsts.NOTICE_TYPE_EMAIL,
                NoticeSendLogConsts.STATUS_NEW, loginName);
        UserNoticeRecord userNoticeRecord = noticeRecordService.getOneByExample(example);
        log.info("send record query result : {} ", userNoticeRecord);
        if (ObjectUtils.isEmpty(userNoticeRecord)) {
            log.error("checkMobileCode: userId: {} loginName: {} behavior: {} checkCode: {} ",
                    userId, loginName, businessTypeEnum.getType(), verificationCode);
            return ResultUtil.failure(ErrorCodeEnum.EMAIL_CODE_VERIFY_ERROR);
        }
        if (StringUtil.notEquals(verificationCode, JSON.parseObject(userNoticeRecord.getParams()).getString("code"))) {
            log.error("[checkCode] {} {} ", verificationCode, JSON.parseObject(userNoticeRecord.getParams()).getString("code"));
            return ResultUtil.failure(ErrorCodeEnum.EMAIL_CODE_VERIFY_ERROR);
        }

        UserNoticeRecord update = new UserNoticeRecord();
        //校验有效期
        if (new Date().after(userNoticeRecord.getExpireTime())) {
            update.setId(userNoticeRecord.getId());
            update.setStatus(NoticeSendLogConsts.STATUS_OVERDUE);

            noticeRecordService.editById(update);
            log.error("expire {}", new Date().after(userNoticeRecord.getExpireTime()));
            return ResultUtil.failure(ErrorCodeEnum.EMAIL_CODE_VERIFY_ERROR);
        }

        update.setId(userNoticeRecord.getId());
        update.setStatus(NoticeSendLogConsts.STATUS_USED);
        long effect = noticeRecordService.editById(update);
        log.error("update status {}", effect);
        if (effect <= 0) {
            log.error("effec < o");
            return ResultUtil.failure(ErrorCodeEnum.EMAIL_CODE_VERIFY_ERROR);
        }
        return ResultUtil.success();
    }
}
