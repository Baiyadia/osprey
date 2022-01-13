package com.kaiqi.osprey.common.bizmodule;

/**
 * @author wangs
 * @title: IBizModule
 * @package com.zhuanzhuan.youpin.ypmall.module
 * @description: TODO
 * @date 2020-07-10 16:36
 */
public interface IBizModule<TR, R, C extends ModuleContext> {

    /**
     * @param [ context 任务执行上下文]
     * @return TR
     * @description: 并行任务逻辑。添加到GroupModule中的任务，由GroupModule指定的线程池执行
     * @author wangs
     * @date 2020-07-08 16:07
     */
    TR asyncTask(C context);

    /**
     * @param [ asyncTaskResult 并行任务执行的结果,
     *          result 一组任务的执行结果,
     *          context 任务执行上下文]
     * @return boolean
     * @description: 并行结果处理
     * 每一个并行任务执行完后对并行结果的逻辑处理
     * 需要向上下文中添加信息的在此方法中添加
     * 由主线程串行执行
     * @author wangs
     * @date 2020-07-08 16:05
     */
    boolean afterAsyncTask(TR asyncTaskResult, R result, C context);
}
