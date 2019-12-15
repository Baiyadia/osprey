package com.kaiqi.osprey.common.mybatis.sharding.service;

import com.kaiqi.osprey.common.mybatis.sharding.ShardTable;
import com.kaiqi.osprey.common.mybatis.sharding.data.InsertRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @param <Dao>
 * @param <Po>
 * @author wangs
 * @date 2017/12/09
 */
public abstract class AbstractAddService<Dao extends InsertRepository<Po>, Po> implements AddService<Po> {

    @Autowired
    protected Dao dao;

    @Override
    public int add(final Po record, final ShardTable shardTable) {
        return this.dao.insert(record, shardTable);
    }

    @Override
    public int batchAdd(final List<Po> records, final ShardTable shardTable) {
        return this.dao.batchInsert(records, shardTable);
    }

    @Override
    public int batchAddOnDuplicateKey(final List<Po> records, final ShardTable shardTable) {
        return this.dao.batchInsertOnDuplicateKey(records, shardTable);
    }
}
