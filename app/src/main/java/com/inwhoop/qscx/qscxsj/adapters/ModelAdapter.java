package com.inwhoop.qscx.qscxsj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.entitys.ModelBean;

import java.util.List;

/**
 * Created by Lucky on 2016/11/23.
 */

public class ModelAdapter extends BaseAdapter {

    private Context mContext;
    private List<ModelBean> mList;

    public ModelAdapter(Context context, List<ModelBean> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_authentication_model, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_model_text.setText(mList.get(position).getName());
        return convertView;
    }

    private class ViewHolder {

        public ViewHolder(View view) {
            item_model_text = (TextView) view.findViewById(R.id.item_model_text);
        }

        private TextView item_model_text;
    }

}
