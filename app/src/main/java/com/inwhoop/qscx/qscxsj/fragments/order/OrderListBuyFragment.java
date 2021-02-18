package com.inwhoop.qscx.qscxsj.fragments.order;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.activitys.main.MyOrderListActivity;
import com.inwhoop.qscx.qscxsj.activitys.ordertaking.OrderDetailActivity;
import com.inwhoop.qscx.qscxsj.adapters.myorder.OrderListBuyAdapter;
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
 * Created by Administrator on 2016/11/30.
 */
public class OrderListBuyFragment extends BaseFragment {

    private MySwipeRefreshLayout mRefreshLayout;
    private RecyclerView mLvList;

    private OrderListBuyAdapter adapter;

    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list_recycler_view;
    }

    @Override
    protected void initView(View view) {
        //初始化ListView
        mLvList = (RecyclerView) view.findViewById(R.id.lv_list);
        mRefreshLayout = (MySwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(onRefreshListener);
        mLvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new OrderListBuyAdapter(getActivity(), new ArrayList<TownOrderBean>());
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
        mLvList.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mRefreshLayout.startRefresh(onRefreshListener);
    }

    /**
     * 订单列表
     *
     * @param page page
     */
    private void requestOrderList(final int page) {
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(getActivity()).getId();
        DriverOrderService.lists(getActivity(), user_id, MyOrderListActivity.ORDER_TYPE_BUY, page + "", new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "lists: " + result);
                List<TownOrderBean> list = GsonUtil.jsonToList(result, TownOrderBean.class);
                if (page == 1) {
                    adapter.setNewData(list);
                } else {
                    adapter.setLoadMoreData(list);
                }
                if (isListLoadEnd(list)) adapter.loadEnd();
                mRefreshLayout.stopRefresh();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                mRefreshLayout.stopRefresh();
                ToastUtil.showShortToast(getActivity(), msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                mRefreshLayout.stopRefresh();
                ToastUtil.showShortToast(getActivity(), errorMsg);
            }
        }));
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            requestOrderList(page);
        }
    };

}
