package com.kaiqi.osprey.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * IP 相关工具类
 *
 * @author wangs
 */
public class IpUtil {

    public static final String REAL_IP_HEADER = "X-Real-IP";

    /**
     * 从 {@link HttpServletRequest} 对象中获；得请求端 IP 地址。
     * 先尝试从 HTTP Header: "X-Real-IP" 中获取
     * 若失败则通过  {@link HttpServletRequest#getRemoteUser()} 获取
     *
     * @param request 请求
     * @return IP 地址的字符串表示
     */
    public static String getRealIPAddress(HttpServletRequest request) {
        String address = request.getHeader(REAL_IP_HEADER);
        String firstAddress = StringUtils.substringBefore(address, ",");
        if (StringUtils.isNotEmpty(firstAddress)) {
            return firstAddress;
        }
        return request.getRemoteAddr();
    }
}
