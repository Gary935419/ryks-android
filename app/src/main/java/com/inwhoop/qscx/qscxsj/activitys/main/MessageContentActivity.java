package com.inwhoop.qscx.qscxsj.activitys.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.MessageService;
import com.inwhoop.qscx.qscxsj.constants.EventBusMsg;
import com.inwhoop.qscx.qscxsj.entitys.MessageDetailsBean;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import cz.msebera.android.httpclient.Header;


public class MessageContentActivity extends BaseActivity implements View.OnClickListener {
    //返回按钮
    private ImageView title_back_img;
    //标题栏
    private TextView title_center_text;
    //消息内容
    private TextView tv_content;

    private String id;

    private MessageDetailsBean messageDetailsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_content);
        //初始化
        initView();
        //设置监听
        initListener();
        details();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        id = getIntent().getStringExtra("id");
        //标题  消息
        title_center_text = (TextView) findViewById(R.id.tv_title_main);
        title_center_text.setText("系统消息");
        //返回按钮
        title_back_img = (ImageView) findViewById(R.id.iv_title_left);
        title_back_img.setImageResource(R.mipmap.icon_return);
        title_back_img.setVisibility(View.VISIBLE);
        //消息内容
        tv_content = (TextView) findViewById(R.id.tv_content);
    }

    private void initData() {
        tv_content.setText(messageDetailsBean.getContent());
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        title_back_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
        }
    }

    private void details() {
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();
        MessageService.details(context, id, user_id, new AsyncHttpHandler() {
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
                        messageDetailsBean = new Gson().fromJson(jsonObject.optString("result"),
                                new TypeToken<MessageDetailsBean>() {
                                }.getType());
                        EventBus.getDefault().post("tag", EventBusMsg.EVENT_UNREAD_MSG);
                        EventBus.getDefault().post("tag", EventBusMsg.EVENT_MSG_READ);
                        initData();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
