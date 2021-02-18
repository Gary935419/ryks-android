package com.inwhoop.qscx.qscxsj.activitys.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.adapters.MyOrderAdapter;

public class MyOrderActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 返回按钮
     */
    private ImageView title_back_img;
    /**
     * 标题
     */
    private TextView title_center_text;
    /**
     * 适配器
     */
    private MyOrderAdapter adapter;
    /**
     * listview
     */
    private ListView lv_myOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        //初始化控件
        initView();
        //设置适配器
        setAdapter();
        //设置监听
        setListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //返回按钮
        title_back_img = (ImageView) findViewById(R.id.iv_title_left);
        title_back_img.setImageResource(R.mipmap.icon_return);
        title_back_img.setVisibility(View.VISIBLE);
        //标题
        title_center_text = (TextView) findViewById(R.id.tv_title_main);
        title_center_text.setText("我的订单");

        //初始化控件
        lv_myOrder = (ListView) findViewById(R.id.lv_myOrder);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        title_back_img.setOnClickListener(this);
    }

    /**
     * 设置配置器
     */
    private void setAdapter() {
        adapter = new MyOrderAdapter(this);
        lv_myOrder.setAdapter(adapter);
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
