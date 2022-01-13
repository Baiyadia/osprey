package com.kaiqi.osprey.security.jwt.util;

import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangs
 * @title: HttpSessionUtils
 * @package com.kaiqi.osprey.security.jwt.util
 * @description: TODO
 * @date 2020-02-08 13:49
 */
public class HttpSessionUtils {

    public static Long getUserId(HttpServletRequest request) {
        JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);
        if (ObjectUtils.isEmpty(userDetails) || ObjectUtils.isEmpty(userDetails.getUserId())) {
            return -1L;
        }
        return userDetails.getUserId();
    }

}
