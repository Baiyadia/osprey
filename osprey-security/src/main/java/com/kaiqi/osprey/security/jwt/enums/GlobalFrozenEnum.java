package com.kaiqi.osprey.security.jwt.enums;

/**
 * 全站交易业务冻结
 *
 * @author wangs
 * @date 2018-07-03
 */
public enum GlobalFrozenEnum {

    /**
     * 全站交易业务冻结
     */
    GLOBAL_FROZEN("global_frozen"),

    /**
     * 全站spot交易业务冻结
     */
    GLOBAL_SPOT_FROZEN("global_spot_frozen"),

    /**
     * 全站c2c交易业务冻结
     */
    GLOBAL_C2C_FROZEN("global_c2c_frozen"),

    /**
     * 全站contract交易业务冻结
     */
    GLOBAL_CONTRACTS_FROZEN("global_contracts_frozen"),

    /**
     * 全站asset交易业务冻结
     */
    GLOBAL_ASSET_FROZEN("global_asset_frozen"),

    /**
     * 未知
     */
    UNKNOWN("unknown");

    private final String name;

    GlobalFrozenEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static GlobalFrozenEnum forName(final String name) {
        for (final GlobalFrozenEnum value : values()) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return GlobalFrozenEnum.UNKNOWN;
    }
}
