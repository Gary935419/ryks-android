package com.inwhoop.qscx.qscxsj.activitys.logins;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.activitys.MainActivity;
import com.inwhoop.qscx.qscxsj.activitys.main.WebViewActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.PublicUserService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.UserBean;
import com.inwhoop.qscx.qscxsj.tools.ActivitySwitchManager;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    private TextView title_center_text;

    private EditText activity_login_account_edt;
    private EditText activity_login_code_edt;
    private Button activity_login_code_btn;
    private Button activity_login_btn;
    private CheckBox activity_agree_service_check;
    private TextView activity_service_tv;
    private TextView activity_private;

    private TimeCount time;

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initListener();

        showContacts();

    }

    public void showContacts() {
        String[] perms = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                // Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (EasyPermissions.hasPermissions(this, perms)) {

        } else {
            EasyPermissions.requestPermissions(this, "必要权限", 0, perms);
        }
    }

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

    private void initView() {
        title_center_text = findViewById(R.id.tv_title_main);
        title_center_text.setText("登录");

        activity_login_account_edt = findViewById(R.id.activity_login_account_edt);
        activity_login_code_edt = findViewById(R.id.activity_login_code_edt);
        activity_login_code_btn = findViewById(R.id.activity_login_code_btn);
        activity_login_btn = findViewById(R.id.activity_login_btn);
        activity_agree_service_check = findViewById(R.id.activity_agree_service_check);
        activity_service_tv = findViewById(R.id.activity_service_tv);
        activity_private = findViewById(R.id.activity_private);

        activity_agree_service_check.setChecked(true);

        time = new TimeCount(60000, 1000);
    }

    private void initListener() {
        activity_login_code_btn.setOnClickListener(this);
        activity_login_btn.setOnClickListener(this);
        activity_service_tv.setOnClickListener(this);
        activity_private.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_code_btn:
                requestVerifyCode();
                break;
            case R.id.activity_login_btn:
                login();
//                startActivity(new Intent(this, AuthenticationActivity.class));
                break;
            case R.id.activity_service_tv:
//                startActivity(new Intent(this, AboutUsActivity.class).putExtra("type", "deal"));
                startActivity(new Intent(this, WebViewActivity.class).putExtra("url", Constants.URL_LAW).putExtra("title", "法律声明"));
                break;
            case R.id.activity_private:
//                startActivity(new Intent(this, AboutUsActivity.class).putExtra("type", "private"));
                startActivity(new Intent(this, WebViewActivity.class).putExtra("url", Constants.URL_PRIVATE).putExtra("title", "隐私政策"));
                break;
        }
    }

    private void requestVerifyCode() {
        String account = activity_login_account_edt.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.showShortToast(this, "请输入手机号");
            return;
        }
        if (!isMobileNO(account)) {
            ToastUtil.showShortToast(this, "请输入正确的手机号");
            return;
        }
        time.start();
        PublicUserService.get_verify_code(this, account, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "getVerifyCode: " + result);
                ToastUtil.showShortToast(context, msg);
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                ToastUtil.showShortToast(context, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                ToastUtil.showShortToast(context, errorMsg);
            }
        }));
    }

    /**
     * 登录
     */
    private void login() {
        String account = activity_login_account_edt.getText().toString().trim();
        String code = activity_login_code_edt.getText().toString().trim();
        if (account == null || account.equals("")) {
            ToastUtil.showShortToast(this, "请输入手机号");
            return;
        }
        if (!isMobileNO(account)) {
            ToastUtil.showShortToast(this, "请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShortToast(this, "请输入验证码");
            return;
        }
        if (!activity_agree_service_check.isChecked()) {
            ToastUtil.showShortToast(this, "请阅读并同意服务协定");
            return;
        }

        PublicUserService.login(this, "2", account, code, new AsyncHttpHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(responseString);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(responseString);
                    if (jsonObject.optInt("status") == 200) {
                        info(jsonObject.getString("result"));
                    } else {
                        ToastUtil.showShortToast(LoginActivity.this, jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void info(String id) {
        PublicUserService.info(this, id, new AsyncHttpHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(responseString);
                JSONObject jsonObject;
                try {

                    jsonObject = new JSONObject(responseString);
                    if (jsonObject.optInt("status") == 200) {
                        userBean = new Gson().fromJson(jsonObject.optString("result"),
                                new TypeToken<UserBean>() {
                                }.getType());
                        LoginUserInfoUtil.setLoginUserInfoBean(LoginActivity.this, userBean);
//                        if (userBean.getCheck().equals("0") || userBean.getCheck().equals("2") || userBean.getCheck().equals("3")) {
//                            startActivity(new Intent(LoginActivity.this, AuthenticationActivity.class)
//                                    .putExtra("check", userBean.getCheck())
//                                    .putExtra("isBack", false)
//                                    .putExtra("reason", userBean.getReason()));
//                            LoginActivity.this.finish();
//                        } else if (userBean.getCheck().equals("1")) {
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                            LoginActivity.this.finish();
//                        }
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        LoginActivity.this.finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            activity_login_code_btn.setText("重新发送");
            activity_login_code_btn.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            activity_login_code_btn.setClickable(false);
            activity_login_code_btn.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            // finish();
            ActivitySwitchManager.simulationPressHomekey(this);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
