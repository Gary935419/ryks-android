package com.inwhoop.qscx.qscxsj.activitys.main;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.GetBasicService;
import com.inwhoop.qscx.qscxsj.business.MemberService;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_center_text;
    private ImageView title_back_img;

    private TextView tv_content;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initView();
        initListener();
    }

    private void initView() {
        type = getIntent().getStringExtra("type");
        title_center_text = (TextView) findViewById(R.id.tv_title_main);
        title_center_text.setText("关于我们");
        title_back_img = (ImageView) findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);
        tv_content = (TextView) findViewById(R.id.tv_content);
        if (type != null) {
            if (type.equals("deal")) {
                title_center_text.setText("《法律声明》");
                getDeal();
            } else if (type.equals("private")) {
                title_center_text.setText("《隐私政策》");
                getDeal();
            }
        } else {
            about_us();
        }
    }

    private void initListener() {
        title_back_img.setOnClickListener(this);
    }

    private void about_us() {
        MemberService.about_us(this, "2", new AsyncHttpHandler() {
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
                        tv_content.setText(jsonObject.optString("result"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getDeal() {
        GetBasicService.get_deal(this, "2", new AsyncHttpHandler() {
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
                        tv_content.setText(jsonObject.optString("result"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
        }
    }
}
