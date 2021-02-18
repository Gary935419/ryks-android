package com.inwhoop.qscx.qscxsj.activitys.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseFragmentActivity;
import com.inwhoop.qscx.qscxsj.adapters.FragmentAdapter;
import com.inwhoop.qscx.qscxsj.fragments.order.OrderListDriveFragment;
import com.inwhoop.qscx.qscxsj.fragments.order.OrderListSongFragment;
import com.inwhoop.qscx.qscxsj.fragments.order.OrderListBuyFragment;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 */
public class MyOrderListActivity extends BaseFragmentActivity implements View.OnClickListener {

    private XTabLayout tabLayout;
    private ViewPager vp_viewPager;
    private TextView title_center_text;    //标题  行程记录
    private ImageView title_back_img;    //行程记录返回按钮

    private FragmentAdapter adapter;

    public static final String ORDER_TYPE_ZHUAN = "1";     //专车送
    public static final String ORDER_TYPE_BUY = "2";      //代买
    public static final String ORDER_TYPE_DRIVE = "3";    //代驾
    public static final String ORDER_TYPE_SHUN = "4";    //顺风送

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_my_order);
        //初始化控件
        initControl();
        initTabLayout();
        //设置监听
        setListener();
    }

    /**
     * 控件初始化
     */
    private void initControl() {
        //初始化RadioGroup
        tabLayout = findViewById(R.id.tab_layout);
        //初始化FrameLayout
        vp_viewPager = findViewById(R.id.vp_viewPager);
        //设置返回按钮
        title_back_img = findViewById(R.id.iv_title_left);
        title_back_img.setImageResource(R.mipmap.icon_return);
        title_back_img.setVisibility(View.VISIBLE);
        //设置标题
        title_center_text = findViewById(R.id.tv_title_main);
        title_center_text.setText("我的订单");
    }

    private void initTabLayout() {
        List<String> titles = new ArrayList<>();
        titles.add("专车送");
        titles.add("顺路送");
        titles.add("代买");
        titles.add("代驾");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(OrderListSongFragment.newInstance(ORDER_TYPE_ZHUAN));     //专车
        fragments.add(OrderListSongFragment.newInstance(ORDER_TYPE_SHUN));     //顺风车
        fragments.add(new OrderListBuyFragment());      //代买
        fragments.add(new OrderListDriveFragment());     //代驾订单
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        vp_viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(vp_viewPager);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        title_back_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                //返回按钮
                finish();
                break;
        }
    }

    @Subscriber(tag = "TravelRecordsCarPoolFragmentUpData")
    private void TravelRecordsCarPoolFragmentUpData(String atge) {
        Fragment fragment = adapter.getItem(vp_viewPager.getCurrentItem());
        if (fragment instanceof OrderListDriveFragment) {
            ((OrderListDriveFragment) fragment).upData();
        }
    }
}
