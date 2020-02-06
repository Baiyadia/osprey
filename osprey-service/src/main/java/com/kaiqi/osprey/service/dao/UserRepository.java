package com.kaiqi.osprey.service.dao;

import com.kaiqi.osprey.common.mybatis.data.CrudRepository;
import com.kaiqi.osprey.service.criteria.UserExample;
import com.kaiqi.osprey.service.domain.User;
import org.springframework.stereotype.Repository;

/**
 * 用户信息表 数据访问类
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:00
 */
@Repository
public interface UserRepository extends CrudRepository<User, UserExample, Long> {

}