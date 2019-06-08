package com.kaiqi.osprey.web.biz;

import com.alibaba.fastjson.JSONObject;
import com.kaiqi.osprey.common.annotation.RedisCache;
import com.kaiqi.osprey.common.annotation.ReliableTransaction;
import com.kaiqi.osprey.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemoBiz extends BaseService {

    @Autowired
    private UserService userService;

    @ReliableTransaction()
    public String testValidate(String arg) {
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

    @RedisCache(key = "'test2'", timeout = 60)
    public String getUsers() {
        return JSONObject.toJSONString(userService.getAll());
    }

}
