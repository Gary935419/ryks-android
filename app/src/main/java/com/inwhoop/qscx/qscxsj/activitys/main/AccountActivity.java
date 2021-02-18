package com.inwhoop.qscx.qscxsj.activitys.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.activitys.MainActivity;
import com.inwhoop.qscx.qscxsj.activitys.logins.LoginActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.PublicUserService;
import com.inwhoop.qscx.qscxsj.entitys.UserBean;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AccountActivity extends BaseActivity {

    private RelativeLayout changePhoneRl;
    private RelativeLayout unregisterAccountRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        TextView title_center_text = (TextView) findViewById(R.id.tv_title_main);
        title_center_text.setText("设置");
        ImageView title_back_img = (ImageView) findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);

        changePhoneRl = findViewById(R.id.change_phone_rl);
        unregisterAccountRl = findViewById(R.id.unregister_account_rl);

        changePhoneRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AccountActivity.this, ChangePhoneActivity.class));

            }
        });

        unregisterAccountRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doUnregister();

            }
        });

        title_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void doUnregister() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("确认注销账户？")
                .setMessage("是否确认注销账户？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismissAlertDialog();
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PublicUserService.unregisterAccount(AccountActivity.this, LoginUserInfoUtil.getLoginUserInfoBean(AccountActivity.this).getId(), new AsyncHttpHandler() {
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
                                        UserBean userBean = new UserBean();
                                        LoginUserInfoUtil.setLoginUserInfoBean(AccountActivity.this, userBean);
                                        MainActivity.getInstance().finish();
                                        AccountActivity.this.finish();
                                        startActivity(new Intent(AccountActivity.this, LoginActivity.class));
                                    } else {
                                        ToastUtil.showShortToast(AccountActivity.this, jsonObject.optString("msg"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });

        builder.create().show();
    }
}