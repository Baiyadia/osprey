package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author wangs
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmAccountReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名(手机或邮箱)
     */
    @NotBlank
    @Length(min = 6, max = 50)
    private String loginName;

    /**
     * 验证码
     */
    private String imageCode;

    /**
     * 序列号
     */
    private String serialNO;

}
