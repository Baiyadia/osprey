package com.kaiqi.osprey.common.ucenter.annotation;

import com.kaiqi.osprey.common.ucenter.consts.SessionConsts;

import java.lang.annotation.*;

/**
 * @author newex-team
 * @date 2018-07-05
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableTokenSession {

    /**
     * The session timeout in seconds. By default, it is set to 600 seconds (10 minutes).
     * This should be a non-negative integer.
     *
     * @return the seconds a session can be inactive before expiring
     */
    int maxInactiveIntervalInSeconds() default SessionConsts.DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS;
}
