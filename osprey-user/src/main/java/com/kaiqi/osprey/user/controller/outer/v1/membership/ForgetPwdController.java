package com.kaiqi.osprey.user.controller.outer.v1.membership;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.entity.WebInfo;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.consts.NoticeSendLogConsts;
import com.kaiqi.osprey.common.util.*;
import com.kaiqi.osprey.security.jwt.util.JwtTokenUtils;
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
import org.springframework.util.ObjectUtils;
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

    /**
     * 找回密码第一步-
     *
     * @param form
     * @param request
     * @return com.kaiqi.osprey.common.commons.ResponseResult
     * @author wangs
     * @date 2022-01-15 22:25
     */
    @PostMapping(value = "/confirm-account")
    public ResponseResult resetConfirmAccount(@RequestBody @Valid ConfirmAccountReqVO form, HttpServletRequest request) {
        try {
            /**
             * 校验登录名合法性
             */
            if (form.getLoginName().contains("@")) {
                if (!StringUtil.isEmail(form.getLoginName())) {
                    log.error("resetConfirmAccount email format error {}", form.getLoginName());
                    return ResultUtil.failure(ErrorCodeEnum.EMAIL_FORMAT_ERROR);
                }
            } else {
                boolean digit = StringUtil.isMobile(form.getLoginName());
                if (!digit) {
                    log.error("resetConfirmAccount mobile format error {}", form.getLoginName());
                    return ResultUtil.failure(ErrorCodeEnum.USER_MOBILE_FORMAT_ERROR);
                }
            }
            /**
             * 根据登录名查找
             */
            User user = userService.getByUserName(form.getLoginName());
            if (user == null) {
                log.error("user is not exist! params={}", JSON.toJSONString(form));
                return ResultUtil.failure(ErrorCodeEnum.COMMON_USER_NOT_EXIST);
            }
            /**
             * 图片验证码校验
             */
            WebInfo webInfo = WebUtil.getWebInfo(request);
            ErrorCodeEnum checkImageResult = appCacheService.resetCheckImageCode(
                    form.getLoginName(), webInfo.getDeviceId(), user.getAreaCode(), form.getImageCode(), form.getSerialNO());
            if (!ObjectUtils.isEmpty(checkImageResult)) {
                return ResultUtil.failure(checkImageResult);
            }
            /**
             * 删除图片验证码
             */
            appCacheService.deleteImageVerificationCode(form.getSerialNO());
            /**
             * 发送短信/邮件验证码
             */
            ConfirmAccountResVO result = ConfirmAccountResVO.builder().build();
            HashMap<String, String> param = Maps.newHashMap();
            param.put("ip", webInfo.getIpAddress());
            if (MobileUtil.checkPhoneNumber(form.getLoginName(), user.getAreaCode())) {
                boolean sendSMSResult = userNoticeService.sendSMS(LocaleUtil.getLocale(request), BusinessTypeEnum.USER_RESET_PASSWORD_MOBILE,
                        NoticeSendLogConsts.BUSINESS_NOTIFICATION, user.getMobile(), user.getAreaCode(), user.getUserId(), param, webInfo.getDeviceId());
                result.setShadeMobile(StringUtil.getStarMobile(user.getMobile()));
                result.setAreaCode(user.getAreaCode());
                result.setMobile(user.getMobile());
                result.setVerifyType(1);
                appCacheService.addSendCodeTimes(webInfo.getDeviceId(), form.getLoginName(), user.getAreaCode(), null);
                log.info("resetConfirmAccount sendSmsResult = {} userId = {} mobile = {}", sendSMSResult, user.getUserId(), user.getMobile());
            }
            if (StringUtil.isEmail(form.getLoginName().trim())) {
                boolean sendEmailResult = userNoticeService.sendEmail(LocaleUtil.getLocale(request), BusinessTypeEnum.USER_RESET_PASSWORD_EMAIL,
                        NoticeSendLogConsts.BUSINESS_CODE, user.getEmail(), user.getUserId(), param, webInfo.getDeviceId());
                result.setShadeEmail(StringUtil.getStarEmail(user.getEmail()));
                result.setEmail(user.getEmail());
                result.setVerifyType(2);
                appCacheService.addSendCodeTimes(webInfo.getDeviceId(), null, null, form.getLoginName());
                log.info("resetConfirmAccount sendEmailResult = {} userId = {} email = {}", sendEmailResult, user.getUserId(), user.getEmail());
            }
            /**
             * 设置loginName传递给第二步
             */
            String serialNO = UUID.randomUUID().toString();
            result.setLoginName(serialNO);
            appCacheService.setResetPwdLoginName(serialNO, form.getLoginName());
            return ResultUtil.success(result);
        } catch (Exception e) {
            log.error("confirm-account unknown error = {]", e);
            return ResultUtil.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 找回密码第二步-密码重置
     *
     * @param form
     * @param request
     * @return com.kaiqi.osprey.common.commons.ResponseResult
     * @author wangs
     * @date 2022-01-15 22:25
     */
    @PostMapping(value = "/pwd-reset")
    public ResponseResult resetLoginPassword(@RequestBody @Valid ResetPwdReqVO form, HttpServletRequest request) {
        try {
            /**
             * 获得第一步的loginName
             */
            String loginName = appCacheService.getResetPwdLoginName(form.getLoginName());
            if (StringUtils.isEmpty(loginName)) {
                log.error("resetLoginPassword password loginName is empty! loginName={}",
                        form.getPassword(), form.getConfirmPassword());
                return ResultUtil.failure(ErrorCodeEnum.COMMON_OPERATE_TIMEOUT);
            }
            form.setLoginName(loginName);
            /**
             * 密码强度校验
             */
            int level = PwdStrengthUtil.getStrengthLevel(form.getPassword());
            if (level < 2) {
                return ResultUtil.failure(ErrorCodeEnum.REGISTER_PASSWORD_SIMPLE);
            }
            /**
             * 检查两次密码输入是否一致
             */
            if (StringUtil.notEquals(form.getPassword(), form.getConfirmPassword())) {
                log.error("resetLoginPassword password not eq confirmPassword! password={},confirmPassword={}",
                        form.getPassword(), form.getConfirmPassword());
                return ResultUtil.failure(ErrorCodeEnum.REGISTER_PASSWORD_NOT_EQUAL);
            }
            /**
             * 检查用户名是否存在
             */
            User user = userService.getByUserName(form.getLoginName());
            if (ObjectUtils.isEmpty(user)) {
                log.error("resetLoginPassword is not exist! params={}", JSON.toJSONString(form));
                return ResultUtil.failure(ErrorCodeEnum.COMMON_USER_NOT_EXIST);
            }
            /**
             * 校验验证码
             */
            ResponseResult<?> checkCodeResult = checkCodeService.checkCode(user.getUserId(),
                    form.getVerifyType() == 1 ? user.getAreaCode() + form.getLoginName() : form.getLoginName(),
                    form.getVerifyType() == 1 ? form.getMobileCode() : form.getEmailCode(),
                    form.getVerifyType() == 1 ? BusinessTypeEnum.USER_RESET_PASSWORD_MOBILE : BusinessTypeEnum.USER_RESET_PASSWORD_EMAIL,
                    form.getVerifyType());
            if (checkCodeResult != null && checkCodeResult.getCode() > 0) {
                log.error("Code check error! userId={}", user.getUserId());
                return checkCodeResult;
            }
            /**
             * 重置密码
             */
            long returnValue = userService.resetPassword(user, passwordEncoder.encode(form.getPassword()));
            log.info("resetLoginPassword. Username:{},ip:{},returnValue:{}", form.getLoginName(), IpUtil.getRealIPAddress(request), returnValue);
            if (returnValue <= 0) {
                log.error("resetLoginPassword failure!username={}", form.getLoginName());
                return ResultUtil.failure(ErrorCodeEnum.PWD_SETTING_ERROR);
            }
            /**
             * 清空session
             */
            try {
                JwtTokenUtils.clearSession(user.getUserId());
            } catch (Exception e) {
                log.error("resetLoginPassword error userId={}", user.getUserId());
            }
            return ResultUtil.success();
        } catch (Exception e) {
            log.error("unknown error = {}", e);
            return ResultUtil.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }
}
