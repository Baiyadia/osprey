package com.kaiqi.osprey.security.jwt;

import com.kaiqi.osprey.common.consts.AppEnvConsts;
import com.kaiqi.osprey.common.consts.WebConsts;
import com.kaiqi.osprey.common.session.model.SessionInfo;
import com.kaiqi.osprey.common.session.service.SessionService;
import com.kaiqi.osprey.common.util.IpUtil;
import com.kaiqi.osprey.common.util.WebUtil;
import com.kaiqi.osprey.security.jwt.exception.*;
import com.kaiqi.osprey.security.jwt.model.JwtConsts;
import com.kaiqi.osprey.security.jwt.model.JwtPublicClaims;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.security.jwt.token.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author wangs
 * @date 2017/11/20
 */
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private SessionService sessionService;

    public JwtInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        log.debug("request url:{}", request.getRequestURL());

        String tokenName = this.jwtTokenProvider.getJwtConfig().getRequestHeaderName();
        String token = request.getHeader(tokenName);
        if (StringUtils.isBlank(token)) {
            if (AppEnvConsts.isProductionMode()) {
                throw new JwtTokenNotFoundException();
            }
            //非生产环境方便调试支持从cookie中读取token
            Cookie tokenCookie = WebUtils.getCookie(request, WebConsts.TOKEN);
            if (tokenCookie == null || StringUtils.isBlank(tokenCookie.getValue())) {
                throw new JwtTokenNotFoundException();
            }
        }

        JwtUserDetails jwtUserDetails = null;
        try {
            JwtPublicClaims claims = this.jwtTokenProvider.parseClaims(token);
            jwtUserDetails = this.jwtTokenProvider.getJwtUserDetails(claims);
            this.validateAndRefresh(jwtUserDetails, token, request);
        } catch (JwtTokenForbiddenException | SessionExpiredException ex) {
            throw ex;
        } catch (Exception ex) {
            this.clearSession(token, jwtUserDetails);
            if (ex instanceof SignatureException) {
                throw new JwtTokenInvalidException(ex.getMessage(), ex);
            }
            if (ex instanceof ExpiredJwtException) {
                throw new JwtTokenExpiredException();
            }
            if (ex instanceof AbnormalAccessException) {
                throw new AbnormalAccessException(ex.getMessage(), ex);
            }
            throw new JwtTokenInvalidException(ex.getMessage(), ex);
        }

        request.setAttribute(WebConsts.JWT_CURRENT_USER, jwtUserDetails);
        request.setAttribute(WebConsts.JWT_CURRENT_USER_ID, jwtUserDetails.getUserId());
        return super.preHandle(request, response, handler);
    }

    private void validateAndRefresh(JwtUserDetails jwtUserDetails, String token, HttpServletRequest request) {
        if (jwtUserDetails.getStatus().equals(JwtConsts.STATUS_TWO_FACTOR)) {
            return;
        }

        SessionInfo session = this.sessionService.getByToken(token);
        if (Objects.isNull(session)) {
            throw new SessionExpiredException();
        }

        jwtUserDetails.setUsername(session.getUsername());
        jwtUserDetails.setStatus(session.getStatus());
        jwtUserDetails.setFrozen(session.getFrozen());

        if (jwtUserDetails.isForbidden()) {
            throw new JwtTokenForbiddenException();
        }
        //非正常访问
        if (this.jwtTokenProvider.verifyIpAndDevice(
                jwtUserDetails,
                WebUtil.getDeviceId(request),
                IpUtil.toLong(IpUtil.getRealIPAddress(request)))
        ) {
            throw new AbnormalAccessException();
        }

        this.sessionService.refresh(token, jwtUserDetails.getUserId());
    }

    private void clearSession(String token, JwtUserDetails jwtUserDetails) {
        this.sessionService.remove(token, jwtUserDetails == null ? -1 : jwtUserDetails.getUserId());
    }
}
