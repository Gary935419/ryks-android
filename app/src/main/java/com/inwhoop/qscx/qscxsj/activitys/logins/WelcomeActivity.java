package com.inwhoop.qscx.qscxsj.activitys.logins;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.activitys.MainActivity;
import com.inwhoop.qscx.qscxsj.business.PublicUserService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.entitys.UserBean;
import com.inwhoop.qscx.qscxsj.utils.FileUtils;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.utils.gson.GsonUtil;
import com.inwhoop.qscx.qscxsj.utils.statusbar.StatusBarUtil;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class WelcomeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private UserBean userBean;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.welcome_top));
        setContentView(R.layout.activity_welcome);
        showContacts();
        new Thread(new Runnable() {
            public void run() {
                user_id = LoginUserInfoUtil.getLoginUserInfoBean(context).getId();
                if (user_id != null && user_id.length() != 0) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                } else {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    WelcomeActivity.this.finish();
                }
            }

        }).start();
    }

    public void showContacts() {
        String[] perms = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (EasyPermissions.hasPermissions(this, perms)) {
            FileUtils.createAppFiles();
        } else {
            EasyPermissions.requestPermissions(this, "必要权限", 0, perms);
        }
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //下面两个方法是实现EasyPermissions的EasyPermissions.PermissionCallbacks接口
    //分别返回授权成功和失败的权限
    //成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
    }

    //失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtil.showShortToast(context, "获取权限失败！请自行手动开启" + "   perms " + perms.toString());
    }

    /**
     * 个人信息
     */
    private void requestUserInfo(String id) {
        PublicUserService.info(this, id, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "requestUserInfo: " + result);
                userBean = GsonUtil.toBean(result, UserBean.class);
                LoginUserInfoUtil.setLoginUserInfoBean(WelcomeActivity.this, userBean);
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                WelcomeActivity.this.finish();
                 /*if (userBean.getCheck().equals("0") || userBean.getCheck().equals("2") || userBean.getCheck().equals("3")) {
                            startActivity(new Intent(WelcomeActivity.this, AuthenticationActivity.class)
                                    .putExtra("check", userBean.getCheck())
                                    .putExtra("isBack", false)
                                    .putExtra("reason", userBean.getReason()));
                            WelcomeActivity.this.finish();
                        } else if (userBean.getCheck().equals("1")) {
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                            WelcomeActivity.this.finish();
                        }*/
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                ToastUtil.showShortToast(context, msg);
                openActivity(LoginActivity.class);
            }

            @Override
            public void onHttpError(String errorMsg) {
                ToastUtil.showShortToast(context, errorMsg);
                openActivity(LoginActivity.class);
            }
        }));
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            requestUserInfo(user_id);
        }
    };
}
