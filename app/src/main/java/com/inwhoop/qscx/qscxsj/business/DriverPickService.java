package com.inwhoop.qscx.qscxsj.business;

import android.content.Context;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.commons.HttpStatic;
import com.inwhoop.qscx.qscxsj.entitys.ComplainBean;
import com.inwhoop.qscx.qscxsj.tools.NetWorkState;
import com.inwhoop.qscx.qscxsj.utils.HttpUtil;
import com.inwhoop.qscx.qscxsj.utils.SharedPreferencesUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Lucky on 2016/11/29.
 */

public class DriverPickService {

    public static void get_basic(Context context, String id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_GET_BASIC, params, asyncHttpResponseHandler);
    }

    public static void working(Context context, String id, String taker_type_id, String longitude,
                               String latitude, String car_type_id, String surplus_seat,
                               String route_city_id1, String route_city_id2,
                               AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("taker_type_id", taker_type_id);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("car_type_id", car_type_id);
        params.put("surplus_seat", surplus_seat);
        params.put("route_city_id1", route_city_id1);
        params.put("route_city_id2", route_city_id2);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_WORKING, params, asyncHttpResponseHandler);
    }

    public static void off_duty(Context context, String id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_OFF_DUTY, params, asyncHttpResponseHandler);
    }

    public static void update_coordinate(Context context, String id, String longitude, String latitude,
                                         AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_UPDATE_COORDINATE, params, asyncHttpResponseHandler);
    }

    /**
     * 获取新订单信息
     */
    public static void get_popup(Context context, String id, String waiting_id, String taker_type_id, AsyncHttpResponseHandler handler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("waiting_id", waiting_id);
        params.put("taker_type_id", taker_type_id);
        params.put("latitude", SharedPreferencesUtil.getLat(context));
        params.put("longitude", SharedPreferencesUtil.getLng(context));
        HttpUtil.post(context, HttpStatic.DRIVERPICK_GET_POPUP, params, handler);
    }

    /**
     * 接单/拒绝接单
     */
    public static void handle_popup(Context context, String id, String waiting_id, String handle, String taker_type_id, AsyncHttpResponseHandler handler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("waiting_id", waiting_id);
        params.put("handle", handle);
        params.put("taker_type_id", taker_type_id);
        params.put("latitude", SharedPreferencesUtil.getLat(context));
        params.put("longitude", SharedPreferencesUtil.getLng(context));
        HttpUtil.post(context, HttpStatic.DRIVERPICK_HANDLE_POPUP, params, handler);
    }

    //废弃
    public static void get_order(Context context, String order_id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("order_id", order_id);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_GET_ORDER, params, asyncHttpResponseHandler);
    }

    public static void town_get_order(Context context, String order_id, String taker_type_id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if(!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("order_id", order_id);
        params.put("taker_type_id", taker_type_id);    //代驾传2 其它传1
        HttpUtil.post(context, HttpStatic.DRIVERPICK_TOWN_GET_ORDER, params, asyncHttpResponseHandler);
    }

    public static void aboard(Context context, String taker_type_id, String order_small_id,
                              AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("taker_type_id", taker_type_id);
        params.put("order_small_id", order_small_id);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_ABOARD, params, asyncHttpResponseHandler);
    }

    public static void cancel(Context context, String taker_type_id, String order_small_id,
                              AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("taker_type_id", taker_type_id);
        params.put("order_small_id", order_small_id);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_CANCEL, params, asyncHttpResponseHandler);
    }

    public static void start_trip(Context context, String taker_type_id, String order_id,
                                  AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("taker_type_id", taker_type_id);
        params.put("order_id", order_id);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_START_TRIP, params, asyncHttpResponseHandler);
    }

    public static void small_ok(Context context, String taker_type_id, String order_small_id,
                                AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("taker_type_id", taker_type_id);
        params.put("order_small_id", order_small_id);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_SMALL_OK, params, asyncHttpResponseHandler);
    }

    public static void order_ok(Context context, String taker_type_id, String order_id,
                                AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("taker_type_id", taker_type_id);
        params.put("order_id", order_id);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_ORDER_OK, params, asyncHttpResponseHandler);
    }

    public static void pick_up(Context context, String order_id, String pick_up_code,
                                AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("order_id", order_id);
        params.put("pick_up_code", pick_up_code);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_PICK_UP, params, asyncHttpResponseHandler);
    }
}
