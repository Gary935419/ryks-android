package com.inwhoop.qscx.qscxsj.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.activitys.main.WebViewActivity;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.base.CommonBaseAdapter;
import com.inwhoop.qscx.qscxsj.entitys.AgreementBean;

import java.util.List;

public class AgreementAdapter extends CommonBaseAdapter<AgreementBean> {

    public AgreementAdapter(Context context, List<AgreementBean> datas) {
        super(context, datas, false);
    }

    @Override
    protected void convert(ViewHolder holder, AgreementBean data, int position, String payload) {
        holder.setText(R.id.tv_title, data.getTitle());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_agreement;
    }
}
