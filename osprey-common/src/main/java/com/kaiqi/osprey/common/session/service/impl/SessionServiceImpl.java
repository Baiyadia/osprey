package com.kaiqi.osprey.common.session.service.impl;

import com.kaiqi.osprey.common.session.consts.SessionConsts;
import com.kaiqi.osprey.common.session.data.SessionRepository;
import com.kaiqi.osprey.common.session.model.SessionInfo;
import com.kaiqi.osprey.common.session.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author wangs
 * @date 2018-06-29
 */
@Slf4j
@Component
public class SessionServiceImpl implements SessionService<SessionInfo> {

    @Resource(type = SessionRepository.class)
    private SessionRepository<SessionInfo> sessionRepository;

    @Override
    public void setMaxInactiveInterval(long seconds) {
        this.sessionRepository.setMaxInactiveInterval(seconds);
    }

    @Override
    public void save(String token, SessionInfo session) {
        Validate.notNull(session);
        String sessionIdKey = this.getSessionIdKey(token);
        this.sessionRepository.save(sessionIdKey, session);
    }

    @Override
    public void updateByUserId(SessionInfo newSession) {
        Validate.notNull(newSession);

        String userIdKey = this.getUserIdKey(newSession.getUserId());
        String sessionIdKey = this.sessionRepository.findSessionIdKey(userIdKey);
        SessionInfo session = this.getByUserId(newSession.getUserId());
        if (Objects.nonNull(session)) {
            if (newSession.getStatus() != null) {
                session.setStatus(newSession.getStatus());
            }
            if (newSession.getFrozen() != null) {
                session.setFrozen(newSession.getFrozen());
            }
            this.save(sessionIdKey, session);
        } else {
            log.warn("userId:{} session not found", newSession.getUserId());
        }
    }

    @Override
    public void refresh(String token, long userId) {
        String sessionIdKey = this.getSessionIdKey(token);
        String userIdKey = this.getUserIdKey(userId);
        this.sessionRepository.refresh(sessionIdKey, userIdKey);
    }

    @Override
    public SessionInfo getByToken(String token) {
        return this.sessionRepository.findBySessionIdKey(this.getSessionIdKey(token));
    }

    @Override
    public SessionInfo getByUserId(long userId) {
        return this.sessionRepository.findByUserIdKey(this.getUserIdKey(userId));
    }

    @Override
    public void remove(String token) {
        String sessionIdKey = this.getSessionIdKey(token);
        this.sessionRepository.delete(sessionIdKey);
    }

    @Override
    public void remove(String token, long userId) {
        String sessionIdKey = this.getSessionIdKey(token);
        String userIdKey = this.getUserIdKey(userId);
        this.sessionRepository.delete(sessionIdKey, userIdKey);
    }

    @Override
    public void remove(long userId) {
        String userIdKey = this.getUserIdKey(userId);
        String sessionIdKey = this.sessionRepository.findSessionIdKey(userIdKey);
        this.sessionRepository.delete(sessionIdKey, userIdKey);
    }

    @Override
    public String getSessionIdKey(String token) {
        return SessionConsts.SESSION_ID_PREFIX + DigestUtils.sha256Hex(token);
    }

    @Override
    public String getUserIdKey(long userId) {
        return SessionConsts.SESSION_USER_ID_PREFIX + userId;
    }

    @Override
    public void setGlobalStatus(String key, Integer status) {
        this.sessionRepository.setGlobalStatus(key, status);
    }

    @Override
    public Integer getGlobalStatus(String key) {
        return this.sessionRepository.getGlobalStatus(key);
    }

    @Override
    public void deleteGlobalStatus(String key) {
        this.sessionRepository.deleteGlobalStatus(key);
    }
}
