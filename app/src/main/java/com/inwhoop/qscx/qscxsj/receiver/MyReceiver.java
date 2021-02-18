package com.inwhoop.qscx.qscxsj.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inwhoop.qscx.qscxsj.business.DriverPickService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.constants.EventBusMsg;
import com.inwhoop.qscx.qscxsj.entitys.NewOrder;
import com.inwhoop.qscx.qscxsj.entitys.JPushBean;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LogUtils;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.SoundPoolHelper;
import com.inwhoop.qscx.qscxsj.utils.gson.GsonUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.Iterator;
import java.util.Objects;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LogUtils.i("[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogUtils.i("[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.i("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            String str = bundle.getString(JPushInterface.EXTRA_EXTRA);
            LogUtils.i(str);
            requestPopup(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtils.i("[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            LogUtils.i("[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtils.i("[MyReceiver] 用户点击打开了通知");

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogUtils.i("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtils.i("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            LogUtils.i("[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    private JPushBean mJPushBean = null;

    /**
     * 获取新订单信息
     *
     * @param context
     * @param bundle
     */
    private void requestPopup(Context context, Bundle bundle) {
        SoundPoolHelper.getInstance().play(Constants.SOUND_NEW_ORDER, false);    //语音提示
        mJPushBean = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA),
                new TypeToken<JPushBean>() {
                }.getType());
        if (mJPushBean != null) {
            String user_id = LoginUserInfoUtil.getLoginUserInfoBean(context).getId();
            DriverPickService.get_popup(context, user_id, mJPushBean.getWaiting_id(), mJPushBean.getTaker_type_id(),
                    new TextHttpResCallback(mListener));
        } else {
            sendEventPush(null);
        }
    }

    /**
     * api回调
     */
    private final OnHttpCallbackListener mListener = new OnHttpCallbackListener() {
        @Override
        public void onHttpSuccess(String result, String msg) {
            L.i(TAG, "get_popup: " + result);
            NewOrder bean = GsonUtil.toBean(result, NewOrder.class);
            if (bean != null) {
                SoundPoolHelper.getInstance().play(Constants.SOUND_NEW_ORDER, false);    //语音提示
                bean.setWaiting_id(mJPushBean.getWaiting_id());
                bean.setTaker_type_id(mJPushBean.getTaker_type_id());
                sendEventPush(bean);
            } else {
                sendEventPush(null);
            }
        }

        @Override
        public void onHttpFailure(int status, String msg) {
            sendEventPush(null);
        }

        @Override
        public void onHttpError(String errorMsg) {
            sendEventPush(null);
        }
    };

    /**
     * 发送消息EventBus
     */
    private void sendEventPush(NewOrder bean) {
        if (bean != null) {
            EventBus.getDefault().post(bean, EventBusMsg.EVENT_PUSH);
        }
    }

    /**
     * 打印所有的 intent extra 数据
     *
     * @param bundle bundle
     * @return
     */
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (Objects.requireNonNull(bundle.getString(JPushInterface.EXTRA_EXTRA)).isEmpty()) {
                    LogUtils.i("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:").append(key).append(", value: [").append(myKey).append(" - ")
                                .append(json.optString(myKey)).append("]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
