package com.kaiqi.osprey.service.service.impl;

import com.kaiqi.osprey.common.consts.NoticeSendLogConsts;
import com.kaiqi.osprey.common.mybatis.service.AbstractCrudService;
import com.kaiqi.osprey.service.criteria.UserNoticeRecordExample;
import com.kaiqi.osprey.service.dao.UserNoticeRecordRepository;
import com.kaiqi.osprey.service.domain.UserNoticeRecord;
import com.kaiqi.osprey.service.service.UserNoticeRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户通知记录表 服务实现
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:50
 */
@Slf4j
@Service
public class UserNoticeRecordServiceImpl extends AbstractCrudService<UserNoticeRecordRepository, UserNoticeRecord, UserNoticeRecordExample, Long> implements UserNoticeRecordService {

    @Autowired
    private UserNoticeRecordRepository userNoticeRecordRepository;

    @Override
    protected UserNoticeRecordExample getPageExample(final String fieldName, final String keyword) {
        final UserNoticeRecordExample example = new UserNoticeRecordExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public UserNoticeRecord getLatestRecord(Long userId, String loginName, Integer Channel, Integer noticeType) {
        UserNoticeRecordExample example = new UserNoticeRecordExample();
        UserNoticeRecordExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId)
                .andNoticeTypeEqualTo(noticeType)
                .andChannelEqualTo(Channel)
                .andStatusEqualTo(NoticeSendLogConsts.STATUS_NEW);
        if (!StringUtils.isEmpty(loginName)) {
            criteria.andTargetEqualTo(loginName);
        }
        example.setOrderByClause(" id desc ");
        return getOneByExample(example);
    }
}