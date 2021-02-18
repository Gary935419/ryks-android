package com.inwhoop.qscx.qscxsj.views.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.OrderDetailBean;
import com.inwhoop.qscx.qscxsj.entitys.TownOrderBean;
import com.inwhoop.qscx.qscxsj.utils.DateUtils;
import com.inwhoop.qscx.qscxsj.utils.PicUtil;
import com.inwhoop.qscx.qscxsj.utils.Utils;
import com.inwhoop.qscx.qscxsj.views.CircleImageView;

public class OrderDetailDialog extends Dialog {

    private final Context mContext;
    private final OrderDetailBean detailBean;
    private final TownOrderBean orderBean;

    private CircleImageView ivHead;
    private TextView tvName;
    private TextView tvOrderTime;
    private ImageView ivTelephone;
    private TextView tvStart;
    private TextView tvEnd;

    private TextView tvPriceStartUp;
    private TextView tvKm;
    private TextView tvPriceKeepPrice;
    private TextView tvPriceLittle;
    private TextView tvReceiveOrderTime;
    private TextView tvGetGoodsTime;
    private TextView tvFinishTime;
    private TextView tvGetGoodsTimeTitle;

    private Button btnSure;

    public OrderDetailDialog(@NonNull Context context, OrderDetailBean orderDetailBean, TownOrderBean townOrderBean) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.detailBean = orderDetailBean;
        this.orderBean = townOrderBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheet_order_detail);
        if (getWindow() != null) {
            getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            getWindow().setGravity(Gravity.BOTTOM);
            getWindow().setWindowAnimations(R.style.dialogBottomAnimation);
        }
        initView();
        initListener();
        bindData();
    }

    private void initView() {
        ivHead = (CircleImageView) findViewById(R.id.iv_myPortrait);
        tvName = (TextView) findViewById(R.id.tv_driverName);
        tvOrderTime = (TextView) findViewById(R.id.tv_orderTime);
        ivTelephone = (ImageView) findViewById(R.id.im_userTelephone);
        ivTelephone.setVisibility(View.GONE);
        tvStart = (TextView) findViewById(R.id.tv_start);
        tvEnd = (TextView) findViewById(R.id.tv_end);
        tvPriceStartUp = (TextView) findViewById(R.id.tv_price_start_up);
        tvKm = (TextView) findViewById(R.id.tv_km);
        tvPriceLittle = (TextView) findViewById(R.id.tv_price_little);
        tvPriceKeepPrice = (TextView) findViewById(R.id.tv_price_keep_price);
        tvReceiveOrderTime = (TextView) findViewById(R.id.tv_receive_order_time);
        tvGetGoodsTime = (TextView) findViewById(R.id.tv_get_goods_time);
        tvFinishTime = (TextView) findViewById(R.id.tv_finish_time);
        tvGetGoodsTimeTitle = (TextView) findViewById(R.id.tv_get_goods_time_title);
        btnSure = findViewById(R.id.btn_sure);
    }

    private void initListener() {
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void bindData() {
        if (orderBean != null) {
            PicUtil.displayImage(orderBean.getHead_img(), ivHead);
            tvName.setText(orderBean.getName());
            tvOrderTime.setText(orderBean.getTimes());
        }
        if (detailBean != null) {
            tvStart.setText(detailBean.getStart_location());
            tvEnd.setText(detailBean.getEnd_location());
            tvPriceStartUp.setText(Utils.getPriceWithUnit(detailBean.getOrder_driver_price()));
            tvKm.setText(detailBean.getDistribution_km() + "km");
            tvPriceKeepPrice.setText(Utils.getPriceWithUnit(detailBean.getProtect_price()));
            tvPriceLittle.setText(Utils.getPriceWithUnit(detailBean.getTip_price()));
            tvReceiveOrderTime.setText(DateUtils.stamp2time5(detailBean.getGetorder_time()));
            tvFinishTime.setText(DateUtils.stamp2time5(detailBean.getComplete_time()));
            String orderType = detailBean.getOrder_type();
            if (orderType.equals(Constants.ORDER_TYPE_DRIVE)) {
                //代驾订单
                tvGetGoodsTimeTitle.setText("上车时间");
                tvGetGoodsTime.setText(DateUtils.stamp2time5(detailBean.getTakeup_time()));
            } else {
                //送货订单
                tvGetGoodsTimeTitle.setText("取件时间");
                tvGetGoodsTime.setText(DateUtils.stamp2time5(detailBean.getTakegoods_time()));
            }
        }
    }
}
