package com.inwhoop.qscx.qscxsj.adapters.myorder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.base.CommonBaseAdapter;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.InterCityOrderBean;
import com.inwhoop.qscx.qscxsj.entitys.NewOrder;
import com.inwhoop.qscx.qscxsj.tools.SystemManager;
import com.inwhoop.qscx.qscxsj.utils.Utils;

import java.util.List;

/**
 * 代驾订单列表
 */
public class OrderListCanReceiveAdapter extends CommonBaseAdapter<NewOrder> {

    public OrderListCanReceiveAdapter(Context context, List<NewOrder> datas) {
        super(context, datas, true);
    }

    @Override
    protected void convert(ViewHolder holder, final NewOrder data, int position, String payload) {
        String startLocation = data.getStart_location();
        holder.setImageThumb(R.id.iv_head, data.getHead_img()).setText(R.id.tv_name, data.getName())
                .setText(R.id.tv_order_time, data.getTimes())
                .setText(R.id.tv_location, TextUtils.isEmpty(startLocation) ? "附近地点" : startLocation)
                .setText(R.id.tv_destination, data.getEnd_location());
        String orderType = data.getOrder_type();
        switch (orderType) {
            case Constants.ORDER_TYPE_ZHUAN:
                holder.setText(R.id.tv_order_type, "专车送");
                break;
            case Constants.ORDER_TYPE_SHUN:
                holder.setText(R.id.tv_order_type, "顺路送");
                break;
            case Constants.ORDER_TYPE_BUY:
                holder.setText(R.id.tv_order_type, "代买");
                break;
            case Constants.ORDER_TYPE_DRIVE:
                holder.setText(R.id.tv_order_type, "代驾");
                break;
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_order_list_can_receive;
    }
}
