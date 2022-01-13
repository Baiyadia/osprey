package com.kaiqi.osprey.user.service;

import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.model.DeviceVerifyResVO;

import java.util.HashMap;

/**
 * 用户通知事件表 服务接口
 *
 * @author wangs
 * @date 2018-07-28
 */
public interface UserNoticeService {

    /**
     * 根据登录名发送验证码
     *
     * @param userName         登录名
     * @param locale           i18n
     * @param businessTypeEnum 业务类型 1.注册发送短信验证码 2.注册发送邮箱验证码 3.。。。。
     * @param businessCode     业务类型 1.短信验证码 2.活动短信 3.。。。
     * @param user             user实体
     * @param param            参数列表
     * @param deviceId         设备号
     * @return
     */
    DeviceVerifyResVO sendNoticeByUserName(String userName, String locale, BusinessTypeEnum businessTypeEnum, int businessCode,
                                           User user, String deviceId, HashMap<String, String> param);

    /**
     * 发送短信验证码
     *
     * @param locale           i18n
     * @param businessTypeEnum 业务类型 1.注册发送短信验证码 2.注册发送邮箱验证码 3.。。。。
     * @param businessCode     业务类型 1.短信验证码 2.活动短信 3.。。。
     * @param mobile           手机号
     * @param areaCode         区域号
     * @param userId           用户id 没有为-1 未登录时的。
     * @param newHashMap       参数列表
     * @param deviceId         设备号
     * @return
     */
    boolean sendSMS(String locale, BusinessTypeEnum businessTypeEnum, int businessCode, String mobile, Integer areaCode, long userId, HashMap<String, String> newHashMap, String deviceId);

    /**
     * 发送邮箱验证码
     *
     * @param locale           i18n
     * @param businessTypeEnum 业务类型 1.注册发送短信验证码 2.注册发送邮箱验证码 3.。。。。
     * @param businessCode     业务类型 1.短信验证码 2.活动短信 3.。。。
     * @param email            邮箱
     * @param userId           用户id 没有为-1 未登录时的。
     * @param newHashMap       参数列表
     * @param deviceId         设备号
     * @return
     */
    boolean sendEmail(String locale, BusinessTypeEnum businessTypeEnum, int businessCode, String email, long userId, HashMap<String, String> newHashMap, String deviceId);
}
