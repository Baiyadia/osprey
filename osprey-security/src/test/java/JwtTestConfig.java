import com.kaiqi.osprey.security.jwt.crypto.AesJwtTokenCryptoProvider;
import com.kaiqi.osprey.security.jwt.crypto.JwtTokenCryptoProvider;
import com.kaiqi.osprey.security.jwt.model.JwtConfig;
import com.kaiqi.osprey.security.jwt.token.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangs
 * @date 2017/12/25
 */
@Configuration
@EnableConfigurationProperties(value = JwtProperties.class)
public class JwtTestConfig {

    @Autowired
    private JwtProperties jwtProperties;

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        JwtConfig jwtConfig = new JwtConfig();
        jwtConfig.setCryptoKey(jwtProperties.getCryptoKey());
        jwtConfig.setIssuer(jwtProperties.getIssuer());
        jwtConfig.setSecret(jwtProperties.getSecret());
        jwtConfig.setExpiration(jwtProperties.getExpiration());

        JwtTokenCryptoProvider cryptoProvider = new AesJwtTokenCryptoProvider();
        return new JwtTokenProvider(jwtConfig, cryptoProvider);
    }
}
