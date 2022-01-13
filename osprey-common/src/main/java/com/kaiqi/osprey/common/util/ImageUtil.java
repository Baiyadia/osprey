package com.kaiqi.osprey.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * @author wangs
 * @date 2018/03/18
 */
@Slf4j
public class ImageUtil {

    public static String getBase64Image(BufferedImage image) {
        String base64 = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "JPEG", byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            base64 = "data:image/png;base64," + new String(Base64.getEncoder().encode(bytes));
        } catch (Exception e) {
            ImageUtil.log.error("getBase64Image encode exception", e);
        }
        return base64;
    }

    /**
     * base64字符串转换成图片
     *
     * @param image base64字符串
     * @return
     */
    public static InputStream base64ToImage(String image) { // 对字节数组字符串进行Base64解码并生成图片
        // 图像数据为空
        if (StringUtils.isEmpty(image)) {
            return null;
        }
        try {
            // Base64解码
            byte[] b = Base64.getDecoder().decode(image);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            InputStream arrayInputStream = new ByteArrayInputStream(b);
            return arrayInputStream;
        } catch (Exception e) {
            return null;
        }
    }
}