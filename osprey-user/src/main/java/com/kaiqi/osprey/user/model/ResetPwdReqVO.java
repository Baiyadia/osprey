package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 找回密码
 * @author wangs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPwdReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名(手机或邮箱)
     */
    @NotBlank
    @Length(min = 6, max = 50)
    private String loginName;
    /**
     * 密码
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String password;
    /**
     * 新密码
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String confirmPassword;

    /**
     * 手机验证码
     */
    private String mobileCode;

    /**
     * 邮箱验证码
     */
    private String emailCode;

    private Integer verifyType;
}
