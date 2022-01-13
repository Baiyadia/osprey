package com.kaiqi.osprey.user.enums;

import lombok.Getter;

/**
 * 用户反馈类型
 */
@Getter
public enum FeedbackTypeEnum {

    /**
     * 1.问题反馈
     */
    FEEDBACK_PROBLEM(1);

    private final int type;

    FeedbackTypeEnum(final int type) {
        this.type = type;
    }

}
