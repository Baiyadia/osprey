package com.kaiqi.osprey.common.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * @author wangs
 * @title: SpringBeanUtil
 * @package PACKAGE_NAME
 * @description: TODO
 * @date 2020-08-11 21:32
 */
@Slf4j
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        //获得所有serviceBeans
        applicationContext = ac;
    }

    public SpringBeanUtil() {
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return getApplicationContext().getBean(beanName, clazz);
    }

    public static <T> List<T> getBeans(Class<T> interfaceClass) {
        return Lists.newArrayList(getApplicationContext().getBeansOfType(interfaceClass).values());
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotation) {
        return getApplicationContext().getBeansWithAnnotation(annotation);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
}
