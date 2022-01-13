package com.kaiqi.osprey.service.service.impl;

import com.kaiqi.osprey.common.mybatis.service.AbstractCrudService;
import com.kaiqi.osprey.common.util.StringUtil;
import com.kaiqi.osprey.service.criteria.UserExample;
import com.kaiqi.osprey.service.dao.UserRepository;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户信息表 服务实现
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:00
 */
@Slf4j
@Service
public class UserServiceImpl extends AbstractCrudService<UserRepository, User, UserExample, Long> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected UserExample getPageExample(final String fieldName, final String keyword) {
        final UserExample example = new UserExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public User getByUserName(String username) {
        if (StringUtil.isEmail(username)) {
            return getByEmail(username);
        }
        return getByMobile(username);
    }

    @Override
    public User getByUserId(Long userId) {
        final UserExample example = new UserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return getOneByExample(example);
    }

    @Override
    public User getByMobile(String mobile) {
        UserExample example = new UserExample();
        example.createCriteria().andMobileEqualTo(mobile);
        return getOneByExample(example);
    }

    @Override
    public User getByEmail(String email) {
        UserExample example = new UserExample();
        example.createCriteria().andEmailEqualTo(email);
        return getOneByExample(example);
    }

    @Override
    public User getByOpenId(String openId) {
        UserExample example = new UserExample();
        example.createCriteria().andOpenIdEqualTo(openId);
        return getOneByExample(example);
    }

    @Override
    public List<User> getByUserIds(final List<Long> userIds) {
        final UserExample example = new UserExample();
        example.createCriteria().andUserIdIn(userIds);
        return getByExample(example);
    }

    @Override
    public List<User> getByOpenIds(List<String> openIds) {
        final UserExample example = new UserExample();
        example.createCriteria().andOpenIdIn(openIds);
        return getByExample(example);
    }

    @Override
    public boolean checkLoginName(String loginName) {
        String currentLoginName = StringUtils.trim(loginName);
        UserExample example = new UserExample();
        example.createCriteria().andMobileEqualTo(currentLoginName);
        example.or().andEmailEqualTo(currentLoginName);
        return dao.countByExample(example) > 0;
    }

    @Override
    public boolean updateNickname(long userId, String nickname) {
        User user = new User();
        user.setNickname(nickname);
        UserExample example = new UserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        int result = dao.updateByExample(user, example);
        return result > 0;
    }

    @Override
    public long resetPassword(User user, String encode) {
        user.setPassword(encode);
        return dao.updateById(user);
    }
}