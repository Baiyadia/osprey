package com.kaiqi.osprey.service.dao;

import com.kaiqi.osprey.common.mybatis.data.CrudRepository;
import com.kaiqi.osprey.service.criteria.UserSettingsExample;
import com.kaiqi.osprey.service.domain.UserSettings;
import org.springframework.stereotype.Repository;

/**
 * 用户设置表 数据访问类
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:55
 */
@Repository
public interface UserSettingsRepository extends CrudRepository<UserSettings, UserSettingsExample, Long> {

}