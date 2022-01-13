package com.kaiqi.osprey.user.controller.outer.v1.membership;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.consts.NoticeSendLogConsts;
import com.kaiqi.osprey.common.util.*;
import com.kaiqi.osprey.security.jwt.token.JwtTokenUtils;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.service.UserService;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.model.ConfirmAccountReqVO;
import com.kaiqi.osprey.user.model.ConfirmAccountResVO;
import com.kaiqi.osprey.user.model.ResetPwdReqVO;
import com.kaiqi.osprey.user.service.AppCacheService;
import com.kaiqi.osprey.user.service.CheckCodeService;
import com.kaiqi.osprey.user.service.UserNoticeService;
import com.kaiqi.osprey.user.util.PwdStrengthUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author wangs
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/osprey/users/membership/forget-pwd")
public class ForgetPwdController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppCacheService appCacheService;
    @Autowired
    private UserNoticeService userNoticeService;
    @Autowired
    private CheckCodeService checkCodeService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/confirm-account")
    public ResponseResult resetConfirmAccount(@RequestBody @Valid ConfirmAccountReqVO form,
                                              HttpServletRequest request) {
        try {
            String deviceId = WebUtil.getDeviceId(request);
            String ipAddress = IpUtil.getRealIPAddress(request);

            if (form.getLoginName().contains("@")) {
                if (!StringUtil.isEmail(form.getLoginName())) {
                    ForgetPwdController.log.error("resetConfirmAccount email format error {}", form.getLoginName());
                    return ResultUtil.failure(ErrorCodeEnum.EMAIL_FORMAT_ERROR);
                }
            } else {
                boolean digit = MobileUtil.isDigit(form.getLoginName());
                if (!digit) {
                    ForgetPwdController.log.error("resetConfirmAccount mobile format error {}", form.getLoginName());
                    return ResultUtil.failure(ErrorCodeEnum.USER_MOBILE_FORMAT_ERROR);
                }
            }

            User user = userService.getByUserName(form.getLoginName());

            if (user == null) {
                ForgetPwdController.log.error("user is not exist! params={}", JSON.toJSONString(form));
                return ResultUtil.failure(ErrorCodeEnum.COMMON_USER_NOT_EXIST);
            }

            Integer areaCode = user.getAreaCode();

            if (!checkIsNeedImageVerification(form, deviceId, ipAddress, areaCode)) {
                if (StringUtils.isEmpty(form.getImageCode())) {
                    return ResultUtil.failure(ErrorCodeEnum.NEED_IMAGE_VERIFICATION);
                }
                String redisVerificationCode = appCacheService.getImageVerificationCode(form.getSerialNO());

                if (StringUtils.isBlank(redisVerificationCode)) {
                    ForgetPwdController.log.error("verification code is expired! params={}", JSON.toJSONString(form));
                    return ResultUtil.failure(ErrorCodeEnum.IMAGE_CODE_CHECK_ERROR);
                }
                if (StringUtil.notEqualsIgnoreCaseWithTrim(redisVerificationCode, form.getImageCode())) {
                    ForgetPwdController.log.error("verification code is error! params={}", JSON.toJSONString(form));
                    return ResultUtil.failure(ErrorCodeEnum.IMAGE_CODE_CHECK_ERROR);
                }
            }

            appCacheService.deleteImageVerificationCode(form.getSerialNO());

            ConfirmAccountResVO resForm = ConfirmAccountResVO.builder().build();

            HashMap<String, String> param = Maps.newHashMap();
            param.put("ip", ipAddress);

            if (StringUtil.isEmail(form.getLoginName().trim())) {
                boolean sendEmailResult = userNoticeService.sendEmail(LocaleUtil.getLocale(request), BusinessTypeEnum.USER_RESET_PASSWORD_EMAIL,
                        NoticeSendLogConsts.BUSINESS_CODE, user.getEmail(), user.getUserId(), param, deviceId);
                resForm.setShadeEmail(StringUtil.getStarEmail(user.getEmail()));
                resForm.setEmail(user.getEmail());
                resForm.setVerifyType(2);

                appCacheService.setResetSendEmailTimes(ipAddress, deviceId, form.getLoginName(), BusinessTypeEnum.USER_RESET_PASSWORD_EMAIL);

                ForgetPwdController.log.info("resetConfirmAccount sendEmailResult = {} userId = {} email = {}", sendEmailResult, user.getUserId(), user.getEmail());
            }
            if (MobileUtil.checkPhoneNumber(form.getLoginName(), user.getAreaCode())) {
                boolean sendSMSResult = userNoticeService.sendSMS(LocaleUtil.getLocale(request), BusinessTypeEnum.USER_RESET_PASSWORD_MOBILE,
                        NoticeSendLogConsts.BUSINESS_NOTIFICATION, user.getMobile(), user.getAreaCode(), user.getUserId(), param, deviceId);
                resForm.setShadeMobile(StringUtil.getStarMobile(user.getMobile()));
                resForm.setAreaCode(user.getAreaCode());
                resForm.setMobile(user.getMobile());
                resForm.setVerifyType(1);

                appCacheService.setResetSendMobileTimes(ipAddress, deviceId, user.getAreaCode(), form.getLoginName(), BusinessTypeEnum.USER_RESET_PASSWORD_EMAIL);
                ForgetPwdController.log.info("resetConfirmAccount sendSmsResult = {} userId = {} mobile = {}", sendSMSResult, user.getUserId(), user.getMobile());
            }
            String serialNO = UUID.randomUUID().toString();
            resForm.setLoginName(serialNO);
            appCacheService.setResetPwdLoginName(serialNO, form.getLoginName());

            return ResultUtil.success(resForm);
        } catch (Exception e) {
            log.error("confirm-account unknow error = {]", e);
            return ResultUtil.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }

    }

    private boolean checkIsNeedImageVerification(ConfirmAccountReqVO form, String deviceId, String ip, Integer areaCode) {
        return appCacheService.checkResetIsNeedImage(form.getLoginName(), deviceId, ip, areaCode);
    }

    @PostMapping(value = "/pwd-reset")
    public ResponseResult resetLoginPassword(@RequestBody @Valid ResetPwdReqVO form,
                                             HttpServletRequest request) {
        try {
            String loginName = appCacheService.getResetPwdLoginName(form.getLoginName());
            if (StringUtils.isEmpty(loginName)) {
                ForgetPwdController.log.error("resetLoginPassword password loginName is empty! loginName={}", form.getPassword(),
                        form.getConfirmPassword());
                return ResultUtil.failure(ErrorCodeEnum.COMMON_OPERATE_TIMEOUT);
            }

            /**
             * 密码强度校验
             */
            int level = PwdStrengthUtil.getStrengthLevel(form.getPassword());
            if (level < 2) {
                return ResultUtil.failure(ErrorCodeEnum.REGISTER_PASSWORD_SIMPLE);
            }

            form.setLoginName(loginName);
            if (StringUtil.notEquals(form.getPassword(), form.getConfirmPassword())) {
                ForgetPwdController.log.error("resetLoginPassword password not eq confirmPassword! password={},confirmPassword={}", form.getPassword(),
                        form.getConfirmPassword());
                return ResultUtil.failure(ErrorCodeEnum.REGISTER_PASSWORD_NOT_EQUAL);
            }
            String ipAddress = IpUtil.getRealIPAddress(request);
            String deviceId = WebUtil.getDeviceId(request);
            boolean exist = userService.checkLoginName(form.getLoginName());
            if (!exist) {
                ForgetPwdController.log.error("resetLoginPassword is not exist! params={}", JSON.toJSONString(form));
                return ResultUtil.failure(ErrorCodeEnum.COMMON_USER_NOT_EXIST);
            }
            User user = userService.getByUserName(form.getLoginName());
            long userId = user.getUserId();
            /**
             *   邮箱验证
             */
            if (form.getVerifyType() == 2) {
                ResponseResult<?> emailResult = checkCodeService.checkEmailCode(
                        user.getUserId(), form.getLoginName(), form.getEmailCode(), BusinessTypeEnum.USER_RESET_PASSWORD_EMAIL);
                if (emailResult != null && emailResult.getCode() > 0) {
                    ForgetPwdController.log.error("resetLoginPassword, emailCode Check error! userid={}", userId);
                    return emailResult;
                }
            }
            if (form.getVerifyType() == 1) {
                ResponseResult<?> smsResult = checkCodeService.checkMobileCode(
                        user.getUserId(), user.getAreaCode() + form.getLoginName(), form.getMobileCode(), BusinessTypeEnum.USER_RESET_PASSWORD_MOBILE);
                if (smsResult != null && smsResult.getCode() > 0) {
                    ForgetPwdController.log.error("smsResult, mobileCode check error! userid={}", userId);
                    return smsResult;
                }
            }
            long returnValue = userService.resetPassword(user, passwordEncoder.encode(form.getPassword()));
            ForgetPwdController.log.info("resetLoginPassword. Username:{},ip:{},returnValue:{}", form.getLoginName(), ipAddress, returnValue);
            if (returnValue <= 0) {
                ForgetPwdController.log.error("resetLoginPassword failure!username={}", form.getLoginName());
                return ResultUtil.failure(ErrorCodeEnum.PWD_SETTING_ERROR);
            }
            try {
                JwtTokenUtils.clearSession(userId);
            } catch (Exception e) {
                ForgetPwdController.log.error("resetLoginPassword userId={}", userId);
            }
            return ResultUtil.success();
        } catch (Exception e) {
            log.error("unknow error = {}", e);
            return ResultUtil.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }

    }
}
