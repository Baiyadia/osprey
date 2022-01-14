package com.kaiqi.osprey.security.jwt.enums;

/**
 * @author wangs
 * @date 2018-07-04
 */
public enum BizTypeEnum {
    /**
     * 所有(all)交易业务
     */
    ALL("all"),

    /**
     * 现货(spot)交易业务
     */
    SPOT("spot"),

    /**
     * 法币(c2c)交易业务
     */
    C2C("c2c"),

    /**
     * 合约(contracts)交易业务
     */
    CONTRACTS("contracts"),

    /**
     * 合约(asset)交易业务
     */
    ASSET("asset"),

    /**
     * 未知
     */
    UNKNOWN("unknown");

    private final String name;

    BizTypeEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static BizTypeEnum forName(final String name) {
        for (final BizTypeEnum value : values()) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return BizTypeEnum.UNKNOWN;
    }
}
