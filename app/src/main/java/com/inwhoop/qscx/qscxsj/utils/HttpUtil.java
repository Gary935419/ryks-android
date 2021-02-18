package com.inwhoop.qscx.qscxsj.utils;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.entity.StringEntity;


/**
 * 类名:HttpUtil
 */
public class HttpUtil {
    private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象

    public static void cancelRequest(Context paramContext) {
        client.cancelRequests(paramContext, true);
    }

    public static void get(Context context, String url, RequestParams params,
                           AsyncHttpResponseHandler paramAsyncHttpResponseHandler) {
        PersistentCookieStore localPersistentCookieStore = new PersistentCookieStore(context);

        client.setCookieStore(localPersistentCookieStore);
        client.get(url, params, paramAsyncHttpResponseHandler);
        Log.i("inwhoop", "get,url:" + url.toString());
        Log.i("inwhoop", "get,params:" + params.toString());

    }

    public static void get(String urlString, AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
    {
        client.get(urlString, res);
    }

    public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res) // url里面带参数
    {
        client.get(urlString, params, res);
    }

    public static void get(String urlString, JsonHttpResponseHandler res) // 不带参数，获取json对象或者数组
    {
        client.get(urlString, res);
    }

    public static void get(String urlString, RequestParams params, JsonHttpResponseHandler res) // 带参数，获取json对象或者数组
    {
        client.get(urlString, params, res);
    }

    public static void get(String uString, BinaryHttpResponseHandler bHandler) // 下载数据使用，会返回byte数据
    {
        client.get(uString, bHandler);
    }

    public static AsyncHttpClient getClient() {
        return client;
    }

    //上传文件专用
    public static void postFile(Context context, String url, RequestParams params,
                                AsyncHttpResponseHandler paramAsyncHttpResponseHandler) {
        PersistentCookieStore localPersistentCookieStore = new PersistentCookieStore(context);
        client.setTimeout(180 * 1000);
        client.setCookieStore(localPersistentCookieStore);
        client.post(url, params, paramAsyncHttpResponseHandler);
        L.net("postfile,url:" + url);
        L.net("postfile,params:" + params.toString());
    }

    public static void post(Context context, String url, RequestParams params,
                            AsyncHttpResponseHandler paramAsyncHttpResponseHandler) {
        PersistentCookieStore localPersistentCookieStore = new PersistentCookieStore(context);
        client.setTimeout(1 * 100);
        client.setCookieStore(localPersistentCookieStore);
        client.post(url, params, paramAsyncHttpResponseHandler);
        L.net("post,url:" + url);
        L.net("post,params:" + params.toString());
    }

    public static void post(Context context, String url, StringEntity entity,
                            AsyncHttpResponseHandler paramAsyncHttpResponseHandler) {
        PersistentCookieStore localPersistentCookieStore = new PersistentCookieStore(context);

        client.setTimeout(10 * 1000);
        client.setCookieStore(localPersistentCookieStore);
        client.post(context, url, entity, "application/json", paramAsyncHttpResponseHandler);
        L.net("post,url:" + url);
    }

}