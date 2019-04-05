package com.kaiqi.osprey.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceApplicationTests {

    @Resource
    private DemoService demoService;

    @Resource
    private KQService kqService;

    @Test
    public void contextLoads() {
        String result = demoService.testValidate("方法参数");
    }

    @Test
    public void testKQService() {
        int result = kqService.demo("方法参数");
    }

}
