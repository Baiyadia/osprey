package com.kaiqi.osprey.common.ucenter.data;

import com.kaiqi.osprey.common.ucenter.model.SessionInfo;

/**
 * @param <S>
 */
public interface SessionRepository<S extends SessionInfo> {

    /**
     * @param seconds
     */
    void setMaxInactiveInterval(long seconds);

    /**
     * @param sessionIdKey
     * @param session
     */
    void save(String sessionIdKey, S session);

    /**
     * @param sessionIdKey
     * @param userIdKey
     */
    void refresh(final String sessionIdKey, final String userIdKey);

    /**
     * @param sessionIdKey
     * @return
     */
    S findBySessionIdKey(final String sessionIdKey);

    /**
     * @param userIdKey
     * @return
     */
    S findByUserIdKey(final String userIdKey);

    /**
     * @param userIdKey
     * @return
     */
    String findSessionIdKey(final String userIdKey);

    /**
     * @param sessionIdKey
     */
    void delete(final String sessionIdKey);

    /**
     * @param sessionIdKey
     * @param userIdKey
     */
    void delete(final String sessionIdKey, final String userIdKey);

    /**
     * @param key
     * @param status
     */
    void setGlobalStatus(String key, Integer status);

    /**
     * @param key
     * @return
     */
    Integer getGlobalStatus(String key);

    /**
     * @param key
     * @return
     */
    void deleteGlobalStatus(String key);
}
