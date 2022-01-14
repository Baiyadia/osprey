package com.kaiqi.osprey.security;

import com.kaiqi.osprey.common.consts.UserAuthConsts;

import java.lang.annotation.*;

/**
 * 绑定当前登录的用户
 *
 * @author wangs
 * @date 2017/12/09
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

    /**
     * 当前用户在request中的名字
     *
     * @return
     */
    String value() default UserAuthConsts.CURRENT_USER;

}
