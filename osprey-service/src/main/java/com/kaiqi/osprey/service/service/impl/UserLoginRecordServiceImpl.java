package com.kaiqi.osprey.service.service.impl;

import com.kaiqi.osprey.common.mybatis.service.AbstractCrudService;
import com.kaiqi.osprey.service.criteria.UserLoginRecordExample;
import com.kaiqi.osprey.service.dao.UserLoginRecordRepository;
import com.kaiqi.osprey.service.domain.UserLoginRecord;
import com.kaiqi.osprey.service.service.UserLoginRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录记录表 服务实现
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:46
 */
@Slf4j
@Service
public class UserLoginRecordServiceImpl extends AbstractCrudService<UserLoginRecordRepository, UserLoginRecord, UserLoginRecordExample, Long> implements UserLoginRecordService {

    @Autowired
    private UserLoginRecordRepository userLoginRecordRepository;

    @Override
    protected UserLoginRecordExample getPageExample(final String fieldName, final String keyword) {
        final UserLoginRecordExample example = new UserLoginRecordExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}