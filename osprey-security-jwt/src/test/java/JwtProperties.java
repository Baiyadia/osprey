import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangs
 * @date 2017/11/20
 */
@Configuration
@ConfigurationProperties(prefix = "newex.jwt")
public class JwtProperties {
    private String requestHeaderName = "X-Authorization";
    private String issuer = "newex";
    private String secret = "ZwwXGHRDUy1wUWgv";
    private String cryptoKey = "Dk4nIS49RksJJRoB";
    private String excludePathPatterns = "";
    private long expiration = 604800;
    private String cryptoAlgorithm = "AES";

    /**
     * 获取httpRequest Header中存放jwt token的名称
     * (默认为"Authorization")
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
     * 获取颁发机构(默认为"newex")
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
     * 获取排出jwt拦截的url pattern(ant pattern 表达式)
     * 多个pattern用英文逗号(,)分隔
     *
     * @return url pattern(ant pattern 表达式)
     * @see org.springframework.util.AntPathMatcher
     */
    public String getExcludePathPatterns() {
        return this.excludePathPatterns;
    }

    /**
     * 设置排出jwt拦截的url pattern(ant pattern 表达式)
     * 多个pattern用英文逗号(,)分隔
     *
     * @param excludePathPatterns 排出csrf的url pattern(ant pattern 表达式)
     */
    public void setExcludePathPatterns(final String excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }

    /**
     * 获取JWT playload内容加密算法名称(默认为AES）
     *
     * @return
     */
    public String getCryptoAlgorithm() {
        return this.cryptoAlgorithm;
    }

    /**
     * 设置JWT playload内容加密算法名称(默认为AES）
     *
     * @param cryptoAlgorithm
     */
    public void setCryptoAlgorithm(final String cryptoAlgorithm) {
        this.cryptoAlgorithm = cryptoAlgorithm;
    }
}
