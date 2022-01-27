package com.kaiqi.osprey.user.controller.outer.v1.support;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.consts.SwitchConsts;
import com.kaiqi.osprey.common.session.model.SessionInfo;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.common.util.StringUtil;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.security.jwt.util.HttpSessionUtils;
import com.kaiqi.osprey.security.jwt.util.JwtTokenUtils;
import com.kaiqi.osprey.service.domain.UserFeedback;
import com.kaiqi.osprey.service.domain.UserSettings;
import com.kaiqi.osprey.service.service.UserFeedbackService;
import com.kaiqi.osprey.service.service.UserSettingsService;
import com.kaiqi.osprey.user.enums.FeedbackTypeEnum;
import com.kaiqi.osprey.user.model.FeedbackReqVO;
import com.kaiqi.osprey.user.service.UserBizService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author wangs
 */
@Slf4j
@RestController
@RequestMapping("/v1/osprey/users/support/settings")
@Api(value = "center", tags = "用户中心")
public class SettingsController {

    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private UserBizService userBizService;
    @Autowired
    private UserFeedbackService userFeedbackService;

    //用户最大反馈数量
    private static final int USER_MAX_FEEDBACK_NUMBER = 10;

    /**
     * 设置手机验证
     *
     * @param request
     * @param mobileAuth 0不验证 1验证
     * @return
     */
    @RequestMapping("addressVisible")
    public ResponseResult setAddressVisible(HttpServletRequest request,
                                            @RequestParam("mobileAuth") Integer mobileAuth) {
        JwtUserDetails tokenUser = JwtTokenUtils.getCurrentLoginUserFromToken(request);
        UserSettings userSettings = userSettingsService.getByUserId(tokenUser.getUserId());

        UserSettings settings4Update = new UserSettings();
        settings4Update.setId(userSettings.getId());
        settings4Update.setMobileAuthFlag(mobileAuth);
        userSettingsService.editById(userSettings);
        return ResultUtil.success();
    }

    /**
     * 更新用户协议
     *
     * @param isAgree 1同意 0拒绝
     * @param request http request
     * @return
     */
    @ApiOperation("更新用户协议")
    @RequestMapping("/protocol")
    public ResponseResult protocolUpdate(@RequestParam("isAgree") Integer isAgree, HttpServletRequest request) {
        if (!isAgree.equals(SwitchConsts.ON)) {
            return ResultUtil.failure(ErrorCodeEnum.USER_REJECT_PROTOCOL);
        }
        long userId = HttpSessionUtils.getUserId(request);
        boolean result = userBizService.updateUserProtocol(userId);
        if (result) {
            SessionInfo session = JwtTokenUtils.getSession(userId);
            session.setProtocolAuthFlag(SwitchConsts.ON);
            JwtTokenUtils.updateSession(session);
            return ResultUtil.success();
        }
        return ResultUtil.failure(ErrorCodeEnum.USER_AGREE_PROTOCOL_UPDATE_ERROR);
    }

    /**
     * 提交用户反馈
     */
    @PostMapping("/feedback")
    public ResponseResult feedback(@RequestBody FeedbackReqVO feedbackReqVO, HttpServletRequest request) {
        try {
            JwtUserDetails tokenUser = JwtTokenUtils.getCurrentLoginUser(request);
            if (!StringUtils.isEmpty(feedbackReqVO.getEmail()) && !StringUtil.isEmail(feedbackReqVO.getEmail())) {
                return ResultUtil.failure(ErrorCodeEnum.EMAIL_FORMAT_ERROR);
            }
            if (!StringUtils.isEmpty(feedbackReqVO.getMobile()) && !StringUtil.isMobile(feedbackReqVO.getMobile())) {
                return ResultUtil.failure(ErrorCodeEnum.USER_MOBILE_FORMAT_ERROR);
            }
            /**
             * 检查过去24小时反馈数量是否超限
             */
            List<UserFeedback> feedbackList = userFeedbackService.getUserFeedbackListHours(tokenUser.getUserId(), 24);
            if (feedbackList.size() >= USER_MAX_FEEDBACK_NUMBER) {
                return ResultUtil.failure(ErrorCodeEnum.USER_SUBMIT_FEEDBACK_LIMIT);
            }
            /**
             * 反馈记录
             */
            UserFeedback feedback = new UserFeedback();
            feedback.setContent(feedbackReqVO.getContent());
            feedback.setCreateTime(new Date());
            feedback.setStatus(SwitchConsts.ON);
            feedback.setUpdateTime(new Date());
            if (!StringUtils.isEmpty(feedbackReqVO.getEmail())) {
                feedback.setEmail(feedbackReqVO.getEmail());
            }
            if (!StringUtils.isEmpty(feedbackReqVO.getMobile())) {
                feedback.setMobile(feedbackReqVO.getMobile());
            }
            feedback.setUserId(tokenUser.getUserId());
            feedback.setType(FeedbackTypeEnum.FEEDBACK_PROBLEM.getType());
            userFeedbackService.add(feedback);
            return ResultUtil.success();
        } catch (Exception e) {
            log.error("users center submit feedback error", e);
            return ResultUtil.failure(ErrorCodeEnum.USER_SUBMIT_FEEDBACK_FAILURE);
        }
    }
}

