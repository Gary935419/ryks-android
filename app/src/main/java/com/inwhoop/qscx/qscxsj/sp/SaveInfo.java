package com.inwhoop.qscx.qscxsj.sp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.inwhoop.qscx.qscxsj.commons.StaticValues;
import com.inwhoop.qscx.qscxsj.entitys.DriverPickBasicBean;
import com.inwhoop.qscx.qscxsj.entitys.UserBean;
import com.inwhoop.qscx.qscxsj.utils.gson.GsonUtil;

public class SaveInfo {

    @SuppressLint("StaticFieldLeak")
    private volatile static SaveInfo instance;
    private final Context context;

    public static SaveInfo getInstance(Context context) {
        if (instance == null)
            synchronized (SaveInfo.class) {
                if (instance == null)
                    instance = new SaveInfo(context.getApplicationContext());
            }
        return instance;
    }

    public SaveInfo(Context context) {
        this.context = context;
    }

    /**
     * 获取个人信息
     */
    public UserBean getLoginUserInfo() {
        String user = D.getInstance(context).getString(StaticValues.LOGIN_USERINFO_BEAN, "");
        return GsonUtil.toBean(user, UserBean.class);
    }

    /**
     * 保存个人信息
     */
    public void setLoginUserInfo(UserBean userBean) {
        String user = GsonUtil.toJson(userBean);
        D.getInstance(context).putString(StaticValues.LOGIN_USERINFO_BEAN, user);
    }

    /**
     * 获取基本信息
     */
    public DriverPickBasicBean getBasicInfo() {
        String basic = D.getInstance(context).getString(StaticValues.DRIVER_PICK_BASIC_BEAN, "");
        return GsonUtil.toBean(basic, DriverPickBasicBean.class);
    }

    /**
     * 保存基本信息
     */
    public void setBasicInfo(DriverPickBasicBean basicBean) {
        String basic = GsonUtil.toJson(basicBean);
        D.getInstance(context).putString(StaticValues.DRIVER_PICK_BASIC_BEAN, basic);
    }

    /**
     * 获取当前经度
     */
    public String getLng() {
        return D.getInstance(context).getString(StaticValues.LNG, "");
    }

    /**
     * 保存经度
     */
    public void setLng(String lng) {
        D.getInstance(context).putString(StaticValues.LNG, lng);
    }

    /**
     * 获取当前纬度
     */
    public String getLat() {
        return D.getInstance(context).getString(StaticValues.LAT, "");
    }

    /**
     * 保存纬度
     */
    public void setLat(String lat) {
        D.getInstance(context).putString(StaticValues.LAT, lat);
    }

    /**
     * 获取认证信息
     */
    public boolean isAuthentication() {
        UserBean userBean = getLoginUserInfo();
        if (userBean != null) {
            return userBean.getUser_check().equals("1") || userBean.getDriving_check().equals("1");
        }
        return false;
    }
}
