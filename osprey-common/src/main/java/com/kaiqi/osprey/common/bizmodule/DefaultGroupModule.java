package com.kaiqi.osprey.common.bizmodule;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * @author wangs
 * @title: GroupModule
 * @package com.zhuanzhuan.youpin.ypmall.module
 * @description: 任务组
 * @date 2020-07-10 15:56
 */
@Slf4j
@Getter
public class DefaultGroupModule<R, C extends ModuleContext> extends GroupModule<R, C> {

    private DefaultGroupModule(List<IndividualModule> param, int order, AsyncTaskExecutor executor) {
        this.param = param;
        this.order = order;
        this.executor = executor;
    }

    public static DefaultGroupModule build(List<IndividualModule> param, int order, AsyncTaskExecutor executor) {
        return new DefaultGroupModule(param, order, executor);
    }

    @Override
    public boolean afterAsyncTask(Object asyncTaskResult, R result, C context) {
        List<IndividualModule> taskList = param
                .stream()
                .sorted(Comparator.comparing(AbstractBizModule::getOrder))
                .collect(Collectors.toList());

        if (!ObjectUtils.isEmpty(this.executor)) {
            return doTaskAsync(taskList, result, context);
        }

        return doTaskSync(taskList, result, context);
    }

    /**
     * @description: 同步执行任务列表
     * @author wangs
     * @date 2020-08-04 11:21
     */
    private boolean doTaskSync(List<IndividualModule> taskList, R result, C context) {
        StopWatch stopWatch = new StopWatch("GroupModule.doTaskSync");
        taskList.stream().forEach(module -> {
            stopWatch.start("asyncTask" + module.getOrder() + ": " + module.getClass().getSimpleName());
            Object taskResult = null;
            try {
                taskResult = module.asyncTask(context);
            } catch (Exception e) {
                log.error("do task asyncTask error", e);
            }
            stopWatch.stop();

            stopWatch.start("afterAsyncTask" + module.getOrder() + ": " + module.getClass().getSimpleName());
            try {
                module.afterAsyncTask(taskResult, result, context);
            } catch (Exception e) {
                log.error("do task afterAsyncTask error {}", e);
            }
            stopWatch.stop();
        });
        log.info("{}异步任务组件耗时监控 taskSize={} {}", taskList.size(), stopWatch.prettyPrint());
        return true;
    }

    /**
     * @description: 异步执行任务列表
     * @author wangs
     * @date 2020-08-04 11:21
     */
    private boolean doTaskAsync(List<IndividualModule> taskList, R result, C context) {
        List<CompletableFuture> futures = taskList
                .stream()
                .map(a -> supplyAsync(() -> a.asyncTask(context), executor))
                .collect(Collectors.toList());

        StopWatch stopWatch = new StopWatch("GroupModule.doTaskAsync");
        stopWatch.start("CompletableFuture.allOf");
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
        stopWatch.stop();

        for (int i = 0; i < futures.size(); i++) {
            IndividualModule task = taskList.get(i);
            stopWatch.start("afterAsyncTask" + task.getOrder() + ": " + task.getClass().getSimpleName());
            try {
                Object taskResult = futures.get(i).get();
                task.afterAsyncTask(taskResult, result, context);
            } catch (Exception e) {
                log.error("get future result error", e);
            }
            stopWatch.stop();
        }
        log.info("{}异步任务组件耗时监控 taskSize={}", taskList.size(), stopWatch.prettyPrint());
        return true;
    }

    @Override
    public String taskId() {
        return "defaultGroupModule";
    }

}
