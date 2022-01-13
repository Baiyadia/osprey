package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmAccountResVO {
    /**
     * 用户名(手机或邮箱)
     */
    private String loginName;
    /**
     * 手机号加星
     */
    private String shadeMobile;
    /**
     * 邮箱加星
     */
    private String shadeEmail;
    /**
     * 区号
     */
    private Integer areaCode;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 验证类型 1手机号 2 邮箱
     */
    private Integer verifyType;

}
