package com.kaiqi.osprey.common.bizmodule;

/**
 * @author wangs
 * @title: IndividualModule
 * @package com.zhuanzhuan.youpin.ypmall.module
 * @description: 业务模块类
 * <TP> 并行任务参数
 * <TR> 并行任务返回值
 * <R> 任务组的公共返回结果（一组任务根据order大小按先后顺序处理同一个公共返回结果）
 * <C> 任务执行上下文（order值较大任务可以从context中获取order值较小任务向context中存放的数据）
 * @date 2020-06-29 11:43
 */
public abstract class IndividualModule<TP, TR, R, C extends ModuleContext> extends AbstractBizModule<TP, TR, R, C> {

    /**
     * @param [ context 任务执行上下文]
     * @return boolean
     * @description: 并行任务的前置校验，校验结果为true时提交任务
     * @author wangs
     * @date 2020-07-08 16:08
     */
    boolean validateAsyncTask(C context) {
        return true;
    }

    /**
     * @param [ asyncTaskResult 并行任务执行结果]
     * @return boolean
     * @description: 任务执行断言
     * 可以对并行任务执行结果做校验，根据校验结果做不同的业务处理
     * @author wangs
     * @date 2020-07-08 16:09
     */
    boolean taskAssert(TR asyncTaskResult) {
        return true;
    }

}
