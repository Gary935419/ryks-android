package com.inwhoop.qscx.qscxsj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.entitys.CarTypeBean;
import com.inwhoop.qscx.qscxsj.utils.PicUtil;

import java.util.List;

/**
 * Created by Lucky on 2016/11/22.
 */

public class CarTypeAdapter extends BaseAdapter {

    private Context mContext;
    private List<CarTypeBean> mList;

    public CarTypeAdapter(Context context, List<CarTypeBean> list) {
        mContext = context;
        mList = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_authentication_cartype, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PicUtil.displayImage(mList.get(position).getHead_img(), holder.item_cartype_img);
        holder.item_cartype_text.setText(mList.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        public ViewHolder(View convertView) {
            item_cartype_img = (ImageView) convertView.findViewById(R.id.item_cartype_img);
            item_cartype_text = (TextView) convertView.findViewById(R.id.item_cartype_text);
        }

        private ImageView item_cartype_img;
        private TextView item_cartype_text;
    }
}
