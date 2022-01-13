package com.kaiqi.osprey.service.service.impl;

import com.kaiqi.osprey.common.mybatis.service.AbstractCrudService;
import com.kaiqi.osprey.service.criteria.VersionPublishExample;
import com.kaiqi.osprey.service.dao.VersionPublishRepository;
import com.kaiqi.osprey.service.domain.VersionPublish;
import com.kaiqi.osprey.service.service.VersionPublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客户端版本发布表 服务实现
 *
 * @author youpin-team
 * @date 2020-02-06 17:58:11
 */
@Slf4j
@Service
public class VersionPublishServiceImpl extends AbstractCrudService<VersionPublishRepository, VersionPublish, VersionPublishExample, Long> implements VersionPublishService {

    @Autowired
    private VersionPublishRepository versionPublishRepository;

    @Override
    protected VersionPublishExample getPageExample(final String fieldName, final String keyword) {
        final VersionPublishExample example = new VersionPublishExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}