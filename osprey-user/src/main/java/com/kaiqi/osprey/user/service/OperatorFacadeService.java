package com.kaiqi.osprey.user.service;

import com.kaiqi.osprey.common.commons.entity.WebInfo;
import com.kaiqi.osprey.user.domain.UserDetails;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;

/**
 * @author wangs
 */
public interface OperatorFacadeService {

    /**
     * 记录用户登录历史 判断是否是新设备 新设备的话发送短信或者邮件 告诉用户登录了新设备
     *
     * @param token
     * @param userDetails
     * @param userRegisterTypeMobile
     */
    void recordLoginEvent(String token, UserDetails userDetails, BusinessTypeEnum userRegisterTypeMobile, WebInfo webInfo);

}
