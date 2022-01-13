package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEmailReqVO {

    @NotNull
    private String email;
    @NotNull
    private String emailCode;
    //    @NotNull
    private String password;
    @NotNull
    private String oldEmailCode;
}
