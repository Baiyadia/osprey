package com.kaiqi.osprey.security.jwt.model;

/**
 * @author newex-team
 * @date 2017/12/20
 */
public final class JwtConfig {
    private String requestHeaderName;
    private String issuer;
    private String secret;
    private String cryptoKey;
    private long expiration;
    private boolean validateIp;
    private boolean validateIpAndDevice;

    /**
     * 获取httpRequest Header中存放jwt token的名称
     *
     * @return requestHeaderName
     */
    public String getRequestHeaderName() {
        return this.requestHeaderName;
    }

    /**
     * 设置httpRequest Header中存放jwt token的名称
     *
     * @param requestHeaderName httpRequest Header中存放jwt token的名称
     */
    public void setRequestHeaderName(final String requestHeaderName) {
        this.requestHeaderName = requestHeaderName;
    }

    /**
     * 获取颁发机构
     *
     * @return 颁发机构
     */
    public String getIssuer() {
        return this.issuer;
    }

    /**
     * 设置颁发机构
     *
     * @param issuer 颁发机构
     */
    public void setIssuer(final String issuer) {
        this.issuer = issuer;
    }

    /**
     * 获取jwt签名密钥
     *
     * @return jwt签名密钥
     */
    public String getSecret() {
        return this.secret;
    }

    /**
     * 设置jwt签名密钥
     *
     * @param secret jwt签名密钥
     */
    public void setSecret(final String secret) {
        this.secret = secret;
    }

    /**
     * 获取JWT playload内容加密密钥(默认为AES算法）
     *
     * @return JWT playload内容加密密钥
     */
    public String getCryptoKey() {
        return this.cryptoKey;
    }

    /**
     * 设置 JWT playload内容加密密钥(默认为AES算法）
     *
     * @param cryptoKey JWT playload内容加密密钥
     */
    public void setCryptoKey(final String cryptoKey) {
        this.cryptoKey = cryptoKey;
    }

    /**
     * 获取TOKEN过期时间或刷新时间
     * (默认为"604800"相当于7天有效期)
     *
     * @return TOKEN过期时间或刷新时间
     */
    public long getExpiration() {
        return this.expiration;
    }

    /**
     * 设置TOKEN过期时间或刷新时间
     *
     * @param expiration TOKEN过期时间或刷新时间
     */
    public void setExpiration(final long expiration) {
        this.expiration = expiration;
    }

    /**
     * 获取是否验证jwt中的ip与设备id的有效性(默认为true)
     *
     * @return true|false
     */
    public boolean isValidateIpAndDevice() {
        return this.validateIpAndDevice;
    }

    /**
     * 设置是否验证jwt中的ip与设备id的有效性(默认为true)
     *
     * @param validateIpAndDevice true|false
     */
    public void setValidateIpAndDevice(final boolean validateIpAndDevice) {
        this.validateIpAndDevice = validateIpAndDevice;
    }
}
