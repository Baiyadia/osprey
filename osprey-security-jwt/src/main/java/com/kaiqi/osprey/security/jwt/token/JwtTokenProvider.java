package com.kaiqi.osprey.security.jwt.token;

import com.kaiqi.osprey.security.jwt.crypto.JwtTokenCryptoProvider;
import com.kaiqi.osprey.security.jwt.model.JwtConfig;
import com.kaiqi.osprey.security.jwt.model.JwtPublicClaims;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

/**
 * JwtToken生成器
 * @author wangs
 * @date 2017/12/19
 */
@Slf4j
@Component
public class JwtTokenProvider {

    private JwtConfig jwtConfig;
    private JwtTokenCryptoProvider cryptoProvider;

    public JwtTokenProvider(JwtConfig jwtConfig, JwtTokenCryptoProvider cryptoProvider) {
        this.jwtConfig = jwtConfig;
        this.cryptoProvider = cryptoProvider;
    }

    public JwtConfig getJwtConfig() {
        return this.jwtConfig;
    }

    public JwtTokenCryptoProvider getCryptoProvider() {
        return this.cryptoProvider;
    }

    public long getUserId(String token) {
        JwtUserDetails jwtUserDetails = this.getJwtUserDetails(token);
        return jwtUserDetails.getUserId();
    }

    public JwtUserDetails getJwtUserDetails(String token) {
        JwtPublicClaims publicClaims = this.parseClaims(token);
        if (this.isExpiredToken(publicClaims.getExpiration())) {
            log.debug("token is expired");
            return JwtUserDetails.builder().status(-1).build();
        }
        return this.getJwtUserDetails(this.parseClaims(token));
    }

    public JwtUserDetails getJwtUserDetails(JwtPublicClaims publicClaims) {
        long userId = NumberUtils.toLong(this.decrypt(publicClaims.getUserId()), -1);
        long ip = NumberUtils.toLong(this.decrypt(publicClaims.getIp()), -1);
        String devId = this.decrypt(publicClaims.getDevId());

        return JwtUserDetails.builder()
                             .sid(publicClaims.getId())
                             .userId(userId)
                             .ip(ip)
                             .devId(devId)
                             .status(publicClaims.getStatus())
                             .created(publicClaims.getIssuedAt())
                             .build();
    }

    public <T> T getClaims(JwtPublicClaims claims, Function<JwtPublicClaims, T> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    public JwtPublicClaims parseClaims(String token) {
        Claims claims = Jwts.parser()
                                  .setSigningKey(this.jwtConfig.getSecret())
                                  .parseClaimsJws(token)
                                  .getBody();
        return new JwtPublicClaims(claims);
    }

    public String generateToken(JwtUserDetails jwtUserDetails) {
        String userId = this.encrypt(String.valueOf(jwtUserDetails.getUserId()));
        String ip = this.encrypt(String.valueOf(jwtUserDetails.getIp()));
        String devId = this.encrypt(jwtUserDetails.getDevId());

        Date createdDate = jwtUserDetails.getCreated();
        Date expirationDate = ObjectUtils.defaultIfNull(jwtUserDetails.getExpired(), this.getExpirationDate(createdDate));
        log.debug("doGenerateToken createdDate: {}", createdDate);

        JwtPublicClaims publicClaims = new JwtPublicClaims(new DefaultClaims());
        publicClaims
                .setId(StringUtils.defaultIfBlank(jwtUserDetails.getSid(), UUID.randomUUID().toString()))
                .setUserId(userId)
                .setIp(ip)
                .setDevId(devId)
                .setStatus(jwtUserDetails.getStatus())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .setIssuer(this.jwtConfig.getIssuer());

        return Jwts.builder()
                   .setClaims(publicClaims.getClaims())
                   .signWith(SignatureAlgorithm.HS256, this.jwtConfig.getSecret())
                   .compact();
    }

    public String refreshToken(String token) {
        Date createdDate = JwtTokenTimeProvider.now();
        Date expirationDate = this.getExpirationDate(createdDate);

        Claims claims = this.parseClaims(token).getClaims();
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                   .setClaims(claims)
                   .signWith(SignatureAlgorithm.HS256, this.jwtConfig.getSecret())
                   .compact();
    }

    public boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        JwtPublicClaims jwtPublicClaims = this.parseClaims(token);
        Date created = jwtPublicClaims.getIssuedAt();
        Date expired = jwtPublicClaims.getExpiration();
        return (this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset) || this.isExpiredToken(expired));
    }

    public boolean validateToken(String token, JwtUserDetails user) {
        JwtPublicClaims jwtPublicClaims = this.parseClaims(token);
        String username = jwtPublicClaims.getUsername();
        Date expired = jwtPublicClaims.getExpiration();
        return (username.equals(user.getUsername())
                && !this.isExpiredToken(expired));
    }

    public boolean isExpiredToken(Date expired) {
        return expired.before(JwtTokenTimeProvider.now());
    }

    public Date getExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + this.jwtConfig.getExpiration() * 1000);
    }

    public boolean verifyIpAndDevice(JwtUserDetails jwtUserDetails, String deviceId, long ip) {
        if (this.jwtConfig.isValidateIpAndDevice()) {
            return (jwtUserDetails.isNotFromSameDevice(deviceId) ||
                    jwtUserDetails.isNotFromSameIp(ip));
        }
        return false;
    }

    private boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private String encrypt(String content) {
        return this.cryptoProvider.encrypt(StringUtils.defaultIfEmpty(content, ""), this.jwtConfig.getCryptoKey());
    }

    private String decrypt(String content) {
        return this.cryptoProvider.decrypt(StringUtils.defaultIfEmpty(content, ""), this.jwtConfig.getCryptoKey());
    }
}
