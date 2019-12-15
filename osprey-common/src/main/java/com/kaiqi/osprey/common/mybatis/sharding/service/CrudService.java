package com.kaiqi.osprey.common.mybatis.sharding.service;

/**
 * 基本增删改查(CRUD)数据访问服务接口
 *
 * @param <T> Po
 * @param <U> Example
 * @param <K> key字段数据类型(Integer,Long,String等)
 * @author wangs
 * @date 2017/12/09
 */
public interface CrudService<T, U, K> extends
        AddService<T>,
        RemoveService<T, U, K>,
        EditService<T, U>,
        GetService<T, U, K> {
}
