package com.kaiqi.osprey.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author wangs
 * @title: RedisCache
 * @package com.kaiqi.osprey.common.annotation
 * @description: TODO
 * @date 2019-06-05 17:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface RedisCache {

    enum CacheType {REDIS, LOCAL}

    CacheType valuePosition() default CacheType.REDIS;

    String key();

    long timeout() default -1;

    TimeUnit unit() default TimeUnit.SECONDS;

    String excludeCondition() default "";
}
