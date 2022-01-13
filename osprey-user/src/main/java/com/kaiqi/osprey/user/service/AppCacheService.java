package com.kaiqi.osprey.user.service;

import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;

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
     * 统计重置密码邮箱发送次数
     *
     * @param ip
     * @param deviceId
     * @param loginName
     * @param userResetPasswordEmail
     */
    void setResetSendEmailTimes(String ip, String deviceId, String loginName, BusinessTypeEnum userResetPasswordEmail);

    /**
     * 统计重置密码手机发送次数
     *
     * @param ipAddress
     * @param deviceId
     * @param loginName
     * @param userResetPasswordEmail
     */
    void setResetSendMobileTimes(String ipAddress, String deviceId, Integer areaCode, String loginName, BusinessTypeEnum userResetPasswordEmail);

    /**
     * 获取短信或者邮件-校验设备发送次数
     *
     * @param ip
     * @param deviceId
     * @param areaCode
     * @param mobile
     * @param email
     * @return
     */
    boolean checkDeviceSendTimes(String ip, String deviceId, Integer areaCode, String mobile, String email);

    /**
     * 获取短信或者邮件-记录设备发送次数
     *
     * @param deviceId
     * @param mobile
     * @param areaCode
     * @param email
     */
    void recordDeviceSendTimes(String deviceId, String mobile, Integer areaCode, String email);

    boolean checkResetIsNeedImage(String loginName, String deviceId, String ip, Integer areaCode);

    void setLoginTimesLimitCount(User user, HttpServletRequest request, String deviceId);
}
