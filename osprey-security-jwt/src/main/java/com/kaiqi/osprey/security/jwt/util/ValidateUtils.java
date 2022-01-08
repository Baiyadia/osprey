package com.kaiqi.osprey.security.jwt.util;

import com.kaiqi.osprey.common.util.IpUtil;
import com.kaiqi.osprey.security.jwt.model.JwtConsts;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangs
 * @date 2018-07-08
 */
public class ValidateUtils {

    /**
     * @param request
     * @return
     */
    public static Long getRequestIP(final HttpServletRequest request) {
        return IpUtil.toLong(IpUtil.getRealIPAddress(request));
    }

    /**
     * @param request
     * @return
     */
    public static String getDeviceId(final HttpServletRequest request) {
        return StringUtils.defaultIfBlank(request.getHeader(JwtConsts.X_DEV_ID), StringUtils.EMPTY);
    }
}
