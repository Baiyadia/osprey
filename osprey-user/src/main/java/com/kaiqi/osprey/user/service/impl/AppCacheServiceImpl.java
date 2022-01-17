package com.kaiqi.osprey.user.service.impl;

import com.google.common.collect.Maps;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.consts.NoticeSendLogConsts;
import com.kaiqi.osprey.common.consts.RedisConsts;
import com.kaiqi.osprey.common.redis.REDIS;
import com.kaiqi.osprey.common.util.IpUtil;
import com.kaiqi.osprey.common.util.LocaleUtil;
import com.kaiqi.osprey.common.util.StringUtil;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.user.enums.BusinessTypeEnum;
import com.kaiqi.osprey.user.enums.RegisterTypeEnum;
import com.kaiqi.osprey.user.service.AppCacheService;
import com.kaiqi.osprey.user.service.UserNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wangs
 */
@Slf4j
@Service
public class AppCacheServiceImpl implements AppCacheService {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserNoticeService userNoticeService;

    private static long ONE_HOUR_SECONDS = 60 * 60;
    private static long ONE_DAY_SECONDS = 24 * ONE_HOUR_SECONDS;

    private static long ONE_HOUR_MILLIS = 60 * 60 * 1000;
    private static long ONE_DAY_MILLIS = 24 * ONE_HOUR_MILLIS;

    // 一小时内设备发送验证码次数限制
    private static final int SEND_CODE_TIMES_LIMIT_HOUR = 3;
    // 一天内设备发送验证码次数限制
    private static final int SEND_CODE_TIMES_LIMIT_DAY = 5;

    // 一小时内登录次数超过限制需验证图片验证码
    private static final int LOGIN_TIMES_NEED_IMAGE_LIMIT_HOUR = 3;
    // 24小时内登录次数超过限制需验证图片验证码
    private static final int LOGIN_TIMES_NEED_IMAGE_LIMIT_DAY = 10;

    // 一小时内登录次数限制
    private static final int LOGIN_TIMES_LIMIT_HOUR = 5;
    // 24小时内登录次数限制
    private static final int LOGIN_TIMES_LIMIT_DAY = 20;

    @Override
    public void setImageVerificationCode(String serialNO, String code) {
        String key = RedisConsts.VERIFICATION_CODE_KEY + serialNO;
        redisTemplate.opsForValue().set(key, code, RedisConsts.DURATION_SECONDS_OF_5_MINTUES, TimeUnit.SECONDS);
    }

    @Override
    public String getImageVerificationCode(String serialNO) {
        return redisTemplate.opsForValue().get(RedisConsts.VERIFICATION_CODE_KEY + serialNO);
    }

    @Override
    public void deleteImageVerificationCode(String serialNO) {
        redisTemplate.delete(RedisConsts.VERIFICATION_CODE_KEY + serialNO);
    }

    @Override
    public void setResetPwdLoginName(String serialNO, String loginName) {
        redisTemplate.opsForValue().set(RedisConsts.USER_RESET_CODE_KEY + serialNO,
                loginName, RedisConsts.DURATION_SECONDS_OF_15_MINTUES, TimeUnit.SECONDS);
    }

    @Override
    public String getResetPwdLoginName(String loginName) {
        return redisTemplate.opsForValue().get(RedisConsts.USER_RESET_CODE_KEY + loginName);
    }

    /**
     * 验证登录是否需要图片验证码
     * 密码输入错误达到一定次数需要图片验证码
     */
    @Override
    public ErrorCodeEnum loginCheckImageCode(Long userId, String imageCode, String serialNo) {
        String countTimesKey = RedisConsts.PASSWORD_ERROR_TIMES_LIMIT_PRE + userId;
        // 检查是否超限
        if (!overDayHourTimesLimit(countTimesKey, LOGIN_TIMES_NEED_IMAGE_LIMIT_HOUR, LOGIN_TIMES_NEED_IMAGE_LIMIT_DAY)) {
            return null;
        }
        // 超限校验图片验证码
        return imageCodeVerify(imageCode, serialNo);
    }

    @Override
    public ErrorCodeEnum resetCheckImageCode(String loginName, String deviceId, Integer areaCode, String imageCode, String serialNo) {
        if (StringUtil.isEmail(loginName)) {
            if (!overCodeSendTimesLimit(deviceId, null, null, loginName)) {
                return null;
            }
        } else {
            if (!overCodeSendTimesLimit(deviceId, areaCode, loginName, null)) {
                return null;
            }
        }
        // 超限校验图片验证码
        return imageCodeVerify(imageCode, serialNo);
    }

    @Override
    public boolean overCodeSendTimesLimit(String deviceId, Integer areaCode, String mobile, String email) {
        String deviceKey = RedisConsts.DEVICE_SEND_CODE_TIME_PRE + deviceId;
        if (overDayHourTimesLimit(deviceKey, SEND_CODE_TIMES_LIMIT_HOUR, SEND_CODE_TIMES_LIMIT_DAY)) {
            return true;
        }
        if (!StringUtils.isEmpty(areaCode) && !StringUtils.isEmpty(mobile)) {
            String mobileKey = RedisConsts.MOBILE_SEND_CODE_TIME_PRE + areaCode + mobile;
            if (overDayHourTimesLimit(mobileKey, SEND_CODE_TIMES_LIMIT_HOUR, SEND_CODE_TIMES_LIMIT_DAY)) {
                return true;
            }
        }
        if (!StringUtils.isEmpty(email)) {
            String emailKey = RedisConsts.EMAIL_SEND_CODE_TIME_PRE + email;
            if (overDayHourTimesLimit(emailKey, SEND_CODE_TIMES_LIMIT_HOUR, SEND_CODE_TIMES_LIMIT_DAY)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addSendCodeTimes(String deviceId, String mobile, Integer areaCode, String email) {
        /**
         * 增加设备验证码发送次数
         */
        String deviceKey = RedisConsts.DEVICE_SEND_CODE_TIME_PRE + deviceId;
        long current = System.currentTimeMillis();
        REDIS.zAdd(deviceKey, current, String.valueOf(current));
        REDIS.expire(deviceKey, AppCacheServiceImpl.ONE_DAY_SECONDS);
        /**
         * 增加手机验证码发送次数
         */
        if (!StringUtils.isEmpty(mobile)) {
            String mobileKey = RedisConsts.MOBILE_SEND_CODE_TIME_PRE + areaCode + mobile;
            REDIS.zAdd(mobileKey, current, String.valueOf(current));
            REDIS.expire(mobileKey, AppCacheServiceImpl.ONE_DAY_SECONDS);
        }
        /**
         * 增加邮箱验证码发送次数
         */
        if (!StringUtils.isEmpty(email)) {
            String emailKey = RedisConsts.EMAIL_SEND_CODE_TIME_PRE + email;
            REDIS.zAdd(emailKey, current, String.valueOf(current));
            REDIS.expire(emailKey, AppCacheServiceImpl.ONE_DAY_SECONDS);
        }
    }

    @Override
    public void addPwdErrorTimes(User user, HttpServletRequest request, String deviceId) {
        String countTimesKey = RedisConsts.PASSWORD_ERROR_TIMES_LIMIT_PRE + user.getUserId();
        long current = System.currentTimeMillis();

        long oneHourPre = current - ONE_HOUR_MILLIS;
        Set<String> hasLoginTimes = REDIS.zRangeByScore(countTimesKey, oneHourPre, current);
        if (hasLoginTimes.size() + 1 >= LOGIN_TIMES_LIMIT_HOUR) {
            loginTimesSendNotify(user, request, deviceId, 1, 5);
        }

        long oneDayHourPre = current - ONE_DAY_MILLIS;
        hasLoginTimes = REDIS.zRangeByScore(countTimesKey, oneDayHourPre, current);
        if (hasLoginTimes.size() + 1 >= LOGIN_TIMES_LIMIT_DAY) {
            loginTimesSendNotify(user, request, deviceId, 24, 20);
        }

        REDIS.zAdd(countTimesKey, current, String.valueOf(current));
        REDIS.expire(countTimesKey, AppCacheServiceImpl.ONE_DAY_SECONDS);
    }

    /**
     * 密码输入错误次数超限通知
     */
    private void loginTimesSendNotify(User user, HttpServletRequest request, String deviceId, Integer hour, Integer times) {
        String sendNotifyTagKey = RedisConsts.PASSWORD_ERROR_TIMES_LIMIT_NOTIFY_TAG + user.getUserId();
        // 1.检查一小时内是否发送过
        String notifyTag = REDIS.get(sendNotifyTagKey);
        if (!org.apache.commons.lang3.StringUtils.isEmpty(notifyTag)) {
            return;
        }
        String ip = IpUtil.getRealIPAddress(request);
        HashMap<String, String> param = Maps.newHashMap();
        param.put("ip", ip);
        param.put("hour", String.valueOf(hour));
        param.put("times", String.valueOf(times));
        // 2.发送短信或邮箱
        if (user.getRegisterType().equals(RegisterTypeEnum.MOBILE.getType())) {
            boolean sendSMSResult = userNoticeService.sendSMS(LocaleUtil.getLocale(request), BusinessTypeEnum.USER_LOGIN_PASS_SMS_ERROR_NOTIFY,
                    NoticeSendLogConsts.BUSINESS_NOTIFICATION, user.getMobile(), user.getAreaCode(), user.getUserId(), param, deviceId);
            AppCacheServiceImpl.log.info("loginTimesSendNotify sendSms result = {} userId = {} ", sendSMSResult, user.getUserId());
        } else {
            boolean sendEmailResult = userNoticeService.sendEmail(LocaleUtil.getLocale(request), BusinessTypeEnum.USER_LOGIN_PASS_MAIL_ERROR_NOTIFY,
                    NoticeSendLogConsts.BUSINESS_NOTIFICATION, user.getEmail(), user.getUserId(), param, deviceId);
            AppCacheServiceImpl.log.info("loginTimesSendNotify sendEmail result = {} userId = {} ", sendEmailResult, user.getUserId());
        }
        // 3.设置标识
        REDIS.setEx(sendNotifyTagKey, 1, RedisConsts.DURATION_SECONDS_OF_60_MINTUES);
    }

    /**
     * 检查对应配置的每小时/每天数量是否超限
     */
    private boolean overDayHourTimesLimit(String key, long hourLimit, long dayLimit) {
        long current = System.currentTimeMillis();
        int hourTimes = REDIS.zRangeByScore(key, current - ONE_HOUR_MILLIS, current).size();
        int dayTimes = REDIS.zRangeByScore(key, current - ONE_DAY_MILLIS, current).size();
        if (hourTimes + 1 > hourLimit || dayTimes + 1 > dayLimit) {
            return true;
        }
        return false;
    }

    /**
     * 图片验证码校验
     *
     * @author wangs
     * @date 2022-01-15 22:44
     */
    private ErrorCodeEnum imageCodeVerify(String imageCode, String serialNo) {
        if (StringUtils.isEmpty(imageCode)) {
            return ErrorCodeEnum.NEED_IMAGE_VERIFICATION;
        }
        String redisVerificationCode = getImageVerificationCode(serialNo);
        if (StringUtils.isEmpty(redisVerificationCode)) {
            log.error("verification code is expired! imageCode={} serialNO={}", imageCode, serialNo);
            return ErrorCodeEnum.IMAGE_CODE_CHECK_ERROR;
        }
        if (StringUtil.notEqualsIgnoreCaseWithTrim(redisVerificationCode, imageCode)) {
            log.error("verification code is error! imageCode={} serialNO={}", imageCode, serialNo);
            return ErrorCodeEnum.IMAGE_CODE_CHECK_ERROR;
        }
        deleteImageVerificationCode(serialNo);
        return null;
    }

}
