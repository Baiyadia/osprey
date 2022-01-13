package com.kaiqi.osprey.user.controller.outer.v1.ucenter;

import com.google.common.collect.Maps;
import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.consts.NoticeSendLogConsts;
import com.kaiqi.osprey.common.consts.RedisConsts;
import com.kaiqi.osprey.common.consts.SwitchConsts;
import com.kaiqi.osprey.common.redis.REDIS;
import com.kaiqi.osprey.common.session.model.SessionInfo;
import com.kaiqi.osprey.common.session.model.UserProfile;
import com.kaiqi.osprey.common.util.*;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.security.jwt.util.JwtTokenUtils;
import com.kaiqi.osprey.service.criteria.UserExample;
import com.kaiqi.osprey.service.criteria.UserFeedbackExample;
import com.kaiqi.osprey.service.criteria.UserSettingsExample;
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

    @PostMapping("/profile")
    public ResponseResult profile(HttpServletRequest request) {
        try {
            JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUserFromToken(request);
            User dbUser = this.userService.getById(userDetails.getUserId());
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
            log.error("users center profile error {}", e);
            return ResultUtil.failure(ErrorCodeEnum.USER_CENTER_GET_PROFILE_ERROR);
        }
    }

    @PostMapping("/feedback")
    public ResponseResult feedback(@RequestBody FeedbackReqVO feedbackReqVO, HttpServletRequest request) {
        try {
            JwtUserDetails currentLoginUser = JwtTokenUtils.getCurrentLoginUser(request);
            UserFeedback feedback = new UserFeedback();

            if (!StringUtils.isEmpty(feedbackReqVO.getEmail()) && !StringUtil.isEmail(feedbackReqVO.getEmail())) {
                return ResultUtil.failure(ErrorCodeEnum.EMAIL_FORMAT_ERROR);
            }
            UserFeedbackExample feedbackExample = new UserFeedbackExample();
            feedbackExample.createCriteria().andUserIdEqualTo(currentLoginUser.getUserId())
                           .andCreateTimeBetween(DateUtil.addHours(new Date(), -24), new Date());
            List<UserFeedback> byExample = this.userFeedbackService.getByExample(feedbackExample);
            if (byExample.size() >= USER_MAX_FEEDBACK_NUMBER) {
                return ResultUtil.failure(ErrorCodeEnum.USER_SUBMIT_FEEDBACK_LIMIT);
            }

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
            feedback.setUserId(currentLoginUser.getUserId());
            feedback.setType(FeedbackTypeEnum.FEEDBACK_PROBLEM.getType());
            this.userFeedbackService.add(feedback);
            return ResultUtil.success();
        } catch (Exception e) {
            log.error("users center submit feedback error {} ", e);
            return ResultUtil.failure(ErrorCodeEnum.USER_SUBMIT_FEEDBACK_FAILURE);
        }
    }

    @PostMapping("nickname")
    public ResponseResult updateNickname(@RequestParam("nickname") String nickname,
                                         HttpServletRequest request) {
        JwtUserDetails currentLoginUser = JwtTokenUtils.getCurrentLoginUser(request);
        try {
            UserExample example = new UserExample();
            example.createCriteria()
                   .andUserIdEqualTo(currentLoginUser.getUserId())
                   .andNicknameEqualTo(nickname);
            boolean result = this.userService.updateNickname(currentLoginUser.getUserId(), nickname);
            if (result) {
                SessionInfo session = JwtTokenUtils.getSession(currentLoginUser.getUserId());
                session.setUsername(nickname);
                JwtTokenUtils.updateSession(session);
            }
            return ResultUtil.success();
        } catch (Exception e) {
            log.error("updateNickname failure user {}  {}", currentLoginUser, e);
            return ResultUtil.failure(ErrorCodeEnum.USER_SET_NICKNAME_ERROR);
        }

    }

    /**
     * 向老手机号发送验证码
     *
     * @param request
     * @param type    1.更换手机 2更换邮箱
     * @return
     */
    @RequestMapping("changeMobileOrEmailPre")
    public ResponseResult changeMobileOrEmailPre(HttpServletRequest request,
                                                 @RequestParam("type") Integer type) {
        JwtUserDetails jwtUserDetails = JwtTokenUtils.getCurrentLoginUserFromToken(request);
        User user = userService.getById(jwtUserDetails.getUserId());
        String deviceId = WebUtil.getDeviceId(request);
        String ip = IpUtil.getRealIPAddress(request);
        HashMap<String, String> param = Maps.newHashMap();
        param.put("ip", ip);
        if (type == 1) {
            if (StringUtils.isEmpty(user.getMobile())) {
                return ResultUtil.failure(ErrorCodeEnum.USER_MOBILE_NOT_BIND);
            }
            this.userNoticeService.sendSMS(LocaleUtil.getLocale(request),
                    BusinessTypeEnum.USER_CHANGE_MOBILE_SEND_OLD,
                    NoticeSendLogConsts.BUSINESS_CODE,
                    user.getMobile(),
                    user.getAreaCode(),
                    user.getUserId(), param, deviceId
            );
        } else {
            if (StringUtils.isEmpty(user.getEmail())) {
                return ResultUtil.failure(ErrorCodeEnum.USER_EMAIL_NOT_BIND);
            }
            this.userNoticeService.sendEmail(LocaleUtil.getLocale(request),
                    BusinessTypeEnum.USER_CHANGE_EMAIL_SEND_OLD,
                    NoticeSendLogConsts.BUSINESS_CODE,
                    user.getEmail(),
                    user.getUserId(), param, deviceId
            );
        }
        return ResultUtil.success();
    }

    @RequestMapping("checkOldDeviceCode")
    public ResponseResult checkOldDeviceCode(@RequestParam("type") Integer type,
                                             @RequestParam("oldDeviceCode") String oldDeviceCode,
                                             HttpServletRequest request) {
        JwtUserDetails user = JwtTokenUtils.getCurrentLoginUserFromToken(request);
        User dbUser = this.userService.getById(user.getUserId());
        if (type == 1) {
            ResponseResult<?> oldCheckResult = this.checkCodeService.checkMobileCode(
                    user.getUserId(),
                    dbUser.getAreaCode() + dbUser.getMobile(),
                    oldDeviceCode,
                    BusinessTypeEnum.USER_CHANGE_MOBILE_SEND_OLD);
            if (oldCheckResult.getCode() != 0) {
                return ResultUtil.failure(ErrorCodeEnum.USER_OLD_SMS_CODE_VERIFY_ERROR);
            }
        } else {
            ResponseResult<?> oldCheckResult = this.checkCodeService.checkEmailCode(
                    user.getUserId(),
                    user.getEmail(),
                    oldDeviceCode,
                    BusinessTypeEnum.USER_CHANGE_EMAIL_SEND_OLD);
            if (oldCheckResult.getCode() != 0) {
                return ResultUtil.failure(ErrorCodeEnum.USER_OLD_EMAIL_CODE_VERIFY_ERROR);
            }
        }
        Map<String, String> step1SuccessTab = Maps.newHashMap();
        String signStr = UUID.randomUUID().toString().replace("-", "");
        REDIS.setEx(RedisConsts.USER_CHANGE_OLD_DEVICE + user.getUserId(), signStr, RedisConsts.DURATION_SECONDS_OF_15_MINTUES);
        step1SuccessTab.put("step2Sign", signStr);
        return ResultUtil.success(step1SuccessTab);
    }

    @RequestMapping("changeMobile")
    public ResponseResult changeMobile(HttpServletRequest request,
                                       @RequestBody @Valid ChangeMobileReqVO form) {

        JwtUserDetails user = JwtTokenUtils.getCurrentLoginUserFromToken(request);

        User dbUser = this.userService.getById(user.getUserId());

        String step2Sign = REDIS.get(RedisConsts.USER_CHANGE_OLD_DEVICE + user.getUserId());
        if (StringUtils.isEmpty(step2Sign) || !step2Sign.equals(form.getOldMobileCode())) {
            return ResultUtil.failure(ErrorCodeEnum.USER_OLD_SMS_CODE_VERIFY_ERROR);
        }

        ResponseResult<?> emailResult = this.checkCodeService.checkMobileCode(
                user.getUserId(),
                form.getAreaCode() + form.getMobile(),
                form.getMobileCode(),
                BusinessTypeEnum.USER_CHANGE_MOBILE);
        if (emailResult.getCode() != 0) {
            return ResultUtil.failure(ErrorCodeEnum.SMS_CODE_VERIFY_ERROR);
        }
        dbUser.setAreaCode(form.getAreaCode());
        dbUser.setMobile(form.getMobile());
        this.userService.editById(dbUser);
        SessionInfo session = JwtTokenUtils.getSession(user.getUserId());
        session.setMobile(form.getMobile());
        JwtTokenUtils.updateSession(session);
        return ResultUtil.success();
    }

    @RequestMapping("changeEmail")
    public ResponseResult changeEmail(HttpServletRequest request,
                                      @RequestBody @Valid ChangeEmailReqVO form) {

        JwtUserDetails user = JwtTokenUtils.getCurrentLoginUserFromToken(request);
        //检查密码放前面
        User dbUser = this.userService.getById(user.getUserId());
        String step2Sign = REDIS.get(RedisConsts.USER_CHANGE_OLD_DEVICE + user.getUserId());
        if (StringUtils.isEmpty(step2Sign) || !step2Sign.equals(form.getOldEmailCode())) {
            return ResultUtil.failure(ErrorCodeEnum.USER_OLD_SMS_CODE_VERIFY_ERROR);
        }

        ResponseResult<?> emailResult = this.checkCodeService.checkEmailCode(
                user.getUserId(),
                form.getEmail(),
                form.getEmailCode(),
                BusinessTypeEnum.USER_CHANGE_EMAIL);
        if (emailResult.getCode() != 0) {
            return ResultUtil.failure(ErrorCodeEnum.EMAIL_CODE_VERIFY_ERROR);
        }

        dbUser.setEmail(form.getEmail());
        this.userService.editById(dbUser);
        SessionInfo session = JwtTokenUtils.getSession(user.getUserId());
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
        JwtUserDetails user = JwtTokenUtils.getCurrentLoginUserFromToken(request);
        UserSettingsExample example = new UserSettingsExample();
        example.createCriteria().andUserIdEqualTo(user.getUserId());
        UserSettings userSettings = userSettingsService.getOneByExample(example);
        userSettings.setMobileAuthFlag(mobileAuth);
        userSettingsService.editById(userSettings);
        return ResultUtil.success();
    }
}

