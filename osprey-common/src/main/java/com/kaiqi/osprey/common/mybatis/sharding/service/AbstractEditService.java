package com.kaiqi.osprey.common.mybatis.sharding.service;

import com.kaiqi.osprey.common.mybatis.sharding.ShardTable;
import com.kaiqi.osprey.common.mybatis.sharding.data.UpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @param <Dao>
 * @param <Po>
 * @param <Example>
 * @author wangs
 * @date 2017/12/09
 */
public abstract class AbstractEditService<Dao extends UpdateRepository<Po, Example>, Po, Example>
        implements EditService<Po, Example> {

    @Autowired
    protected Dao dao;

    @Override
    public int editById(final Po record, final ShardTable shardTable) {
        return this.dao.updateById(record, shardTable);
    }

    @Override
    public int editByExample(final Po record, final Example example, final ShardTable shardTable) {
        return this.dao.updateByExample(record, example, shardTable);
    }

    @Override
    public int batchEdit(final List<Po> records, final ShardTable shardTable) {
        return this.dao.batchUpdate(records, shardTable);
    }
}
