package com.inwhoop.qscx.qscxsj.commons;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;

import org.json.JSONArray;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/5.
 */
public class Statics {

    //字体设置
    public static Typeface typeface;
    //是否是第一次登录
    public static boolean isFristLogin = false;
    //屏幕宽高
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    //记录上一次请求短信验证码
    public static int register_yzmtime = 0;
    public static int forgetpassword_yzmtime = 0;

    //用户信息
    public static String USER_ACCOUNT;
    public static String USER_PASSWORD;
    public static String USER_ID = "";
    public static String USER_IMG = "";
    public static String USER_NAME = "";
    public static String USER_DATA = "";
    public static String USER_SEX = "";
    public static String USER_ICON_URL = "";
    public static double USER_BALANCE = 0;
    public static String USER_BANGDING = "";

    //文件保存路径
    public static final String FILE_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zzjx/cache/";
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zzjx/file/";
    //是否有DeBugger
    public static final boolean IS_DEBUG = false;
    //版本
    public static int SDK_INT = Build.VERSION.SDK_INT;

    public static String IMAGELOADER_TAG_ICON_USERHEAD = "0";

    //是不是修改密码
    public static Boolean isChangePassword = false;

    //计算年龄
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    //do nothing
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        } else {
            //monthNow<monthBirth
            //donothing
        }

        return age;
    }

    public static String Select_province_id = "1";
    public static String Select_city_id = "1";
    public static String Select_county_id = "";
    public static JSONArray Area_all_array;
}
