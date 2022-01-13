package com.kaiqi.osprey.common.bizmodule;

import com.kaiqi.osprey.common.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;

import java.util.Comparator;
import java.util.List;

/**
 * @author wangs
 * @title: AsyncTaskHandler
 * @package com.zhuanzhuan.youpin.ypmall.module
 * @description: 异步方法执行类
 * @date 2020-06-29 16:23
 */
@Slf4j
public class ModuleTaskHandler {

    /**
     * @param [ clazz 任务类,
     *          param 并行任务参数,
     *          order 任务执行顺序]
     * @return com.zhuanzhuan.youpin.ypmall.module.AbstractBizModule
     * @description: 构建普通任务
     * 为避免多线程执行过程中操作本类对象产生线程安全问题，建议采用多例模式
     * @author wangs
     * @date 2020-07-08 16:32
     */
    public static <TP> IndividualModule buildIndividualTask(Class<? extends IndividualModule> clazz, TP param, int order) {
        IndividualModule bean = SpringBeanUtil.getBean(clazz);
        if (ObjectUtils.isEmpty(bean)) {
            return null;
        }
        return (IndividualModule) bean.build(param, order);
    }

    /**
     * @param [ taskList 任务实体列表,
     *          order 任务组执行顺序,
     *          executor 执行线程池]
     * @return com.zhuanzhuan.youpin.ypmall.module.GroupModule
     * @description: 构建任务组
     * 为避免多线程执行过程中操作本类对象产生线程安全问题，建议采用多例模式
     * @author wangs
     * @date 2020-08-04 11:07
     */
    public static GroupModule buildGroupTask(List<IndividualModule> taskList, int order, AsyncTaskExecutor executor) {
        return DefaultGroupModule.build(taskList, order, executor);
    }

    /**
     * @param [ modules 一组任务,
     *          result 任务组的公共返回结果]
     * @return void
     * @description: 提交任务并执行（使用默认context上下文）
     * @author wangs
     * @date 2020-07-08 16:28
     */
    public static <R> void submit(String logStr, List<AbstractBizModule> modules, R result) {
        submit(logStr, modules, result, new ModuleContext());
    }

    /**
     * @param [ modules 一组任务,
     *          result 任务组的公共返回结果,
     *          context 任务执行上下文]
     * @return void
     * @description: 提交任务并执行（指定context上下文）
     * 任务提交后，将按照任务顺序串行执行
     * 普通任务由主线程依次串行执行asyncTask、afterAsyncTask方法
     * 任务组中的asyncTask方法由任务组指定的线程池并行执行，asyncTask并行执行完后，按照任务顺序串行执行afterAsyncTask方法
     * @author wangs
     * @date 2020-07-08 16:28
     */
    public static <R, C extends ModuleContext> void submit(String logStr, List<AbstractBizModule> modules, R result, C context) {
        StopWatch stopWatch = new StopWatch("ModuleTaskHandler.submit");
        modules.stream()
               .sorted(Comparator.comparing(AbstractBizModule::getOrder))
               .forEach(module -> {
                   stopWatch.start("asyncTask" + module.getOrder() + ": " + module.getClass().getSimpleName());
                   Object taskResult = null;
                   try {
                       taskResult = module.asyncTask(context);
                   } catch (Exception e) {
                       log.error("do module asyncTask error ", e);
                   }
                   stopWatch.stop();

                   stopWatch.start("afterAsyncTask" + module.getOrder() + ": " + module.getClass().getSimpleName());
                   try {
                       module.afterAsyncTask(taskResult, result, context);
                   } catch (Exception e) {
                       log.error("do module afterAsyncTask error {}", logStr, e);
                   }
                   stopWatch.stop();
               });
        log.info("{}异步任务组件耗时监控 moduleSize={} {}", logStr, modules.size(), stopWatch.prettyPrint());
    }

    /**
     * @param [logStr logStr,
     *                modules 一组任务,
     *                result 任务组的公共返回结果,
     *                context 任务执行上下文,
     *                executor 默认并行任务执行线程池（任务中没有指定的时候使用）]
     * @return void
     * @description: 提交任务并执行
     * @author wangs
     * @date 2020-07-08 16:28
     */
    public static <R, C extends ModuleContext> void submitWithDefaultExecutor(String logStr, List<AbstractBizModule> modules, R result, C context, AsyncTaskExecutor executor) {
        modules.stream()
               .sorted(Comparator.comparing(AbstractBizModule::getOrder))
               .forEach(module -> {
                   try {
                       if (module instanceof GroupModule && ObjectUtils.isEmpty(((GroupModule) module).getExecutor())) {
                           ((GroupModule) module).setExecutor(executor);
                       }
                       Object taskResult = module.asyncTask(context);
                       module.afterAsyncTask(taskResult, result, context);
                   } catch (Exception e) {
                       log.error("do module task error", e);
                   }
               });
    }
}

