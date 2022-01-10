package com.kaiqi.osprey.web.service;

import com.kaiqi.osprey.web.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangs
 * @title: DemoBizTest
 * @package com.kaiqi.osprey.web.biz
 * @description: TODO
 * @date 2019-06-08 21:55
 */
public class DemoServiceTest extends AbstractTest {

    @Autowired
    private DemoService demoService;

    @Test
    public void testGetProperties() {
        String result = demoService.propertiesDemo();
        Assert.assertNotNull(result);
    }

    @Test
    public void testJDBC() {
        String result = demoService.jdbcDemo();
        System.out.println(result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testCatch() {
        String result = demoService.testCatch();
        Assert.assertNotNull(result);
    }

    @Test
    public void testRedis() {
        String result = demoService.redisDemo();
        Assert.assertNotNull(result);
    }

}
