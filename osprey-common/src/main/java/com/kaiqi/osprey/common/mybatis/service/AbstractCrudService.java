package com.kaiqi.osprey.common.mybatis.service;

import com.kaiqi.osprey.common.mybatis.data.CrudRepository;

import java.util.List;

/**
 * 基本增删改查(CRUD)数据访问服务基类
 *
 * @param <Dao>
 * @param <Po>
 * @param <Example>
 * @author wangs
 * @date 2017/12/09
 */
public abstract class AbstractCrudService<Dao extends CrudRepository<Po, Example, Type>, Po, Example, Type>
        extends AbstractGetService<Dao, Po, Example, Type>
        implements CrudService<Po, Example, Type> {

    @Override
    public int add(final Po record) {
        return this.dao.insert(record);
    }

    @Override
    public int batchAdd(final List<Po> records) {
        return this.dao.batchInsert(records);
    }

    @Override
    public int batchAddOnDuplicateKey(final List<Po> records) {
        return this.dao.batchInsertOnDuplicateKey(records);
    }

    @Override
    public int editById(final Po record) {
        return this.dao.updateById(record);
    }

    @Override
    public int editByExample(final Po record, final Example example) {
        return this.dao.updateByExample(record, example);
    }

    @Override
    public int batchEdit(final List<Po> records) {
        return this.dao.batchUpdate(records);
    }

    @Override
    public int removeById(final Type id) {
        return this.dao.deleteById(id);
    }

    @Override
    public int removeByExample(final Example example) {
        return this.dao.deleteByExample(example);
    }

    @Override
    public int removeIn(final List<Po> records) {
        return this.dao.deleteIn(records);
    }
}
