package com.inwhoop.qscx.qscxsj.fragments.order;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.activitys.ordertaking.OrderListIngActivity;
import com.inwhoop.qscx.qscxsj.adapters.myorder.OrderListIngAdapter;
import com.inwhoop.qscx.qscxsj.base.BaseFragment;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnItemClickListener;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnLoadMoreListener;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.business.DriverPickService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.constants.EventBusMsg;
import com.inwhoop.qscx.qscxsj.dialogs.NewOrderComeDialog;
import com.inwhoop.qscx.qscxsj.entitys.DriverPickBasicBean;
import com.inwhoop.qscx.qscxsj.entitys.NewOrder;
import com.inwhoop.qscx.qscxsj.sp.SaveInfo;
import com.inwhoop.qscx.qscxsj.utils.DialogUtils;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.utils.gson.GsonUtil;
import com.inwhoop.qscx.qscxsj.views.MySwipeRefreshLayout;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class OrderListIngFragment extends BaseFragment {

    private static final String TAKER_TYPE_ID = "taker_type_id";

    private MySwipeRefreshLayout refresh_layout;
    private RecyclerView lv_list;

    private String takerTypeId;
    private int page = 1;
    private OrderListIngAdapter adapter;

    private NewOrderComeDialog dialog;

    /**
     * 获取实例
     */
    public static OrderListIngFragment newInstance(String taker_type_id) {
        OrderListIngFragment fragment = new OrderListIngFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAKER_TYPE_ID, taker_type_id);
        fragment.setArguments(bundle);
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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initAdapter() {
        adapter = new OrderListIngAdapter(getActivity(), new ArrayList<NewOrder>());
        adapter.setLoadEndView(R.layout.abs_footer_end);
        adapter.setLoadingView(R.layout.abs_footer_loading);
        adapter.setEmptyView(R.layout.abs_footer_empty);
        adapter.setLoadFailedView(R.layout.abs_footer_failed);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (!isReload) page++;
                requestOrderListIng(page);
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener<NewOrder>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, NewOrder data, int position) {
                DriverPickBasicBean bean = getParent().getBasicBean();
                if (bean == null || !bean.getOrder_id().equals(data.getOrder_small_id())) {
                    //切换订单
                    String[] strings = new String[2];
                    strings[0] = data.getOrder_small_id();
                    strings[1] = takerTypeId;
                    EventBus.getDefault().post(strings, EventBusMsg.EVENT_CHANGE_ORDER);
                    ToastUtil.showShortToast(getActivity(), "切换成功");
                    getActivity().finish();
                } else {
                    ToastUtil.showShortToast(getActivity(), "已在当前进行订单中");
                }
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
    private void requestOrderListIng(final int page) {
        DriverOrderService.getOrderListIng(getActivity(), page + "", takerTypeId, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "requestOrderListIng: " + result);
                List<NewOrder> list = GsonUtil.jsonToList(result, NewOrder.class);
                for (NewOrder order : list) {
                    order.setSelect(false);
                    if (order.isCurrentOrder(getParent().getBasicBean())) {
                        order.setSelect(true);
                    }
                }
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

    private final SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            requestOrderListIng(page);
        }
    };

    private OrderListIngActivity getParent() {
        return (OrderListIngActivity) getActivity();
    }

    @Subscriber(tag = EventBusMsg.EVENT_PUSH)
    private void eventGetNewOrder(NewOrder bean) {
        if (refresh_layout != null && !isDetached())
            refresh_layout.startRefresh(refreshListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.dismiss(dialog);
        dialog = null;
    }
}
