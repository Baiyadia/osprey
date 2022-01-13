package com.kaiqi.osprey.common.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author wangs
 * @title: LocalCache
 * @package com.kaiqi.osprey.common.cache
 * @description: 本地缓存（未实现）
 * @date 2019-06-05 20:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface LocalCache {

    /**
     * key
     */
    String key();

    /**
     * 缓存是否永久不失效
     */
    boolean permanent() default false;

    /**
     * 缓存刷新频率/缓存失效时间
     */
    long flashFreq() default 1;

    /**
     * 缓存刷新频率粒度/缓存失效时间粒度
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

    /**
     * 结果为 null 时是否缓存
     */
    boolean isNullCached() default true;

    /**
     * 缓存时间增量
     * 缓存过期时间 = flashFreq + cacheIncrement
     */
    long cacheIncrement() default 0;

    /**
     * 方法执行等待时间，超时get()方法抛出异常（未实现）
     */
    long methodWaitMillis() default 100;

}
