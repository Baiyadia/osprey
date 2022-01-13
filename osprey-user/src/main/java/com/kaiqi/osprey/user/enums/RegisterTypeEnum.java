package com.kaiqi.osprey.user.enums;

import lombok.Getter;

/**
 * 用户注册类型
 *
 * @author wangs
 */
@Getter
public enum RegisterTypeEnum {

    MOBILE(1),
    EMAIL(2);

    private final Integer type;

    RegisterTypeEnum(final int type) {
        this.type = type;
    }
}
