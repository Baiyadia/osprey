package com.kaiqi.osprey.service.service;

import com.kaiqi.osprey.common.mybatis.service.CrudService;
import com.kaiqi.osprey.service.criteria.UserFeedbackExample;
import com.kaiqi.osprey.service.domain.UserFeedback;

/**
 * 用户反馈表 服务接口
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:39
 */
public interface UserFeedbackService extends CrudService<UserFeedback, UserFeedbackExample, Long> {

}