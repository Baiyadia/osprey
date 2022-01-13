package com.kaiqi.osprey.user.controller.outer.v1.support;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.service.criteria.VersionPublishExample;
import com.kaiqi.osprey.service.domain.VersionPublish;
import com.kaiqi.osprey.service.service.VersionPublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangs
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/coinsafe/users/support/public")
public class VersionPublishController {

    @Autowired
    private VersionPublishService versionPublishService;

    @RequestMapping("/version")
    public ResponseResult publish(@RequestParam("type") Integer type) {
        try {
            VersionPublishExample versionPublishExample = new VersionPublishExample();
            versionPublishExample.createCriteria().andTypeEqualTo(type);
            versionPublishExample.setOrderByClause(" id desc ");
            VersionPublish versionPublish = this.versionPublishService.getOneByExample(versionPublishExample);
            if (ObjectUtils.isEmpty(versionPublish)) {
                return ResultUtil.failure(ErrorCodeEnum.VERSION_PUBLISH_RECORD_EMPTY);
            }
            return ResultUtil.success(versionPublish);
        } catch (Exception e) {
            return ResultUtil.failure(ErrorCodeEnum.VERSION_PUBLISH_RECORD_EMPTY);
        }
    }
}
