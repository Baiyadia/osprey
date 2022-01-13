package com.kaiqi.osprey.common.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangs
 * @title: WebInfo
 * @package com.kaiqi.osprey.common.commons.entity
 * @description: web请求信息
 * @date 2022-01-11 10:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebInfo {

    /**
     * 设备ID
     */
    private String deviceId;
    /**
     * UA
     */
    private String userAgent;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * 国际化
     */
    private String locale;

}
