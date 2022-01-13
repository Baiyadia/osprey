package com.kaiqi.osprey.service.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户端版本发布表
 *
 * @author youpin-team
 * @date 2020-02-06 17:58:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VersionPublish {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 版本类型 1android 2ios
     */
    private Integer type;

    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 版本下载地址
     */
    private String downUrl;

    /**
     * 版本更新内容
     */
    private String remark;

    /**
     * 是否强制更新 1强制0不强制
     */
    private Integer isForceUpdate;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 发布时间
     */
    private Date publishTime;
}