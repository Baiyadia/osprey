package com.kaiqi.osprey.common.session.data.redis;

import com.alibaba.fastjson.JSON;
import com.kaiqi.osprey.common.redis.REDIS;
import com.kaiqi.osprey.common.session.consts.SessionConsts;
import com.kaiqi.osprey.common.session.data.SessionRepository;
import com.kaiqi.osprey.common.session.model.SessionInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wangs
 * @date 2018-06-28
 */
@Component
public class RedisSessionRepository implements SessionRepository<SessionInfo> {

    //    @Resource(name = "ucenterStringRedisTemplate")
//    private StringRedisTemplate redisTemplate;
    @Resource
    private REDIS redis;

    @Override
    public void setMaxInactiveInterval(long seconds) {
        redis.set(SessionConsts.SESSION_MAX_INACTIVE_INTERVAL, String.valueOf(seconds));
    }

    @Override
    public void save(String sessionIdKey, SessionInfo session) {
        String userIdKey = SessionConsts.SESSION_USER_ID_PREFIX + session.getUserId();
        redis.setEx(sessionIdKey, JSON.toJSONString(session), getMaxInactiveInterval());
        redis.setEx(userIdKey, sessionIdKey, getMaxInactiveInterval());
    }

    @Override
    public void refresh(String sessionIdKey, String userIdKey) {
        redis.expire(sessionIdKey, getMaxInactiveInterval());
        redis.expire(userIdKey, getMaxInactiveInterval());
    }

    @Override
    public SessionInfo findBySessionIdKey(String sessionIdKey) {
        String value = redis.get(sessionIdKey);
        if (StringUtils.isNotEmpty(value)) {
            return JSON.parseObject(value, SessionInfo.class);
        }
        return null;
    }

    @Override
    public SessionInfo findByUserIdKey(String userIdKey) {
        String sessionIdKey = redis.get(userIdKey);
        if (StringUtils.isNotEmpty(sessionIdKey)) {
            return findBySessionIdKey(sessionIdKey);
        }
        return null;
    }

    @Override
    public String findSessionIdKey(String userIdKey) {
        String sessionIdKey = redis.get(userIdKey);
        return StringUtils.defaultIfBlank(sessionIdKey, null);
    }

    @Override
    public void delete(String sessionIdKey) {
        redis.delete(sessionIdKey);
    }

    @Override
    public void delete(String sessionIdKey, String userIdKey) {
        redis.delete(sessionIdKey);
        redis.delete(userIdKey);
    }

    @Override
    public void setGlobalStatus(String key, Integer status) {
        redis.set(key, status.toString());
    }

    @Override
    public Integer getGlobalStatus(String key) {
        return NumberUtils.toInt(redis.get(key), 0);
    }

    @Override
    public void deleteGlobalStatus(String key) {
        redis.delete(key);
    }

    private long getMaxInactiveInterval() {
        String value = redis.get(SessionConsts.SESSION_MAX_INACTIVE_INTERVAL);
        return NumberUtils.toLong(value, SessionConsts.DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS);
    }
}
