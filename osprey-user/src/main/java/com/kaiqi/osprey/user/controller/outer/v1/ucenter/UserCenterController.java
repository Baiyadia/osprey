package com.kaiqi.osprey.user.controller.outer.v1.ucenter;

import com.google.common.collect.Maps;
import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.entity.WebInfo;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.consts.NoticeSendLogConsts;
import com.kaiqi.osprey.common.consts.RedisConsts;
import com.kaiqi.osprey.common.consts.SwitchConsts;
import com.kaiqi.osprey.common.redis.REDIS;
import com.kaiqi.osprey.common.session.model.SessionInfo;
import com.kaiqi.osprey.common.session.model.UserProfile;
import com.kaiqi.osprey.common.util.LocaleUtil;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.common.util.StringUtil;
import com.kaiqi.osprey.common.util.WebUtil;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.security.jwt.util.JwtTokenUtils;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.domain.UserFeedback;
import com.kaiqi.osprey.service.domain.UserSettings;
import com.kaiqi.osprey.service.service.UserFeedbackService;
import com.kaiqi.osprey.service.service.UserService;
import com.kaiqi.osprey.service.service.UserSettingsService;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.enums.FeedbackTypeEnum;
import com.kaiqi.osprey.user.model.ChangeEmailReqVO;
import com.kaiqi.osprey.user.model.ChangeMobileReqVO;
import com.kaiqi.osprey.user.model.FeedbackReqVO;
import com.kaiqi.osprey.user.service.CheckCodeService;
import com.kaiqi.osprey.user.service.UserNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * @author wangs
 */
@Slf4j
@RestController
@RequestMapping("/v1/osprey/users")
public class UserCenterController {

    @Autowired
    private UserFeedbackService userFeedbackService;
    @Autowired
    private UserService userService;
    @Autowired
    private CheckCodeService checkCodeService;
    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private UserNoticeService userNoticeService;

    //用户最大反馈数量
    private static final int USER_MAX_FEEDBACK_NUMBER = 10;

    /**
     * 用户基本信息
     */
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

    /**
     * 用户反馈
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

    /**
     * 更换昵称
     */
    @PostMapping("nickname")
    public ResponseResult updateNickname(@RequestParam("nickname") String nickname,
                                         HttpServletRequest request) {
        try {
            JwtUserDetails tokenUser = JwtTokenUtils.getCurrentLoginUser(request);
            /**
             * 更新昵称
             */
            boolean result = userService.updateNickname(tokenUser.getUserId(), nickname);
            /**
             * 更新session中的昵称
             */
            if (result) {
                SessionInfo session = JwtTokenUtils.getSession(tokenUser.getUserId());
                session.setUsername(nickname);
                JwtTokenUtils.updateSession(session);
            }
            return ResultUtil.success();
        } catch (Exception e) {
            log.error("updateNickname failure", e);
            return ResultUtil.failure(ErrorCodeEnum.USER_SET_NICKNAME_ERROR);
        }
    }

    /**
     * 更换手机号/邮箱第一步 - 向旧设备手机/邮箱发送验证码
     *
     * @param request
     * @param type    1.更换手机 2更换邮箱
     * @return
     */
    @RequestMapping("changeMobileOrEmailPre")
    public ResponseResult changeMobileOrEmailPre(HttpServletRequest request,
                                                 @RequestParam("type") Integer type) {
        JwtUserDetails tokenUser = JwtTokenUtils.getCurrentLoginUserFromToken(request);
        User dbUser = userService.getById(tokenUser.getUserId());
        if (type == 1 && StringUtils.isEmpty(dbUser.getMobile())) {
            return ResultUtil.failure(ErrorCodeEnum.USER_MOBILE_NOT_BIND);
        }
        if (type == 2 && StringUtils.isEmpty(dbUser.getEmail())) {
            return ResultUtil.failure(ErrorCodeEnum.USER_EMAIL_NOT_BIND);
        }
        /**
         * 发送验证码
         */
        WebInfo webInfo = WebUtil.getWebInfo(request);
        HashMap<String, String> param = Maps.newHashMap();
        param.put("ip", webInfo.getIpAddress());
        userNoticeService.sendNoticeByUserName(
                type == 1 ? dbUser.getMobile() : dbUser.getEmail(),
                LocaleUtil.getLocale(request),
                type == 1 ? BusinessTypeEnum.USER_CHANGE_MOBILE_SEND_OLD : BusinessTypeEnum.USER_CHANGE_EMAIL_SEND_OLD,
                NoticeSendLogConsts.BUSINESS_CODE,
                dbUser,
                webInfo.getDeviceId(),
                param);
        return ResultUtil.success();
    }

    /**
     * 更换手机号/邮箱第二步 - 旧设备验证码校验
     */
    @RequestMapping("checkOldDeviceCode")
    public ResponseResult checkOldDeviceCode(@RequestParam("type") Integer type,
                                             @RequestParam("oldDeviceCode") String oldDeviceCode,
                                             HttpServletRequest request) {
        JwtUserDetails user = JwtTokenUtils.getCurrentLoginUserFromToken(request);
        User dbUser = userService.getById(user.getUserId());
        ResponseResult<?> oldCheckResult = checkCodeService.checkCode(
                user.getUserId(),
                type == 1 ? dbUser.getAreaCode() + dbUser.getMobile() : user.getEmail(),
                oldDeviceCode,
                type == 1 ? BusinessTypeEnum.USER_CHANGE_MOBILE_SEND_OLD : BusinessTypeEnum.USER_CHANGE_EMAIL_SEND_OLD,
                type);
        if (oldCheckResult.getCode() != 0) {
            return ResultUtil.failure(ErrorCodeEnum.USER_OLD_CODE_VERIFY_ERROR);
        }
        /**
         * 验证码校验完成后，生成校验码，用于第三步的校验
         */
        String signStr = UUID.randomUUID().toString().replace("-", "");
        REDIS.setEx(RedisConsts.USER_CHANGE_OLD_DEVICE + user.getUserId(), signStr, RedisConsts.DURATION_SECONDS_OF_15_MINTUES);

        Map<String, String> step1SuccessTab = Maps.newHashMap();
        step1SuccessTab.put("step2Sign", signStr);
        return ResultUtil.success(step1SuccessTab);
    }

    /**
     * 更换手机号/邮箱第三步 - 更换手机号
     */
    @RequestMapping("changeMobile")
    public ResponseResult changeMobile(HttpServletRequest request,
                                       @RequestBody @Valid ChangeMobileReqVO form) {
        JwtUserDetails tokenUser = JwtTokenUtils.getCurrentLoginUserFromToken(request);
        User dbUser = userService.getById(tokenUser.getUserId());
        /**
         * 校验第二步生成的校验码
         */
        String step2Sign = REDIS.get(RedisConsts.USER_CHANGE_OLD_DEVICE + tokenUser.getUserId());
        if (StringUtils.isEmpty(step2Sign) || !step2Sign.equals(form.getOldMobileCode())) {
            return ResultUtil.failure(ErrorCodeEnum.USER_OLD_CODE_VERIFY_ERROR);
        }
        /**
         * 再次校验验证码
         */
        ResponseResult<?> checkResult = checkCodeService.checkMobileCode(
                tokenUser.getUserId(),
                form.getAreaCode() + form.getMobile(),
                form.getMobileCode(),
                BusinessTypeEnum.USER_CHANGE_MOBILE);
        if (checkResult.getCode() != 0) {
            return ResultUtil.failure(ErrorCodeEnum.SMS_CODE_VERIFY_ERROR);
        }
        /**
         * 更新DB
         */
        User user4Update = new User();
        user4Update.setId(dbUser.getId());
        user4Update.setAreaCode(form.getAreaCode());
        user4Update.setMobile(form.getMobile());
        userService.editById(user4Update);
        /**
         * 更新session
         */
        SessionInfo session = JwtTokenUtils.getSession(tokenUser.getUserId());
        session.setMobile(form.getMobile());
        JwtTokenUtils.updateSession(session);
        return ResultUtil.success();
    }

    /**
     * 更换手机号/邮箱第三步 - 更换邮箱
     */
    @RequestMapping("changeEmail")
    public ResponseResult changeEmail(HttpServletRequest request,
                                      @RequestBody @Valid ChangeEmailReqVO form) {
        JwtUserDetails tokenUser = JwtTokenUtils.getCurrentLoginUserFromToken(request);
        User dbUser = userService.getById(tokenUser.getUserId());
        /**
         * 校验第二步生成的校验码
         */
        String step2Sign = REDIS.get(RedisConsts.USER_CHANGE_OLD_DEVICE + tokenUser.getUserId());
        if (StringUtils.isEmpty(step2Sign) || !step2Sign.equals(form.getOldEmailCode())) {
            return ResultUtil.failure(ErrorCodeEnum.USER_OLD_CODE_VERIFY_ERROR);
        }
        /**
         * 再次校验验证码
         */
        ResponseResult<?> emailResult = checkCodeService.checkEmailCode(
                tokenUser.getUserId(),
                form.getEmail(),
                form.getEmailCode(),
                BusinessTypeEnum.USER_CHANGE_EMAIL);
        if (emailResult.getCode() != 0) {
            return ResultUtil.failure(ErrorCodeEnum.EMAIL_CODE_VERIFY_ERROR);
        }
        /**
         * 更新DB
         */
        User user4Update = new User();
        user4Update.setId(dbUser.getId());
        user4Update.setEmail(form.getEmail());
        userService.editById(user4Update);
        /**
         * 更新session
         */
        SessionInfo session = JwtTokenUtils.getSession(tokenUser.getUserId());
        session.setEmail(form.getEmail());
        JwtTokenUtils.updateSession(session);
        return ResultUtil.success();
    }

    /**
     * 设置手机验证
     *
     * @param request
     * @param mobileAuth 0不验证 1验证
     * @return
     */
    @RequestMapping("setAddressVisible")
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
}

