package com.kaiqi.osprey.common.session.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionInfo implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 登录用户名(手机号或邮箱地址)
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 用户状态0为开启，1为禁用，其他保留，默认为0
     */
    private Integer status;

    /**
     * 用户账号全局冻结状态 1已冻结;0未冻结 默认为0
     */
    private Integer frozen;

    /**
     * 是否同意了协议 0不同意 1同意
     */
    private Integer protocolAuthFlag;

    /**
     * 是否设置了交易密码 0未设置 1已设置
     */
    private Integer tradePasswordSetFlag;
}
