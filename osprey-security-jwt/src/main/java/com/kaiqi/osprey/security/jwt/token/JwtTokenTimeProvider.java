package com.kaiqi.osprey.security.jwt.token;

import java.util.Date;

/**
 * @author newex-team
 * @date 2017/11/20
 */
public class JwtTokenTimeProvider {
    public static Date now() {
        return new Date();
    }
}
