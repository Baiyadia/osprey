package com.kaiqi.osprey.web.service;

import com.alibaba.fastjson.JSONObject;
import com.kaiqi.osprey.common.cache.RedisCache;
import com.kaiqi.osprey.common.redis.REDIS;
import com.kaiqi.osprey.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class DemoService extends BaseService {

    @Resource
    private UserService userService;

    @Resource
    private REDIS redis;

    public String propertiesDemo() {
        try {
            System.out.println("方法执行");
        } catch (Exception e) {
            System.out.println("异常处理执行");
        } finally {
            System.out.println("finally执行");
        }
        System.out.println(appConf.serverConfig());
        System.out.println(appConf.bourseConfig());
        return "方法返回值";
    }

    public String jdbcDemo() {
        String result = JSONObject.toJSONString(userService.getAll());
        log.info(result);
        return result;
    }

    public String redisDemo() {
        String result = redis.get("test");
        System.out.println(result);
        return result;
    }

    @RedisCache(key = "'testCatch'")
    public String testCatch() {
        return "catchValue";
    }

}
