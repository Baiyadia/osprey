package com.kaiqi.osprey.user.util;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

/**
 * @author lilaizhen
 */
@Slf4j
public class VerificationCodeUtils {

    private static final Random RANDOM = new Random();

    public VerificationCodeUtils() {
    }

    /**
     * 获取4位长度的随机码
     *
     * @return
     */

    public static String generate4RandomCode() {
        return VerificationCodeUtils.generateRandomCode(4);
    }

    /**
     * 获取6位长度的随机码
     *
     * @return
     */

    public static String generate6RandomCode() {
        return VerificationCodeUtils.generateRandomCode(6);
    }

    public static String generate8RandomCode() {
        return VerificationCodeUtils.generate8RandomCode(8);
    }

    public static String generate8RandomCode(int length) {
        String[] codeStr = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(codeStr[VerificationCodeUtils.RANDOM.nextInt(codeStr.length)]);
        }
        return sb.toString();
    }

    public static String generateRandomCode(int length) {
        String[] codeStr = new String[] {
                "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(codeStr[VerificationCodeUtils.RANDOM.nextInt(codeStr.length)]);
        }
        return sb.toString();
    }

    public static Color createsRandomColor() {
        int r = (new Double(Math.random() * 256.0D)).intValue();
        int g = (new Double(Math.random() * 256.0D)).intValue();
        int b = (new Double(Math.random() * 256.0D)).intValue();
        return new Color(r, g, b);
    }

    public static Object[] getImageCode(int length) {
        int width = 120;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, 1);
        Graphics2D g = (Graphics2D) image.getGraphics();
        String code = VerificationCodeUtils.generateRandomCode(length);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] {
                Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color
                .MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW
        };
        float[] fractions = new float[colors.length];

        for (int i = 0; i < colors.length; ++i) {
            colors[i] = colorSpaces[VerificationCodeUtils.RANDOM.nextInt(colorSpaces.length)];
            fractions[i] = VerificationCodeUtils.RANDOM.nextFloat();
        }

        Arrays.sort(fractions);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
        Color c = VerificationCodeUtils.getRandColor(200, 250);
        g.setColor(c);
        g.fillRect(0, 2, width, height - 4);
        g.setColor(VerificationCodeUtils.getRandColor(160, 200));

        int area;
        int fontSize;
        int x;
        int y;
        for (int i = 0; i < 20; ++i) {
            area = VerificationCodeUtils.RANDOM.nextInt(width - 1);
            fontSize = VerificationCodeUtils.RANDOM.nextInt(height - 1);
            x = VerificationCodeUtils.RANDOM.nextInt(6) + 1;
            y = VerificationCodeUtils.RANDOM.nextInt(12) + 1;
            g.drawLine(area, fontSize + 5, area + x + 40, fontSize + y + 15);
        }

        float yawpRate = 0.05F;
        area = (int) (yawpRate * (float) width * (float) height);

        int i;
        for (fontSize = 0; fontSize < area; ++fontSize) {
            x = VerificationCodeUtils.RANDOM.nextInt(width);
            y = VerificationCodeUtils.RANDOM.nextInt(height);
            i = VerificationCodeUtils.getRandomIntColor();
            image.setRGB(x, y, i);
        }

        VerificationCodeUtils.shear(g, width, height, c);
        g.setColor(VerificationCodeUtils.getRandColor(100, 160));
        fontSize = height - 4;
        Font font = new Font("Microsoft YaHei", 2, fontSize);
        g.setFont(font);
        char[] chars = code.toCharArray();

        for (i = 0; i < 4; ++i) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(
                    0.7853981633974483D * VerificationCodeUtils.RANDOM.nextDouble() * (double) (VerificationCodeUtils.RANDOM.nextBoolean() ? 1 : -1),
                    (double) (width / 4 * i + fontSize / 2), (double) (height / 2)
            );
            g.setTransform(affine);
            g.drawChars(chars, i, 1, (width - 10) / 4 * i + 5, height / 2 + fontSize / 2 - 10);
        }

        g.dispose();
        return new Object[] { image, code };
    }

    private static Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int r = fc + VerificationCodeUtils.RANDOM.nextInt(bc - fc);
        int g = fc + VerificationCodeUtils.RANDOM.nextInt(bc - fc);
        int b = fc + VerificationCodeUtils.RANDOM.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        int[] rgb = VerificationCodeUtils.getRandomRgb();
        int color = 0;
        int[] var2 = rgb;
        int var3 = rgb.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            int c = var2[var4];
            color <<= 8;
            color |= c;
        }

        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];

        for (int i = 0; i < 3; ++i) {
            rgb[i] = VerificationCodeUtils.RANDOM.nextInt(255);
        }

        return rgb;
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        VerificationCodeUtils.shearX(g, w1, h1, color);
        VerificationCodeUtils.shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {
        int period = VerificationCodeUtils.RANDOM.nextInt(2);
        boolean borderGap = true;
        int frames = 1;
        int phase = VerificationCodeUtils.RANDOM.nextInt(2);

        for (int i = 0; i < h1; ++i) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + 6.283185307179586D * (double) phase / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {
        int period = VerificationCodeUtils.RANDOM.nextInt(40) + 10;
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;

        for (int i = 0; i < w1; ++i) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + 6.283185307179586D * (double) phase / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }

    /**
     * 获取n位随机验证码
     */
    public static String getRandNumber(int len) {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        Random random = new Random();
        while (true) {
            if (count > 6) {
                break;
            }
            int num = random.nextInt(10);
            if (count == 1 && num == 0) {
                continue;
            }
            if (sb.indexOf(String.valueOf(num)) == -1) {
                sb.append(num);
                count++;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(VerificationCodeUtils.generate8RandomCode());
    }
}
