package com.kaiqi.osprey.user.enums;

import lombok.Getter;

/**
 * 状态enum
 * @author lilaizhen
 */

@Getter
public enum UserStatusEnum {

    /**
     * 用户状态 1 正常 0 禁用 -1 所有业务被冻结  -2 用户不存在
     */
    USER_STATUS_NORMAL(1),
    USER_STATUS_FORBIDDEN(0),
    USER_STATUS_FROZEN(-1),
    USER_STATUS_NOT_EXIST(-2),

    USER_MOBILE_SETTING_STATUS_OK(1),
    USER_MOBILE_SETTING_STATUS_NO(0),

    USER_EMAIL_SETTING_STATUS_OK(1),
    USER_EMAIL_SETTING_STATUS_NO(0),

    USER_GOOGLE_SETTING_STATUS_OK(1),
    USER_GOOGLE_SETTING_STATUS_NO(0),

    USER_REJECT_PROTOCOL(0),
    USER_AGREE_PROTOCOL(1),

    USER_TRADE_PASSWORD_ALREADY_SET(1),
    USER_TRADE_PASSWORD_NO_SET(0),

    USER_FEEDBACK_NO_REPLY(1),
    USER_FEEDBACK_HAS_REPLY(2),

    USER_CONTACT_ADDRESS_OK(1),
    USER_CONTACT_ADDRESS_DEL(-1),

    USER_LOGIN_AUTH_FLAG_OK(1),
    USER_LOGIN_AUTH_FLAG_NO(0),

    USER_SUBNOTIFY_FLAG_OK(1),
    USER_SUBNOTIFY_FLAG_NO(0),

    USER_PWD_STRENGTH_OK(1),
    USER_PWD_STRENGTH_NO(0),

    USER_CONTACT_TYPE_NORMAL(1),
    USER_CONTACT_TYPE_RELATE(2),

    USER_CONTACT_ADDRESS_VISIBLE_OK(1),
    USER_CONTACT_ADDRESS_VISIBLE_ERROR(0),

    ;
    private final int status;

    UserStatusEnum(final int status) {
        this.status = status;
    }
}
