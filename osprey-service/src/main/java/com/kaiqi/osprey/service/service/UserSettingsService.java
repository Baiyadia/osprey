package com.kaiqi.osprey.service.service;

import com.kaiqi.osprey.common.mybatis.service.CrudService;
import com.kaiqi.osprey.service.criteria.UserSettingsExample;
import com.kaiqi.osprey.service.domain.UserSettings;

/**
 * 用户设置表 服务接口
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:55
 */
public interface UserSettingsService extends CrudService<UserSettings, UserSettingsExample, Long> {

    UserSettings getByUserId(Long userId);

}