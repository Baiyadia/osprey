package com.kaiqi.osprey.security.jwt;

import com.kaiqi.osprey.common.ucenter.service.SessionService;
import com.kaiqi.osprey.security.jwt.enums.BizTypeEnum;
import com.kaiqi.osprey.security.jwt.enums.GlobalFrozenEnum;
import com.kaiqi.osprey.security.jwt.exception.BusinessFrozenException;
import com.kaiqi.osprey.security.jwt.model.JwtConsts;
import com.kaiqi.osprey.security.jwt.model.JwtFrozenConfig;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author newex-team
 * @date 2017/11/20
 */
@Slf4j
public class JwtFrozenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtFrozenConfig frozenConfig;
    @Autowired
    private SessionService sessionService;

    public JwtFrozenInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        log.debug("request url:{}", request.getRequestURL());
        this.validateFrozen(request);
        return super.preHandle(request, response, handler);
    }

    private void validateFrozen(HttpServletRequest request) {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) request.getAttribute(JwtConsts.JWT_CURRENT_USER);
        if (jwtUserDetails == null ||
                this.isGlobalFrozen(GlobalFrozenEnum.GLOBAL_FROZEN.getName()) ||
                jwtUserDetails.isFrozen() ||
                this.isSpotFrozen(jwtUserDetails) ||
                this.isC2CFrozen(jwtUserDetails) ||
                this.isContractsFrozen(jwtUserDetails) ||
                this.isAssetFrozen(jwtUserDetails)) {
            throw new BusinessFrozenException();
        }
    }

    private boolean isGlobalFrozen(String name) {
        return Integer.valueOf(1).equals(this.sessionService.getGlobalStatus(name));
    }

    private boolean isSpotFrozen(JwtUserDetails jwtUserDetails) {
        return (this.frozenConfig.getBizType() == BizTypeEnum.SPOT &&
                (this.isGlobalFrozen(GlobalFrozenEnum.GLOBAL_SPOT_FROZEN.getName()) || jwtUserDetails.isSpotFrozen())
        );
    }

    private boolean isC2CFrozen(JwtUserDetails jwtUserDetails) {
        return (this.frozenConfig.getBizType() == BizTypeEnum.C2C &&
                (this.isGlobalFrozen(GlobalFrozenEnum.GLOBAL_C2C_FROZEN.getName()) || jwtUserDetails.isC2CFrozen())
        );
    }

    private boolean isContractsFrozen(JwtUserDetails jwtUserDetails) {
        return (this.frozenConfig.getBizType() == BizTypeEnum.CONTRACTS &&
                (this.isGlobalFrozen(GlobalFrozenEnum.GLOBAL_CONTRACTS_FROZEN.getName()) || jwtUserDetails.isContractsFrozen())
        );
    }

    private boolean isAssetFrozen(JwtUserDetails jwtUserDetails) {
        return (this.frozenConfig.getBizType() == BizTypeEnum.ASSET &&
                (this.isGlobalFrozen(GlobalFrozenEnum.GLOBAL_ASSET_FROZEN.getName()) || jwtUserDetails.isAssetFrozen())
        );
    }
}
