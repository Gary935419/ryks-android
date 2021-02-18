package com.inwhoop.qscx.qscxsj.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.activitys.MainActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.DriverPickService;
import com.inwhoop.qscx.qscxsj.entitys.DriverPickBasicBean;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Lucky on 2016/11/29.
 */

public class StartOrderDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private RelativeLayout dialog_start_order_seat_down_rl;
    private RelativeLayout dialog_start_order_seat_up_rl;
    private TextView dialog_start_order_seat_tv;
    private TextView dialog_start_order_start_tv;
    private TextView dialog_start_order_end_tv;
    private ImageView dialog_start_order_change_iv;
    private Button dialog_start_order_cancel_btn;
    private Button dialog_start_order_ok_btn;

    private Button goOrOut;

    private DriverPickBasicBean driverPickBasicBean;
    private LatLng mLocation;
    private int surplus_seat = 1;
    private String route_city_id1;
    private String route_city_id2;

    private String changeStr;

    public StartOrderDialog(Context context) {
        super(context);

        mContext = context;
    }

    public StartOrderDialog(Context context, Button button, DriverPickBasicBean data, LatLng location) {
        super(context);

        mContext = context;
        driverPickBasicBean = data;
        mLocation = location;
        goOrOut = button;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_order);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        dialog_start_order_seat_down_rl = (RelativeLayout) findViewById(R.id.dialog_start_order_seat_down_rl);
        dialog_start_order_seat_up_rl = (RelativeLayout) findViewById(R.id.dialog_start_order_seat_up_rl);
        dialog_start_order_seat_tv = (TextView) findViewById(R.id.dialog_start_order_seat_tv);
        dialog_start_order_start_tv = (TextView) findViewById(R.id.dialog_start_order_start_tv);
        dialog_start_order_end_tv = (TextView) findViewById(R.id.dialog_start_order_end_tv);
        dialog_start_order_change_iv = (ImageView) findViewById(R.id.dialog_start_order_change_iv);
        dialog_start_order_cancel_btn = (Button) findViewById(R.id.dialog_start_order_cancel_btn);
        dialog_start_order_ok_btn = (Button) findViewById(R.id.dialog_start_order_ok_btn);
    }

    private void initData() {
        dialog_start_order_start_tv.setText(driverPickBasicBean.getRoute_city_font1());
        dialog_start_order_end_tv.setText(driverPickBasicBean.getRoute_city_font2());
        dialog_start_order_seat_tv.setText(driverPickBasicBean.getSeat() + "位");
        surplus_seat = Integer.parseInt(driverPickBasicBean.getSeat());
        route_city_id1 = driverPickBasicBean.getRoute_city_id1();
        route_city_id2 = driverPickBasicBean.getRoute_city_id2();
    }

    private void initListener() {
        dialog_start_order_cancel_btn.setOnClickListener(this);
        dialog_start_order_ok_btn.setOnClickListener(this);
        dialog_start_order_seat_down_rl.setOnClickListener(this);
        dialog_start_order_seat_up_rl.setOnClickListener(this);
        dialog_start_order_change_iv.setOnClickListener(this);
    }

    private void working() {

        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(mContext).getId();

        DriverPickService.working(mContext, user_id, driverPickBasicBean.getTaker_type_id(),
                mLocation.longitude + "", mLocation.latitude + "", driverPickBasicBean.getCar_type_id(),
                surplus_seat + "", route_city_id1, route_city_id2, new AsyncHttpHandler() {
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
                                ToastUtil.showShortToast(mContext, jsonObject.optString("msg"));
                                goOrOut.setText("我要下班");
                                MainActivity.getInstance().requestGetBasic();
                            } else {
                                ToastUtil.showShortToast(mContext, jsonObject.optString("msg"));
                            }
                            cancel();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_start_order_cancel_btn:
                cancel();
                break;
            case R.id.dialog_start_order_ok_btn:
                working();
                break;
            case R.id.dialog_start_order_seat_down_rl:
                if (surplus_seat > 1) {
                    surplus_seat--;
                    dialog_start_order_seat_tv.setText(surplus_seat + "位");
                } else {
                    ToastUtil.showShortToast(mContext, "可乘车人数不得少于1人");
                }
                break;
            case R.id.dialog_start_order_seat_up_rl:
                if (surplus_seat < Integer.parseInt(driverPickBasicBean.getSeat())) {
                    surplus_seat++;
                    dialog_start_order_seat_tv.setText(surplus_seat + "位");
                } else {
                    ToastUtil.showShortToast(mContext, "超出车辆标准载客数");
                }
                break;
            case R.id.dialog_start_order_change_iv:
                changeStr = route_city_id1;
                route_city_id1 = route_city_id2;
                route_city_id2 = changeStr;
                changeStr = dialog_start_order_start_tv.getText().toString().trim();
                dialog_start_order_start_tv.setText(dialog_start_order_end_tv.getText().toString().trim());
                dialog_start_order_end_tv.setText(changeStr);
                break;
        }
    }
}
