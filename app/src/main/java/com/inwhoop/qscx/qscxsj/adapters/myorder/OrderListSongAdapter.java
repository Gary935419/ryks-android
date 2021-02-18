package com.inwhoop.qscx.qscxsj.adapters.myorder;

import android.content.Context;
import android.view.View;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.base.CommonBaseAdapter;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.TownOrderBean;
import com.inwhoop.qscx.qscxsj.tools.SystemManager;
import com.inwhoop.qscx.qscxsj.utils.Utils;

import java.util.List;

/**
 * 代买订单列表
 */
public class OrderListSongAdapter extends CommonBaseAdapter<TownOrderBean> {

    public OrderListSongAdapter(Context context, List<TownOrderBean> datas) {
        super(context, datas, true);
    }

    @Override
    protected void convert(ViewHolder holder, final TownOrderBean data, int position, String payload) {
        holder.setImageThumb(R.id.iv_head, data.getHead_img()).setText(R.id.tv_name, data.getName())
                .setText(R.id.tv_order_time, data.getTimes())
                .setText(R.id.tv_price, Utils.getPriceWithUnit(data.getOrder_driver_price()))
                .setText(R.id.tv_tip_price, Utils.getPriceWithUnit(data.getTip_price()))
                .setText(R.id.tv_location, data.getStart_location()).setText(R.id.tv_destination, data.getEnd_location());
        //订单状态
        String orderStatus = data.getStatus();
        switch (orderStatus) {
            case Constants.STATUS_SONG_FINISH:
                holder.setImageResource(R.id.iv_order_status, R.mipmap.icon_finish);
                break;
            case Constants.STATUS_SONG_CANCEL:
                holder.setImageResource(R.id.iv_order_status, R.mipmap.icon_cancel);
                break;
            case Constants.STATUS_SONG_CANCEL_BY_PASSANGER:
                holder.setImageResource(R.id.iv_order_status, R.mipmap.icon_revoke);
                break;
            default:
                holder.setVisible(R.id.iv_order_status, true);
                holder.setImageResource(R.id.iv_order_status, R.mipmap.icon_nofinish);
                break;
        }
        //打电话
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
