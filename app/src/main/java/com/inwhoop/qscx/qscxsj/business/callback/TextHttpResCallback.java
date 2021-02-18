package com.inwhoop.qscx.qscxsj.business.callback;

import com.inwhoop.qscx.qscxsj.commons.HttpStatic;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TextHttpResCallback extends TextHttpResponseHandler {

    private final OnHttpCallbackListener onHttpCallbackListener;

    public TextHttpResCallback(OnHttpCallbackListener onHttpCallbackListener) {
        this.onHttpCallbackListener = onHttpCallbackListener;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        try {
            L.net("response: " + responseString);
            JSONObject js = new JSONObject(responseString);
            int status = js.optInt("status");
            if (status == 200) {
                //成功
                String result = js.optString("result");
                String msg = js.optString("msg");
                L.net("result: " + result);
                L.net("msg: " + msg);
                if (onHttpCallbackListener != null)
                    onHttpCallbackListener.onHttpSuccess(result, msg);
            } else {
                //失败
                if (js.has("msg")) {
                    String msg = js.optString("msg");
                    L.net("msg: " + msg);
                    if (onHttpCallbackListener != null)
                        onHttpCallbackListener.onHttpFailure(status, msg);
                } else {
                    if (onHttpCallbackListener != null)
                        onHttpCallbackListener.onHttpFailure(status, "API未知错误");
                }
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
            if (onHttpCallbackListener != null)
                onHttpCallbackListener.onHttpFailure(201, "API解析异常");
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        if (onHttpCallbackListener != null)
            onHttpCallbackListener.onHttpError("网络超时或服务器连接失败，请重试");
//            onHttpCallbackListener.onHttpError(throwable.getMessage());
    }
}
