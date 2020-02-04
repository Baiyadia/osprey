package com.kaiqi.osprey.security.jwt.crypto;

/**
 * @author newex-team
 * @date 2017/12/19
 */
public interface JwtTokenCryptoProvider {
    /**
     * @param content
     * @param key
     * @return
     */
    String encrypt(final String content, final String key);

    /**
     * @param content
     * @param key
     * @return
     */
    String decrypt(final String content, final String key);
}
