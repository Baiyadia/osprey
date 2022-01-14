package com.kaiqi.osprey.user.service;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.entity.WebInfo;
import com.kaiqi.osprey.common.exception.OspreyBizException;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.domain.UserSettings;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.model.AccessTokenResVO;
import com.kaiqi.osprey.user.model.RegisterReqVO;

/**
 * 用户通知事件表 服务接口
 *
 * @author wangs
 * @date 2018-07-28
 */
public interface UserBizService {

    /**
     * 用户更新协议
     *
     * @param userId
     * @return
     */
    boolean updateUserProtocol(long userId);

    /**
     * 设置交易密码
     *
     * @param
     * @return
     */
    boolean setTradePassword(String encryptedPass, Long userId, String encodePath);

    /**
     * 检查是否是新设备登录
     *
     * @param deviceId
     * @param userId
     * @return
     */
    boolean isNewDevice(String deviceId, Long userId);

    /**
     * 用户注册接口
     *
     * @param reqVO            注册请求参数
     * @param webInfo          web请求信息
     * @param businessTypeEnum 业务类型
     * @return
     */
    ResponseResult register(RegisterReqVO reqVO, WebInfo webInfo, BusinessTypeEnum businessTypeEnum) throws OspreyBizException;

    /**
     * 令牌颁发
     *
     * @param user         user
     * @param settings     UserSettings
     * @param webInfo      web请求信息
     * @param businessType 业务类型
     * @return
     */
    AccessTokenResVO issueToken(User user, UserSettings settings, WebInfo webInfo, BusinessTypeEnum businessType);

}
