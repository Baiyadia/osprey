package com.kaiqi.osprey.security.jwt.model;

import com.kaiqi.osprey.security.jwt.enums.BizTypeEnum;

/**
 * @author newex-team
 * @date 2018-07-04
 */
public class JwtFrozenConfig {

    private BizTypeEnum bizType;

    public BizTypeEnum getBizType() {
        return bizType;
    }

    public void setBizType(BizTypeEnum bizType) {
        this.bizType = bizType;
    }
}
