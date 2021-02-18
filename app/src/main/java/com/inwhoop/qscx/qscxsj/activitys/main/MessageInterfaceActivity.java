package com.inwhoop.qscx.qscxsj.activitys.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.adapters.MessageInterfaceAdapter;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnItemClickListener;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnItemLongClickListener;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnLoadMoreListener;
import com.inwhoop.qscx.qscxsj.business.MessageService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.constants.EventBusMsg;
import com.inwhoop.qscx.qscxsj.entitys.MessageListsBean;
import com.inwhoop.qscx.qscxsj.utils.DialogUtils;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.utils.gson.GsonUtil;
import com.inwhoop.qscx.qscxsj.views.MySwipeRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MessageInterfaceActivity extends BaseActivity {

    private ImageView ivTitleLeft;

    private MessageInterfaceAdapter adapter;

    private RecyclerView lvList;
    private MySwipeRefreshLayout refreshLayout;
    private AlertDialog deleteDialog;

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_interface);
        //初始化控件
        initView();
        initAdapter();
        //配置监听
        initListener();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        TextView tvTitleMain = (TextView) findViewById(R.id.tv_title_main);
        ivTitleLeft = (ImageView) findViewById(R.id.iv_title_left);
        lvList = (RecyclerView) findViewById(R.id.lv_list);
        refreshLayout = (MySwipeRefreshLayout) findViewById(R.id.refresh_layout);
        tvTitleMain.setText("我的消息");
        ivTitleLeft.setVisibility(View.VISIBLE);
    }

    private void initAdapter() {
        lvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageInterfaceAdapter(this, new ArrayList<MessageListsBean>());
        adapter.setEmptyView(R.layout.abs_footer_empty);
        adapter.setLoadEndView(R.layout.abs_footer_end);
        adapter.setLoadingView(R.layout.abs_footer_loading);
        adapter.setLoadFailedView(R.layout.abs_footer_failed);
        adapter.setOnItemClickListener(new OnItemClickListener<MessageListsBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, MessageListsBean data, int position) {
                startActivity(new Intent(MessageInterfaceActivity.this, MessageContentActivity.class)
                        .putExtra("id", data.getId()));
            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener<MessageListsBean>() {
            @Override
            public boolean onItemLongClick(ViewHolder viewHolder, final MessageListsBean data, int position) {
                deleteDialog = DialogUtils.showConfirmDialog(context, "确认删除该条消息？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogUtils.dismiss(deleteDialog);
                        requestDeleteMsg(data.getId());
                    }
                });
                DialogUtils.show(context, deleteDialog);
                return false;
            }
        });
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (!isReload) page++;
                requestMsgList(page);
            }
        });
        lvList.setAdapter(adapter);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout.setOnRefreshListener(refreshListener);
    }

    private void initData() {
        refreshLayout.startRefresh(refreshListener);
    }

    /**
     * 消息列表
     */
    private void requestMsgList(final int page) {
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();
        MessageService.lists(this, "2", user_id, page + "", new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "requestMsgList: " + result);
                List<MessageListsBean> list = GsonUtil.jsonToList(result, MessageListsBean.class);
                if (page == 1) {
                    adapter.setNewData(list);
                } else {
                    adapter.setLoadMoreData(list);
                }
                if (list == null || list.size() < 10) {
                    adapter.loadEnd();
                }
                refreshLayout.stopRefresh();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                refreshLayout.stopRefresh();
            }

            @Override
            public void onHttpError(String errorMsg) {
                refreshLayout.stopRefresh();
            }
        }));
    }

    /**
     * 删除消息
     */
    private void requestDeleteMsg(String id) {
        showLoadingDialog("正在删除...", false);
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();
        MessageService.del(context, id, user_id, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(context, "删除成功");
                EventBus.getDefault().post("tag", EventBusMsg.EVENT_UNREAD_MSG);
                refreshLayout.startRefresh(refreshListener);
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                dismissLoadingDialog();
            }

            @Override
            public void onHttpError(String errorMsg) {
                dismissLoadingDialog();
            }
        }));
    }

    /**
     * 刷新监听
     */
    private final SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            requestMsgList(page);
        }
    };

    @Subscriber(tag = EventBusMsg.EVENT_MSG_READ)
    private void MessageInterfaceActivityLists(String tag) {
        refreshLayout.startRefresh(refreshListener);
    }
}
