package com.inwhoop.qscx.qscxsj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.base.CommonBaseAdapter;
import com.inwhoop.qscx.qscxsj.entitys.Balance;

import java.util.List;

/**
 * Created by Lucky on 2016/12/5.
 */
public class BalanceDetailAdapter extends CommonBaseAdapter<Balance.BalanceDetail> {

    public BalanceDetailAdapter(Context context, List<Balance.BalanceDetail> datas) {
        super(context, datas, true);
    }

    @Override
    protected void convert(ViewHolder holder, Balance.BalanceDetail data, int position, String payload) {
        holder.setText(R.id.tv_balance_name, data.getContent());
        holder.setText(R.id.tv_balance_time, data.getAdd_time());
        holder.setVisible(R.id.tv_balance_phone, false);
        String type = data.getType();
        if (type.equals("1")) {
            holder.setText(R.id.tv_balance_price, "+ " + data.getMoney());
        } else {
            holder.setText(R.id.tv_balance_price, "- " + data.getMoney());
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_balance_detail;
    }
}
