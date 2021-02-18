package com.inwhoop.qscx.qscxsj.utils;

import android.content.Context;

import com.inwhoop.qscx.qscxsj.entitys.DriverPickBasicBean;
import com.inwhoop.qscx.qscxsj.entitys.UserBean;
import com.inwhoop.qscx.qscxsj.sp.SaveInfo;


public class LoginUserInfoUtil {

    public static UserBean getLoginUserInfoBean(Context context) {

        try {
            UserBean loginUserInfoBean = SharedPreferencesUtil.getLoginUserInfoBean(context);
            if (loginUserInfoBean != null) {
                return loginUserInfoBean;
            } else {
                return new UserBean();
            }
        } catch (Exception e) {
            return new UserBean();
        }
    }

    public static void setLoginUserInfoBean(Context context, UserBean loginUserInfoBean) {
        SaveInfo.getInstance(context).setLoginUserInfo(loginUserInfoBean);
    }

    public static DriverPickBasicBean getDriverPickBasicBean(Context context) {

        try {
            DriverPickBasicBean driverPickBasicBean = SharedPreferencesUtil
                    .getDriverPickBasicBean(context);
            if (driverPickBasicBean != null) {
                return driverPickBasicBean;
            } else {
                return new DriverPickBasicBean();
            }
        } catch (Exception e) {
            return new DriverPickBasicBean();
        }
    }

    public static void setDriverPickBasicBean(Context context,
                                              DriverPickBasicBean driverPickBasicBean) {
        // LoginUserInfoUtil.loginUserInfoBean = loginUserInfoBean;

        SharedPreferencesUtil.saveDriverPickBasicBean(context, driverPickBasicBean);

    }

    public static boolean isAuthentication(Context context) {
        UserBean userBean = getLoginUserInfoBean(context);
        return userBean.getUser_check().equals("1") || userBean.getDriving_check().equals("1");
    }
}
