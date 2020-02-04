package com.kaiqi.osprey.security.jwt.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * @author newex-team
 * @date 2017/12/19
 */
public class JwtTokenNotFoundException extends AccessDeniedException {
    public JwtTokenNotFoundException() {
        this("Access token not found");
    }

    public JwtTokenNotFoundException(final String msg) {
        super(msg);
    }
}
