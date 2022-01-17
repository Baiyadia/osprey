package com.kaiqi.osprey.user.service;

import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.service.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangs
 */
public interface AppCacheService {

    /**
     * 设置图片验证码缓存时间
     *
     * @param serialNO 唯一id
     * @param code     验证码
     */
    void setImageVerificationCode(String serialNO, String code);

    /**
     * 获取图片验证码 serialNO
     *
     * @param serialNO
     * @return
     */
    String getImageVerificationCode(String serialNO);

    /**
     * 删除图片验证码
     *
     * @param serialNO
     */
    void deleteImageVerificationCode(String serialNO);

    /**
     * 重置密码第一步 设置loginName传递给第二步
     *
     * @param serialNO
     * @param loginName
     */
    void setResetPwdLoginName(String serialNO, String loginName);

    /**
     * 重置密码第二步 获取第一步的loginName
     *
     * @param loginName
     * @return
     */
    String getResetPwdLoginName(String loginName);

    /**
     * 获取短信或者邮件-校验设备发送次数
     *
     * @param deviceId
     * @param areaCode
     * @param mobile
     * @param email
     * @return
     */
    boolean overCodeSendTimesLimit(String deviceId, Integer areaCode, String mobile, String email);

    /**
     * 获取短信或者邮件-记录验证码发送次数
     *
     * @param deviceId
     * @param mobile
     * @param areaCode
     * @param email
     */
    void addSendCodeTimes(String deviceId, String mobile, Integer areaCode, String email);

    /**
     * 登录检查图片验证码
     *
     * @param userId
     */
    ErrorCodeEnum loginCheckImageCode(Long userId, String imageCode, String serialNo);

    /**
     * 密码重置检查图片验证码
     *
     * @param deviceId
     * @param loginName
     * @param areaCode
     */
    ErrorCodeEnum resetCheckImageCode(String loginName, String deviceId, Integer areaCode, String imageCode, String serialNo);

    /**
     * 增加密码错误次数
     *
     * @param request
     * @param deviceId
     */
    void addPwdErrorTimes(User user, HttpServletRequest request, String deviceId);
}
