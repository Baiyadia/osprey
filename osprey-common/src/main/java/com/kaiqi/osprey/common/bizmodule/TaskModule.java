package com.kaiqi.osprey.common.bizmodule;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangs
 * @title: TaskModule
 * @package com.zhuanzhuan.youpin.ypmall.module
 * @description: 任务类注解
 * 为避免多线程并发对类内部属性操作产生的线程安全问题，默认指定多例模式
 * @date 2020-06-29 12:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
//@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.INTERFACES)
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public @interface TaskModule {

}
