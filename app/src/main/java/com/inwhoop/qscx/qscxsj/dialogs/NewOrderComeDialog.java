package com.inwhoop.qscx.qscxsj.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.activitys.MainActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.DriverPickService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.constants.EventBusMsg;
import com.inwhoop.qscx.qscxsj.entitys.NewOrder;
import com.inwhoop.qscx.qscxsj.utils.DateUtils;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.PicUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.utils.Utils;
import com.inwhoop.qscx.qscxsj.utils.gson.GsonUtil;
import com.inwhoop.qscx.qscxsj.views.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import cz.msebera.android.httpclient.Header;

/**
 * 新订单提醒提示框
 */
public class NewOrderComeDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = "NewOrderComeDialog";

    private final Context mContext;
    private final NewOrder mBean;

    private LinearLayout layout_goods_information;
    private LinearLayout layout_start_location;
    private LinearLayout layout_distance_start;
    private LinearLayout layout_distance_order;
    private CircleImageView dialog_chengji_head_img_iv;
    private TextView dialog_chengji_name_tv;
    private TextView dialog_chengji_num_tv;
    private TextView dialog_chengji_address_tv;
    private TextView dialog_chengji_end_address_tv;
    private TextView tvCountDownTime;
    private TextView tvTitle;
    private TextView tv_distance_start;
    private TextView tv_distance_order;
    private TextView tv_time_start;
    private TextView tv_goods_information;
    private TextView tv_remark;
    private TextView tv_tip_price;
    private TextView tv_total_price;

    private Button btnReceive;
    private Button btnRefuse;

    private TimeCount time;

    public NewOrderComeDialog(Context context, NewOrder data) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.mBean = data;
        time = new TimeCount(30000, 1000);
        L.i(TAG, GsonUtil.toJson(mBean));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_order_come);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        dialog_chengji_head_img_iv = (CircleImageView) findViewById(R.id.dialog_chengji_head_img_iv);
        dialog_chengji_name_tv = (TextView) findViewById(R.id.dialog_chengji_name_tv);
        dialog_chengji_num_tv = (TextView) findViewById(R.id.dialog_chengji_num_tv);
        dialog_chengji_address_tv = (TextView) findViewById(R.id.dialog_chengji_address_tv);
        dialog_chengji_end_address_tv = (TextView) findViewById(R.id.dialog_chengji_end_address_tv);
        btnReceive = (Button) findViewById(R.id.btn_receive);
        btnRefuse = (Button) findViewById(R.id.btn_refuse);
        tvCountDownTime = (TextView) findViewById(R.id.dialog_chengji_js_rl);
        tvTitle = (TextView) findViewById(R.id.title_name_tv);
        tv_distance_start = (TextView) findViewById(R.id.tv_distance_start);
        tv_distance_order = (TextView) findViewById(R.id.tv_distance_order);
        tv_time_start = (TextView) findViewById(R.id.tv_time_start);
        layout_goods_information = findViewById(R.id.layout_goods_information);
        tv_goods_information = (TextView) findViewById(R.id.tv_goods_information);
        tv_remark = (TextView) findViewById(R.id.tv_remark);
        tv_tip_price = (TextView) findViewById(R.id.tv_tip_price);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        layout_start_location = findViewById(R.id.layout_start_location);
        layout_distance_start = findViewById(R.id.layout_distance_start);
        layout_distance_order = findViewById(R.id.layout_distance_order);
        time.start();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        dialog_chengji_num_tv.setVisibility(View.GONE);
        PicUtil.displayImage(mBean.getHead_img(), dialog_chengji_head_img_iv);
        dialog_chengji_name_tv.setText(mBean.getName());
        dialog_chengji_address_tv.setText(mBean.getStart_location());
        dialog_chengji_end_address_tv.setText(mBean.getEnd_location());
        tv_distance_start.setText(Utils.m2km(mBean.getPickup_distance()) + "km");
        tv_distance_order.setText(Utils.m2km(mBean.getDelivery_distance()) + "km");
        tv_time_start.setText(DateUtils.stamp2time5(mBean.getAppointment_time()));
        tv_goods_information.setText(mBean.getItem_information());
        layout_goods_information.setVisibility(TextUtils.isEmpty(mBean.getItem_information()) ? View.GONE : View.VISIBLE);
        tv_remark.setText(mBean.getOrder_remark());
        tv_tip_price.setText(Utils.getPriceWithUnit(mBean.getOrder_tip_price()));
        tv_total_price.setText(Utils.getPriceWithUnit(mBean.getOrder_price()));
        String orderType = mBean.getOrder_type();
        switch (orderType) {
            case Constants.ORDER_TYPE_ZHUAN:
                tvTitle.setText("专车快送");
                break;
            case Constants.ORDER_TYPE_SHUN:
                tvTitle.setText("顺风快送");
                break;
            case Constants.ORDER_TYPE_BUY:
                String startLocation = mBean.getStart_location();
                if (TextUtils.isEmpty(startLocation) || startLocation.equals("暂无出发地") || startLocation.contains("附近")) {
                    tvTitle.setText("附近代买");
                    layout_start_location.setVisibility(View.GONE);
                    layout_distance_start.setVisibility(View.GONE);
                    layout_distance_order.setVisibility(View.GONE);
                } else {
                    tvTitle.setText("代买订单");
                }
                break;
            case Constants.ORDER_TYPE_DRIVE:
                tvTitle.setText("代驾订单");
                break;
        }
    }

    private void initListener() {
        btnRefuse.setOnClickListener(this);
        btnReceive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //拒绝
            case R.id.btn_refuse:
                handlePopup(Constants.RECEIVE_ORDER_REFUSE);
                cancel();
                break;
            //接单
            case R.id.btn_receive:
                handlePopup(Constants.RECEIVE_ORDER_SURE);
                break;
        }
    }

    /**
     * 接单/拒绝接单
     */
    private void handlePopup(final String handle) {
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(mContext).getId();
        DriverPickService.handle_popup(mContext, user_id, mBean.getWaiting_id(), handle, mBean.getTaker_type_id(), new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                if (handle.equals(Constants.RECEIVE_ORDER_SURE)) {
                    //接单
                    ToastUtil.showShortToast(mContext, "接单成功");
                    MainActivity.order_id = result;
                    String[] strings = new String[2];
                    strings[0] = result;
                    strings[1] = mBean.getTaker_type_id();
                    EventBus.getDefault().post(strings, EventBusMsg.EVENT_RECEIVE_ORDER);
                }
                time.cancel();
                cancel();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                ToastUtil.showShortToast(mContext, msg);
                time.cancel();
                cancel();
            }

            @Override
            public void onHttpError(String errorMsg) {
            }
        }));
    }

    /**
     * 接单倒计时
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            handlePopup("2");
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            tvCountDownTime.setText(millisUntilFinished / 1000 + "s");
        }
    }
}
