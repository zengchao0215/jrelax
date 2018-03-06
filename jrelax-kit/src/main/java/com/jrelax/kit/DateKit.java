package com.jrelax.kit;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateKit {
    //预设日期格式
    public static final String YYYY = "yyyy";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss sss";

    public static final String YYYYMM = "yyyyMM";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHH = "yyyyMMdd HH";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmsssss";

    //时间格式
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String HH_MM = "HH:mm";
    public static final String HHMMSS = "HHmmss";
    public static final String HHMM = "HHmm";

    /**
     * 将字符串解析为日期
     *
     * @param dateStr 日期字符串 格式为 yyyy-MM-dd
     * @return
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, "yyyy-MM-dd");
    }

    /**
     * 将字符串解析为日期
     *
     * @param dateStr 日期字符串，格式取决于`format`
     * @param format  来源日期格式
     * @return
     */
    public static Date parse(String dateStr, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间格式化
     *
     * @param date   日期
     * @param format 格式
     * @return
     */
    public static String format(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 时间格式化
     *
     * @param milliSeconds 毫秒数
     * @param format       格式
     * @return
     */
    public static String format(long milliSeconds, String format) {
        Date date = new Date();
        date.setTime(milliSeconds);

        return format(date, format);
    }

    /**
     * 时间格式化
     *
     * @param date 日期
     * @return 格式为 yyyy-MM-dd
     */
    public static String format(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 获取当前时间
     *
     * @return 格式为 yyyy-MM-dd HH:mm:ss
     */
    public static String now() {
        return format(new Date(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前时间
     *
     * @param format 格式
     * @return
     */
    public static String now(String format) {
        return format(new Date(), format);
    }

    /**
     * 获取当前日期，格式 yyyy-MM-dd
     *
     * @return
     */
    public static String today() {
        return now(YYYY_MM_DD);
    }

    /**
     * 获取一天的开始
     *
     * @return
     */
    public static String todayOfStart() {
        return today() + " 00:00:00";
    }

    /**
     * 获取今天的开始
     *
     * @return
     */
    public static Date todayOfStartToDate() {
        return parse(today() + " 00:00:00", YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取一天的技术
     *
     * @return
     */
    public static String todayOfEnd() {
        return today() + " 23:59:59";
    }

    /**
     * 获取今天的结束
     *
     * @return
     */
    public static Date todayOfEndToDate() {
        return parse(today() + " 23:59:59", YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前时间
     * 返回日期对象
     *
     * @return
     */
    public static Date nowOfDate() {
        return new Date();
    }

    /**
     * 获取当前时间
     *
     * @param format 时间格式
     * @return
     */
    public static Date nowOfDate(String format) {
        return DateKit.parse(now(format));
    }

    /**
     * 获取某天的开始时间
     *
     * @param date
     * @return
     */
    public static Date getDayOfStart(Date date) {
        return parse(format(date) + " 00:00:00", YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取某天的结束时间
     *
     * @param date
     * @return
     */
    public static Date getDayOfEnd(Date date) {
        return parse(format(date) + " 23:59:59", YYYY_MM_DD_HH_MM_SS);
    }


    /**
     * 获取年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(1);
    }

    /**
     * 获取月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) + 1;
    }

    /**
     * 获取天
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(5);
    }

    /**
     * 获取星期
     *
     * @return 格式为 星期X
     */
    public static String getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String wk = "";
        switch (week) {
            case 1:
                wk = "星期天";
                break;
            case 2:
                wk = "星期一";
                break;
            case 3:
                wk = "星期二";
                break;
            case 4:
                wk = "星期三";
                break;
            case 5:
                wk = "星期四";
                break;
            case 6:
                wk = "星期五";
                break;
            case 7:
                wk = "星期六";
                break;
        }
        return wk;
    }

    /**
     * 获取月的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取年的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取小时
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(11);
    }

    /**
     * 获取分钟
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(12);
    }

    /**
     * 获取秒数
     *
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(13);
    }

    /**
     * 获取毫秒
     *
     * @param date
     * @return
     */
    public static long getMilliSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取某一年的第一天
     *
     * @param date 日期
     * @return
     */
    public static Date getFirstDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获取某一年的第一天
     *
     * @param year 年份
     * @return
     */
    public static Date getFirstDayOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获取某年某月的第一天
     *
     * @param date 日期
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 获取某年某月的第一天
     *
     * @param year  年份
     * @param month 月份
     * @return
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();

        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getTime();
    }

    /**
     * 获取某年某月某周的第一天
     * 按中国的习惯，第一天 = 周一
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.WEEK_OF_MONTH, week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    /**
     * 获取某年某月某周的第一天
     * 按中国的习惯，第一天 = 周一
     *
     * @param year  年份
     * @param month 月份
     * @param week  第几周
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int month, int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.WEEK_OF_MONTH, week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    /**
     * 获取某年的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);

        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return calendar.getTime();
    }

    /**
     * 获取某年的最后一天
     *
     * @param year 年份
     * @return
     */
    public static Date getLastDayOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);

        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return calendar.getTime();
    }

    /**
     * 获取某年某月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();

        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取某年某月某周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.WEEK_OF_MONTH, week);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        return calendar.getTime();
    }

    /**
     * 获取某年某月某周的最后一天
     *
     * @param year  年份
     * @param month 月份
     * @param week  周
     * @return
     */
    public static Date getLastDayOfWeek(int year, int month, int week) {
        Calendar calendar = Calendar.getInstance();

        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.WEEK_OF_MONTH, week);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        return calendar.getTime();
    }

    /**
     * 根据周数(月)获取周起始日期
     *
     * @param date 日期
     * @param week 周数
     * @return
     */
    public static Date[] getDatesOfWeekMonth(Date date, int week) {
        Date[] dates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.WEEK_OF_MONTH, week);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONTH);
        dates[0] = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dates[1] = calendar.getTime();

        return dates;
    }

    /**
     * 根据周数(月)获取周起始日期
     *
     * @param year
     * @param month
     * @param week
     * @return
     */
    public static Date[] getDatesOfWeekMonth(int year, int month, int week) {
        Date[] dates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.WEEK_OF_MONTH, week);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONTH);
        dates[0] = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dates[1] = calendar.getTime();

        return dates;
    }

    /**
     * 根据周数(年)获取周起始日期
     *
     * @param date
     * @param week
     * @return
     */
    public static Date[] getDatesOfWeekYear(Date date, int week) {
        Date[] dates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONTH);
        dates[0] = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dates[1] = calendar.getTime();

        return dates;
    }

    /**
     * 根据周数(年)获取周起始日期
     *
     * @param year
     * @param month
     * @param week
     * @return
     */
    public static Date[] getDatesOfWeekYear(int year, int month, int week) {
        Date[] dates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONTH);
        dates[0] = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dates[1] = calendar.getTime();

        return dates;
    }

    /**
     * 日期运算
     *
     * @param date  基准日期
     * @param years 年数，增加用整数，减少用负数
     * @return
     */
    public static Date calcOfYear(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    /**
     * 日期运算
     *
     * @param date   基准日期
     * @param months 月数，增加用整数，减少用负数
     * @return
     */
    public static Date calcOfMonth(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 日期运算
     *
     * @param date 基准日期
     * @param days 天数，增加用整数，减少用负数
     * @return
     */
    public static Date calcOfDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * 日期运算
     *
     * @param date  基准日期
     * @param weeks 周数，增加用整数，减少用负数
     * @return
     */
    public static Date calcOfWeek(Date date, int weeks) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_MONTH, weeks);
        return calendar.getTime();
    }

    /**
     * 日期运算
     *
     * @param date  基准日期
     * @param hours 小时数，增加用整数，减少用负数
     * @return
     */
    public static Date calcOfHour(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * 日期运算
     *
     * @param date    基准日期
     * @param minutes 分钟数，增加用整数，减少用负数
     * @return
     */
    public static Date calcOfMinute(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 日期运算
     *
     * @param date    基准日期
     * @param seconds 秒数，增加用整数，减少用负数
     * @return
     */
    public static Date calcOfSecond(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 日期运算
     *
     * @param date         基准日期
     * @param milliSeconds 毫秒数，增加用整数，减少用负数
     * @return
     */
    public static Date calcOfMilliSecond(Date date, int milliSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, milliSeconds);
        return calendar.getTime();
    }

    /**
     * 两个时间找出最晚的时间
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return
     */
    public static Date compareOfMax(Date date1, Date date2) {
        return date1.getTime() > date2.getTime() ? date1 : date2;
    }

    /**
     * 两个时间找出最早的时间
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return
     */
    public static Date compareOfMin(Date date1, Date date2) {
        return date1.getTime() < date2.getTime() ? date1 : date2;
    }

    /**
     * 计算两个时间的差距
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 年数
     */
    public static int compareOfDiffYear(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);

        return Math.abs(calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR));
    }

    /**
     * 计算两个时间的差距
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 月数
     */
    public static int compareOfDiffMonth(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);

        return (compareOfDiffYear(date1, date2) * 12) + Math.abs(calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH));
    }

    /**
     * 计算两个时间的差距
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 天数
     */
    public static int compareOfDiffDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);

        calendar2.setTime(date2);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);

        return Math.abs(((int) (calendar1.getTime().getTime() / 1000) - (int) (calendar2.getTime().getTime() / 1000)) / 3600 / 24);
    }

    /**
     * 计算两个时间的差距
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 毫秒数
     */
    public static long compareOfDiff(Date date1, Date date2) {
        return Math.abs(date1.getTime() - date2.getTime());
    }

    /**
     * 判断是否是闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取指定URL的网络时间
     *
     * @param link
     * @return
     */
    public static Date getNetDate(String link) {
        try {
            URL url = new URL(link);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            return new Date(ld);// 转换为标准时间对象
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取网络时间
     * 服务器为：http://www.baidu.com
     *
     * @return
     */
    public static Date getNetDate() {
        return getNetDate("http://www.baidu.com");
    }

    /**
     * date1时间是否在date2之前
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean before(Date date1, Date date2) {
        return date1.getTime() < date2.getTime();
    }

    /**
     * date1时间是否在date2之后
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean after(Date date1, Date date2) {
        return date1.getTime() > date2.getTime();
    }
}