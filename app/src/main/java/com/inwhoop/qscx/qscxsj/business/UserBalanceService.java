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
 * Created by Lucky on 2016/12/13.
 */

public class UserBalanceService {

    public static void detailed(Context context, String id, String page, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("page", page);
        HttpUtil.post(context, HttpStatic.USERBALANCE_DETAILED, params, asyncHttpResponseHandler);
    }

    public static void postal(Context context, String id, String bank_account, String name, String card_number,
                              String money, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("bank_account", bank_account);
        params.put("name", name);
        params.put("card_number", card_number);
        params.put("money", money);
        HttpUtil.post(context, HttpStatic.USERBALANCE_POSTAL, params, asyncHttpResponseHandler);
    }
}
