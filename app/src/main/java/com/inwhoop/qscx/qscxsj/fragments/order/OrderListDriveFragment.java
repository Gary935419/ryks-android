package com.inwhoop.qscx.qscxsj.fragments.order;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.activitys.main.MyOrderListActivity;
import com.inwhoop.qscx.qscxsj.activitys.ordertaking.OrderDetailActivity;
import com.inwhoop.qscx.qscxsj.adapters.JourneyCarPoolAdapter;
import com.inwhoop.qscx.qscxsj.adapters.myorder.OrderListDriveAdapter;
import com.inwhoop.qscx.qscxsj.base.BaseFragment;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnItemClickListener;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnLoadMoreListener;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.InterCityOrderBean;
import com.inwhoop.qscx.qscxsj.entitys.TownOrderBean;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.utils.gson.GsonUtil;
import com.inwhoop.qscx.qscxsj.views.MySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 代驾订单
 */
public class OrderListDriveFragment extends BaseFragment {

    private RecyclerView lvList;
    private MySwipeRefreshLayout refreshLayout;

    private OrderListDriveAdapter adapter;

    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list_recycler_view;
    }

    @Override
    protected void initView(View view) {
        //初始化ListView
        lvList = (RecyclerView) view.findViewById(R.id.lv_list);
        refreshLayout = (MySwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(listener);
        adapter = new OrderListDriveAdapter(getActivity(), new ArrayList<InterCityOrderBean>());
        lvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setEmptyView(R.layout.abs_footer_empty);
        adapter.setLoadEndView(R.layout.abs_footer_end);
        adapter.setLoadingView(R.layout.abs_footer_loading);
        adapter.setLoadFailedView(R.layout.abs_footer_failed);
        adapter.setOnItemClickListener(new OnItemClickListener<InterCityOrderBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, InterCityOrderBean data, int position) {
                TownOrderBean bean = new TownOrderBean();
                bean.setAccount(data.getAccount());
                bean.setHead_img(data.getHead_img());
                bean.setName(data.getName());
                bean.setPrice(data.getPrice());
                bean.setStatus(data.getStatus());
                bean.setOrder_small_id(data.getOrder_small_id());
                bean.setTimes(data.getTimes());
                OrderDetailActivity.startIntent(getActivity(), bean, Constants.TAKER_TYPE_ID_DRIVE);
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
    protected void initData() {
        refreshLayout.startRefresh(listener);
    }

    /**
     * 获取订单列表
     */
    private void requestOrderList(final int page) {
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(getActivity()).getId();
        DriverOrderService.lists(getActivity(), user_id, MyOrderListActivity.ORDER_TYPE_DRIVE, page + "", new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "requestOrderList: " + result);
                List<InterCityOrderBean> list = GsonUtil.jsonToList(result, InterCityOrderBean.class);
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

    public void upData() {
        refreshLayout.startRefresh(listener);
    }
}
