package com.kaiqi.osprey.service.dao;

import com.kaiqi.osprey.common.mybatis.data.CrudRepository;
import com.kaiqi.osprey.service.criteria.UserNoticeRecordExample;
import com.kaiqi.osprey.service.domain.UserNoticeRecord;
import org.springframework.stereotype.Repository;

/**
 * 用户通知记录表 数据访问类
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:50
 */
@Repository
public interface UserNoticeRecordRepository extends CrudRepository<UserNoticeRecord, UserNoticeRecordExample, Long> {

}