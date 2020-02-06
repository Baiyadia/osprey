package com.kaiqi.osprey.service.service;

import com.kaiqi.osprey.common.mybatis.service.CrudService;
import com.kaiqi.osprey.service.criteria.UserExample;
import com.kaiqi.osprey.service.domain.User;

/**
 * 用户信息表 服务接口
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:00
 */
public interface UserService extends CrudService<User, UserExample, Long> {

}