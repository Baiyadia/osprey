package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author wangs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletPathReqVO {
    /**
     * 加密后的密码
     */
    @NotBlank
    @Length(min = 20, max = 256)
    private String encryptedPass;
    /**
     * 需要校验的密码
     */
    private String validatePass;
    /**
     * 加密后的path
     */
    @NotBlank
    @Length(min = 20, max = 256)
    private String encodePath;
}
