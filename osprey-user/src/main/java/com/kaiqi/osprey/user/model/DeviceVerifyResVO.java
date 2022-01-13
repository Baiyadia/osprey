package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceVerifyResVO {

    private String email;
    private Integer areaCode;
    private String mobile;
    private Integer sendType;
    private boolean sendSuccess;
}
