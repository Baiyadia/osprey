package com.kaiqi.osprey.common.consts;

/**
 * @author wangs
 */
public class RedisConsts {

    //===================Redis Const Start================================================================
    /**
     * 3分钟的秒数
     */
    public static final long DURATION_SECONDS_OF_3_MINTUES = 3 * 60;

    /**
     * 5分钟的秒数
     */
    public static final long DURATION_SECONDS_OF_5_MINTUES = 5 * 60;

    /**
     * 10分钟的秒数
     */
    public static final long DURATION_SECONDS_OF_10_MINTUES = 10 * 60;

    /**
     * 15分钟的秒数
     */
    public static final long DURATION_SECONDS_OF_15_MINTUES = 15 * 60;

    /**
     * 30分钟的秒数
     */
    public static final long DURATION_SECONDS_OF_30_MINTUES = 30 * 60;

    /**
     * 60分钟的秒数
     */
    public static final long DURATION_SECONDS_OF_60_MINTUES = 60 * 60;

    //===================Redis Const END================================================================

    //===================Redis Key Prefix Start================================================================

    /**
     * 用户注册/登录/验证码
     */
    public static final String VERIFICATION_CODE_KEY = "coinsafe:user:verification-code:";

    /**
     * 找回密码
     */
    public static final String USER_RESET_CODE_KEY = "coinsafe:user:reset-code:";
    /**
     * 登录二次验证
     */
    public static final String USER_STEP2_LOGIN_KEY = "coinsafe:user:step2-login-code:";

    //===================Redis Key Prefix END================================================================

    /**
     * 密码错误统计
     */
    public static final String PASSWORD_ERROR_TIMES_LIMIT_PRE = "coinsafe:user:password-error-times-limit:";

    /**
     * 密码错误是否通知过用户表示
     */
    public static final String PASSWORD_ERROR_TIMES_LIMIT_NOTIFY_TAG = "coinsafe:user:password-error-times-limit-notify-tag:";

    /**
     * 统计设备请求了多少次验证码
     */
    public static final String DEVICE_SEND_CODE_TIME_PRE = "coinsafe:user:device-send-code-time-pre:";
    /**
     * 统计邮箱请求了多少次验证码
     */
    public static final String EMAIL_SEND_CODE_TIME_PRE = "coinsafe:user:email-send-code-time-pre:";
    /**
     * 统计手机请求了多少次验证码
     */
    public static final String MOBILE_SEND_CODE_TIME_PRE = "coinsafe:user:mobile-send-code-time-pre:";

    //currency market

    /**
     *  set coinsafe:wallet:okex: pay,dpy,mkr
     */
    public static final String MARKET_COIN_SUPPORT_OKEX = "coinsafe:wallet:okex:";
    /**
     *  set coinsafe:wallet:fcoin: ft
     */
    public static final String MARKET_COIN_SUPPORT_FCOIN = "coinsafe:wallet:fcoin:";
    /**
     *  set coinsafe:wallet:binance: bnb,bnt,gnt,snt,rep,lrc,dgd
     */
    public static final String MARKET_COIN_SUPPORT_BINANCE = "coinsafe:wallet:binance:";
    /**
     * set coinsafe:wallet:zb: btc,eth,eos,bch,xrp,ada,ltc,dash,neo,doge,1st,bat,ae,gnt,xem,qtum,omg,snt
     */
    public static final String MARKET_COIN_SUPPORT_ZB = "coinsafe:wallet:zb:";

    public static final String MARKET_PRICE_BTC_PRE = "coinsafe:wallet:market-price-btc-pre:";
    public static final String MARKET_PRICE_USDT_PRE = "coinsafe:wallet:market-price-usdt-pre:";
    public static final String MARKET_PRICE_CNY_PRE = "coinsafe:wallet:market-price-cny-pre:";
    public static final String MARKET_PRICE_USDT_CNY = "coinsafe:wallet:market-price-usdt-cny:";

    public static final String GAS_BTC_BEST = "coinsafe:wallet:gas-btc-best";
    public static final String GAS_BTC_USER_BEST = "coinsafe:wallet:gas-btc-user-best";
    public static final String GAS_ETH_BEST = "coinsafe:wallet:gas-eth-best";

    // 消息推送相关

    /***
     * too many request 黑名单
     */
    public static final String TOO_MANY_REQUEST_IP = "coinsafe:notify:too-many-request-ip:";
    /**
     * 记录ip的访问速率
     */

    public static final String REQUEST_RATE_IP = "coinsafe:notify:request-rate-ip:";

    /**
     * 钱包相关消息
     */
    public static final String NOTIFY_WALLET_NOTICE = "coinsafe:notify:approve-wallet-notice";
    /**
     * 钱包创建成功消息
     */
    public static final String NOTIFY_WALLET_CREATE = "coinsafe:notify:approve-wallet-create";
    /**
     * 钱包加入消息
     */
    public static final String NOTIFY_WALLET_JOIN = "coinsafe:notify:approve-wallet-join";
    /**
     * 签名审批消息
     */
    public static final String NOTIFY_APPROVE_SIGNATURE = "coinsafe:notify:approve-signature";

    /**
     * 糖果消息
     */
    public static final String NOTIFY_SEND_CANDY = "coinsafe:notify:send-candy";

    /**
     * 充值消息
     */
    public static final String NOTIFY_DEPOSIT = "coinsafe:notify:deposit";
    /**
     * 提现消息
     */
    public static final String NOTIFY_WITHDRAW = "coinsafe:notify:withdraw";

    /**
     * 运营消息
     */
    public static final String NOTIFY_OPERATION = "coinsafe:notify:operation";
    /**
     * 登录消息
     */
    public static final String NOTIFY_LOGIN = "coinsafe:notify:login";

    /**
     * 登出消息
     */
    public static final String NOTIFY_LOGOUT = "coinsafe:notify:logout";

    public static final String USER_CHANGE_OLD_DEVICE = "coinsafe:device:step1:";

}
