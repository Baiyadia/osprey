package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lilaizhen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileVO {
    private String openId;
    private String username;
    private String email;
    private String mobile;
    private Integer protocolAuthFlag;
    private Integer subNotifyFlag;
    private Integer registerType;
    private Integer tradePasswordFlag;
    private Integer isAddressVisible;
    private Integer contactCount;
    /**
     * 密码最后更新时间
     */
    private Date tradePassUpdate;
}
