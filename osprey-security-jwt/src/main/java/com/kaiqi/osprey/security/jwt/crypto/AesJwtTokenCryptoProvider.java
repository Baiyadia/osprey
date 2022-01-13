package com.kaiqi.osprey.security.jwt.crypto;

import com.kaiqi.osprey.common.crypto.AESUtil;
import org.springframework.stereotype.Component;

/**
 * AES 加/解密提供者类
 *
 * @author wangs
 * @date 2017/12/19
 */
@Component
public class AesJwtTokenCryptoProvider implements JwtTokenCryptoProvider {

    @Override
    public String encrypt(String content, String key) {
        return AESUtil.encrypt(content, key);
    }

    @Override
    public String decrypt(String content, String key) {
        return AESUtil.decrypt(content, key);
    }
}
