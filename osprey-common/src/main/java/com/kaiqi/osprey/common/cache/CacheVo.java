package com.kaiqi.osprey.common.cache;

/**
 * @author wangs
 * @title: LocalCache
 * @package com.kaiqi.osprey.common.cache
 * @description: 缓存对象
 * @date 2019-06-05 20:43
 */
public class CacheVo<T> {

    /**
     * @description: 缓存数据
     * @author wangs
     * @date 2020-08-12 15:38
     */
    private T data;

    /**
     * @description: 失效时间
     * @author wangs
     * @date 2020-08-12 15:38
     */
    private Long expireTimes;

    public CacheVo() {
    }

    public CacheVo(T data, Long expireTimes) {
        this.data = data;
        this.expireTimes = expireTimes;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getExpireTimes() {
        return expireTimes;
    }

    public void setExpireTimes(Long expireTimes) {
        this.expireTimes = expireTimes;
    }
}
