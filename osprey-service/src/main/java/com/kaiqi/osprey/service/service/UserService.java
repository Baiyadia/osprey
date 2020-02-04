package com.kaiqi.osprey.service.service;

import com.kaiqi.osprey.common.mybatis.service.CrudService;
import com.kaiqi.osprey.service.criteria.UserExample;
import com.kaiqi.osprey.service.domain.User;

/**
 * @author youpin-team
 * @date 2019-06-03 16:36:46
 */
public interface UserService extends CrudService<User, UserExample, Long> {

}