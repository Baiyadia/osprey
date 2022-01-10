package com.kaiqi.osprey.web.service;

import com.alibaba.fastjson.JSONObject;
import com.kaiqi.osprey.common.cache.RedisCache;
import com.kaiqi.osprey.common.redis.REDIS;
import com.kaiqi.osprey.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class DemoService extends BaseService {

    @Resource
    private UserService userService;

    @Resource
    private REDIS redis;

    /**
     * 配置读取示例
     *
     * @author wangs
     * @date 2022-01-10 14:39
     */
    public String propertiesDemo() {
        log.info(JSONObject.toJSONString(appConf.serverConfig()));
        log.info(JSONObject.toJSONString(appConf.boursesConfig()));
        return JSONObject.toJSONString(appConf.boursesConfig());
    }

    /**
     * 数据库操作示例
     *
     * @author wangs
     * @date 2022-01-10 14:39
     */
    public String jdbcDemo() {
        String result = JSONObject.toJSONString(userService.getAll());
        log.info(result);
        return result;
    }

    /**
     * redis操作示例
     *
     * @author wangs
     * @date 2022-01-10 14:39
     */
    public String redisDemo() {
        String result = redis.get("test");
        System.out.println(result);
        return result;
    }

    @RedisCache(key = "'testCatch'")
    public String testCatch() {
        return "catchValue";
    }

    public void testDemo(@Value("aaBb") String text) {
        log.info("text");
    }
}
