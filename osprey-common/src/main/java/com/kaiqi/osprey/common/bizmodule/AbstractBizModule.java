package com.kaiqi.osprey.common.bizmodule;

import lombok.Getter;

/**
 * @author wangs
 * @title: IBizModule
 * @package com.zhuanzhuan.youpin.ypmall.module
 * @description: 业务模块类
 * <T> 调用的入参
 * <TR> 并行调用的返回
 * <R> 任务的返回
 * <C> 上下文
 * @date 2020-06-29 11:43
 */
@Getter
public abstract class AbstractBizModule<TP, TR, R, C extends ModuleContext> implements IBizModule<TR, R, C> {

    /**
     * 任务执行顺序
     */
    protected int order;

    /**
     * 异步任务参数
     */
    protected TP param;

    /**
     * @param [param 并行任务参数,
     *               order 并行任务顺序（任务执行时按order顺序串行执行；添加到GroupModule中的任务，afterAsyncTask方法按order顺序串行执行）]
     * @return com.zhuanzhuan.youpin.ypmall.module.IBizModule
     * @description: 构造任务
     * @author wangs
     * @date 2020-07-08 16:13
     */
    public AbstractBizModule<TP, TR, R, C> build(TP param, int order) {
        this.param = param;
        this.order = order;
        return this;
    }

    /**
     * @return java.lang.String
     * @description: 任务的唯一标识
     * 可以依据该参数从ModuleTaskGroup中获得任务对象
     * @author wangs
     * @date 2020-07-08 16:19
     */
    public abstract String taskId();

    @Override
    public String toString() {
        return "order=" + order + super.toString();
    }
}
