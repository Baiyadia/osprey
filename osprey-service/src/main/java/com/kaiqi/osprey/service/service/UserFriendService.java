package com.kaiqi.osprey.service.service;

import com.kaiqi.osprey.common.mybatis.service.CrudService;
import com.kaiqi.osprey.service.criteria.UserFriendExample;
import com.kaiqi.osprey.service.domain.UserFriend;

/**
 * 用户联系人表 服务接口
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:43
 */
public interface UserFriendService extends CrudService<UserFriend, UserFriendExample, Long> {

}