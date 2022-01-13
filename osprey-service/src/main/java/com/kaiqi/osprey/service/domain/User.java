package com.kaiqi.osprey.service.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息表
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 公共用户id
     */
    private String openId;

    /**
     * 账号所属的主账号id 0标识是主账号
     */
    private Long parentId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户登录密码
     */
    private String password;

    /**
     * 支付密码加密hash校验值
     */
    private String tradePasswordCryptoHash;

    /**
     * 手机编号 默认 86 中国
     */
    private Integer areaCode;

    /**
     * 注册方式：手机还是邮箱
     */
    private Integer registerType;

    /**
     * 发送邮件包含的防钓鱼码
     */
    private String antiPhishingCode;

    /**
     * 注册IP
     */
    private String regIp;

    /**
     * 最后一次登IP
     */
    private String lastLoginIp;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 密码最后更新时间
     */
    private Date tradePassUpdate;

    /**
     * 联系人数量统计
     */
    private Integer contactCount;

    /**
     * 用户状态 1.开启 0是禁用
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