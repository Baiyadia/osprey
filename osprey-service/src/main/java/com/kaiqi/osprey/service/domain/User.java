package com.kaiqi.osprey.service.domain;

import lombok.*;

/**
 *
 * @author wangs
 * @date 2019-06-03 16:36:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;
}