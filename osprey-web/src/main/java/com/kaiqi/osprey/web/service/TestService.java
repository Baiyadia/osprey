package com.kaiqi.osprey.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wangs
 * @title: TestClass
 * @package com.bj58.youpin.m.web.controllers
 * @description: TODO
 * @date 2020-02-11 15:03
 */
@Component
public class TestService {

    private String text;

    public TestService(@Value("aaBb") String text) {
        this.text = text;
    }

    //    @Component
//    @NoArgsConstructor
//    public class aa {
//
//        private String text;
//
//        public aa(@Value("aaBb") String text) {
//            this.text = text;
//        }
//
//        public String out() {
//            return text;
//        }
//    }

}
