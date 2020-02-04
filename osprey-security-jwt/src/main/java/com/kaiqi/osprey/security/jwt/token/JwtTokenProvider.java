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

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author newex-team
 * @date 2017/12/19
 */
@Slf4j
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;
    private final JwtTokenCryptoProvider cryptoProvider;

    public JwtTokenProvider(final JwtConfig jwtConfig, final JwtTokenCryptoProvider cryptoProvider) {
        this.jwtConfig = jwtConfig;
        this.cryptoProvider = cryptoProvider;
    }

    public JwtConfig getJwtConfig() {
        return this.jwtConfig;
    }

    public JwtTokenCryptoProvider getCryptoProvider() {
        return this.cryptoProvider;
    }

    public long getUserId(final String token) {
        final JwtUserDetails jwtUserDetails = this.getJwtUserDetails(token);
        return jwtUserDetails.getUserId();
    }

    public JwtUserDetails getJwtUserDetails(final String token) {
        final JwtPublicClaims publicClaims = this.parseClaims(token);
        if (this.isExpiredToken(publicClaims.getExpiration())) {
            log.debug("token is expired");
            return JwtUserDetails.builder().status(-1).build();
        }
        return this.getJwtUserDetails(this.parseClaims(token));
    }

    public JwtUserDetails getJwtUserDetails(final JwtPublicClaims publicClaims) {
        final long userId = NumberUtils.toLong(this.decrypt(publicClaims.getUserId()), -1);
        final long ip = NumberUtils.toLong(this.decrypt(publicClaims.getIp()), -1);
        final String devId = this.decrypt(publicClaims.getDevId());

        return JwtUserDetails.builder()
                             .sid(publicClaims.getId())
                             .userId(userId)
                             .ip(ip)
                             .devId(devId)
                             .status(publicClaims.getStatus())
                             .created(publicClaims.getIssuedAt())
                             .build();
    }

    public <T> T getClaims(final JwtPublicClaims claims, final Function<JwtPublicClaims, T> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    public JwtPublicClaims parseClaims(final String token) {
        final Claims claims = Jwts.parser()
                                  .setSigningKey(this.jwtConfig.getSecret())
                                  .parseClaimsJws(token)
                                  .getBody();
        return new JwtPublicClaims(claims);
    }

    public String generateToken(final JwtUserDetails jwtUserDetails) {
        final String userId = this.encrypt(String.valueOf(jwtUserDetails.getUserId()));
        final String ip = this.encrypt(String.valueOf(jwtUserDetails.getIp()));
        final String devId = this.encrypt(jwtUserDetails.getDevId());

        final Date createdDate = jwtUserDetails.getCreated();
        final Date expirationDate = ObjectUtils.defaultIfNull(jwtUserDetails.getExpired(), this.getExpirationDate(createdDate));
        log.debug("doGenerateToken createdDate: {}", createdDate);

        final JwtPublicClaims publicClaims = new JwtPublicClaims(new DefaultClaims());
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

    public String refreshToken(final String token) {
        final Date createdDate = JwtTokenTimeProvider.now();
        final Date expirationDate = this.getExpirationDate(createdDate);

        final Claims claims = this.parseClaims(token).getClaims();
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                   .setClaims(claims)
                   .signWith(SignatureAlgorithm.HS256, this.jwtConfig.getSecret())
                   .compact();
    }

    public boolean canTokenBeRefreshed(final String token, final Date lastPasswordReset) {
        final JwtPublicClaims jwtPublicClaims = this.parseClaims(token);
        final Date created = jwtPublicClaims.getIssuedAt();
        final Date expired = jwtPublicClaims.getExpiration();
        return (this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset) || this.isExpiredToken(expired));
    }

    public boolean validateToken(final String token, final JwtUserDetails user) {
        final JwtPublicClaims jwtPublicClaims = this.parseClaims(token);
        final String username = jwtPublicClaims.getUsername();
        final Date expired = jwtPublicClaims.getExpiration();
        return (username.equals(user.getUsername())
                && !this.isExpiredToken(expired));
    }

    public boolean isExpiredToken(final Date expired) {
        return expired.before(JwtTokenTimeProvider.now());
    }

    public Date getExpirationDate(final Date createdDate) {
        return new Date(createdDate.getTime() + this.jwtConfig.getExpiration() * 1000);
    }

    public boolean verifyIpAndDevice(final JwtUserDetails jwtUserDetails, final String deviceId, final long ip) {
        if (this.jwtConfig.isValidateIpAndDevice()) {
            return (jwtUserDetails.isNotFromSameDevice(deviceId) ||
                    jwtUserDetails.isNotFromSameIp(ip));
        }
        return false;
    }

    private boolean isCreatedBeforeLastPasswordReset(final Date created, final Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private String encrypt(final String content) {
        return this.cryptoProvider.encrypt(StringUtils.defaultIfEmpty(content, ""), this.jwtConfig.getCryptoKey());
    }

    private String decrypt(final String content) {
        return this.cryptoProvider.decrypt(StringUtils.defaultIfEmpty(content, ""), this.jwtConfig.getCryptoKey());
    }
}
