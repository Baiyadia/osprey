package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerifyCodeReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 相关行为业务
     */
    @NotNull
    private Integer behavior;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 图片验证码
     */
    private String imageCode;
    /**
     * 验证码唯一标识
     */
    private String serialNO;
}
