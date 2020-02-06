package com.kaiqi.osprey.service.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户设置表
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSettings {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0 只需要密码
     */
    private Integer loginAuthFlag;

    /**
     * 0不开启 1开启
     */
    private Integer googleAuthFlag;

    /**
     * 邮箱验证 0不开启 1开启
     */
    private Integer emailAuthFlag;

    /**
     * 手机验证 0不开启 1开启
     */
    private Integer mobileAuthFlag;

    /**
     * 是否开启了订阅 0未开 1开启
     */
    private Integer subNotifyFlag;

    /**
     * 是否同意了协议 0不同意 1同意
     */
    private Integer protocolAuthFlag;

    /**
     * 是否设置了交易密码 0未设置 1已设置
     */
    private Integer tradePasswordSetFlag;

    /**
     * 登录密码强度级别
     */
    private Integer loginPwdStrength;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}