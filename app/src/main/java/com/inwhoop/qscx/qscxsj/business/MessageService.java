package com.inwhoop.qscx.qscxsj.business;

import android.content.Context;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.commons.HttpStatic;
import com.inwhoop.qscx.qscxsj.tools.NetWorkState;
import com.inwhoop.qscx.qscxsj.utils.HttpUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Lucky on 2016/12/14.
 */

public class MessageService {

    public static void unread_num(Context context, String type, String id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("type", type);
        params.put("id", id);
        HttpUtil.post(context, HttpStatic.MESSAGE_UNREAD_NUM, params, asyncHttpResponseHandler);
    }

    public static void lists(Context context, String type, String id, String page, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("type", type);
        params.put("id", id);
        params.put("page", page);
        HttpUtil.post(context, HttpStatic.MESSAGE_LISTS, params, asyncHttpResponseHandler);
    }

    public static void details(Context context, String id, String user_id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("user_id", user_id);
        HttpUtil.post(context, HttpStatic.MESSAGE_DETAILS, params, asyncHttpResponseHandler);
    }

    public static void del(Context context, String id, String user_id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("user_id", user_id);
        HttpUtil.post(context, HttpStatic.MESSAGE_DEL, params, asyncHttpResponseHandler);
    }
}
