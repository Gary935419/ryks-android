package com.inwhoop.qscx.qscxsj.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/2/2.
 */
public class TimeUtil {
    /**
     * 获取当前时间的时间戳
     *
     * @return string
     */
    public static String getTime() {
        Long tsLong = System.currentTimeMillis() / 1000;
        return tsLong.toString();
    }

    /**
     * 返回当前日期是一年中的第几天
     *
     * @return
     */
    public static int getDayPositionForYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("DDD");
        return Integer.parseInt(sdf.format(new Date()));
    }

    /**
     * 返回当前日期是一年中的第几周
     *
     * @return
     */
    public static int getWeekPositionForYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("ww");
        return Integer.parseInt(sdf.format(new Date()));
    }

    /**
     * 返回目标日期是一年中的第几周
     *
     * @return
     */
    public static int getWeekPositionForYear(long targetTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("ww");
        return Integer.parseInt(sdf.format(new Date(targetTime)));
    }

    /**
     * 返回当前日期是一年中的第几月
     *
     * @return
     */
    public static int getMonthPositionForYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return Integer.parseInt(sdf.format(new Date()));
    }

    /**
     * 返回目标日期是一年中的第几月
     *
     * @return
     */
    public static int getMonthPositionForYear(long targetTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return Integer.parseInt(sdf.format(new Date(targetTime)));
    }


    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012年03月12日
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
//  String pTime = "2012年03月12日";
    public static String getWeek(String pTime) {

        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }

        return Week;
    }


    /**
     * 获取密钥字段
     *
     * @return
     */
    public static String getKeyStr() {
        String time = getTime();
        String tiem1_5 = time.substring(0, 5);
        String time6_10 = time.substring(5, 10);
        String key = "8es9w98yw&^%eq98";
        System.out.println("时间�?1-5=" + tiem1_5 + "6-10=" + time6_10);
        return "&key=" + md5(tiem1_5 + key + time6_10) + "&time=" + time;
    }

    /**
     * 获取KEY
     *
     * @return
     */
    public static String getKey() {
        String time = getTime();
        String tiem1_5 = time.substring(0, 5);
        String time6_10 = time.substring(5, 10);
        String key = "8es9w98yw&^%eq98";
        System.out.println("时间�?1-5=" + tiem1_5 + "6-10=" + time6_10);
        return md5(tiem1_5 + key + time6_10);
    }

    /**
     * MD5加密
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 获取时间的差�?
     *
     * @param startTime �?始时�? 10位时间戳
     * @param endTime   结束时间 10位时间戳
     * @return 小时�?
     */
    public static String getTimePoor(String startTime, String endTime) {
        float a = (float) ((Long.valueOf(endTime) - Long.valueOf(startTime)));
        float hour = (a / (60 * 60));
        return meg(hour) + "";
    }

    /**
     * 保留小数点后两位小数
     *
     * @param i
     * @return
     */
    public static double meg(double i) {
        int b = (int) Math.round(i * 100); // 小数点后两位前移，并四舍五入
        double c = ((double) b / 100.00); // 还原小数点后两位

        return c;
    }

    /**
     * 时间戳转日期字符�?
     *
     * @param time 10位时间戳
     * @return
     */
    public static String timeToStr(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm");
        String date = sdf.format(new Date(time * 1000));
        return date;
    }

    public static String timeToStr(long time, String model) {
        SimpleDateFormat sdf = new SimpleDateFormat(model);
        String date = sdf.format(new Date(time * 1000));
        return date;
    }

    public static String timeToStr(String time) {
        if (!"".equals(time) && time != null) {
            long timeString = Long.parseLong(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm");
            String date = sdf.format(new Date(timeString * 1000));
            return date;
        } else {
            return "1970-01-01";
        }
    }

    public static String timeToStr(String time, String model) {
        if (!"".equals(time) && time != null) {
            long timeString = Long.parseLong(time);
            SimpleDateFormat sdf = new SimpleDateFormat(model);
            String date = sdf.format(new Date(timeString * 1000));
            return date;
        } else {
            return "1970-01-01";
        }
    }

    /**
     * 日期字符串转时间�?
     *
     * @param user_time 日期
     * @return
     */

    public static String getTime(String user_time, int type) {
        String re_time = null;
        SimpleDateFormat sdf;
        if (type == 1) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        Date d = null;
        try {
            d = sdf.parse(user_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long l = d.getTime();
        String str = String.valueOf(l);
//		re_time = str.substring(0, 10);
        re_time = str.substring(0, str.length() - 3);

        return re_time;
    }

    /**
     * 日期字符串转时间�?
     *
     * @param user_time 日期
     * @return
     */

    public static String getTime(String user_time, String model) {
        String re_time = null;
        SimpleDateFormat sdf;

        sdf = new SimpleDateFormat(model);

        Date d = null;
        try {
            d = sdf.parse(user_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long l = d.getTime();
        String str = String.valueOf(l);
//		re_time = str.substring(0, 10);
        re_time = str.substring(0, str.length() - 3);

        return re_time;
    }


    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param timeStr 时间戳
     * @return
     */
    public static String getStandardDate(String timeStr) {

        StringBuffer sb = new StringBuffer();

        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - (t * 1000);
        long mill = (long) Math.ceil(time / 1000);//秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }


    public static long getTimeToNow(String selectTimeStr) {
        long selectTime = Long.parseLong(selectTimeStr);
        long nowTime = Long.parseLong(getTime(timeToStr(getTime(), "yyyy年MM月dd日"), "yyyy年MM月dd日"));

        long distanceTime = selectTime - nowTime;


        long distanceDays = distanceTime / 86400;

        return distanceDays;
    }

    public static long getHoursToNow(String selectTimeStr) {
        long selectTime = Long.parseLong(selectTimeStr);
        long nowTime = Long.parseLong(getTime(timeToStr(getTime(), "yyyy年MM月dd日"), "yyyy年MM月dd日"));

        long distanceTime = selectTime - nowTime;


        long distanceDays = distanceTime / 3600;

        return distanceDays;
    }

    public static List<String> getEveryDay(String startTime, String endTime) {
        long startt = Long.valueOf(getTime(startTime, 1));
        long endt = Long.valueOf(getTime(endTime, 1));
        long poorT = endt - startt;
        int day = (int) poorT / (3600 * 24);
        List<String> list = new ArrayList<String>();
        list.add(startTime);
        for (int i = 1; i < day; i++) {
            list.add(timeToStr(startt + 3600 * 24 * i));
        }
        list.add(endTime);
        return list;
    }

    public static String getCurrTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    public static String getCurrTime2() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    public static String getCurrTime3() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    public static double jisuan(String date1, String date2) {
        double result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start;
        try {
            start = sdf.parse(date1);
            Date end = sdf.parse(date2);
            long cha = end.getTime() - start.getTime();
            result = cha * 1.0 / (1000 * 60 * 60);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    // 获取手机IMEI码 .平板为null，就要取设备号
    public static String getUdid(Context mContext) {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String ss = telephonyManager.getDeviceId();
        if (null == ss)
            ss = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        return ss;
    }

    // 加密
    public static String getBase64(String str) {
        // 加密传入的数据是byte类型的，并非使用decode方法将原始数据转二进制，String类型的数据 使用 str.getBytes()即可
        // 在这里使用的是encode方式，返回的是byte类型加密数据，可使用new String转为String类型
        String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));

        // 这里 encodeToString 则直接将返回String类型的加密数据
        String enToStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);

        return enToStr;
    }
}
