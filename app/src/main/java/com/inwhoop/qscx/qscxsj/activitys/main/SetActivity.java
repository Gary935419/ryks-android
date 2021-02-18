package com.inwhoop.qscx.qscxsj.activitys.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.activitys.MainActivity;
import com.inwhoop.qscx.qscxsj.activitys.logins.LoginActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.GetBasicService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.entitys.UserBean;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.SharedPreferencesUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class SetActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title_main;
    private ImageView iv_title_left;
    private RelativeLayout layout_feedback;
    private RelativeLayout layout_about_us;
    private RelativeLayout layout_account;
    private RelativeLayout layout_agreement_rule;
    private Button btn_logout;

    private String agreement = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        initView();
        initListener();
        getAgreement();
    }

    private void initView() {
        tv_title_main = findViewById(R.id.tv_title_main);
        tv_title_main.setText("设置");
        iv_title_left = findViewById(R.id.iv_title_left);
        iv_title_left.setVisibility(View.VISIBLE);
        layout_account = findViewById(R.id.layout_account);
        layout_agreement_rule = findViewById(R.id.layout_agreement_rule);
        layout_feedback = findViewById(R.id.layout_feedback);
        layout_about_us = findViewById(R.id.layout_about_us);
        btn_logout = findViewById(R.id.btn_logout);
    }

    private void initListener() {
        iv_title_left.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        layout_feedback.setOnClickListener(this);
        layout_about_us.setOnClickListener(this);
        layout_account.setOnClickListener(this);
        layout_agreement_rule.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.btn_logout:
                UserBean userBean = new UserBean();
                LoginUserInfoUtil.setLoginUserInfoBean(this, userBean);
                MainActivity.getInstance().finish();
                this.finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.layout_feedback:
//                ToastUtil.showShortToast(this, "意见反馈");
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.layout_about_us:
//                ToastUtil.showShortToast(this, "关于我们");
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.layout_account:
                startActivity(new Intent(this, AccountActivity.class));
                break;
            case R.id.layout_agreement_rule:
                if (TextUtils.isEmpty(agreement)) {
                    if (TextUtils.isEmpty(SharedPreferencesUtil.getAgreement(context))) {
                        ToastUtil.showShortToast(context, "链接获取失败!");
                        return;
                    } else {
                        agreement = SharedPreferencesUtil.getAgreement(context);
                    }
                }
                startActivity(new Intent(this, AgreementActivity.class).putExtra("agreement", agreement));
                break;
        }
    }

    private void getAgreement() {
        GetBasicService.get_agreement(this, new AsyncHttpHandler() {
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
                        agreement = responseString;
                        SharedPreferencesUtil.saveAgreement(context, agreement);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
