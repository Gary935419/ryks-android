package com.inwhoop.qscx.qscxsj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.entitys.LineBean;

import java.util.List;

/**
 * Created by Lucky on 2016/11/22.
 */

public class LineAdapter extends BaseAdapter {

    private Context mContext;
    private List<LineBean> mList;

    public LineAdapter(Context context, List<LineBean> list) {
        mList = list;
        mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_authentication_line, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_line_start.setText(mList.get(position).getRoute_city_name1());
        holder.item_line_end.setText(mList.get(position).getRoute_city_name2());
        return convertView;
    }

    private class ViewHolder {

        public ViewHolder(View view) {
            item_line_start = (TextView) view.findViewById(R.id.item_line_start);
            item_line_end = (TextView) view.findViewById(R.id.item_line_end);
        }

        private TextView item_line_start;
        private TextView item_line_end;
    }
}
