package com.kaiqi.osprey.user.controller.outer.v1.membership;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.exception.OspreyBizException;
import com.kaiqi.osprey.common.util.MobileUtil;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.common.util.StringUtil;
import com.kaiqi.osprey.common.util.WebUtil;
import com.kaiqi.osprey.service.service.UserService;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.model.RegisterReqVO;
import com.kaiqi.osprey.user.service.CheckCodeService;
import com.kaiqi.osprey.user.service.UserBizService;
import com.kaiqi.osprey.user.util.PwdStrengthUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 用户注册
 *
 * @author wangs
 */
@Slf4j
@RestController
@RequestMapping("/v1/osprey/users/membership/sign-up")
public class SignUpController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserBizService userBizService;
    @Autowired
    private CheckCodeService checkCodeService;

    /**
     * 手机号注册
     */
    @PostMapping("/mobile")
    public ResponseResult registerByMobile(@RequestBody @Valid RegisterReqVO form, HttpServletRequest request) {
        try {
            /**
             * 验证手机号
             */
            String mobile = StringUtils.trim(form.getMobile());
            boolean isPhone = MobileUtil.checkPhoneNumber(mobile, form.getAreaCode());
            if (!isPhone) {
                log.error("mobile number format error! mobile={}", mobile);
                return ResultUtil.failure(ErrorCodeEnum.USER_MOBILE_FORMAT_ERROR);
            }
            /**
             * 暂不支持注册的国家
             */
            List<Integer> limitCountry = Arrays.asList(850, 963, 249, 880, 591, 593, 996);
            if (limitCountry.contains(form.getAreaCode())) {
                log.error("current country {} is unSupport", form.getAreaCode());
                return ResultUtil.failure(ErrorCodeEnum.USER_REGISTER_COUNTRY_UNSUPPORT);
            }
            /**
             * 密码强度校验
             */
            int level = PwdStrengthUtil.getStrengthLevel(form.getPassword());
            if (level < 2) {
                log.error("password strength not enough level={}", level);
                return ResultUtil.failure(ErrorCodeEnum.REGISTER_PASSWORD_SIMPLE);
            }
            /**
             * 手机号是否已存在
             */
            boolean isPhoneBound = userService.checkLoginName(mobile);
            if (isPhoneBound) {
                log.error("current mobile :{} is bound", mobile);
                return ResultUtil.failure(ErrorCodeEnum.REGISTER_USER_EXIST);
            }
            /**
             * 验证手机验证码是否正确
             */
            ResponseResult<?> smsResult = checkCodeService.checkMobileCode(
                    -1,
                    form.getAreaCode() + mobile,
                    form.getVerificationCode(),
                    BusinessTypeEnum.USER_REGISTER_TYPE_MOBILE);
            if (smsResult.getCode() > 0) {
                return smsResult;
            }
            /**
             * 用户注册
             * 需要记录用户各种信息、插入一条登录记录、发邮件、新设备登录
             */
            return userBizService.register(form, WebUtil.getWebInfo(request), BusinessTypeEnum.USER_REGISTER_TYPE_MOBILE);
        } catch (OspreyBizException e) {
            log.error("registerByMobile exception {}", e);
            return ResultUtil.failure(ErrorCodeEnum.REGISTER_SAVE_FAILED);
        } catch (Exception e) {
            log.error("sign up error = {}", e);
            return ResultUtil.failure(ErrorCodeEnum.UNKNOWN_ERROR);
        }

    }

    /**
     * 邮箱注册
     */
    @PostMapping("email")
    public ResponseResult registerByEmail(@RequestBody @Valid RegisterReqVO form, HttpServletRequest request) {
        try {
            /**
             * 验证邮箱
             */
            String email = StringUtils.trim(form.getEmail());
            if (!StringUtil.isEmail(form.getEmail())) {
                return ResultUtil.failure(ErrorCodeEnum.EMAIL_FORMAT_ERROR);
            }
            /**
             * 邮箱是否已存在
             */
            boolean isEmailBound = userService.checkLoginName(email);
            if (isEmailBound) {
                log.error("email is exists! email:{}", email);
                return ResultUtil.failure(ErrorCodeEnum.REGISTER_USER_EXIST);
            }
            /**
             * 密码强度校验
             */
            int level = PwdStrengthUtil.getStrengthLevel(form.getPassword());
            if (level < 2) {
                return ResultUtil.failure(ErrorCodeEnum.REGISTER_PASSWORD_SIMPLE);
            }
            /**
             * 验证邮箱验证码是否正确
             */
            ResponseResult<?> emailResult = checkCodeService.checkEmailCode(
                    -1,
                    form.getEmail(),
                    form.getVerificationCode(),
                    BusinessTypeEnum.USER_REGISTER_TYPE_EMAIL);
            if (emailResult.getCode() > 0) {
                return emailResult;
            }
            /**
             * 用户注册
             * 需要记录用户各种信息、插入一条登录记录、发邮件、新设备登录
             */
            return userBizService.register(form, WebUtil.getWebInfo(request), BusinessTypeEnum.USER_REGISTER_TYPE_EMAIL);
        } catch (OspreyBizException e) {
            log.error("user register by email error req= {} , ex= {}", form, e);
            return ResultUtil.failure(ErrorCodeEnum.REGISTER_SAVE_FAILED);
        }
    }
}
