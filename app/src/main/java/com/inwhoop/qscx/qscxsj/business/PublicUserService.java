package com.inwhoop.qscx.qscxsj.business;

import android.content.Context;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.commons.HttpStatic;
import com.inwhoop.qscx.qscxsj.tools.NetWorkState;
import com.inwhoop.qscx.qscxsj.utils.HttpUtil;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Lucky on 2016/11/21.
 */

public class PublicUserService {

    private static final String TAG = PublicUserService.class.getSimpleName();

    public static void login(Context context, String type, String account, String code,
                             AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("type", type);
        params.put("account", account);
        params.put("code", code);
        HttpUtil.post(context, HttpStatic.USER_LOGIN, params,
                asyncHttpResponseHandler);
    }

    public static void get_verify_code(Context context, String account, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("account", account);
        HttpUtil.post(context, HttpStatic.USER_GET_VERIFY_CODE, params, asyncHttpResponseHandler);
    }

    public static void info(Context context, String id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        HttpUtil.post(context, HttpStatic.USER_INFO, params, asyncHttpResponseHandler);
    }

    /**
     * @param context
     * @param id
     * @param type                     1 跑腿  2代驾
     * @param asyncHttpResponseHandler
     */
    public static void getProbate(Context context, String id, String type, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("type", type);
        HttpUtil.post(context, HttpStatic.USER_GET_PROBATE, params, asyncHttpResponseHandler);
    }

    public static void probate(Context context, String id, String name, int sex, String cards, String times,
                               String car_number, File img_cards_face, File img_cards_side,
                               File img_drivers, File img_vehicle, File img_drive_person, File img_worker, String taker_type_id,
                               String route_city_id1, String route_city_id2, String car_type_id,
                               String attribute, String brand, String check_type,
                               AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context, context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("name", name);
        params.put("sex", sex);
        params.put("cards", cards);
        params.put("times", times);
        params.put("car_number", car_number);
        try {
            params.put("img_cards_face", img_cards_face);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            params.put("img_cards_side", img_cards_side);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            params.put("img_drivers", img_drivers);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            params.put("img_vehicle", img_vehicle);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            params.put("img_car_user", img_drive_person);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            params.put("img_worker", img_worker);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params.put("taker_type_id", taker_type_id);
        params.put("route_city_id1", route_city_id1);
        params.put("route_city_id2", route_city_id2);
        params.put("car_type_id", car_type_id);
        params.put("attribute", attribute);
        params.put("brand", brand);
        params.put("check_type", check_type);
        HttpUtil.post(context, HttpStatic.USER_PROBATE, params, asyncHttpResponseHandler);
    }

    public static void personal(Context context, String id, File head_img, String name,
                                String taker_type_id, String route_city_id1, String route_city_id2, String invitation_code,
                                AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        try {
            params.put("head_img", head_img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params.put("name", name);
        params.put("taker_type_id", taker_type_id);
        params.put("route_city_id1", route_city_id1);
        params.put("route_city_id2", route_city_id2);
        params.put("invitation_code", invitation_code);
        HttpUtil.post(context, HttpStatic.USER_PERSONAL, params, asyncHttpResponseHandler);
    }

    public static void changePhone(Context context, String id, String phone, String code, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        params.put("phone", phone);
        params.put("code", code);
        HttpUtil.post(context, HttpStatic.USER_CHANGE_PHONE, params, asyncHttpResponseHandler);
    }

    public static void unregisterAccount(Context context, String id, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!NetWorkState.isNetworkConnected(context)) {
            ToastUtil.showShortToast(context,
                    context.getString(R.string.no_net_connnected));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("md5", HttpStatic.MD5);
        params.put("id", id);
        HttpUtil.post(context, HttpStatic.USER_UNREGISTER, params, asyncHttpResponseHandler);
    }
}