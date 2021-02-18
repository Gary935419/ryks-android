package com.inwhoop.qscx.qscxsj.activitys.ordertaking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.entitys.ComplainBean;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * @Author: Samson.Sun
 * @time: 2020年8月4日20:27:29 20:27
 * @UpdateAuthor:
 * @UpdateTime: 2020年8月4日20:27:29 20:27
 * @Description:
 **/
public class ComplainActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_center_text;
    private ImageView title_back_img;
    private ComplainBean complainBean;
    private EditText activity_feedback_feedback_edt;
    private Button activity_feedback_ok_btn;

    /**
     * 跳转方法
     */
    public static void startIntent(Context context, ComplainBean bean) {
        Intent intent = new Intent(context, ComplainActivity.class);
        intent.putExtra("complain", bean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_complain);
        complainBean = (ComplainBean) getIntent().getSerializableExtra("complain");
        initView();
    }

    private void initView() {
        title_center_text = findViewById(R.id.tv_title_main);
        title_back_img = findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);
        title_back_img.setOnClickListener(this);
        title_center_text.setText("报备");
        activity_feedback_feedback_edt = findViewById(R.id.activity_feedback_feedback_edt);
        activity_feedback_ok_btn = findViewById(R.id.activity_feedback_ok_btn);
        activity_feedback_ok_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.activity_feedback_ok_btn:
                String content = activity_feedback_feedback_edt.getEditableText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showShortToast(context, "请输入报备内容");
                    return;
                }
                complainBean.setContent(content);
                doComplain();
                break;
            default:
                break;
        }
    }

    private void doComplain() {
        DriverOrderService.do_complain(context, complainBean, new AsyncHttpHandler() {
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
                        ToastUtil.showShortToast(context, "提交成功");
                        ComplainActivity.this.finish();
                    } else if (jsonObject.optInt("status") == 301) {
                        ToastUtil.showShortToast(context, jsonObject.getString("msg"));
                        ComplainActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
