package com.kaiqi.osprey.common.session.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wangs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    private String openId;
    private String username;
    private String email;
    private String mobile;

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
    private Integer tradePasswordFlag;

    /**
     * 注册方式：手机还是邮箱
     */
    private Integer registerType;

    private Integer contactCount;
    /**
     * 密码最后更新时间
     */
    private Date tradePassUpdate;
}
