package com.kaiqi.osprey.user.domain;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetails {
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
    private Integer parentId;
    /**
     * 昵称
     */
    private String nickName;
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
     * 加密path
     */
    private String cryptoPath;
    /**
     * 谷歌验证码私钥
     */
    private Integer googleCode;
    /**
     * 手机编号 默认 86 中国
     */
    private Integer areaCode;
    /**
     * 记录是通过手机还是邮箱注册的
     */
    private Integer registerType;
    /**
     * 用户状态 1.开启 0是禁用
     */
    private Integer status;
    /**
     * 注册ip
     */
    private String regIp;
    /**
     * 发送邮件包含的防钓鱼码
     */
    private String antiPhishingCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
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
     * 最后一次登录ip
     */
    private String lastLoginIp;
    /**
     * 设备id
     */
    private String deviceId;
}
