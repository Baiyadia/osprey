package com.kaiqi.osprey.web.controller;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.exception.OspreyBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangs
 * @title: IndexController
 * @package com.kaiqi.osprey.controller
 * @description: TODO
 * @date 2019-06-01 22:08
 */
@Slf4j
@RestController
@RequestMapping("/v1/osprey/index")
public class IndexController {

    @GetMapping(value = "/{walletId}")
    ResponseResult index(@PathVariable("walletId") Long walletId,
                         @RequestParam(value = "pageNum", required = false, defaultValue = "1") final int pageNum,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") final int pageSize) {
        throw new OspreyBizException(ErrorCodeEnum.TRANSACTION_ERROR);
    }

    @PostMapping(value = "/index2/{walletId}")
    ResponseResult index2(@PathVariable("walletId") Long walletId,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") final int pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") final int pageSize) {
        throw new OspreyBizException(ErrorCodeEnum.TRANSACTION_ERROR);
    }

}
