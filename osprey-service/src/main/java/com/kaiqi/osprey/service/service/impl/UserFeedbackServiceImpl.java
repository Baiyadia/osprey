package com.kaiqi.osprey.service.service.impl;

import com.kaiqi.osprey.common.mybatis.service.AbstractCrudService;
import com.kaiqi.osprey.common.util.DateUtil;
import com.kaiqi.osprey.service.criteria.UserFeedbackExample;
import com.kaiqi.osprey.service.dao.UserFeedbackRepository;
import com.kaiqi.osprey.service.domain.UserFeedback;
import com.kaiqi.osprey.service.service.UserFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户反馈表 服务实现
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:39
 */
@Slf4j
@Service
public class UserFeedbackServiceImpl extends AbstractCrudService<UserFeedbackRepository, UserFeedback, UserFeedbackExample, Long> implements UserFeedbackService {

    @Autowired
    private UserFeedbackRepository userFeedbackRepository;

    @Override
    protected UserFeedbackExample getPageExample(final String fieldName, final String keyword) {
        final UserFeedbackExample example = new UserFeedbackExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<UserFeedback> getUserFeedbackListHours(Long userId, int hours) {
        UserFeedbackExample feedbackExample = new UserFeedbackExample();
        feedbackExample.createCriteria()
                       .andUserIdEqualTo(userId)
                       .andCreateTimeBetween(DateUtil.addHours(new Date(), -hours), new Date());
        return getByExample(feedbackExample);
    }
}