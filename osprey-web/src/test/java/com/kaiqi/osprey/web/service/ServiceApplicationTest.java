package com.kaiqi.osprey.web.service;

import com.kaiqi.osprey.web.AbstractTest;
import com.kaiqi.osprey.web.biz.DemoBiz;
import com.kaiqi.osprey.web.biz.KQService;
import org.junit.Test;

import javax.annotation.Resource;

public class ServiceApplicationTest extends AbstractTest {

    @Resource
    private DemoBiz demoService;

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
