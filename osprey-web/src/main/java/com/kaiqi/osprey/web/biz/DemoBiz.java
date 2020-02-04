package com.kaiqi.osprey.web.biz;

import com.alibaba.fastjson.JSONObject;
import com.kaiqi.osprey.common.annotation.RedisCache;
import com.kaiqi.osprey.common.redis.REDIS;
import com.kaiqi.osprey.service.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DemoBiz extends BaseService {

    @Resource
    private UserService userService;

    @Resource
    private REDIS redis;

    public String testGetProperties() {
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

    public String testJDBC() {
        String result = JSONObject.toJSONString(userService.getAll());
        System.out.println(result);
        return result;
    }

    public String testRedis() {
        String result = redis.get("test");
        System.out.println(result);
        return result;
    }

    @RedisCache(key = "'testCatch'", timeout = 60)
    public String testCatch() {
        return "catchValue";
    }

}
