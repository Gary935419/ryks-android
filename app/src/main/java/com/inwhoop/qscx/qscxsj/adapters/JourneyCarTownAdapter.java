package com.inwhoop.qscx.qscxsj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.activitys.ordertaking.OrderDetailActivity;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.TownOrderBean;
import com.inwhoop.qscx.qscxsj.tools.SystemManager;
import com.inwhoop.qscx.qscxsj.utils.PicUtil;
import com.inwhoop.qscx.qscxsj.views.CircleImageView;

import java.util.List;

/**
 * Created by Lucky on 2016/12/14.
 */

public class JourneyCarTownAdapter extends BaseAdapter {
    private Context mContext;
    private List<TownOrderBean> mList;
    private LayoutInflater mInflater;
    private int taker_type_id;

    public JourneyCarTownAdapter(Context context, List<TownOrderBean> list, int type) {
        this.mContext = context;
        mList = list;
        taker_type_id = type;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_travel_records_car_pool_list, null);
            vh = new ViewHolder();
            vh.layoutItem = convertView.findViewById(R.id.layoutItem);
            vh.civ_orderPicture = convertView.findViewById(R.id.civ_orderPicture);
            vh.iv_orderCondition = convertView.findViewById(R.id.iv_orderCondition);
            vh.tv_carCondition = convertView.findViewById(R.id.tv_order_time);
            vh.tv_cost = convertView.findViewById(R.id.tv_cost);
            vh.tv_destination = convertView.findViewById(R.id.tv_destination);
            vh.tv_driverName = convertView.findViewById(R.id.tv_driverName);
            vh.tv_location = convertView.findViewById(R.id.tv_location);
            vh.tv_orderTime = convertView.findViewById(R.id.tv_orderTime);
            vh.im_userTelephone = convertView.findViewById(R.id.im_userTelephone);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //司机头像
        PicUtil.displayImage(mList.get(position).getHead_img(), vh.civ_orderPicture);
        //订单状态
        if (taker_type_id == 1) {
//            if (mList.get(position).getStatus().equals("5")) {
//                vh.iv_orderCondition.setImageResource(R.mipmap.icon_finish);
//            } else
            if (mList.get(position).getStatus().equals("6")) {
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_finish);
            } else if (mList.get(position).getStatus().equals("7")) {
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_cancel);
            } else if (mList.get(position).getStatus().equals("8")) {
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_revoke);
            } else {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_nofinish);
            }
        } else if (taker_type_id == 2) {
            if (mList.get(position).getStatus().equals("5")) {
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_finish);
            } else if (mList.get(position).getStatus().equals("6")) {
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_finish);
            } else if (mList.get(position).getStatus().equals("7")) {
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_cancel);
            }
        } else if (taker_type_id == 3) {
            if (mList.get(position).getStatus().equals("2")) {
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_finish);
            } else if (mList.get(position).getStatus().equals("3")) {
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_revoke);
            } else {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_nofinish);
            }
        }
        //车辆状况
        vh.tv_carCondition.setText(mList.get(position).getTimes());
        //价格
        vh.tv_cost.setText("￥" + mList.get(position).getPrice());
        //目的地
        vh.tv_destination.setText(mList.get(position).getEnd_location());
        //司机名字
        vh.tv_driverName.setText(mList.get(position).getName());
        //当前位置
        vh.tv_location.setText(mList.get(position).getStart_location());
        //下单时间
        vh.tv_orderTime.setText(mList.get(position).getTimes());

        vh.im_userTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemManager.callPhone(mContext, mList.get(position).getAccount());
            }
        });

        vh.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetailActivity.startIntent(mContext, mList.get(position), Constants.TAKER_TYPE_ID_DRIVE);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        View layoutItem;
        //驾驶员
        TextView tv_driverName;
        //车况
        TextView tv_carCondition;
        //目的地
        TextView tv_destination;
        //价格
        TextView tv_cost;
        //当前位置
        TextView tv_location;
        //下单时间
        TextView tv_orderTime;
        //订单状况
        ImageView iv_orderCondition;
        //司机头像
        CircleImageView civ_orderPicture;

        ImageView im_userTelephone;
    }
}
