package com.kaiqi.osprey.service;

import com.kaiqi.osprey.annotation.TestValidate;
import org.springframework.stereotype.Service;

@Service
public class DemoService extends BaseService {

    @TestValidate(desc = "测一把")
    public String testValidate(String arg) {
        try {
            System.out.println("方法执行");
        } catch (Exception e) {
            System.out.println("异常处理执行");
        } finally {
            System.out.println("finally执行");
        }
        System.out.println(serverConfig.getAddress());
        return "方法返回值";
    }

}
