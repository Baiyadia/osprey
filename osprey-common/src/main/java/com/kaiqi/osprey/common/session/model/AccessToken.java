package com.kaiqi.osprey.common.session.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wangs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 1L;
    private String accessToken;
    private String refreshToken;
    private UserProfile profile;
//    private List<String> scopes;
}
