package com.inwhoop.qscx.qscxsj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.views.CircleImageView;
import com.inwhoop.qscx.qscxsj.R;

/**
 * Created by Administrator on 2016/12/6.
 */

public class MyOrderAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    private Context context;

    public MyOrderAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_my_order, null);
            vh = new ViewHolder();
            vh.civ_portrait = (CircleImageView) convertView.findViewById(R.id.civ_portrait);
            vh.tv_carSituation = (TextView) convertView.findViewById(R.id.tv_carSituation);
            vh.tv_destination = (TextView) convertView.findViewById(R.id.tv_destination);
            vh.tv_nameNumber = (TextView) convertView.findViewById(R.id.tv_nameNumber);
            vh.tv_point_of_departure = (TextView) convertView.findViewById(R.id.tv_point_of_departure);
            vh.iv_callNumber = (ImageView) convertView.findViewById(R.id.iv_callNumber);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.iv_callNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_callNumber:
                        ToastUtil.showShortToast(context, "电话拨通中" + position);
                        break;
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {
        //头像
        private CircleImageView civ_portrait;
        //司机称呼
        private TextView tv_nameNumber;
        //车辆情况
        private TextView tv_carSituation;
        //出发地
        private TextView tv_point_of_departure;
        //目的地
        private TextView tv_destination;
        //打电话
        private ImageView iv_callNumber;
    }

}
