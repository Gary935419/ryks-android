package com.inwhoop.qscx.qscxsj.activitys.logins;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;

public class AuthenticationInfoActivity extends BaseActivity {
    private Button activity_authentication_info_ok_btn;
    private TextView title_center_text;
    private TextView tvPhone;
    private ImageView title_back_img;
    private static final String phoneNum = "4000000739";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_info);
        initView();
    }

    private void initView() {
        title_center_text = findViewById(R.id.tv_title_main);
        tvPhone = findViewById(R.id.tvPhone);
        title_center_text.setText("欢迎加入如邮快送");
        title_back_img = findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);
        title_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        activity_authentication_info_ok_btn = findViewById(R.id.activity_authentication_info_ok_btn);
        activity_authentication_info_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String str = "&#160;&#160;&#160;&#160;联系电话：<font color='#0000FF'>" + phoneNum + "</font>";
        tvPhone.setText(Html.fromHtml(str));
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                        + phoneNum));
                call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(call);
            }
        });
    }
}
