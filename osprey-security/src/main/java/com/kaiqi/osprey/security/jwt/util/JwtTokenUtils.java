package com.kaiqi.osprey.security.jwt.util;

import com.kaiqi.osprey.common.commons.entity.WebInfo;
import com.kaiqi.osprey.common.consts.WebConsts;
import com.kaiqi.osprey.common.session.model.SessionInfo;
import com.kaiqi.osprey.common.session.service.SessionService;
import com.kaiqi.osprey.common.util.IpUtil;
import com.kaiqi.osprey.security.jwt.model.JwtConsts;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.security.jwt.token.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangs
 * @date 2017/12/19
 */
@Slf4j
@Component
public class JwtTokenUtils {

    private static JwtTokenProvider jwtTokenProvider;
    private static SessionService<SessionInfo> sessionService;

    @Autowired
    public JwtTokenUtils(JwtTokenProvider jwtTokenProvider, SessionService<SessionInfo> sessionService) {
        JwtTokenUtils.jwtTokenProvider = jwtTokenProvider;
        JwtTokenUtils.sessionService = sessionService;
    }

    /**
     * 从http request中获取当前登录用户对象
     *
     * @param request HttpServletRequest
     * @return @see #JwtUserDetails
     */
    public static JwtUserDetails getCurrentLoginUser(HttpServletRequest request) {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) request.getAttribute(WebConsts.JWT_CURRENT_USER);
        if (jwtUserDetails == null) {
            return JwtUserDetails.builder().status(JwtConsts.STATUS_NOT_EXISTS).build();
        }
        return jwtUserDetails;
    }

    /**
     * 从http request中获取当前登录用户对象
     *
     * @param request HttpServletRequest
     * @return @see #JwtUserDetails
     */
    public static JwtUserDetails getCurrentLoginUserFromToken(HttpServletRequest request) {
        String tokenName = jwtTokenProvider.getJwtConfig().getRequestHeaderName();
        String token = request.getHeader(tokenName);
        if (StringUtils.isBlank(token)) {
            log.warn("jwt token is empty");
            return JwtUserDetails.builder().status(JwtConsts.STATUS_NOT_EXISTS).build();
        }
        JwtUserDetails jwtUserDetails = jwtTokenProvider.getJwtUserDetails(token);
        if (jwtUserDetails.getStatus().equals(JwtConsts.STATUS_NOT_EXISTS)) {
            log.warn("jwt token is expired");
            return JwtUserDetails.builder().status(JwtConsts.STATUS_NOT_EXISTS).build();
        }

        //二步验证token
        if (jwtUserDetails.getStatus().equals(JwtConsts.STATUS_TWO_FACTOR)) {
            return jwtUserDetails;
        }

        SessionInfo session = sessionService.getByToken(token);
        if (session == null) {
            log.warn("jwt token is expired");
            return JwtUserDetails.builder().status(JwtConsts.STATUS_NOT_EXISTS).build();
        }
        jwtUserDetails.setUsername(session.getUsername());
        jwtUserDetails.setStatus(session.getStatus());
        jwtUserDetails.setFrozen(session.getFrozen());
        return jwtUserDetails;
    }

    /**
     * 生成jwt token 并 创建登录session记录
     *
     * @param jwtUserDetails {@link JwtUserDetails}
     * @param wenInfo        http request
     * @return access token
     */
    public static String generateTokenAndCreateSession(JwtUserDetails jwtUserDetails, WebInfo wenInfo) {
        String accessToken = generateToken(jwtUserDetails, wenInfo);
        createSession(jwtUserDetails, accessToken);
        return accessToken;
    }

    /**
     * 生成jwt token
     *
     * @param jwtUserDetails {@link JwtUserDetails}
     * @param webInfo        http request
     * @return access token
     */
    public static String generateToken(JwtUserDetails jwtUserDetails, WebInfo webInfo) {
        jwtUserDetails.setIp(IpUtil.toLong(webInfo.getIpAddress()));
        jwtUserDetails.setDevId(webInfo.getDeviceId());
        return jwtTokenProvider.generateToken(jwtUserDetails);
    }

    /**
     * 创建登录session记录
     *
     * @param jwtUserDetails {@link JwtUserDetails}
     * @param token          access token
     */
    public static void createSession(JwtUserDetails jwtUserDetails, String token) {
        SessionInfo sessionInfo = SessionInfo
                .builder()
                .userId(jwtUserDetails.getUserId())
                .username(jwtUserDetails.getUsername())
                .status(jwtUserDetails.getStatus())
                .frozen(jwtUserDetails.getFrozen())
                .build();
        sessionService.save(token, sessionInfo);
    }

    /**
     * 更新用户登录会话状态
     *
     * @param newSession {@link SessionInfo}
     */
    public static void updateSession(SessionInfo newSession) {
        sessionService.updateByUserId(newSession);
    }

    /**
     * 清除登录会话记录
     *
     * @param request http request
     */
    public static void clearSession(HttpServletRequest request) {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) request.getAttribute(WebConsts.JWT_CURRENT_USER);
        if (jwtUserDetails != null) {
            sessionService.remove(jwtUserDetails.getUserId());
        }
    }

    /**
     * 清除登录会话记录
     *
     * @param userId 登录用户id
     */
    public static void clearSession(long userId) {
        sessionService.remove(userId);
    }

    /**
     * 根据用户ID获取当前用户登录会话信息
     *
     * @param userId 用户id
     * @return {@link SessionInfo}
     */
    public static SessionInfo getSession(long userId) {
        return sessionService.getByUserId(userId);
    }

    /**
     * 获取与登录会话有关的全局冻结业务变量
     *
     * @param key 缓存key名称
     * @return 状态值
     */
    public static Integer getGlobalFrozenStatus(String key) {
        return sessionService.getGlobalStatus(key);
    }

    /**
     * 设置与登录会话有关的全局冻结业务变量
     *
     * @param key    缓存key名称
     * @param status 状态值
     */
    public static void setGlobalFrozenStatus(String key, Integer status) {
        sessionService.setGlobalStatus(key, status);
    }

    /**
     * 删除登录会话有关的全局冻结业务变量
     *
     * @param key 缓存key名称
     */
    public static void removeGlobalFrozenStatus(String key) {
        sessionService.deleteGlobalStatus(key);
    }

}
