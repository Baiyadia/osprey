package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileVerifyCodeReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 相关行为业务
     */
    @NotNull
    private Integer behavior;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 区域码
     */
    private Integer areaCode;
    /**
     * 图片验证码
     */
    private String imageCode;
    /**
     * 验证码唯一标识
     */
    private String serialNO;

}
