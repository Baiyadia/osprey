package com.kaiqi.osprey.web.dao;

import com.kaiqi.osprey.common.mybatis.data.CrudRepository;
import com.kaiqi.osprey.web.criteria.UserExample;
import com.kaiqi.osprey.web.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author youpin-team
 * @date 2019-06-03 16:36:46
 */
@Repository
public interface UserRepository extends CrudRepository<User, UserExample, Long> {

}