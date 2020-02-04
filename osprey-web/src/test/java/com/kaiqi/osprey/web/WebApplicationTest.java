package com.kaiqi.osprey.web;

import com.kaiqi.osprey.web.biz.DemoBiz;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class WebApplicationTest extends AbstractTest {

    @Resource
    private DemoBiz demoService;

    @Test
    public void testGetProperties() {
        String result = demoService.testGetProperties();
        Assert.assertNotNull(result);
    }

    @Test
    public void testJDBC() {
        String result = demoService.testJDBC();
        Assert.assertNotNull(result);
    }

    @Test
    public void testCatch() {
        String result = demoService.testCatch();
        Assert.assertNotNull(result);
    }

    @Test
    public void testRedis() {
        String result = demoService.testRedis();
        Assert.assertNotNull(result);
    }

}
