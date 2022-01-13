package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaCodeVO {
    private Integer areaCode;
    private Integer countryCode;
    private String name;
    private Integer status;
}