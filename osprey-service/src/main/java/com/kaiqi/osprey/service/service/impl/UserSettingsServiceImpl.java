package com.kaiqi.osprey.service.service.impl;

import com.kaiqi.osprey.common.mybatis.service.AbstractCrudService;
import com.kaiqi.osprey.service.criteria.UserSettingsExample;
import com.kaiqi.osprey.service.dao.UserSettingsRepository;
import com.kaiqi.osprey.service.domain.UserSettings;
import com.kaiqi.osprey.service.service.UserSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户设置表 服务实现
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:55
 */
@Slf4j
@Service
public class UserSettingsServiceImpl extends AbstractCrudService<UserSettingsRepository, UserSettings, UserSettingsExample, Long> implements UserSettingsService {

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Override
    protected UserSettingsExample getPageExample(final String fieldName, final String keyword) {
        final UserSettingsExample example = new UserSettingsExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}