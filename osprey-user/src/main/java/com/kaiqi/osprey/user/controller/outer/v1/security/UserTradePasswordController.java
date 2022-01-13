package com.kaiqi.osprey.user.controller.outer.v1.security;

import com.kaiqi.osprey.common.commons.ResponseResult;
import com.kaiqi.osprey.common.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.common.consts.SwitchConsts;
import com.kaiqi.osprey.common.session.model.SessionInfo;
import com.kaiqi.osprey.common.util.ResultUtil;
import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.security.jwt.util.JwtTokenUtils;
import com.kaiqi.osprey.service.domain.User;
import com.kaiqi.osprey.service.service.UserService;
import com.kaiqi.osprey.user.model.WalletPathReqVO;
import com.kaiqi.osprey.user.service.UserBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangs
 */
@RestController
@Slf4j
@RequestMapping("/v1/osprey/users/security/trade-pass")
public class UserTradePasswordController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserBizService userBizService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/update")
    public ResponseResult tradePassUpdate(@RequestBody WalletPathReqVO walletPathReqVO,
                                          HttpServletRequest request) {
        JwtUserDetails currentLoginUser = JwtTokenUtils.getCurrentLoginUserFromToken(request);
        try {
            if (ObjectUtils.isEmpty(currentLoginUser)) {
                return ResultUtil.failure(ErrorCodeEnum.USER_NO_LOGIN);
            }
            if (currentLoginUser.getTradePasswordSetFlag() == SwitchConsts.OFF) {
                boolean result = userBizService.setTradePassword(passwordEncoder.encode(walletPathReqVO.getEncryptedPass() + currentLoginUser.getUserId()),
                        currentLoginUser.getUserId(), walletPathReqVO.getEncodePath());
                if (result) {
                    SessionInfo session = JwtTokenUtils.getSession(currentLoginUser.getUserId());
                    session.setTradePasswordSetFlag(SwitchConsts.ON);
                    JwtTokenUtils.updateSession(session);
                }
                return ResultUtil.success();
            } else {
                if (!StringUtils.isEmpty(walletPathReqVO.getValidatePass())) {
                    User user = userService.getById(currentLoginUser.getUserId());
                    if (!passwordEncoder.matches((walletPathReqVO.getValidatePass() + currentLoginUser.getUserId()), user.getTradePasswordCryptoHash())) {
                        return ResultUtil.failure(ErrorCodeEnum.TRADE_PASSWORD_CHECK_ERROR);
                    }
                    userBizService.setTradePassword(passwordEncoder.encode(walletPathReqVO.getEncryptedPass() + currentLoginUser.getUserId()),
                            currentLoginUser.getUserId(), walletPathReqVO.getEncodePath());
                    return ResultUtil.success();
                }
            }
            log.error("tradePassUpdate error validatePass is null user {}", currentLoginUser.getUserId());
            return ResultUtil.failure(ErrorCodeEnum.SET_TRADE_PASSWORD_ERROR);
        } catch (Exception e) {
            log.error("tradePassUpdate error userid {} e {}", currentLoginUser.getUserId(), e);
            return ResultUtil.failure(ErrorCodeEnum.SET_TRADE_PASSWORD_ERROR);
        }
    }
}
