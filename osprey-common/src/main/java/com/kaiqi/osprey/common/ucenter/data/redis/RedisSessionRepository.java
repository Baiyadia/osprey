package com.kaiqi.osprey.common.ucenter.data.redis;

import com.alibaba.fastjson.JSON;
import com.kaiqi.osprey.common.ucenter.config.SessionConfig;
import com.kaiqi.osprey.common.ucenter.consts.SessionConsts;
import com.kaiqi.osprey.common.ucenter.data.SessionRepository;
import com.kaiqi.osprey.common.ucenter.model.SessionInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author newex-team
 * @date 2018-06-28
 */
public class RedisSessionRepository
        implements SessionRepository<SessionInfo> {

    @Resource(name = "ucenterStringRedisTemplate")
    private StringRedisTemplate redisTemplate;
    @Resource
    private SessionConfig sessionConfig;

    @Override
    public void setMaxInactiveInterval(final long seconds) {
        this.redisTemplate.opsForValue().set(SessionConsts.SESSION_MAX_INACTIVE_INTERVAL, String.valueOf(seconds));
    }

    @Override
    public void save(final String sessionIdKey, final SessionInfo session) {
        final String userIdKey = SessionConsts.SESSION_USER_ID_PREFIX + session.getUserId();
        this.redisTemplate.opsForValue().set(
                sessionIdKey,
                JSON.toJSONString(session),
                this.getMaxInactiveInterval(),
                TimeUnit.SECONDS
        );
        this.redisTemplate.opsForValue().set(
                userIdKey,
                sessionIdKey,
                this.getMaxInactiveInterval(),
                TimeUnit.SECONDS
        );
    }

    @Override
    public void refresh(final String sessionIdKey, final String userIdKey) {
        this.redisTemplate.expire(
                sessionIdKey,
                this.getMaxInactiveInterval(),
                TimeUnit.SECONDS
        );
        this.redisTemplate.expire(
                userIdKey,
                this.getMaxInactiveInterval(),
                TimeUnit.SECONDS
        );
    }

    @Override
    public SessionInfo findBySessionIdKey(final String sessionIdKey) {
        final String value = this.redisTemplate.opsForValue().get(sessionIdKey);
        if (StringUtils.isNotEmpty(value)) {
            return JSON.parseObject(value, SessionInfo.class);
        }
        return null;
    }

    @Override
    public SessionInfo findByUserIdKey(final String userIdKey) {
        final String sessionIdKey = this.redisTemplate.opsForValue().get(userIdKey);
        if (StringUtils.isNotEmpty(sessionIdKey)) {
            return this.findBySessionIdKey(sessionIdKey);
        }
        return null;
    }

    @Override
    public String findSessionIdKey(final String userIdKey) {
        final String sessionIdKey = this.redisTemplate.opsForValue().get(userIdKey);
        return StringUtils.defaultIfBlank(sessionIdKey, null);
    }

    @Override
    public void delete(final String sessionIdKey) {
        this.redisTemplate.delete(sessionIdKey);
    }

    @Override
    public void delete(final String sessionIdKey, final String userIdKey) {
        this.redisTemplate.delete(sessionIdKey);
        this.redisTemplate.delete(userIdKey);
    }

    @Override
    public void setGlobalStatus(final String key, final Integer status) {
        this.redisTemplate.opsForValue().set(key, status.toString());
    }

    @Override
    public Integer getGlobalStatus(final String key) {
        return NumberUtils.toInt(this.redisTemplate.opsForValue().get(key), 0);
    }

    @Override
    public void deleteGlobalStatus(final String key) {
        this.redisTemplate.delete(key);
    }

    private long getMaxInactiveInterval() {
        final String value = this.redisTemplate.opsForValue().get(SessionConsts.SESSION_MAX_INACTIVE_INTERVAL);
        return NumberUtils.toLong(value, this.sessionConfig.getMaxInactiveInterval().getSeconds());
    }
}
