package com.inwhoop.qscx.qscxsj.adapters.myorder;

import android.content.Context;
import android.view.View;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.base.CommonBaseAdapter;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.InterCityOrderBean;
import com.inwhoop.qscx.qscxsj.tools.SystemManager;
import com.inwhoop.qscx.qscxsj.utils.Utils;

import java.util.List;

/**
 * 代驾订单列表
 */
public class OrderListDriveAdapter extends CommonBaseAdapter<InterCityOrderBean> {

    public OrderListDriveAdapter(Context context, List<InterCityOrderBean> datas) {
        super(context, datas, true);
    }

    @Override
    protected void convert(ViewHolder holder, final InterCityOrderBean data, int position, String payload) {
        holder.setImageThumb(R.id.iv_head, data.getHead_img()).setText(R.id.tv_name, data.getName())
                .setText(R.id.tv_order_time, data.getTimes())
                .setText(R.id.tv_price, Utils.getPriceWithUnit(data.getOrder_driver_price()))
                .setText(R.id.tv_tip_price, Utils.getPriceWithUnit(data.getTip_price()))
                .setText(R.id.tv_location, data.getStart_location()).setText(R.id.tv_destination, data.getEnd_location());
        //订单状态
        String orderStatus = data.getStatus();
        switch (orderStatus) {
            case Constants.STATUS_DRIVE_FINISH:
                holder.setImageResource(R.id.iv_order_status, R.mipmap.icon_finish);
                break;
            case Constants.STATUS_DRIVE_CANCEL:
                holder.setImageResource(R.id.iv_order_status, R.mipmap.icon_cancel);
                break;
            case Constants.STATUS_DRIVE_PASSENGER_CANCEL:
                holder.setImageResource(R.id.iv_order_status, R.mipmap.icon_revoke);
                break;
            default:
                holder.setImageResource(R.id.iv_order_status, R.mipmap.icon_nofinish);
                break;
        }
        holder.setOnClickListener(R.id.iv_telephone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemManager.callPhone(mContext, data.getAccount());
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_order_list_buy;
    }
}
