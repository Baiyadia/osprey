package com.kaiqi.osprey.common.consts;

import org.apache.commons.lang3.StringUtils;

/**
 * 应用环境常量
 *
 * @author newex-team
 * @date 2017/12/09
 **/
public class AppEnvConsts {
    /**
     * http context 路径境配置项
     */
    public final static String CONTEXT_PATH = "ctxPath";

    /**
     * 系统运行环境配置项
     */
    public final static String ENV_NAME_ITEM = "env";

    /**
     * 应用名称配置项
     */
    public final static String APP_NAME_ITEM = "appName";

    /**
     * 系统统一版本名称境配置项
     */
    public final static String VERSION_ITEM = "version";

    /**
     * 系统版本随机数配置项
     */
    public final static String RANDOM_ITEM = "rnd";

    /**
     * 系统统一版本名称境配置项默认值
     */
    public static String VERSION = "2.0";

    /**
     * 系统运行环境配置项默认值
     */
    public static String ENV_NAME = "prod";

    /**
     * 设置应用发布版本
     *
     * @param version
     */
    public static void setVersion(final String version) {
        AppEnvConsts.VERSION = version;
    }

    /**
     * 设置应用部署环境名，取值范围为[dev,test,pre,test]
     *
     * @param envName
     */
    public static void setEnvName(final String envName) {
        AppEnvConsts.ENV_NAME = envName;
    }

    /**
     * 应用部署环境名是否为生产环境
     *
     * @return true|false
     */
    public static boolean isProductionMode() {
        return StringUtils.equalsAnyIgnoreCase(ENV_NAME, "prod");
    }
}
