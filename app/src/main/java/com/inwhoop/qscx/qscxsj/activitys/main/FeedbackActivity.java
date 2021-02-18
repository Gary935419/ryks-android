package com.inwhoop.qscx.qscxsj.activitys.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.MemberService;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_center_text;
    private ImageView title_back_img;

    private TextView activity_feedback_feedback_edt;
    private Button activity_feedback_ok_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initView();
        initListener();
    }

    private void initView() {
        title_center_text = (TextView) findViewById(R.id.tv_title_main);
        title_center_text.setText("意见反馈");
        title_back_img = (ImageView) findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);

        activity_feedback_feedback_edt = (TextView) findViewById(R.id.activity_feedback_feedback_edt);
        activity_feedback_ok_btn = (Button) findViewById(R.id.activity_feedback_ok_btn);
    }

    private void initListener() {
        title_back_img.setOnClickListener(this);
        activity_feedback_ok_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.activity_feedback_ok_btn:
                feedback();
                break;
        }
    }

    private void feedback() {

        String content = activity_feedback_feedback_edt.getText().toString().trim();

        if (content == null || content.equals("")) {
            ToastUtil.showShortToast(context, "请填写反馈信息");
            return;
        }

        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();

        MemberService.feedback(this, user_id, content, new AsyncHttpHandler() {
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
                        ToastUtil.showShortToast(context, jsonObject.optString("msg"));
                        FeedbackActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
