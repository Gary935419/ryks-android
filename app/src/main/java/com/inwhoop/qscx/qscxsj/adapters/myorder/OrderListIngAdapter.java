package com.inwhoop.qscx.qscxsj.adapters.myorder;

import android.content.Context;
import android.text.TextUtils;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.base.CommonBaseAdapter;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.NewOrder;

import java.util.List;

/**
 * 代驾订单列表
 */
public class OrderListIngAdapter extends CommonBaseAdapter<NewOrder> {

    public OrderListIngAdapter(Context context, List<NewOrder> datas) {
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
        holder.setImageResource(R.id.iv_select, data.isSelect() ? R.mipmap.ic_select : R.mipmap.ic_select_un);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_order_list_ing;
    }
}
