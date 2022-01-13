package com.kaiqi.osprey.service.dao;

import com.kaiqi.osprey.common.mybatis.data.CrudRepository;
import com.kaiqi.osprey.service.criteria.VersionPublishExample;
import com.kaiqi.osprey.service.domain.VersionPublish;
import org.springframework.stereotype.Repository;

/**
 * 客户端版本发布表 数据访问类
 *
 * @author youpin-team
 * @date 2020-02-06 17:58:11
 */
@Repository
public interface VersionPublishRepository extends CrudRepository<VersionPublish, VersionPublishExample, Long> {

}