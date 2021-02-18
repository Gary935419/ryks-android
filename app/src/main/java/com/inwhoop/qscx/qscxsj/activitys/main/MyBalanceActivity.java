package com.inwhoop.qscx.qscxsj.activitys.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.adapters.BalanceDetailAdapter;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnLoadMoreListener;
import com.inwhoop.qscx.qscxsj.business.UserBalanceService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.entitys.Balance;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.utils.gson.GsonUtil;
import com.inwhoop.qscx.qscxsj.views.MySwipeRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyBalanceActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title_main;
    private ImageView iv_title_left;
    private TextView activity_my_balance_tv;

    private Button btn_withdraw;
    private MySwipeRefreshLayout refreshLayout;

    private RecyclerView lvList;
    private BalanceDetailAdapter adapter;

    private Balance myBalanceBean;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_balance);
        initView();
        initAdapter();
        initListener();
        initData();
    }

    private void initView() {
        tv_title_main = (TextView) findViewById(R.id.tv_title_main);
        tv_title_main.setText("钱包");
        iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
        iv_title_left.setVisibility(View.VISIBLE);
        activity_my_balance_tv = (TextView) findViewById(R.id.activity_my_balance_tv);
        btn_withdraw = (Button) findViewById(R.id.activity_my_balance_withdrawals_btn);
        refreshLayout = (MySwipeRefreshLayout) findViewById(R.id.refresh_layout);
        lvList = (RecyclerView) findViewById(R.id.lv_list);
    }

    private void initAdapter() {
        lvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BalanceDetailAdapter(this, new ArrayList<Balance.BalanceDetail>());
        adapter.setEmptyView(R.layout.abs_footer_empty);
        adapter.setLoadEndView(R.layout.abs_footer_end);
        adapter.setLoadingView(R.layout.abs_footer_loading);
        adapter.setLoadFailedView(R.layout.abs_footer_failed);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (!isReload) page++;
                requestBalanceDetailList(page);
            }
        });
        lvList.setAdapter(adapter);
    }

    private void initListener() {
        iv_title_left.setOnClickListener(this);
        btn_withdraw.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(refreshListener);
    }

    private void initData() {
        refreshLayout.startRefresh(refreshListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.activity_my_balance_withdrawals_btn:
                startActivity(new Intent(this, WithdrawDepositActivity.class));
                break;
        }
    }

    private void requestBalanceDetailList(final int page) {
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();
        UserBalanceService.detailed(this, user_id, page + "", new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "requestBalanceDetailList: " + result);
                myBalanceBean = GsonUtil.toBean(result, Balance.class);
                if (myBalanceBean != null) {
                    activity_my_balance_tv.setText(myBalanceBean.getBalance());
                    if (page == 1) {
                        adapter.setNewData(myBalanceBean.getLists());
                    } else {
                        adapter.setLoadMoreData(myBalanceBean.getLists());
                    }
                    if (myBalanceBean.getLists() == null || myBalanceBean.getLists().size() < 10) adapter.loadEnd();
                }
                refreshLayout.stopRefresh();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                refreshLayout.stopRefresh();
                ToastUtil.showShortToast(context, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                refreshLayout.stopRefresh();
                ToastUtil.showShortToast(context, errorMsg);
            }
        }));
    }

    private final SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            requestBalanceDetailList(page);
        }
    };
}
