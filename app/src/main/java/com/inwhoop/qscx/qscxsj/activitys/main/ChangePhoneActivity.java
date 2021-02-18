package com.inwhoop.qscx.qscxsj.activitys.main;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.activitys.logins.LoginActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.business.PublicUserService;
import com.inwhoop.qscx.qscxsj.entitys.UserBean;
import com.inwhoop.qscx.qscxsj.tools.ActivitySwitchManager;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ChangePhoneActivity extends BaseActivity {

    private EditText activity_login_account_edt;
    private EditText activity_login_code_edt;
    private Button activity_login_code_btn;
    private Button submitBtn;

    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);

        TextView title_center_text = (TextView) findViewById(R.id.tv_title_main);
        title_center_text.setText("修改手机号");
        ImageView title_back_img = (ImageView) findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);
        title_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activity_login_account_edt = (EditText) findViewById(R.id.activity_login_account_edt);
        activity_login_code_edt = (EditText) findViewById(R.id.activity_login_code_edt);
        activity_login_code_btn = (Button) findViewById(R.id.activity_login_code_btn);
        submitBtn = (Button) findViewById(R.id.submit_btn);

        activity_login_code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVerifyCode();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doChangePhone();
            }
        });

        time = new TimeCount(60000, 1000);

    }

    private void getVerifyCode() {

        String account = activity_login_account_edt.getText().toString().trim();

        if (account == null || account.equals("")) {
            ToastUtil.showShortToast(this, "请输入手机号");
            return;
        }

        if (!isMobileNO(account)) {
            ToastUtil.showShortToast(this, "请输入正确的手机号");
            return;
        }

        time.start();

        PublicUserService.get_verify_code(this, account, new AsyncHttpHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(responseString);
            }
        });
    }

    private void doChangePhone() {

        String account = activity_login_account_edt.getText().toString().trim();
        String vCodeStr = activity_login_code_edt.getText().toString().trim();

        if (account == null || account.equals("")) {
            ToastUtil.showShortToast(this, "请输入手机号");
            return;
        }

        if (!isMobileNO(account)) {
            ToastUtil.showShortToast(this, "请输入正确的手机号");
            return;
        }

        if (TextUtils.isEmpty(vCodeStr)) {
            ToastUtil.showShortToast(this, "请输入验证码");
            return;
        }

        PublicUserService.changePhone(this,
                LoginUserInfoUtil.getLoginUserInfoBean(this).getId(),
                account,
                vCodeStr,
                new AsyncHttpHandler() {
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
                                info(LoginUserInfoUtil.getLoginUserInfoBean(context).getId());
                                ToastUtil.showShortToast(ChangePhoneActivity.this, "修改成功");
                            } else {
                                ToastUtil.showShortToast(ChangePhoneActivity.this, jsonObject.optString("msg"));
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
                        UserBean userBean = new Gson().fromJson(jsonObject.optString("result"),
                                new TypeToken<UserBean>() {
                                }.getType());
                        LoginUserInfoUtil.setLoginUserInfoBean(ChangePhoneActivity.this, userBean);
                        ChangePhoneActivity.this.finish();
                    } else {
                        ToastUtil.showShortToast(ChangePhoneActivity.this, jsonObject.optString("msg"));
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
}