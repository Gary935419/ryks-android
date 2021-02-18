package com.inwhoop.qscx.qscxsj.business;

import android.content.Context;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.commons.HttpStatic;
import com.inwhoop.qscx.qscxsj.entitys.ComplainBean;
import com.inwhoop.qscx.qscxsj.sp.SaveInfo;
import com.inwhoop.qscx.qscxsj.tools.NetWorkState;
import com.inwhoop.qscx.qscxsj.utils.HttpUtil;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Lucky on 2016/12/14.
 */

public class DriverOrderService {

    /**
     * 订单列表
     */
    public static void lists(Context context, String id, String taker_type_id, String page, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("taker_type_id", taker_type_id);
        params.put("page", page);
        HttpUtil.post(context, HttpStatic.DRIVERORDER_LISTS, params, asyncHttpResponseHandler);
    }

    /**
     * 上车
     */
    public static void line_on_car(Context context, String id, String order_small_id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("order_small_id", order_small_id);
        HttpUtil.post(context, HttpStatic.DRIVERORDER_LINE_ON_CAR, params, asyncHttpResponseHandler);
    }

    public static void line_on_car_ok(Context context, String id, String order_small_id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }

        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("order_small_id", order_small_id);
        HttpUtil.post(context, HttpStatic.DRIVERORDER_LINE_ON_CAR_OK, params, asyncHttpResponseHandler);
    }


    public static void get_order_complaint(Context context, String order_id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("order_id", order_id);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_GET_ORDER, params, asyncHttpResponseHandler);
    }


    public static void do_complain(Context context, ComplainBean complainBean, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("orderId", complainBean.getOrderId());
        params.put("order_type", complainBean.getOrderType());
        params.put("id", LoginUserInfoUtil.getLoginUserInfoBean(context).getId());
        params.put("content", complainBean.getContent());
        params.put("type", "2");
        HttpUtil.post(context, HttpStatic.DRIVERPICK_COMPLAIN, params, asyncHttpResponseHandler);
    }

    public static void get_order(Context context, String order_id, String taker_type_id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("order_id", order_id);
        params.put("id", LoginUserInfoUtil.getLoginUserInfoBean(context).getId());
        params.put("taker_type_id", taker_type_id);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_GET_ORDER_INFO, params, asyncHttpResponseHandler);
    }

    public static void pickUp(Context context, String order_id, String pick_up_code, File goods_image, File goods_image1, File goods_image2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("order_id", order_id);
        params.put("pick_up_code", pick_up_code);
        try {
            params.put("goods_image", goods_image);
            params.put("goods_image1", goods_image1);
            params.put("goods_image2", goods_image2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HttpUtil.post(context, HttpStatic.DRIVERPICK_PICK_UP, params, asyncHttpResponseHandler);
    }

    public static void checkCode(Context context, String order_id, String pick_up_code, AsyncHttpResponseHandler asyncHttpResponseHandler){
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("order_id", order_id);
        params.put("pick_up_code", pick_up_code);
        HttpUtil.post(context, HttpStatic.DRIVERPICK_CHECK_CODE, params, asyncHttpResponseHandler);
    }

    public static void changeOrderStatus(Context context, String order_id, String order_status, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("order_id", order_id);
        params.put("order_status", order_status);
        params.put("id", LoginUserInfoUtil.getLoginUserInfoBean(context).getId());
        HttpUtil.post(context, HttpStatic.DRIVERPICK_CHANGE_ORDER_STATUS, params, asyncHttpResponseHandler);
    }

    public static void getInfo(Context context, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", LoginUserInfoUtil.getLoginUserInfoBean(context).getId());
        HttpUtil.post(context, HttpStatic.USER_GET_INFO, params, asyncHttpResponseHandler);
    }

    /**
     * 获取可接订单列表
     */
    public static void getOrderListCanReceive(Context context, String page, String taker_type_id, AsyncHttpResponseHandler handler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("taker_type_id", taker_type_id);
        params.put("page", page);
        params.put("id", LoginUserInfoUtil.getLoginUserInfoBean(context).getId());
        HttpUtil.post(context, HttpStatic.DRIVERPICK_GET_ORDER_LIST_CAN_RECEIVE, params, handler);
    }

    /**
     * 获取进行中的订单列表
     */
    public static void getOrderListIng(Context context, String page, String taker_type_id, AsyncHttpResponseHandler handler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("taker_type_id", taker_type_id);
        params.put("page", page);
        params.put("id", SaveInfo.getInstance(context).getLoginUserInfo().getId());
        HttpUtil.post(context, HttpStatic.DRIVERPICK_GET_ORDER_LIST_ING, params, handler);
    }
}
