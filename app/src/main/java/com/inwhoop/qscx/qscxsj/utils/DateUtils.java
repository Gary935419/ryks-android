package com.inwhoop.qscx.qscxsj.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 处理时间日期的工具类（DateFormat和时间戳）
 */
public class DateUtils {

    public static String getTodayDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        return format.format(new Date());
    }

    /**
     * yyyy年MM月dd日HH时mm分ss秒, 返回时间戳
     *
     * @param time
     * @return
     */
    public String data(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    public static String getTodayDateTimes() {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        return format.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime_Today() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date());
    }

    /**
     * 传入yyyy-MM-dd, 返回时间戳
     *
     * @param time
     * @return
     */
    public static String date2timestamp(String time) {
        if (time == null || time.isEmpty() || time.replace(" ", "").isEmpty()) return "0";
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 传入yyyy-MM-dd HH:mm:ss, 返回时间戳
     *
     * @param time
     * @return
     */
    public static String date2timestamp2(String time) {
        if (time == null || time.isEmpty() || time.replace(" ", "").isEmpty()) return "0";
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    public static String getTimestamp(String time, String type) {
        SimpleDateFormat sdr = new SimpleDateFormat(type, Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * yyyy年MM月dd日HH时mm分ss秒
     *
     * @param time
     * @return
     */
    public static String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * yyyy年MM月dd日 HH:mm
     *
     * @param time
     * @return
     */
    public static String timet(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * yyyy/MM/dd,HH:mm
     *
     * @param time
     * @return
     */
    public static String timeslash(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy/MM/dd,HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * yyyy/MM/dd
     *
     * @param time
     * @return
     */
    public static String timeslashData(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy/MM/dd");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
//      int i = Integer.parseInt(time);
        String times = sdr.format(new Date(lcc * 1000L));
        return times;

    }

    /**
     * HH:mm
     *
     * @param time
     * @return
     */
    public static String timeMinute(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * yyyyMMdd HH:mm
     *
     * @param time
     * @return
     */
    public static String tim(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyyMMdd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    /**
     * yyyy-MM-dd HH:mm
     *
     * @param time
     * @return
     */
    public static String time(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    /**
     * 2014年06月14日16时09分00秒
     *
     * @param timeStamp
     * @return
     */
    public static String times(long timeStamp) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM月dd日  #  HH:mm");
        return sdr.format(new Date(timeStamp)).replaceAll("#",
                getWeek(timeStamp));
    }

    /**
     * 获取指定日期转换成星期几
     *
     * @param timeStamp
     * @return
     */
    private static String getWeek(long timeStamp) {
        int mydate = 0;
        String week = null;
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(timeStamp));
        mydate = cd.get(Calendar.DAY_OF_WEEK);
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = "周日";
        } else if (mydate == 2) {
            week = "周一";
        } else if (mydate == 3) {
            week = "周二";
        } else if (mydate == 4) {
            week = "周三";
        } else if (mydate == 5) {
            week = "周四";
        } else if (mydate == 6) {
            week = "周五";
        } else if (mydate == 7) {
            week = "周六";
        }
        return week;
    }

    /**
     * yyyy-MM-dd-HH-mm-ss
     *
     * @param time
     * @return
     */
    public String timesOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String timesTwo(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * 并用分割符把时间分成时间数组
     *
     * @param time
     * @return
     */
    public static String[] timestamp(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        String[] fenge = times.split("[年月日时分秒]");
        return fenge;
    }

    /**
     * 根据传递的类型格式化时间
     *
     * @param str
     * @param type 例如：yy-MM-dd
     * @return
     */
    public static String getDateTimeByMillisecond(String str, String type) {
        Date date = new Date(Long.valueOf(str));
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(date);
    }

    /**
     * 分割符把时间分成时间数组
     *
     * @param time
     * @return
     */
    public String[] division(String time) {

        String[] fenge = time.split("[年月日时分秒]");

        return fenge;

    }

    /**
     * 输入时间戳变星期
     *
     * @param time
     * @return
     */
    public static String changeweek(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(times);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;

    }

    /**
     * 获取日期和星期
     *
     * @param time 时间戳
     * @param type 类型（yyyy-MM-dd-HH-mm-ss）
     * @return yyyy-MM-dd-HH-mm-ss 星期数
     */
    public static String getDateAndWeek(String time, String type) {
        return getDateTimeByMillisecond(time + "000", type) + "  "
                + changeweekOne(time);
    }

    /**
     * 输入时间戳变星期
     *
     * @param time 时间戳
     * @return 星期数
     */
    public static String changeweekOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(times);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;

    }

    /**
     * 获取当前时间
     *
     * @return yyyy-MM-dd HH:mm
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date());
    }

    /**
     * 输入日期如（2014年06月14日16时09分00秒）返回（星期数）
     *
     * @param time 2014年06月14日16时09分00秒
     * @return 星期数
     */
    public String week(String time) {
        Date date = null;
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(time);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几  
        } catch (Exception e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;
    }

    /**
     * 输入日期如（2014-06-14-16-09-00）返回（星期数）
     *
     * @param time 2014-06-14-16-09-00
     * @return 星期数
     */
    public String weekOne(String time) {
        Date date = null;
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(time);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几  
        } catch (Exception e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param timeStr 时间戳
     * @return 距多久之前
     */
    public static String getDate(String timeStr) {
        StringBuffer sb = new StringBuffer();
        String date = "";
        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - (t * 1000);
        long mill = (long) Math.ceil(time / 1000);//秒前
        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前
        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时
        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前
        long month = 31 * day;// 月
        long year = 12 * month;// 年
        if (day < 0) {
            date = "天";
            return date;
        }
        if (day >= 10) {
            date = "天";
            return date;
        } else if (day - 1 > 0) {
            date = "天";
            return date;
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                date = "天";
                return date;
            } else {
                date = "小时";
                return date;
            }
        } else if (minute - 1 > 0) {
            L.e("时间++++1", minute + "");
            if (minute == 60) {
                date = "小时";
                L.e("时间++++2", minute + "");
                return date;
            } else {
                date = String.valueOf(minute);
                L.e("时间++++3", date);
                return date;
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                date = "1分钟";
                return date;
            } else {
                date = "秒";
                return date;
            }
        }

        return "提问时间未到";
    }

    /**
     * 传入时间戳，返回yyyy年MM月dd日
     *
     * @param timeStamp 时间戳
     * @return yyyy年MM月dd日
     */
    public static String stamp2time(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        if (!TextUtils.isEmpty(timeStamp)) {
            Date d = new Date(Long.parseLong(timeStamp + "000"));
            return sdf.format(d);
        }
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 传入时间戳，返回yyyy年MM月dd日 HH:mm
     *
     * @param timeStamp 时间戳
     * @return yyyy年MM月dd日 HH:mm
     */
    public static String stamp2time2(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
        if (!TextUtils.isEmpty(timeStamp)) {
            Date d = new Date(Long.parseLong(timeStamp + "000"));
            return sdf.format(d);
        }
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 传入时间戳，返回yyyy-MM-dd HH:mm
     *
     * @param timeStamp 时间戳
     * @return yyyy-MM-dd HH:mm
     */
    public static String stamp2time3(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        if (!TextUtils.isEmpty(timeStamp)) {
            Date d = new Date(Long.parseLong(timeStamp + "000"));
            return sdf.format(d);
        }
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 传入时间戳，返回yyyy-MM-dd HH:mm:ss
     *
     * @param timeStamp 时间戳
     * @return yyyy-MM-dd HH:mm
     */
    public static String stamp2time5(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            if (!TextUtils.isEmpty(timeStamp)) {
                if (timeStamp.length() == 10) {
                    timeStamp = timeStamp + "000";
                }
                Date d = new Date(Long.parseLong(timeStamp));
                return sdf.format(d);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 传入时间戳，返回yyyy-MM-dd
     *
     * @param timeStamp 时间戳
     * @return yyyy-MM-dd HH:mm
     */
    public static String stamp2time6(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        if (!TextUtils.isEmpty(timeStamp)) {
            Date d = new Date(Long.parseLong(timeStamp + "000"));
            return sdf.format(d);
        }
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 传入时间戳，返回HH:mm
     *
     * @param timeStamp 时间戳
     * @return HH:mm
     */
    public static String stamp2time4(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
        if (!TextUtils.isEmpty(timeStamp)) {
            Date d = new Date(Long.parseLong(timeStamp + "000"));
            return sdf.format(d);
        }
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 将秒数转化为分钟秒
     *
     * @param duration 秒数second
     * @return 分钟如 1'55"
     */
    public static String second2minSec(long duration) {
        String time = "";
        long minute = duration / 60;
        long seconds = duration % 60;
        long second = Math.round((float) seconds);
        time += minute + "'";
        if (second < 10) {
            time += "0";
        }
        time += second;
        time += "''";
        return time;
    }

    /**
     * 将秒数转化为分钟秒
     *
     * @param duration 秒数second
     * @return 分钟如 1'55"
     */
    public static String second2minSec2(long duration) {
        String time = "";
        long minute = duration / 60;
        long seconds = duration % 60;
        long second = Math.round((float) seconds);
        time += minute < 1 ? "" : minute + "'";
        if (second < 10) {
            time += "0";
        }
        time += second;
        time += "''";
        return time;
    }

    public static String getCurrentStamp() {
        return System.currentTimeMillis() / 1000 + "";
    }

    /**
     * 获取当前时间字符串yyyy-MM-dd HH:mm
     */
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return getDateString(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    /**
     * 获取时间yyyy-MM-dd HH:mm
     */
    public static String getDateString(int year, int month, int dayOfMonth, int hour, int minute) {
        Date date = new Date(year - 1900, month, dayOfMonth, hour, minute);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    /**
     * 获取当前时间HH:mm
     *
     * @return
     */
    public static String getNowTime() {
        return stamp2time4(getCurrentStamp());
    }

    /**
     * 传入 yyyy-MM-dd HH:mm:ss
     * 返回 yyyy-MM-dd
     */
    public static String dateToDate1(@NotNull String dateTime) {
        try {
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date date = sdr.parse(dateTime);
            return sdr.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 传入 yyyy-MM-dd HH:mm:ss
     * 返回 yyyy-MM-dd
     */
    public static String dateToDate2(@NotNull String dateTime) {
        try {
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            Date date = sdr.parse(dateTime);
            String s = sdr.format(date);
            return s.substring(s.length() - 5);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 传入 yyyy-MM-dd HH:mm
     * 返回 HH:mm
     */
    public static String dateToDate3(@NotNull String dateTime) {
        try {
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            Date date = sdr.parse(dateTime);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
            return sdf.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 传入 yyyy-MM-dd HH:mm
     * 返回 yyyy/MM/dd
     */
    public static String dateToDate4(@NotNull String dateTime) {
        try {
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            Date date = sdr.parse(dateTime);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
            return sdf.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 计算两个日期的天数差
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 差
     */
    public static int calculateDayCount(String v1, String v2) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(v1);
            Date d2 = df.parse(v2);
            return (int) ((d1.getTime() - d2.getTime()) / (60 * 60 * 1000 * 24));
        } catch (ParseException ex) {
            return 0;
        }
    }

    /**
     * 根据年月日获取日期字符串
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return yyyy-MM-dd
     */
    public static String getDateString(int year, int month, int day) {
        Date date = new Date(year - 1900, month, day);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * 比较两个日期的大小
     *
     * @param str1 第一个
     * @param str2 第二个
     * @return 第一个是否比第二个大 true大 false小
     */
    public static boolean compareDate(String str1, String str2, String format) {
        boolean isBigger = false;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
            if (dt1.getTime() > dt2.getTime()) {
                isBigger = true;
            } else if (dt1.getTime() < dt2.getTime()) {
                isBigger = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isBigger;
    }
}  