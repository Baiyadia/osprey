package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lilaizhen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accessToken;
    private String refreshToken;
    private UserProfileVO profile;
//    private List<String> scopes;
}
