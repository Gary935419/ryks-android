package com.inwhoop.qscx.qscxsj.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inwhoop.qscx.qscxsj.commons.StaticValues;
import com.inwhoop.qscx.qscxsj.entitys.DriverPickBasicBean;
import com.inwhoop.qscx.qscxsj.entitys.UserBean;
import com.inwhoop.qscx.qscxsj.utils.gson.GsonUtil;

public class SharedPreferencesUtil {


    // ============================================================
    public static void saveLoginUserInfoBean(Context context, UserBean loginUserInfoBean) {
        SharedPreferences setting = context.getSharedPreferences(StaticValues.SHARE_INFO, context.MODE_PRIVATE);
        String loginUserInfoBeanString = GsonUtil.toJson(loginUserInfoBean);
        setting.edit().putString(StaticValues.LOGIN_USERINFO_BEAN, loginUserInfoBeanString).commit();
    }

    public static UserBean getLoginUserInfoBean(Context context) {
        SharedPreferences setting = context.getSharedPreferences(
                StaticValues.SHARE_INFO, context.MODE_PRIVATE);

        String loginUserInfoBeanString = setting.getString(
                StaticValues.LOGIN_USERINFO_BEAN, "");

        UserBean loginUserInfoBean = new Gson().fromJson(
                loginUserInfoBeanString, new TypeToken<UserBean>() {
                }.getType());

        return loginUserInfoBean;
    }

    // ============================================================

    public static void saveLng(Context context, String lng) {

        SharedPreferences setting = context.getSharedPreferences(
                StaticValues.SHARE_INFO, 0);

        setting.edit().putString(StaticValues.LNG, lng).commit();


    }

    public static String getLng(Context context) {

        SharedPreferences setting = context.getSharedPreferences(
                StaticValues.SHARE_INFO, 0);

        String lng = setting.getString(StaticValues.LNG, "0");

        return lng;
    }

    // ============================================================
    public static void saveLat(Context context, String lat) {

        SharedPreferences setting = context.getSharedPreferences(
                StaticValues.SHARE_INFO, 0);

        setting.edit().putString(StaticValues.LAT, lat).commit();


    }

    public static String getLat(Context context) {

        SharedPreferences setting = context.getSharedPreferences(
                StaticValues.SHARE_INFO, 0);

        String lat = setting.getString(StaticValues.LAT, "0");

        return lat;
    }

    // ============================================================
    public static void savePWD(Context context, String pwd) {

        SharedPreferences setting = context.getSharedPreferences(
                StaticValues.SHARE_INFO, 0);

        setting.edit().putString(StaticValues.PWD, pwd).commit();


    }

    public static String getPWD(Context context) {

        SharedPreferences setting = context.getSharedPreferences(
                StaticValues.SHARE_INFO, 0);

        String pwd = setting.getString(StaticValues.PWD, "0");

        return pwd;
    }

    // ============================================================

    public static void saveDriverPickBasicBean(Context context, DriverPickBasicBean driverPickBasicBean) {
        SharedPreferences setting = context.getSharedPreferences(StaticValues.SHARE_INFO, context.MODE_PRIVATE);
        String driverPickBasicBeanString = GsonUtil.toJson(driverPickBasicBean);
        setting.edit().putString(StaticValues.DRIVER_PICK_BASIC_BEAN, driverPickBasicBeanString).commit();
    }

    public static DriverPickBasicBean getDriverPickBasicBean(Context context) {
        SharedPreferences setting = context.getSharedPreferences(
                StaticValues.SHARE_INFO, context.MODE_PRIVATE);

        String driverPickBasicBeanString = setting.getString(
                StaticValues.DRIVER_PICK_BASIC_BEAN, "");

        DriverPickBasicBean driverPickBasicBean = new Gson().fromJson(
                driverPickBasicBeanString, new TypeToken<DriverPickBasicBean>() {
                }.getType());

        return driverPickBasicBean;
    }

    // ============================================================

    public static void saveAgreement(Context context, String agreement) {
        SharedPreferences setting = context.getSharedPreferences(
                StaticValues.SHARE_INFO, 0);
        setting.edit().putString("agreement", agreement).commit();
    }

    public static String getAgreement(Context context) {
        SharedPreferences setting = context.getSharedPreferences(
                StaticValues.SHARE_INFO, 0);
        String agreement = setting.getString("agreement", "");
        return agreement;
    }

}
