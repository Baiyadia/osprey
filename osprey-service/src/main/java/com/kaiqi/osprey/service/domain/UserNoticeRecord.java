package com.kaiqi.osprey.service.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户通知记录表
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserNoticeRecord {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 0 代表注册时没有uid
     */
    private Long userId;

    /**
     * 唯一对外标识
     */
    private String token;

    /**
     * 通知渠道1.手机号 2邮箱
     */
    private Integer channel;

    /**
     * 通知类型 1.注册发送验证码
     */
    private Integer noticeType;

    /**
     * 通知模板编号
     */
    private Integer templateCode;

    /**
     * 发送内容
     */
    private String params;

    /**
     * 区域号
     */
    private Integer areaCode;

    /**
     * 目标邮箱 手机号
     */
    private String target;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * imei号
     */
    private String imei;

    /**
     * 记录事件设备号
     */
    private String deviceId;

    /**
     * -1失败 0新建 1发送成功
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}