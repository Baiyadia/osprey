package com.kaiqi.osprey.user.service.impl;

import com.kaiqi.osprey.common.util.DateUtil;
import com.kaiqi.osprey.common.util.IdWorker;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.user.domain.UserDetails;
import com.kaiqi.osprey.user.service.JwtTokenService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author wangs
 */
@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Override
    public JwtUserDetails createJwtUserDetails(UserDetails userDetails) {
        JwtUserDetails jwtUserDetails = JwtUserDetails
                .builder()
                .sid(UUID.randomUUID().toString() + IdWorker.nextId())
                .userId(userDetails.getUserId())
                .openId(userDetails.getOpenId())
                .ip(1L)
                .devId(userDetails.getDeviceId())
                .username(userDetails.getNickName())
                .status(userDetails.getStatus())
                .created(new Date())
                .expired(DateUtil.addDays(new Date(), 7))
                .build();
        return jwtUserDetails;
    }
}
