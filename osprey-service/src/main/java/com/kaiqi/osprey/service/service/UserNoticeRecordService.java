package com.kaiqi.osprey.service.service;

import com.kaiqi.osprey.common.mybatis.service.CrudService;
import com.kaiqi.osprey.service.criteria.UserNoticeRecordExample;
import com.kaiqi.osprey.service.domain.UserNoticeRecord;

/**
 * 用户通知记录表 服务接口
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:50
 */
public interface UserNoticeRecordService extends CrudService<UserNoticeRecord, UserNoticeRecordExample, Long> {

    UserNoticeRecord getLatestRecord(Long userId, String loginName, Integer Channel, Integer noticeType);

}