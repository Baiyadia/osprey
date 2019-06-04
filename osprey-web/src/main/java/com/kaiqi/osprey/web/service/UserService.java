package com.kaiqi.osprey.web.service;

import com.kaiqi.osprey.common.mybatis.service.CrudService;
import com.kaiqi.osprey.web.criteria.UserExample;
import com.kaiqi.osprey.web.domain.User;

/**
 * @author youpin-team
 * @date 2019-06-03 16:36:46
 */
public interface UserService extends CrudService<User, UserExample, Long> {

}