package com.kaiqi.osprey.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author wangs
 * @date 2017/12/09
 * @since 0.0.1
 **/
public class DoubleUtil {

    /**
     * 根据scale的值动态格式化double类型数据
     *
     * @param value double数据
     * @param scale 精度位数(保留的小数位数)
     * @return 格式化后的数据
     */
    public static String format(double value, int scale) {
        String pattern = StringUtils.appendIfMissing("0.", StringUtils.repeat('0', scale));
        return new DecimalFormat(pattern).format(value);
    }

    /**
     * 对double数据进行取精度.
     *
     * @param value double数据.
     * @param scale 精度位数(保留的小数位数).
     * @return 精度计算后的数据.
     */
    public static double round(double value, int scale) {
        int n = (int) Math.pow(10, scale);
        return divide(Math.floor(multiply(value, n)), n, scale);
    }

    /**
     * 小数向上进位
     *
     * @param value double数据.
     * @param scale 精度位数(保留的小数位数).
     * @return
     */
    public static double roundUp(double value, int scale) {
        int n = (int) Math.pow(10, scale);
        double result = divide(Math.ceil(multiply(value, n)), n, scale);
        return result;
    }

    /**
     * double 相加
     *
     * @param d1
     * @param d2s
     * @return
     */
    public static double add(double d1, double... d2s) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        for (double d2 : d2s) {
            BigDecimal bd2 = new BigDecimal(Double.toString(d2));
            bd1 = bd1.add(bd2);
        }
        return bd1.doubleValue();
    }

    /**
     * double 相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double subtract(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 乘法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double multiply(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * double 多数乘法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double multiply(double d1, double... d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        for (double d : d2) {
            bd1 = bd1.multiply(new BigDecimal(Double.toString(d)));
        }
        return bd1.doubleValue();
    }

    /**
     * double 除法并四舍五入，在此之前，你要判断分母是否为0，
     *
     * @param d1    除数
     * @param d2    被除数
     * @param scale 精度位数(保留的小数位数).
     * @return
     */
    public static double divide(double d1, double d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
