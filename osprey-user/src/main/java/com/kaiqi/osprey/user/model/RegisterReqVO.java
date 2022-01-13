package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterReqVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 区域码
     */
    private Integer areaCode;
    /**
     * 手机
     */
    @Length(min = 6, max = 20)
    private String mobile;
    /**
     * 邮箱
     */
    @Email
    @Length(min = 6, max = 30)
    private String email;
    /**
     * 验证码
     */
    @NotBlank
    @Length(min = 6, max = 6)
    private String verificationCode;
    /**
     * 密码
     */
    @NotBlank
    @Length(min = 6, max = 18)
    private String password;
    /**
     * 邀请码
     */
    private String inviteCode;
}
