package com.kaiqi.osprey.service.dao;

import com.kaiqi.osprey.common.mybatis.data.CrudRepository;
import com.kaiqi.osprey.service.criteria.UserFeedbackExample;
import com.kaiqi.osprey.service.domain.UserFeedback;
import org.springframework.stereotype.Repository;

/**
 * 用户反馈表 数据访问类
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:39
 */
@Repository
public interface UserFeedbackRepository extends CrudRepository<UserFeedback, UserFeedbackExample, Long> {

}