package com.kaiqi.osprey.common.bizmodule;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.List;

/**
 * @author wangs
 * @title: GroupModule
 * @package com.zhuanzhuan.youpin.ypmall.module
 * @description: 任务组
 * @date 2020-07-10 15:56
 */
@Slf4j
@Getter
public abstract class GroupModule<R, C extends ModuleContext> extends AbstractBizModule<List<IndividualModule>, Object, R, C> {

    /**
     * @description: 异步任务线程池
     * @author wangs
     * @date 2020-07-12 11:08
     */
    @Setter
    protected AsyncTaskExecutor executor;

    @Override
    public Object asyncTask(C context) {
        return null;
    }
}
