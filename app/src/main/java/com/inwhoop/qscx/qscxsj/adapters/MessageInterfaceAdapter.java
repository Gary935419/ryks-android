package com.inwhoop.qscx.qscxsj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.adapter.base.CommonBaseAdapter;
import com.inwhoop.qscx.qscxsj.entitys.MessageListsBean;

import java.util.List;


/**
 * Created by Administrator on 2016/12/5.
 */
public class MessageInterfaceAdapter extends CommonBaseAdapter<MessageListsBean> {

    public MessageInterfaceAdapter(Context context, List<MessageListsBean> datas) {
        super(context, datas, true);
    }

    @Override
    protected void convert(com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder holder, MessageListsBean data, int position, String payload) {
        holder.setVisible(R.id.iv_un_read, data.getIs_read().equals("0"));
        holder.setText(R.id.tv_msg_time, data.getAdd_time());
        holder.setText(R.id.tv_msg_name, data.getTitle());
        holder.setText(R.id.tv_msg_content, data.getContent());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_message_listview;
    }
}
