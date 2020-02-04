package com.kaiqi.osprey.service.dao;

import com.kaiqi.osprey.common.mybatis.data.CrudRepository;
import com.kaiqi.osprey.service.criteria.UserExample;
import com.kaiqi.osprey.service.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author wangs
 * @date 2019-06-03 16:36:46
 */
@Repository
public interface UserRepository extends CrudRepository<User, UserExample, Long> {

}