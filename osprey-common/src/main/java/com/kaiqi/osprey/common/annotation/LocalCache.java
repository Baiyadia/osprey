package com.kaiqi.osprey.common.annotation;

/**
 * @author wangs
 * @title: LocalCache
 * @package com.kaiqi.osprey.common.annotation
 * @description: TODO
 * @date 2019-06-05 20:43
 */
public class LocalCache {

    private Object value;
    private long expireTime;

    public LocalCache() {
    }

    public LocalCache(Object value, long expireTime) {
        this.value = value;
        this.expireTime = expireTime;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LocalCache{");
        sb.append("value=").append(this.value);
        sb.append(", expireTime=").append(this.expireTime);
        sb.append('}');
        return sb.toString();
    }

}
