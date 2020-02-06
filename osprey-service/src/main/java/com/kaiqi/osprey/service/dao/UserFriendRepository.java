package com.kaiqi.osprey.service.dao;

import com.kaiqi.osprey.common.mybatis.data.CrudRepository;
import com.kaiqi.osprey.service.criteria.UserFriendExample;
import com.kaiqi.osprey.service.domain.UserFriend;
import org.springframework.stereotype.Repository;

/**
 * 用户联系人表 数据访问类
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:43
 */
@Repository
public interface UserFriendRepository extends CrudRepository<UserFriend, UserFriendExample, Long> {

}