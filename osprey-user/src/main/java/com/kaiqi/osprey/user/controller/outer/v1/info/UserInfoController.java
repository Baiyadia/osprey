package com.kaiqi.osprey.user.controller.outer.v1.info;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.session.model.UserProfile;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.security.jwt.util.JwtTokenUtils;
import com.kaiqi.osprey.service.criteria.VersionPublishExample;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.domain.VersionPublish;
import com.kaiqi.osprey.service.service.UserService;
import com.kaiqi.osprey.service.service.VersionPublishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangs
 */
@Slf4j
@RestController
@RequestMapping("/v1/osprey/users/info")
@Api(value = "info", tags = "用户信息")
public class UserInfoController {

    @Autowired
    private UserService userService;
    @Autowired
    private VersionPublishService versionPublishService;

    /**
     * 用户基本信息
     */
    @ApiOperation("用户基本信息")
    @PostMapping("/profile")
    public ResponseResult profile(HttpServletRequest request) {
        try {
            JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUserFromToken(request);
            User dbUser = userService.getById(userDetails.getUserId());
//            UserSettingsExample settingsExample = new UserSettingsExample();
//            settingsExample.createCriteria().andUserIdEqualTo(dbUser.getUserId());
//            UserSettings settings = userSettingsService.getOneByExample(settingsExample);
            UserProfile profileVo = new UserProfile();
            profileVo.setOpenId(userDetails.getOpenId());
            profileVo.setEmail(userDetails.getEmail());
            profileVo.setMobile(userDetails.getMobile());
            profileVo.setUsername(userDetails.getUsername());
            profileVo.setTradePasswordFlag(userDetails.getTradePasswordSetFlag());
            profileVo.setTradePassUpdate(dbUser.getTradePassUpdate());
            profileVo.setContactCount(dbUser.getContactCount());
            return ResultUtil.success(profileVo);
        } catch (Exception e) {
            log.error("users center profile error", e);
            return ResultUtil.failure(ErrorCodeEnum.USER_CENTER_GET_PROFILE_ERROR);
        }
    }

    @ApiOperation("获取客户端版本信息")
    @RequestMapping("version")
    public ResponseResult publish(@RequestParam("type") Integer type) {
        try {
            VersionPublishExample versionPublishExample = new VersionPublishExample();
            versionPublishExample.createCriteria().andTypeEqualTo(type);
            versionPublishExample.setOrderByClause(" id desc ");
            VersionPublish versionPublish = versionPublishService.getOneByExample(versionPublishExample);
            if (ObjectUtils.isEmpty(versionPublish)) {
                return ResultUtil.failure(ErrorCodeEnum.VERSION_PUBLISH_RECORD_EMPTY);
            }
            return ResultUtil.success(versionPublish);
        } catch (Exception e) {
            return ResultUtil.failure(ErrorCodeEnum.VERSION_PUBLISH_RECORD_EMPTY);
        }
    }
}

