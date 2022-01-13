package com.kaiqi.osprey.user.service.impl;

import com.google.common.collect.Maps;
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

    private static long ONE_DAY = 24 * 60 * 60;

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

    @Override
    public void setResetSendEmailTimes(String ip, String deviceId, String loginName, BusinessTypeEnum userResetPasswordEmail) {
        recordDeviceSendTimes(deviceId, null, null, loginName);
    }

    @Override
    public void setResetSendMobileTimes(String ipAddress, String deviceId, Integer areaCode, String loginName, BusinessTypeEnum userResetPasswordEmail) {
        recordDeviceSendTimes(deviceId, loginName, areaCode, null);
    }

    @Override
    public boolean checkDeviceSendTimes(String ip, String deviceId, Integer areaCode, String mobile, String email) {
        int oneHourDeviceTimes = 3;
        //TODO 10 to 5
        int oneDayDeviceTimes = 5;
        String deviceKey = RedisConsts.DEVICE_SEND_CODE_TIME_PRE + deviceId;
        long current = System.currentTimeMillis();
        long oneHourPre = current - 3600_000;
        long oneDayPre = current - 3600_000 * 24;
        Set<String> sendTimes = REDIS.zRangeByScore(deviceKey, oneHourPre, current);
        if (sendTimes.size() + 1 > oneHourDeviceTimes) {
            return false;
        }
        sendTimes = REDIS.zRangeByScore(deviceKey, oneDayPre, current);
        if (sendTimes.size() + 1 > oneDayDeviceTimes) {
            return false;
        }
        if (!StringUtils.isEmpty(areaCode) && !StringUtils.isEmpty(mobile)) {
            String mobileKey = RedisConsts.MOBILE_SEND_CODE_TIME_PRE + areaCode + mobile;
            sendTimes = REDIS.zRangeByScore(mobileKey, oneHourPre, current);
            if (sendTimes.size() + 1 > oneHourDeviceTimes) {
                return false;
            }
            sendTimes = REDIS.zRangeByScore(mobileKey, oneDayPre, current);
            if (sendTimes.size() + 1 > oneDayDeviceTimes) {
                return false;
            }
        }
        if (!StringUtils.isEmpty(email)) {
            String emailKey = RedisConsts.EMAIL_SEND_CODE_TIME_PRE + email;
            sendTimes = REDIS.zRangeByScore(emailKey, oneHourPre, current);
            if (sendTimes.size() + 1 > oneHourDeviceTimes) {
                return false;
            }
            sendTimes = REDIS.zRangeByScore(emailKey, oneDayPre, current);
            if (sendTimes.size() + 1 > oneDayDeviceTimes) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void recordDeviceSendTimes(String deviceId, String mobile, Integer areaCode, String email) {
        String deviceKey = RedisConsts.DEVICE_SEND_CODE_TIME_PRE + deviceId;
        long current = System.currentTimeMillis();
        REDIS.zAdd(deviceKey, current, String.valueOf(current));
        REDIS.expire(deviceKey, AppCacheServiceImpl.ONE_DAY);
        if (!StringUtils.isEmpty(mobile)) {
            String mobileKey = RedisConsts.MOBILE_SEND_CODE_TIME_PRE + areaCode + mobile;
            REDIS.zAdd(mobileKey, current, String.valueOf(current));
            REDIS.expire(mobileKey, AppCacheServiceImpl.ONE_DAY);
        }
        if (!StringUtils.isEmpty(email)) {
            String emailKey = RedisConsts.EMAIL_SEND_CODE_TIME_PRE + email;
            REDIS.zAdd(emailKey, current, String.valueOf(current));
            REDIS.expire(emailKey, AppCacheServiceImpl.ONE_DAY);
        }
    }

    @Override
    public boolean checkResetIsNeedImage(String loginName, String deviceId, String ip, Integer areaCode) {
        if (StringUtil.isEmail(loginName)) {
            return checkDeviceSendTimes(ip, deviceId, null, null, loginName);
        }
        return checkDeviceSendTimes(ip, deviceId, areaCode, loginName, null);
    }

    @Override
    public void setLoginTimesLimitCount(User user, HttpServletRequest request, String deviceId) {
        //一小时内登录五次阈值
        int oneHourMaxLoginErrorTimes = 5;
        //24小时内登录20次阈值
        int oneDayMaxLoginErrorTimes = 20;
        String countTimesKey = RedisConsts.PASSWORD_ERROR_TIMES_LIMIT_PRE + user.getUserId();
        long current = System.currentTimeMillis();

        long oneHourPre = current - 3600_000;
        Set<String> hasLoginTimes = REDIS.zRangeByScore(countTimesKey, oneHourPre, current);
        if (hasLoginTimes.size() + 1 >= oneHourMaxLoginErrorTimes) {
            loginTimesSendNotify(user, request, deviceId, 1, 5);
        }

        long oneDayHourPre = current - 3600_000 * 24;
        hasLoginTimes = REDIS.zRangeByScore(countTimesKey, oneDayHourPre, current);
        if (hasLoginTimes.size() + 1 >= oneDayMaxLoginErrorTimes) {
            loginTimesSendNotify(user, request, deviceId, 24, 20);
        }

        REDIS.zAdd(countTimesKey, current, String.valueOf(current));
        REDIS.expire(countTimesKey, AppCacheServiceImpl.ONE_DAY);
    }

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
}
