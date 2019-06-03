package com.kaiqi.osprey.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wangs
 * @title: ResponseResult
 * @package com.kaiqi.osprey.commons
 * @description: TODO
 * @date 2019-06-01 22:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult implements Serializable {

    private String result;
    private Integer errorCode;
    private String errorMsg;
    private Object data;

}
