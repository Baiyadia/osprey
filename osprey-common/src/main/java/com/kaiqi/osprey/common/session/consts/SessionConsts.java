package com.kaiqi.osprey.common.session.consts;

/**
 * @author wangs
 * @date 2018-06-27
 */
public class SessionConsts {

    /**
     * Default 7 days expired
     */
    public static final int DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS = 604800;

    /**
     * Session default namespace in container(redis,memcached or map)
     */
    public static final String DEFAULT_NAMESPACE = "osprey_session_";

    /**
     * Session expired max inactive interval (default 30 seconds)
     */
    public static final String SESSION_MAX_INACTIVE_INTERVAL = DEFAULT_NAMESPACE + "max_inactive_interval";

    /**
     * Prefix of Session Id("osprey_session_id_")
     */
    public static final String SESSION_ID_PREFIX = DEFAULT_NAMESPACE + "id_";

    /**
     * Prefix of Session User Id ("osprey_session_user_id_")
     */
    public static final String SESSION_USER_ID_PREFIX = DEFAULT_NAMESPACE + "user_id_";
}
