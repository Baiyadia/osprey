package com.kaiqi.osprey.common.ucenter.model;

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
     * 用户状态0为开启，1为禁用，其他保留，默认为0
     */
    private Integer status;

    /**
     * 用户账号全局冻结状态 1已冻结;0未冻结 默认为0
     */
    private Integer frozen;

    /**
     * 是否冻结现货业务1表示是,0表示否，默认为0
     */
    private Integer spotFrozen;

    /**
     * 是否冻结C2C业务1表示是,0表示否，默认为0
     */
    private Integer c2cFrozen;

    /**
     * 是否冻结合约业务1表示是,0表示否，默认为0
     */
    private Integer contractsFrozen;

    /**
     * 是否冻结资产业务1表示是,0表示否，默认为0
     */
    private Integer assetFrozen;
}
