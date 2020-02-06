package com.kaiqi.osprey.service.service.impl;

import com.kaiqi.osprey.common.mybatis.service.AbstractCrudService;
import com.kaiqi.osprey.service.criteria.UserFriendExample;
import com.kaiqi.osprey.service.dao.UserFriendRepository;
import com.kaiqi.osprey.service.domain.UserFriend;
import com.kaiqi.osprey.service.service.UserFriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户联系人表 服务实现
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:43
 */
@Slf4j
@Service
public class UserFriendServiceImpl extends AbstractCrudService<UserFriendRepository, UserFriend, UserFriendExample, Long> implements UserFriendService {

    @Autowired
    private UserFriendRepository userFriendRepository;

    @Override
    protected UserFriendExample getPageExample(final String fieldName, final String keyword) {
        final UserFriendExample example = new UserFriendExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}