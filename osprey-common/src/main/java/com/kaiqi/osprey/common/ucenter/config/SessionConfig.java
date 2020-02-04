package com.kaiqi.osprey.common.ucenter.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * @author newex-team
 * @date 2018-06-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionConfig {

    private Duration maxInactiveInterval;
}
