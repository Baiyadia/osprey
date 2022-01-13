package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 登录表单
 * @author wangs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 登录名
     */
    @NotBlank
    @Length(min = 6, max = 50)
    private String username;

    /**
     * 密码
     */
    @NotBlank
    @Length(min = 6, max = 18)
    private String password;

    /**
     * 新设备校验验证码
     */
//    @Length(min = 4, max = 6)
    private String verificationCode;
    /**
     * 图片验证码 可以是4位的 也可以是6位的
     */
//    @Length(min = 4, max = 6)
    private String imageCode;
    /**
     * 图片验证码的id
     */
//    @Length(min = 6, max = 50)
    private String serialNO;

}
