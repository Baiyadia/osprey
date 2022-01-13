package com.kaiqi.osprey.common.bizmodule;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangs
 * @title: ModuleContext
 * @package com.zhuanzhuan.youpin.ypmall.module
 * @description: 任务执行上下文
 * @date 2020-06-29 11:51
 */
public class ModuleContext {

    protected final ConcurrentHashMap<String, Object> contextMap = new ConcurrentHashMap();

    public Object get(String key) {
        return contextMap.get(key);
    }

    public Object put(String key, Object value) {
        return contextMap.put(key, value);
    }

}
