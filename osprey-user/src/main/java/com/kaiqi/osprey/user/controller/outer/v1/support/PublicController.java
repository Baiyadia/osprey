package com.kaiqi.osprey.user.controller.outer.v1.support;

import com.google.common.collect.Maps;
import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.consts.NoticeSendLogConsts;
import com.kaiqi.osprey.common.util.*;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.security.jwt.util.JwtTokenUtils;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.service.UserService;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.model.EmailVerifyCodeReqVO;
import com.kaiqi.osprey.user.model.MobileVerifyCodeReqVO;
import com.kaiqi.osprey.user.service.AppCacheService;
import com.kaiqi.osprey.user.service.UserNoticeService;
import com.kaiqi.osprey.user.util.VerificationCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wangs
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/osprey/users/support/public")
public class PublicController {

    @Autowired
    private AppCacheService appCacheService;
    @Autowired
    private UserNoticeService userNoticeService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/verification-code/image")
    public ResponseResult getImageVerificationCode() {
        Map<String, Object> params = Maps.newHashMap();
        Object[] imageCode = VerificationCodeUtils.getImageCode(4);
        String code = (String) imageCode[1];
        String base64Img = ImageUtil.getBase64Image((BufferedImage) imageCode[0]);

        String serialNO = UUID.randomUUID().toString();
        params.put("base64Img", base64Img);
        params.put("serialNO", serialNO);
        appCacheService.setImageVerificationCode(serialNO, code);
        log.info("general new serialNO={} , code={}", serialNO, code);
        return ResultUtil.success(params);
    }

    /**
     * @param form
     * @param request
     * @description 无登录, 获取手机验证码
     * @date 2018/5/29 上午10:27
     */
    @PostMapping(value = "/verification-code/mobile")
    public ResponseResult getSmsVerificationCode(@RequestBody @Valid MobileVerifyCodeReqVO form,
                                                 HttpServletRequest request) {
        long userId = -1;
        BusinessTypeEnum businessTypeEnum = BusinessTypeEnum.getBehavior(form.getBehavior());
        if (ObjectUtils.isEmpty(businessTypeEnum)) {
            return ResultUtil.failure(ErrorCodeEnum.BUSINESS_SEND_CODE_UNSUPPORT);
        }

        if (businessTypeEnum.getType().equals(BusinessTypeEnum.USER_LOGIN_TYPE_MOBILE.getType()) ||
                businessTypeEnum.getType().equals(BusinessTypeEnum.USER_RESET_GRAPH_MOBILE.getType())) {
            User user = userService.getByUserName(form.getMobile());
            if (ObjectUtils.isEmpty(user)) {
                return ResultUtil.failure(ErrorCodeEnum.COMMON_USER_NOT_EXIST);
            }
            form.setAreaCode(user.getAreaCode());
        }

        // 验证手机号码是否正确
        if (!MobileUtil.checkPhoneNumber(form.getMobile(), form.getAreaCode())) {
            log.error("sendSMS, send falied! mobile format illegal. userId={}, mobile={} areaCode={}", -1, form.getMobile(), form.getAreaCode());
            return ResultUtil.failure(ErrorCodeEnum.USER_MOBILE_FORMAT_ERROR);
        }

        String deviceId = WebUtil.getDeviceId(request);
        String ip = IpUtil.getRealIPAddress(request);
        if (StringUtils.isEmpty(deviceId)) {
            log.error("getSmsVerificationCode deviceId not found response  = {}", form);
            return ResultUtil.failure(ErrorCodeEnum.COMMON_ILLEGALITY_ACCESS);
        }

        boolean checkDevice = appCacheService.overCodeSendTimesLimit(deviceId, form.getAreaCode(), form.getMobile(), null);
        ResponseResult imageResult = checkImageCode(form.getImageCode(), form.getSerialNO(), checkDevice);
        if (imageResult.getCode() != 0) {
            return imageResult;
        }

        if (businessTypeEnum.getType().equals(BusinessTypeEnum.USER_REGISTER_TYPE_MOBILE.getType())) {
            boolean isExist = userService.checkLoginName(form.getMobile());
            if (isExist) {
                return ResultUtil.failure(ErrorCodeEnum.REGISTER_USER_EXIST);
            }
        }

        if (businessTypeEnum.getType().equals(BusinessTypeEnum.USER_CHANGE_MOBILE.getType())) {
            JwtUserDetails jwtUserDetails = JwtTokenUtils.getCurrentLoginUserFromToken(request);
            if (jwtUserDetails.getStatus() == -2) {
                return ResultUtil.failure(ErrorCodeEnum.USER_NO_LOGIN);
            }
            User userByUsername = userService.getByUserName(form.getMobile());
            if (!ObjectUtils.isEmpty(userByUsername)) {
                if (!jwtUserDetails.getUserId().equals(userByUsername.getUserId())) {
                    return ResultUtil.failure(ErrorCodeEnum.MOBILE_HAS_BIND);
                } else {
                    return ResultUtil.failure(ErrorCodeEnum.MOBILE_SAME_OLD);
                }
            }
            userId = jwtUserDetails.getUserId();
        }

        // 发送短信
        boolean result;
        try {
            HashMap<String, String> param = Maps.newHashMap();
            param.put("ip", ip);
            result = userNoticeService.sendSMS(LocaleUtil.getLocale(request),
                    businessTypeEnum,
                    NoticeSendLogConsts.BUSINESS_CODE,
                    form.getMobile(),
                    form.getAreaCode(),
                    userId, param, deviceId
            );
            appCacheService.addSendCodeTimes(deviceId, form.getMobile(), form.getAreaCode(), null);
        } catch (Exception e) {
            log.error("PublicController getSmsVerificationCode error.", e);
            return ResultUtil.failure(ErrorCodeEnum.SMS_CODE_SEND_FAIL);
        }
        return result ? ResultUtil.success() : ResultUtil.failure(ErrorCodeEnum.SMS_CODE_SEND_FAIL);
    }

    /**
     * @param form
     * @param request
     * @description 无登录, 获取邮箱验证码
     * @date 2018/5/29 上午10:28
     */
    @PostMapping(value = "/verification-code/email")
    public ResponseResult getEmailVerificationCode(@RequestBody @Valid EmailVerifyCodeReqVO form,
                                                   HttpServletRequest request) {
        long userId = -1;
        BusinessTypeEnum businessTypeEnum = BusinessTypeEnum.getBehavior(form.getBehavior());
        if (ObjectUtils.isEmpty(businessTypeEnum)) {
            return ResultUtil.failure(ErrorCodeEnum.BUSINESS_SEND_CODE_UNSUPPORT);
        }
        boolean isEmail = StringUtil.isEmail(form.getEmail());
        if (!isEmail) {
            return ResultUtil.failure(ErrorCodeEnum.EMAIL_FORMAT_ERROR);
        }

        boolean result;
        try {
            String deviceId = WebUtil.getDeviceId(request);
            String ip = IpUtil.getRealIPAddress(request);
            boolean checkDevice = appCacheService.overCodeSendTimesLimit(deviceId, null, null, form.getEmail());
            ResponseResult imageResult = checkImageCode(form.getImageCode(), form.getSerialNO(), checkDevice);
            if (imageResult.getCode() != 0) {
                return imageResult;
            }

            if (businessTypeEnum.getType().equals(BusinessTypeEnum.USER_REGISTER_TYPE_EMAIL.getType()) ||
                    businessTypeEnum.getType().equals(BusinessTypeEnum.USER_RESET_GRAPH_EMAIL.getType())) {
                boolean isExsit = userService.checkLoginName(form.getEmail());
                if (isExsit) {
                    return ResultUtil.failure(ErrorCodeEnum.REGISTER_USER_EXIST);
                }
            }

            if (businessTypeEnum.getType().equals(BusinessTypeEnum.USER_CHANGE_EMAIL.getType())) {
                JwtUserDetails jwtUserDetails = JwtTokenUtils.getCurrentLoginUserFromToken(request);
                if (jwtUserDetails.getStatus() == -2) {
                    return ResultUtil.failure(ErrorCodeEnum.USER_NO_LOGIN);
                }
                User userByUsername = userService.getByUserName(form.getEmail());
                if (!ObjectUtils.isEmpty(userByUsername)) {
                    if (!jwtUserDetails.getUserId().equals(userByUsername.getUserId())) {
                        return ResultUtil.failure(ErrorCodeEnum.EMAIL_HAS_BIND);
                    } else {
                        return ResultUtil.failure(ErrorCodeEnum.EMAIL_SAME_OLD);
                    }
                }
                userId = jwtUserDetails.getUserId();
            }

            HashMap<String, String> param = Maps.newHashMap();
            param.put("ip", ip);
            appCacheService.addSendCodeTimes(deviceId, null, null, form.getEmail());
            result = userNoticeService.sendEmail(LocaleUtil.getLocale(request),
                    businessTypeEnum,
                    NoticeSendLogConsts.BUSINESS_CODE,
                    form.getEmail(),
                    userId, param
                    , deviceId);
        } catch (Exception e) {
            log.error("PublicController getEmailVerificationCode error.", e);
            return ResultUtil.failure(ErrorCodeEnum.EMAIL_CODE_SEND_FAIL);
        }
        return result ? ResultUtil.success() : ResultUtil.failure(ErrorCodeEnum.EMAIL_CODE_SEND_FAIL);
    }

    private ResponseResult checkImageCode(String imageCode, String serialNO, boolean checkDevice) {
        if (!checkDevice) {
            if (StringUtils.isEmpty(imageCode)) {
                return ResultUtil.failure(ErrorCodeEnum.NEED_IMAGE_VERIFICATION);
            }
            String imageCache = appCacheService.getImageVerificationCode(serialNO);
            log.info("imageCode = {}", imageCache);
            if (StringUtils.isEmpty(imageCache)) {
                log.error("imageCache is empty");
                return ResultUtil.failure(ErrorCodeEnum.IMAGE_CODE_CHECK_ERROR);
            }
            if (!imageCache.equalsIgnoreCase(imageCode)) {
                log.error("imageCache not equals imageCache={},imageCode={} ", imageCache, imageCode);
                return ResultUtil.failure(ErrorCodeEnum.IMAGE_CODE_CHECK_ERROR);
            }
            appCacheService.deleteImageVerificationCode(serialNO);
        }
        return ResultUtil.success();
    }

}
