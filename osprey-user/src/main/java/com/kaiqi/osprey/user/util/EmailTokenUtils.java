package com.kaiqi.osprey.user.util;

import com.kaiqi.osprey.common.crypto.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.UUID;

/**
 * Email Token 令牌生成 util类
 *
 * @author newex-team
 * @date 2018-07-02
 */
@Slf4j
public class EmailTokenUtils {

    /**
     * AES加密密钥
     */
    public static final String ASE_KEY = "ZwwXGHRDUy!wUWgv";

    /**
     * 生成邮箱TOKEN
     *
     * @param userId
     * @param type
     * @param refreshExpTime minute -1 没设置
     * @return 数据格式 AES({userId}_{uuid}_{passTime}_{type})
     */
    public static String createEmailToken(final long userId, final int type, final int refreshExpTime) {
        if (type < 0) {
            return null;
        }

        // 过期时间
        long passTime = -1;
        if (refreshExpTime > 0) {
            final Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.MINUTE, 5);
            passTime = nowTime.getTimeInMillis();
        }
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        final String emailToken = userId + "_" + uuid + "_" + passTime + "_" + type;
        return AESUtil.encrypt(emailToken, ASE_KEY);

    }

    /**
     * 生成邮箱TOKEN
     *
     * @param emailToken
     * @return 数据格式 AES({userId}_{uuid}_{passTime}_{type})
     */
    public static EmailToken pareseEmailToken(final String emailToken) {
        try {
            final String decryptToken = AESUtil.decrypt(emailToken, ASE_KEY);
            if (StringUtils.isEmpty(decryptToken)) {
                return null;
            }
            final String[] tokenInfo = decryptToken.split("_");
            if (tokenInfo.length >= 4) {
                final long userId = Long.parseLong(tokenInfo[0]);
                final long passTime = Long.parseLong(tokenInfo[2]);
                final int type = Integer.parseInt(tokenInfo[3]);
                if (passTime > 0) {
                    final long timePassed = System.currentTimeMillis() - passTime;
                    // 登录时长超过限制
                    if (timePassed >= 0) {
                        return null;
                    }
                }
                return new EmailToken(decryptToken, userId, type, passTime);
            }
        } catch (final Exception e) {
            log.error("pareseOKEmailToken error" + emailToken);
        }
        return null;
    }
}
