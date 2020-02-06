package com.kaiqi.osprey.service.service;

import com.kaiqi.osprey.common.mybatis.service.CrudService;
import com.kaiqi.osprey.service.criteria.UserLoginRecordExample;
import com.kaiqi.osprey.service.domain.UserLoginRecord;

/**
 * 用户登录记录表 服务接口
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:46
 */
public interface UserLoginRecordService extends CrudService<UserLoginRecord, UserLoginRecordExample, Long> {

}