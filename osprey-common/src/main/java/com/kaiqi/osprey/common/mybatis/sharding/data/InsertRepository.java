package com.kaiqi.osprey.common.mybatis.sharding.data;

import com.kaiqi.osprey.common.mybatis.sharding.ShardTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @param <T>
 * @author wangs
 * @date 2017/12/09
 */
public interface InsertRepository<T> {

    /**
     * 插入一条数据，忽略record中的ID
     *
     * @param record     pojo对象
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    int insert(@Param("record") T record, @Param("shardTable") ShardTable shardTable);

    /**
     * @param records    pojo记录集
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    int batchInsert(@Param("records") List<T> records, @Param("shardTable") ShardTable shardTable);

    /**
     * 使用mysql on duplicate key 语句插入与修改
     *
     * @param records    pojo记录集
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    int batchInsertOnDuplicateKey(@Param("records") List<T> records, @Param("shardTable") ShardTable shardTable);

}
