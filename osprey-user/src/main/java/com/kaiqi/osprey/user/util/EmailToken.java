package com.kaiqi.osprey.user.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 邮箱令牌结构
 *
 * @author wangs
 * @date 2018-07-02
 */
@Getter
@ToString
@AllArgsConstructor
public final class EmailToken {

    // 用户TOKEN
    private final String emailToken;

    // 用户ID
    private final long userId;

    // 邮件类型
    private final int type;

    // 过期时间
    private final long passTime;
}

