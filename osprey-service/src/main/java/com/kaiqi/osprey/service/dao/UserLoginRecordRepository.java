package com.kaiqi.osprey.service.dao;

import com.kaiqi.osprey.common.mybatis.data.CrudRepository;
import com.kaiqi.osprey.service.criteria.UserLoginRecordExample;
import com.kaiqi.osprey.service.domain.UserLoginRecord;
import org.springframework.stereotype.Repository;

/**
 * 用户登录记录表 数据访问类
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:46
 */
@Repository
public interface UserLoginRecordRepository extends CrudRepository<UserLoginRecord, UserLoginRecordExample, Long> {

}