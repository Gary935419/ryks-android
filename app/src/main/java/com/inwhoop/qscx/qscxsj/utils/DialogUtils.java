package com.inwhoop.qscx.qscxsj.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;

/**
 * Dialog的工具类
 */
public class DialogUtils {

    /**
     * 创建加载中Dialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog showLoading(final Context context, String msg) {
        View v = LayoutInflater.from(context).inflate(R.layout.abs_dialog_loading, null);// 得到加载view
        TextView tipTextView = v.findViewById(R.id.tipTextView);// 提示文字
        if (TextUtils.isEmpty(msg))
            tipTextView.setText("正在加载...");
        else
            tipTextView.setText(msg);// 设置加载信息
        Dialog loadingDialog = new Dialog(context, R.style.CommonDialog);// 创建自定义样式dialog
//        loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        loadingDialog.setContentView(v);
        return loadingDialog;
    }

    /**
     * 提示成功Toast
     */
    public static void showSuccessToast(Context context, String text) {
        Toast toast = new Toast(context);
        View toastLayout = LayoutInflater.from(context).inflate(R.layout.abs_dialog_success, null);
        TextView tvText = toastLayout.findViewById(R.id.tv_text);
        tvText.setText(text);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastLayout);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 显示dialog
     */
    public static void show(Context context, Dialog dialog) {
        if (dialog != null && context != null && !((Activity) context).isFinishing()) {
            dialog.show();
        }
    }

    /**
     * 关闭加载中dialog
     */
    public static void dismiss(Dialog mDialogUtils) {
        if (mDialogUtils != null && mDialogUtils.isShowing()) {
            mDialogUtils.dismiss();
        }
    }

    public static AlertDialog showConfirmDialog(Context context, String message, DialogInterface.OnClickListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("温馨提示");
        dialog.setMessage(message);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", listener);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return dialog;
    }
}