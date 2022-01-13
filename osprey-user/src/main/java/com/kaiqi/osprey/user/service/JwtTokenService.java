package com.kaiqi.osprey.user.service;

import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.user.domain.UserDetails;

/**
 * @author wangs
 */
public interface JwtTokenService {

    /**
     * 通过用户详情对象创建jwt对象
     *
     * @param userDetails
     * @return
     */
    JwtUserDetails createJwtUserDetails(UserDetails userDetails);

}
