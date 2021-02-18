package com.inwhoop.qscx.qscxsj.activitys.ordertaking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.adapters.FragmentAdapter;
import com.inwhoop.qscx.qscxsj.base.BaseFragmentActivity;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.DriverPickBasicBean;
import com.inwhoop.qscx.qscxsj.fragments.order.OrderListIngFragment;
import com.inwhoop.qscx.qscxsj.sp.SaveInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 可接订单列表
 */
public class OrderListIngActivity extends BaseFragmentActivity {

    private DriverPickBasicBean mBasicBean;

    private ImageView ivTitleLeft;
    private TextView tvTitleMain;
    private XTabLayout tab_layout;
    private ViewPager view_pager;

    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_can_receive);
        getOrderIngData();
        initView();
        initTabLayout();
    }

    private void getOrderIngData() {
        mBasicBean = SaveInfo.getInstance(this).getBasicInfo();
    }

    private void initView() {
        ivTitleLeft = (ImageView) findViewById(R.id.iv_title_left);
        tvTitleMain = (TextView) findViewById(R.id.tv_title_main);
        tab_layout = (XTabLayout) findViewById(R.id.tab_layout);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        tvTitleMain.setText("进行中订单列表");
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initTabLayout() {
        List<String> titles = new ArrayList<>();
        titles.add("跑腿");
        titles.add("代驾");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(OrderListIngFragment.newInstance(Constants.TAKER_TYPE_ID_SONG));
        fragments.add(OrderListIngFragment.newInstance(Constants.TAKER_TYPE_ID_DRIVE));
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        view_pager.setAdapter(adapter);
        tab_layout.setupWithViewPager(view_pager);
    }

    public DriverPickBasicBean getBasicBean() {
        return mBasicBean;
    }
}
