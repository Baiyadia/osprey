package com.kaiqi.osprey.user.controller.outer.v1.support;

import com.google.common.collect.Maps;
import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.entity.WebInfo;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.consts.NoticeSendLogConsts;
import com.kaiqi.osprey.common.consts.RedisConsts;
import com.kaiqi.osprey.common.consts.SwitchConsts;
import com.kaiqi.osprey.common.redis.REDIS;
import com.kaiqi.osprey.common.session.model.SessionInfo;
import com.kaiqi.osprey.common.util.LocaleUtil;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.common.util.WebUtil;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.security.jwt.util.JwtTokenUtils;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.service.UserService;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.model.ChangeEmailReqVO;
import com.kaiqi.osprey.user.model.ChangeMobileReqVO;
import com.kaiqi.osprey.user.model.WalletPathReqVO;
import com.kaiqi.osprey.user.service.CheckCodeService;
import com.kaiqi.osprey.user.service.UserBizService;
import com.kaiqi.osprey.user.service.UserNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wangs
 */
@Slf4j
@RestController
@RequestMapping("/v1/osprey/users/support/user-center")
@Api(value = "center", tags = "用户中心")
public class UserCenterController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserBizService userBizService;
    @Autowired
    private UserNoticeService userNoticeService;
    @Autowired
    private CheckCodeService checkCodeService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 更换昵称
     */
    @ApiOperation("更换昵称")
    @PostMapping("/nickname")
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
    @ApiOperation("更换手机号/邮箱第一步 - 向旧设备手机/邮箱发送验证码")
    @RequestMapping("/changeMobileOrEmailPre")
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
    @ApiOperation("更换手机号/邮箱第二步 - 旧设备验证码校验")
    @RequestMapping("/checkOldDeviceCode")
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
    @ApiOperation("更换手机号/邮箱第三步 - 更换手机号")
    @RequestMapping("/changeMobile")
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
    @ApiOperation("更换手机号/邮箱第三步 - 更换邮箱")
    @RequestMapping("/changeEmail")
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
     * 更新交易密码
     */
    @ApiOperation("更新交易密码")
    @PostMapping("/updateTradePwd")
    public ResponseResult updateTradePwd(@RequestBody WalletPathReqVO walletPathReqVO,
                                         HttpServletRequest request) {
        JwtUserDetails tokenUser = JwtTokenUtils.getCurrentLoginUserFromToken(request);
        try {
            if (ObjectUtils.isEmpty(tokenUser)) {
                return ResultUtil.failure(ErrorCodeEnum.USER_NO_LOGIN);
            }
            /**
             * 未设置过交易密码
             */
            if (tokenUser.getTradePasswordSetFlag() == SwitchConsts.OFF) {
                boolean result = userBizService.setTradePassword(
                        passwordEncoder.encode(walletPathReqVO.getEncryptedPass() + tokenUser.getUserId()),
                        tokenUser.getUserId(),
                        walletPathReqVO.getEncodePath());
                if (result) {
                    SessionInfo session = JwtTokenUtils.getSession(tokenUser.getUserId());
                    session.setTradePasswordSetFlag(SwitchConsts.ON);
                    JwtTokenUtils.updateSession(session);
                }
                return ResultUtil.success();
            }
            /**
             * 设置过交易密码
             */
            if (tokenUser.getTradePasswordSetFlag() == SwitchConsts.ON) {
                if (!StringUtils.isEmpty(walletPathReqVO.getValidatePass())) {
                    User dbUser = userService.getById(tokenUser.getUserId());
                    if (!passwordEncoder.matches((walletPathReqVO.getValidatePass() + tokenUser.getUserId()), dbUser.getTradePasswordCryptoHash())) {
                        return ResultUtil.failure(ErrorCodeEnum.TRADE_PASSWORD_CHECK_ERROR);
                    }
                    userBizService.setTradePassword(passwordEncoder.encode(walletPathReqVO.getEncryptedPass() + tokenUser.getUserId()),
                            tokenUser.getUserId(), walletPathReqVO.getEncodePath());
                    return ResultUtil.success();
                }
            }
            log.error("updateTradePwd error validatePass is null user {}", tokenUser.getUserId());
            return ResultUtil.failure(ErrorCodeEnum.SET_TRADE_PASSWORD_ERROR);
        } catch (Exception e) {
            log.error("updateTradePwd error userId {} e {}", tokenUser.getUserId(), e);
            return ResultUtil.failure(ErrorCodeEnum.SET_TRADE_PASSWORD_ERROR);
        }
    }
}

