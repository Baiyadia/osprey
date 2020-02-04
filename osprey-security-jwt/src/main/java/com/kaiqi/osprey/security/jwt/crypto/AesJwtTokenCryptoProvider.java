package com.kaiqi.osprey.security.jwt.crypto;

import com.kaiqi.osprey.common.crypto.AESUtil;

/**
 * AES 加/解密提供者类
 *
 * @author newex-team
 * @date 2017/12/19
 */
public class AesJwtTokenCryptoProvider implements JwtTokenCryptoProvider {

    @Override
    public String encrypt(final String content, final String key) {
        return AESUtil.encrypt(content, key);
    }

    @Override
    public String decrypt(final String content, final String key) {
        return AESUtil.decrypt(content, key);
    }
}
