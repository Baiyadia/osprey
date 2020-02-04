package com.kaiqi.osprey.web.biz;

import com.kaiqi.osprey.web.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangs
 * @title: DemoBizTest
 * @package com.kaiqi.osprey.web.biz
 * @description: TODO
 * @date 2019-06-08 21:55
 */
public class DemoBizTest extends AbstractTest {

    @Autowired
    DemoBiz demoBiz;

    @Test
    public void testGetUsers() {
        String users = demoBiz.testJDBC();
        System.out.println(users);
    }

}
