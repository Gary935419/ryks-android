package com.inwhoop.qscx.qscxsj.activitys.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.activitys.MainActivity;
import com.inwhoop.qscx.qscxsj.adapters.ReceivedOrderAdapter;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.DriverPickService;
import com.inwhoop.qscx.qscxsj.entitys.DriverPickOrderBean;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.views.InnerListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.Subscriber;

import cz.msebera.android.httpclient.Header;

public class ReceivedOrderActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_center_text;
    private ImageView title_back_img;

    private InnerListView activity_received_order_inlv;
    private Button activity_received_order_ok_btn;

    private ReceivedOrderAdapter receivedOrderAdapter;
    private DriverPickOrderBean driverPickOrderBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_order);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        driverPickOrderBean = (DriverPickOrderBean) getIntent().getSerializableExtra("order");
        title_center_text = (TextView) findViewById(R.id.tv_title_main);
        title_center_text.setText("订单详情");
        title_back_img = (ImageView) findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);

        activity_received_order_inlv = (InnerListView) findViewById(R.id.activity_received_order_inlv);
        activity_received_order_ok_btn = (Button) findViewById(R.id.activity_received_order_ok_btn);
        receivedOrderAdapter = new ReceivedOrderAdapter(this, driverPickOrderBean.getLists());
        activity_received_order_inlv.setAdapter(receivedOrderAdapter);
    }

    private void initData() {
        if (driverPickOrderBean.getStatus().equals("1")) {
            activity_received_order_ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int isok = 0;
                    for (int i = 0; i < driverPickOrderBean.getLists().size(); i++) {
                        if (!driverPickOrderBean.getLists().get(i).getStatus().equals("3")) {
                            isok++;
                        }
                    }
                    if (isok == 0) {
                        startTrip();
                    } else {
                        ToastUtil.showShortToast(ReceivedOrderActivity.this, "所有乘客全部乘车后才能开始行程");
                    }
                }
            });
        } else if (driverPickOrderBean.getStatus().equals("2")) {
            activity_received_order_ok_btn.setText("完成订单");
            activity_received_order_ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int isok = 0;
                    for (int i = 0; i < driverPickOrderBean.getLists().size(); i++) {
                        if (!driverPickOrderBean.getLists().get(i).getStatus().equals("6")) {
                            isok++;
                        }
                    }
                    if (isok == 0) {
                        orderOk();
                    } else {
                        ToastUtil.showShortToast(ReceivedOrderActivity.this, "所有乘客全部完成后才能完成订单");
                    }
                }
            });
        }
    }

    private void initListener() {
        title_back_img.setOnClickListener(this);
    }

    @Subscriber(tag = "ReceivedOrderActivityInitList")
    private void ReceivedOrderActivityInitList(DriverPickOrderBean driverPickOrderBean) {
        if (driverPickOrderBean == null || driverPickOrderBean.getLists() == null || driverPickOrderBean.getLists().size() == 0) {
            finish();
        } else {
            this.driverPickOrderBean = driverPickOrderBean;
            receivedOrderAdapter = new ReceivedOrderAdapter(this, driverPickOrderBean.getLists());
            activity_received_order_inlv.setAdapter(receivedOrderAdapter);
            initData();
        }
    }

    @Subscriber(tag = "ReceivedOrderActivityFinish")
    private void ReceivedOrderActivityFinish(String tag) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
//                MainActivity.isReceivedOrder = false;
                finish();
                break;
        }
    }

    private void startTrip() {

        String taker_type_id = LoginUserInfoUtil.getDriverPickBasicBean(this).getTaker_type_id();
        String order_id = LoginUserInfoUtil.getDriverPickBasicBean(this).getOrder_id();

        DriverPickService.start_trip(this, taker_type_id, order_id, new AsyncHttpHandler() {
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
                        activity_received_order_ok_btn.setText("完成订单");
                        initData();
                    }
                    ToastUtil.showShortToast(ReceivedOrderActivity.this, jsonObject.optString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void orderOk() {

        String taker_type_id = LoginUserInfoUtil.getDriverPickBasicBean(this).getTaker_type_id();
        String order_id = LoginUserInfoUtil.getDriverPickBasicBean(this).getOrder_id();

        DriverPickService.order_ok(this, taker_type_id, order_id, new AsyncHttpHandler() {
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
                        ToastUtil.showShortToast(ReceivedOrderActivity.this, jsonObject.optString("msg"));
//                        MainActivity.isReceivedOrder = false;
                        ReceivedOrderActivity.this.finish();
                    } else {
                        ToastUtil.showShortToast(ReceivedOrderActivity.this, jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
