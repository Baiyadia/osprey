package com.kaiqi.osprey.common.util;

import com.kaiqi.osprey.common.commons.entity.WebInfo;
import com.kaiqi.osprey.common.consts.WebConsts;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

    /**
     * 获取登录用户的设备ID
     *
     * @param request
     * @return
     */
    public static String getDeviceId(HttpServletRequest request) {
        return request.getHeader(WebConsts.X_DEV_ID);
    }

    /**
     * 获取用户的User-Agent
     *
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader(WebConsts.USER_AGENT);
    }

    /**
     * 获得web请求信息
     */
    public static WebInfo getWebInfo(HttpServletRequest request) {
        return WebInfo.builder()
                      .userAgent(getUserAgent(request))
                      .ipAddress(IpUtil.getRealIPAddress(request))
                      .deviceId(getDeviceId(request))
                      .locale(LocaleUtil.getLocale(request))
                      .build();
    }
}
