package com.kaiqi.osprey.user.model;

import lombok.*;

import java.util.Date;

/**
 * @author wangs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoVO {
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
    private String googleCode;

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
     * 密码最后更新时间
     */
    private Date tradePassUpdate;
}
