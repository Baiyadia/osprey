package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author wangs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeMobileReqVO {
    @NotNull
    private String mobile;
    @NotNull
    private Integer areaCode;
    @NotNull
    private String mobileCode;
    //    @NotNull
    private String password;
    @NotNull
    private String oldMobileCode;
}
