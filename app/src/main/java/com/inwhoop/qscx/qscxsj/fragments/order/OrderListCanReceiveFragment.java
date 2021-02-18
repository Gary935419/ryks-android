package com.inwhoop.qscx.qscxsj.fragments.order;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.adapters.myorder.OrderListCanReceiveAdapter;
import com.inwhoop.qscx.qscxsj.base.BaseFragment;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnItemClickListener;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnLoadMoreListener;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.business.DriverPickService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.dialogs.NewOrderComeDialog;
import com.inwhoop.qscx.qscxsj.entitys.NewOrder;
import com.inwhoop.qscx.qscxsj.utils.DialogUtils;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.utils.gson.GsonUtil;
import com.inwhoop.qscx.qscxsj.views.MySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class OrderListCanReceiveFragment extends BaseFragment {

    private static final String TAKER_TYPE_ID = "taker_type_id";

    private MySwipeRefreshLayout refresh_layout;
    private RecyclerView lv_list;

    private String takerTypeId;
    private int page = 1;
    private OrderListCanReceiveAdapter adapter;

    private NewOrderComeDialog dialog;

    /**
     * 获取实例
     */
    public static OrderListCanReceiveFragment newInstance(String taker_type_id) {
        OrderListCanReceiveFragment fragment = new OrderListCanReceiveFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAKER_TYPE_ID, taker_type_id);
        fragment.setArguments(bundle);
        if (TextUtils.isEmpty(taker_type_id)) {
            ToastUtil.showShortToast(fragment.getActivity(), "taker_type_id为空，请重试");
        }
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list_recycler_view;
    }

    @Override
    protected void initView(View view) {
        //getbundle
        takerTypeId = getArguments().getString(TAKER_TYPE_ID, "");
        refresh_layout = view.findViewById(R.id.refresh_layout);
        lv_list = view.findViewById(R.id.lv_list);
        refresh_layout.setOnRefreshListener(refreshListener);
        lv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initAdapter() {
        adapter = new OrderListCanReceiveAdapter(getActivity(), new ArrayList<NewOrder>());
        adapter.setLoadEndView(R.layout.abs_footer_end);
        adapter.setLoadingView(R.layout.abs_footer_loading);
        adapter.setEmptyView(R.layout.abs_footer_empty);
        adapter.setLoadFailedView(R.layout.abs_footer_failed);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (!isReload) page++;
                requestOrderListCanReceive(page);
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener<NewOrder>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, NewOrder data, int position) {
                requestGetNewOrderDetail(data.getOrder_small_id());
            }
        });
        lv_list.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        refresh_layout.startRefresh(refreshListener);
    }

    /**
     * 获取可接订单列表
     */
    private void requestOrderListCanReceive(final int page) {
        DriverOrderService.getOrderListCanReceive(getActivity(), page + "", takerTypeId, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "requestOrderListCanReceive: " + result);
                List<NewOrder> list = GsonUtil.jsonToList(result, NewOrder.class);
                if (page == 1) {
                    adapter.setNewData(list);
                } else {
                    adapter.setLoadMoreData(list);
                }
                if (isListLoadEnd(list)) adapter.loadEnd();
                refresh_layout.stopRefresh();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                refresh_layout.stopRefresh();
                adapter.loadFailed();
            }

            @Override
            public void onHttpError(String errorMsg) {
                refresh_layout.stopRefresh();
                adapter.loadFailed();
            }
        }));
    }

    /**
     * 获取新订单详情
     */
    private void requestGetNewOrderDetail(final String orderId) {
        showLoadingDialog("加载订单信息...");
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(getActivity()).getId();
        DriverPickService.get_popup(getActivity(), user_id, orderId, takerTypeId, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "requestGetNewOrderDetail: " + result);
                dismissLoadingDialog();
                NewOrder order = GsonUtil.toBean(result, NewOrder.class);
                if (order != null) {
                    order.setTaker_type_id(takerTypeId);
                    order.setWaiting_id(orderId);
                    dialog = new NewOrderComeDialog(getActivity(), order);
                    DialogUtils.show(getActivity(), dialog);
                }
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                ToastUtil.showShortToast(getActivity(), msg);
                dismissLoadingDialog();
            }

            @Override
            public void onHttpError(String errorMsg) {
                ToastUtil.showShortToast(getActivity(), errorMsg);
                dismissLoadingDialog();
            }
        }));
    }

    private final SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            requestOrderListCanReceive(page);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.dismiss(dialog);
        dialog = null;
    }
}
