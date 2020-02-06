package com.kaiqi.osprey.service.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录记录表
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLoginRecord {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 当前登录分配的token
     */
    private String token;

    /**
     * 唯一设备标识
     */
    private String deviceId;

    /**
     * mac地址
     */
    private String macAddress;

    /**
     * imei号
     */
    private String imei;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 地域
     */
    private String region;

    /**
     * 最近一次登录ip
     */
    private String lastLoginIp;

    /**
     * user-agent
     */
    private String userAgent;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}