package com.kaiqi.osprey.common.util;

/**
 * @author wangs
 * @date 2017/12/09
 **/
public class BitConvertUtil {

    /**
     * @param value
     * @param index
     * @param bit
     * @return
     */
    public static long setBinaryValue(long value, int index, int bit) {
        long dex = 1L;
        if (getBinaryValue(value, index) == 1) {
            if (bit == 0) {
                value = value - (dex << index - 1);
            }
        } else {
            if (bit == 1) {
                value = value | (dex << index - 1);
            }
        }
        return value;
    }

    /**
     * 获取值
     *
     * @param value 值
     * @param bit   位数
     * @return
     */
    public static long getBinaryValue(long value, int bit) {
        return value >> bit & 1;
    }
}
