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
 * Created by Lucky on 2016/12/12.
 */

public class MemberService {

    public static void contact_us(Context context, String type, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("type", type);
        HttpUtil.post(context, HttpStatic.MEMBER_CONTACT_US, params, asyncHttpResponseHandler);
    }

    public static void about_us(Context context, String type, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("type", type);
        HttpUtil.post(context, HttpStatic.MEMBER_ABOUT_US, params, asyncHttpResponseHandler);
    }

    public static void feedback(Context context, String id, String content, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("content", content);
        HttpUtil.post(context, HttpStatic.MEMBER_FEEDBACK, params, asyncHttpResponseHandler);
    }

}
