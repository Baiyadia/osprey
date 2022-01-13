package com.kaiqi.osprey.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wangs
 * @date 2017/12/09
 * @since 0.0.1
 **/
public class DateUtil {

    public static final long FIVE_MINUTE = 5 * 60 * 1000L;
    public static final int WEEK_DAY_NUM = 7;
    public static final int HOUR_16_PM = 16;
    public static final int MINTUES_10 = 10;

    public static void main(String[] args) {
        System.out.println(LocalDate.now().toEpochDay());
    }

    /***
     * 毫秒数据格式化时间
     * @param date 时间毫秒
     * @return 格式为(HH mm ss)的时间字符串
     */
    public static String getFormatTime(long date) {
        return getFormatTime(new Date(date));
    }

    /**
     * 格式化为时间(HH:mm:ss)
     *
     * @param date 日期类型
     * @return 格式为(HH mm ss)的时间字符串
     */
    public static String getFormatTime(Date date) {
        return getFormatDate(date, "HH:mm:ss");
    }

    /**
     * 格式化为时间(yyyy-MM-dd HH:mm)
     *
     * @param date
     * @return
     */
    public static String getFormatDateTimeMinute(Date date) {
        return getFormatDate(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * 格式化为时间(yyyy-MM-dd HH:mm) 获取分钟数
     *
     * @param date
     * @return
     */
    public static Date getDateTimeMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 就获取今天今天0点时间
     *
     * @return
     */
    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取时间A与B之间的分钟差
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static long betweenMinute(Date dateA, Date dateB) {
        long between = dateB.getTime() - dateA.getTime();
        return between / (60 * 1000);
    }

    /**
     * 获取当前格式化时间（HH:mm:ss）
     *
     * @return HH:mm:ss格式的时间字符串
     */
    public static String getCurrentFormatTime() {
        return getFormatDate(new Date());
    }

    /**
     * 格式化日期为(yyyyMMdd)
     *
     * @param date 日期类型
     * @return 格式为(yyyyMMdd)的日期字符串
     */
    public static String getFormatDate(Date date) {
        return getFormatDate(date, "yyyyMMdd");
    }

    /**
     * 格式化日期为(yyyyMMdd)
     *
     * @param date 整型日期
     * @return 格式为(yyyy - MM - dd HH : mm : ss)的日期字符串
     */
    public static String getFormatDate(long date) {
        Date d = new Date(date);
        return getFormatDate(d, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化为日期(pattern)
     *
     * @param date    日期类型
     * @param pattern 日期时间模式
     * @return 格式为(pattern)的日期字符串
     */
    public static String getFormatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * @param date
     * @return
     */
    public static int getWeekOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0) {
            w = WEEK_DAY_NUM;
        }
        return w;
    }

    /**
     * @param hour
     * @return
     */
    public static long getTodayForHour(int hour) {
        Calendar beginningOfDayCalendar = Calendar.getInstance();
        beginningOfDayCalendar.set(Calendar.HOUR_OF_DAY, hour);
        beginningOfDayCalendar.set(Calendar.MINUTE, 0);
        beginningOfDayCalendar.set(Calendar.SECOND, 0);
        beginningOfDayCalendar.set(Calendar.MILLISECOND, 0);
        long beginningOfDayInMillis = beginningOfDayCalendar.getTimeInMillis();
        Date beginningOfDayDate = new Date(beginningOfDayInMillis);
        return beginningOfDayDate.getTime();
    }

    /**
     * 获得当前星期五
     *
     * @return
     */
    public static Date getFriday() {
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.FRIDAY:
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                if (hour > HOUR_16_PM || (hour == HOUR_16_PM && min >= MINTUES_10)) {
                    calendar.add(Calendar.DAY_OF_WEEK, 7);
                }
                break;
            //周6设置
            case Calendar.SATURDAY:
                calendar.add(Calendar.DAY_OF_WEEK, WEEK_DAY_NUM);
                break;
            default:
                break;
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        return calendar.getTime();
    }

    /**
     * date 日期加上或减去几天
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * date 日期加上或减去几小时
     *
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /***
     * 获取季度月最后一个周5
     * @return
     */
    public static String getSeasonFriday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, getSeason(calendar.getTime()) + 1);
        //获取前一个月4月1日
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //3月31日
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.DAY_OF_MONTH, Calendar.FRIDAY - calendar.get(Calendar.DAY_OF_WEEK));
        return getFormatDate(calendar.getTime());
    }

    /**
     * 获取当前月所属的季度
     * <p>
     * 1 第一季度
     * 2 第二季度
     * 3 第三季度
     * 4 第四季度
     *
     * @param date
     * @return 1|2|3|4
     */
    public static int getSeason(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                return Calendar.MARCH;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                return Calendar.JUNE;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                return Calendar.SEPTEMBER;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                return Calendar.DECEMBER;
            default:
                return Calendar.MARCH;
        }
    }

}
