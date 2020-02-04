package com.kaiqi.osprey.service.service.impl;

import com.kaiqi.osprey.common.mybatis.service.AbstractCrudService;
import com.kaiqi.osprey.service.criteria.UserExample;
import com.kaiqi.osprey.service.dao.UserRepository;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author youpin-team
 * @date 2019-06-03 16:36:46
 */
@Slf4j
@Service
public class UserServiceImpl extends AbstractCrudService<UserRepository, User, UserExample, Long> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected UserExample getPageExample(String fieldName, String keyword) {
        UserExample example = new UserExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}