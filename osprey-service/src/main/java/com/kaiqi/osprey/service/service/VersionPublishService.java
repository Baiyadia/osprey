package com.kaiqi.osprey.service.service;

import com.kaiqi.osprey.common.mybatis.service.CrudService;
import com.kaiqi.osprey.service.criteria.VersionPublishExample;
import com.kaiqi.osprey.service.domain.VersionPublish;

/**
 * 客户端版本发布表 服务接口
 *
 * @author youpin-team
 * @date 2020-02-06 17:58:11
 */
public interface VersionPublishService extends CrudService<VersionPublish, VersionPublishExample, Long> {

}