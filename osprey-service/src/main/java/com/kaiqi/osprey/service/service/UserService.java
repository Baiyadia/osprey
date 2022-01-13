package com.kaiqi.osprey.service.service;

import com.kaiqi.osprey.common.mybatis.service.CrudService;
import com.kaiqi.osprey.service.criteria.UserExample;
import com.kaiqi.osprey.service.domain.User;

import java.util.List;

/**
 * 用户信息表 服务接口
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:00
 */
public interface UserService extends CrudService<User, UserExample, Long> {

    User getByUserId(Long userId);

    /**
     * 根据用户名（手机/邮箱）查询用户
     *
     * @param username
     * @return
     */
    User getByUserName(String username);

    User getByMobile(String mobile);

    User getByEmail(String email);

    User getByOpenId(String openId);

    List<User> getByUserIds(List<Long> userIds);

    List<User> getByOpenIds(List<String> openIds);

    /**
     * 检查用户是否存在
     *
     * @param loginName 手机号或邮箱
     * @return
     */
    boolean checkLoginName(String loginName);

    boolean updateNickname(long userId, String nickname);

    long resetPassword(User user, String encode);

}