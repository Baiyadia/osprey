package com.kaiqi.osprey.security.jwt.model;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Date;

/**
 * @author wangs
 * @date 2017/12/19
 */
public class JwtPublicClaims {

    public static final String USER_ID = "uid";
    public static final String IP = "ip";
    public static final String DEV_ID = "dev";
    public static final String STATUS = "sts";

    private Claims claims;

    public JwtPublicClaims(Claims claims) {
        this.claims = claims;
    }

    public Claims getClaims() {
        return this.claims;
    }

    public JwtPublicClaims setId(String id) {
        this.claims.setId(id);
        return this;
    }

    public String getId() {
        return this.claims.getId();
    }

    public JwtPublicClaims setExpiration(Date date) {
        this.claims.setExpiration(date);
        return this;
    }

    public String getUsername() {
        return this.claims.getSubject();
    }

    public JwtPublicClaims setUsername(String username) {
        this.claims.setSubject(username);
        return this;
    }

    public Date getExpiration() {
        return this.claims.getExpiration();
    }

    public JwtPublicClaims setIssuedAt(Date date) {
        this.claims.setIssuedAt(date);
        return this;
    }

    public Date getIssuedAt() {
        return this.claims.getIssuedAt();
    }

    public JwtPublicClaims setIssuer(String issuer) {
        this.claims.setIssuer(issuer);
        return this;
    }

    public String getIssuer() {
        return this.claims.getIssuer();
    }

    public String getUserId() {
        return this.claims.get(USER_ID, String.class);
    }

    public JwtPublicClaims setUserId(String userId) {
        this.claims.put(USER_ID, userId);
        return this;
    }

    public String getIp() {
        return this.claims.get(IP, String.class);
    }

    public JwtPublicClaims setIp(String ip) {
        this.claims.put(IP, ip);
        return this;
    }

    public String getDevId() {
        return this.claims.get(DEV_ID, String.class);
    }

    public JwtPublicClaims setDevId(String devId) {
        this.claims.put(DEV_ID, devId);
        return this;
    }

    public int getStatus() {
        return NumberUtils.toInt(String.valueOf(this.claims.get(STATUS)), 0);
    }

    public JwtPublicClaims setStatus(int status) {
        this.claims.put(STATUS, status);
        return this;
    }
}
