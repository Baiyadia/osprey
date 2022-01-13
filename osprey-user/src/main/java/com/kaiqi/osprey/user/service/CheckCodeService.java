package com.kaiqi.osprey.user.service;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;

/**
 * @author wangs
 */
public interface CheckCodeService {

    /**
     * 手机验证码检查
     *
     * @param userId           -1代表还不知道用户userid
     * @param loginName        登录名
     * @param verificationCode 验证码
     * @param businessTypeEnum 业务类型
     * @return
     */
    ResponseResult<?> checkMobileCode(long userId, String loginName, String verificationCode, BusinessTypeEnum businessTypeEnum);

    /**
     * 邮箱验证码检查
     *
     * @param userId           -1代表还不知道用户userid
     * @param loginName        登录名
     * @param verificationCode 验证码
     * @param businessTypeEnum 业务类型
     * @return
     */
    ResponseResult<?> checkEmailCode(long userId, String loginName, String verificationCode, BusinessTypeEnum businessTypeEnum);
}
