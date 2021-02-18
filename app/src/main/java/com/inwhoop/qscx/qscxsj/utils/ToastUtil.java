package com.inwhoop.qscx.qscxsj.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.inwhoop.qscx.qscxsj.R;


public class ToastUtil {
    private static Toast mToast;

    public static void showLongToast(Context context, String msg) {

        // if (null == mToast) {
        mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);

        // } else {
        // mToast.setText(msg);
        // }
        customizeToastBg(context, mToast, msg);
        // customizeToastColor(context, mToast, msg);
        mToast.show();
    }

    public static void showShortToast(Context context, String msg) {

        // if (null == mToast) {
        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        // } else {
        // mToast.setText(msg);
        //
        // }
        customizeToastBg(context, mToast, msg);
        // customizeToastColor(context, mToast, msg);
        mToast.show();
    }

    private static void customizeToastBg(Context context, Toast mToast, String msg) {
        View toastRoot = ((Activity) context).getLayoutInflater().inflate(R.layout.toast_myself, null);
        TextView message_tv = (TextView) toastRoot.findViewById(R.id.message_tv);
        message_tv.setText(msg);
        mToast.setGravity(Gravity.BOTTOM, 0, 100);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(toastRoot);
    }

    private static void customizeToastColor(Context context, Toast mToast, String msg) {
        View view = mToast.getView();
        view.setBackgroundResource(R.drawable.app_theme_toast_bg);
        mToast.setView(view);
    }

    // public static void showToast(final Activity activity, String msg, int
    // duration)
    // {
    // if (null == mToast)
    // {
    // mToast = Toast.makeText(activity, msg, duration);
    // } else
    // {
    // mToast.setText(msg);
    // }
    // mToast.show();
    // }

    public static void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
