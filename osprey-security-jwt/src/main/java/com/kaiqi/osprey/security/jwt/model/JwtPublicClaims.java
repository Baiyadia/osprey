package com.kaiqi.osprey.security.jwt.model;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Date;

/**
 * @author newex-team
 * @date 2017/12/19
 */
public class JwtPublicClaims {

    public final static String USER_ID = "uid";
    public final static String IP = "ip";
    public final static String DEV_ID = "dev";
    public final static String STATUS = "sts";

    private final Claims claims;

    public JwtPublicClaims(final Claims claims) {
        this.claims = claims;
    }

    public Claims getClaims() {
        return this.claims;
    }

    public JwtPublicClaims setId(final String id) {
        this.claims.setId(id);
        return this;
    }

    public String getId() {
        return this.claims.getId();
    }

    public JwtPublicClaims setExpiration(final Date date) {
        this.claims.setExpiration(date);
        return this;
    }

    public String getUsername() {
        return this.claims.getSubject();
    }

    public JwtPublicClaims setUsername(final String username) {
        this.claims.setSubject(username);
        return this;
    }

    public Date getExpiration() {
        return this.claims.getExpiration();
    }

    public JwtPublicClaims setIssuedAt(final Date date) {
        this.claims.setIssuedAt(date);
        return this;
    }

    public Date getIssuedAt() {
        return this.claims.getIssuedAt();
    }

    public JwtPublicClaims setIssuer(final String issuer) {
        this.claims.setIssuer(issuer);
        return this;
    }

    public String getIssuer() {
        return this.claims.getIssuer();
    }

    public String getUserId() {
        return this.claims.get(USER_ID, String.class);
    }

    public JwtPublicClaims setUserId(final String userId) {
        this.claims.put(USER_ID, userId);
        return this;
    }

    public String getIp() {
        return this.claims.get(IP, String.class);
    }

    public JwtPublicClaims setIp(final String ip) {
        this.claims.put(IP, ip);
        return this;
    }

    public String getDevId() {
        return this.claims.get(DEV_ID, String.class);
    }

    public JwtPublicClaims setDevId(final String devId) {
        this.claims.put(DEV_ID, devId);
        return this;
    }

    public int getStatus() {
        return NumberUtils.toInt(String.valueOf(this.claims.get(STATUS)), 0);
    }

    public JwtPublicClaims setStatus(final int status) {
        this.claims.put(STATUS, status);
        return this;
    }
}
