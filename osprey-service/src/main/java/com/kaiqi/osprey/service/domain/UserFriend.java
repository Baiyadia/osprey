package com.kaiqi.osprey.service.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户联系人表
 *
 * @author youpin-team
 * @date 2020-02-06 12:07:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFriend {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 联系人id
     */
    private Long friendId;

    /**
     * 联系人昵称
     */
    private String friendNickname;

    /**
     * 添加的联系人类型 1.普通 2.关联
     */
    private Integer type;

    /**
     * 联系人状态0删除 1正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}