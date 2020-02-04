package com.kaiqi.osprey.security.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author newex-team
 * @date 2017/12/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserDetails {

    /**
     * 登录唯一序号uuid(从jwt中获取)
     */
    private String sid;

    /**
     * 用户id(从jwt中获取)
     */
    private Long userId;

    /**
     * openId
     */
    private String openId;

    /**
     * 登录ip地址(从jwt中获取)
     */
    private Long ip;

    /**
     * 登录设备id(从jwt中获取)
     */
    private String devId;

    /**
     * 登录用户名(手机号或邮箱地址)(从session中获取)
     */
    private String username;

    /**
     * 用户状态0为开启，1为禁用，-1表示不存在，其他保留，默认为0(从session中获取)
     */
    private Integer status;

    /**
     * 用户账号全局冻结状态 1已冻结;0未冻结 默认为0(从session中获取)
     */
    private Integer frozen;

    /**
     * 是否冻结现货业务1表示是,0表示否，默认为0(从session中获取)
     */
    private Integer spotFrozen;

    /**
     * 是否冻结C2C业务1表示是,0表示否，默认为0(从session中获取)
     */
    private Integer c2cFrozen;

    /**
     * 是否冻结合约业务1表示是,0表示否，默认为0(从session中获取)
     */
    private Integer contractsFrozen;

    /**
     * 是否冻结资产业务1表示是,0表示否，默认为0(从session中获取)
     */
    private Integer assetFrozen;

    /**
     * 登录时间(从jwt中获取)
     */
    private Date created;

    /**
     * 过期时间
     */
    private Date expired;

    /**
     * 当前用户是否不存在
     *
     * @return true|false
     */
    public boolean isNotExists() {
        return Integer.valueOf(-1).equals(this.status);
    }

    /**
     * 当前用户是否被禁用
     *
     * @return true|false
     */
    public boolean isForbidden() {
        return Integer.valueOf(1).equals(this.status);
    }

    /**
     * 当前用户所有的交易业务是否被冻结
     *
     * @return true|false
     */
    public boolean isFrozen() {
        return Integer.valueOf(1).equals(this.frozen);
    }

    /**
     * 当前用户现货交易业务是否被冻结
     *
     * @return true|false
     */
    public boolean isSpotFrozen() {
        return Integer.valueOf(1).equals(this.spotFrozen);
    }

    /**
     * 当前用户法币交易业务是否被冻结
     *
     * @return true|false
     */
    public boolean isC2CFrozen() {
        return Integer.valueOf(1).equals(this.c2cFrozen);
    }

    /**
     * 当前用户合约交易业务是否被冻结
     *
     * @return true|false
     */
    public boolean isContractsFrozen() {
        return Integer.valueOf(1).equals(this.contractsFrozen);
    }

    /**
     * 当前用户资产业务是否被冻结
     *
     * @return true|false
     */
    public boolean isAssetFrozen() {
        return Integer.valueOf(1).equals(this.assetFrozen);
    }

    /**
     * 当前登录用户是否来自同一IP请求
     *
     * @param currentIp 当前请求IP地址
     * @return true|false
     */
    public boolean isNotFromSameIp(final long currentIp) {
        return !Long.valueOf(currentIp).equals(this.ip);
    }

    /**
     * 当前登录用户是否来自同一设备请求
     *
     * @param currentDevId 当前请求设备号
     * @return true|false
     */
    public boolean isNotFromSameDevice(final String currentDevId) {
        return !StringUtils.equals(this.devId, currentDevId);
    }
}
