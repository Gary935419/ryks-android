package com.inwhoop.qscx.qscxsj.fragments.order;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.activitys.main.MyOrderListActivity;
import com.inwhoop.qscx.qscxsj.activitys.ordertaking.OrderDetailActivity;
import com.inwhoop.qscx.qscxsj.adapters.myorder.OrderListSongAdapter;
import com.inwhoop.qscx.qscxsj.base.BaseFragment;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnItemClickListener;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnLoadMoreListener;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.TownOrderBean;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.utils.gson.GsonUtil;
import com.inwhoop.qscx.qscxsj.views.MySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 帮我订单
 */
public class OrderListSongFragment extends BaseFragment {

    private RecyclerView lvList;
    private MySwipeRefreshLayout refreshLayout;

    private String orderType;
    private OrderListSongAdapter adapter;

    private int page = 1;

    /**
     * 创建fragment对象
     *
     * @param orderType 订单类型
     * @return fragment
     */
    public static OrderListSongFragment newInstance(String orderType) {
        Bundle bundle = new Bundle();
        bundle.putString("order_type", orderType);
        OrderListSongFragment fragment = new OrderListSongFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list_recycler_view;
    }

    @Override
    protected void initView(View view) {
        //get argument
        orderType = getArguments().getString("order_type");
        //初始化ListView
        lvList = (RecyclerView) view.findViewById(R.id.lv_list);
        refreshLayout = (MySwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
    }

    @Override
    protected void initAdapter() {
        lvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new OrderListSongAdapter(getActivity(), new ArrayList<TownOrderBean>());
        adapter.setEmptyView(R.layout.abs_footer_empty);
        adapter.setLoadEndView(R.layout.abs_footer_end);
        adapter.setLoadingView(R.layout.abs_footer_loading);
        adapter.setLoadFailedView(R.layout.abs_footer_failed);
        adapter.setOnItemClickListener(new OnItemClickListener<TownOrderBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, TownOrderBean data, int position) {
                OrderDetailActivity.startIntent(getActivity(), data, Constants.TAKER_TYPE_ID_SONG);
            }
        });
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (!isReload) page++;
                requestOrderList(page);
            }
        });
        lvList.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(listener);
    }

    @Override
    protected void initData() {
        refreshLayout.startRefresh(listener);
    }

    /**
     * 获取列表数据
     */
    private void requestOrderList(final int page) {
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(getActivity()).getId();
        DriverOrderService.lists(getActivity(), user_id, orderType, page + "", new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "requestOrderList: " + result);
                List<TownOrderBean> list = GsonUtil.jsonToList(result, TownOrderBean.class);
                if (page == 1) {
                    adapter.setNewData(list);
                } else {
                    adapter.setLoadMoreData(list);
                }
                if (isListLoadEnd(list)) adapter.loadEnd();
                refreshLayout.stopRefresh();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                refreshLayout.stopRefresh();
                ToastUtil.showShortToast(getActivity(), msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                refreshLayout.stopRefresh();
                ToastUtil.showShortToast(getActivity(), errorMsg);
            }
        }));
    }

    /**
     * 刷新监听
     */
    private final SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            requestOrderList(page);
        }
    };
}
