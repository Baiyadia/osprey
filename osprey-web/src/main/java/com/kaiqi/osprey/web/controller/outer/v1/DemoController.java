package com.kaiqi.osprey.web.controller.outer.v1;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.web.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wangs
 * @title: DemoController
 * @package com.kaiqi.osprey.controller
 * @description: TODO
 * @date 2019-06-01 22:08
 */
@Slf4j
@RestController
@RequestMapping("/v1/osprey/demo")
public class DemoController {

    @Resource
    private DemoService demoService;

    @GetMapping(value = "/jdbcDemo/{param1}")
    ResponseResult index(@PathVariable("param1") Long param1,
                         @RequestParam(value = "pageNum", required = false, defaultValue = "1") final int pageNum,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") final int pageSize) {
        log.info("param1={}, pageNum={}, pageSize={}", param1, pageNum, pageSize);
        String result = demoService.jdbcDemo();
        return ResultUtil.success(result);
    }

    @PostMapping(value = "/index2/{walletId}")
    ResponseResult index2(@PathVariable("walletId") Long walletId,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") final int pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") final int pageSize) {
        return ResultUtil.success(demoService);
    }

}
