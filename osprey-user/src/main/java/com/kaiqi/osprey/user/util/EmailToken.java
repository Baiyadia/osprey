package com.kaiqi.osprey.user.util;

import lombok.Getter;

/**
 * 邮箱令牌结构
 *
 * @author newex-team
 * @date 2018-07-02
 */
@Getter
public final class EmailToken {
    // 用户TOKEN
    private final String emailToken;

    // 用户ID
    private final long userId;

    // 邮件类型
    private final int type;

    // 过期时间
    private final long passTime;

    public EmailToken(final String emailToken, final long userId, final int type, final long passTime) {
        super();
        this.emailToken = emailToken;
        this.userId = userId;
        this.type = type;
        this.passTime = passTime;
    }

}

