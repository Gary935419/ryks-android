package com.inwhoop.qscx.qscxsj.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.tools.TopBarColorManager;
import com.inwhoop.qscx.qscxsj.utils.DialogUtils;
import com.inwhoop.qscx.qscxsj.utils.statusbar.StatusBarUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.simple.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseActivity extends Activity {

    public static String TAG = BaseActivity.class.getSimpleName();

    public Context context;
    public Activity activity;

    public static ProgressDialog myDialog;// 加载时的进度条
    public static DisplayImageOptions options;
    public static AlertDialog alertDialog;

    private Dialog cmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
//        StatusBarUtil.setTranslucentStatus(this);
//        setFullAndTransparentScreen();// 设置应用不显示标题栏 + 透明状态栏

        // 将对象注册到事件总线中， ****** 注意要在onDestory中进行注销 ****
        EventBus.getDefault().register(this);
//
        context = this;
        activity = this;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                // .displayer(new RoundedBitmapDisplayer(20))
                .bitmapConfig(Bitmap.Config.RGB_565).build();

//        TopBarColorManager.applyKitKatTranslucency(this);
    }

    /****************************************************************/
    public void showProgressDialog(String message) {
        myDialog = new ProgressDialog(this);// 创建ProgressDialog对象,关联本当前Activity
        myDialog.setMessage(message);// 设置ProgressDialog提示信息
        myDialog.setIcon(R.mipmap.ic_launcher);// 设置标题图标；
        myDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        myDialog.setIndeterminate(true);// 设置ProgressDialog的进度条是否不明确；
        // 这个属性对于ProgressDailog默认的转轮模式没有实际意义，
        // 默认下设置为true，它仅仅对带有ProgressBar的Dialog有作用。
        // 修改这个属性为false后可以实时更新进度条的进度。
        myDialog.setCancelable(true);// 设置ProgressDialog 是否可以按返回键取消；
        myDialog.setCanceledOnTouchOutside(false);// 按对话框以外的地方起作用。按返回键不起作用

        /** 设置透明度 */
        Window window = myDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;// 透明度
        lp.dimAmount = 0.5f;// 黑暗度
        window.setAttributes(lp);
        myDialog.show();// 让ProgressDialog显示

        // 会报错：java.lang.IllegalArgumentException: Window type can not be
        // changed
        // after the window is added.
        // myDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);//屏蔽Home键,不过在这里貌似没有什么用

        myDialog.setOnKeyListener(new DialogInterface.OnKeyListener()// 为按键设置监听
        {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event)// dialog:该密钥该密钥已经被派往对话已经被派往对话;keyCode:对于被按下的物理键的代码;event:包含完整的信息关于这个事件的keyEvent的对象

            {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:// 返回键
                        Log.i("aaa", "KEYCODE_BACK");
                        myDialog.dismiss();
                        finish();
                        return true;
                }
                return false;
            }
        });

    }

    public void showAlertDialog(String message) {
        alertDialog = new AlertDialog.Builder(this).create();// 创建ProgressDialog对象,关联本当前Activity
        alertDialog.setMessage(message);// 设置ProgressDialog提示信息
        alertDialog.setIcon(R.mipmap.ic_launcher);// 设置标题图标；
        //alertDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //alertDialog.setIndeterminate(true);// 设置ProgressDialog的进度条是否不明确；
        // 这个属性对于ProgressDailog默认的转轮模式没有实际意义，
        // 默认下设置为true，它仅仅对带有ProgressBar的Dialog有作用。
        // 修改这个属性为false后可以实时更新进度条的进度。
        alertDialog.setCancelable(true);// 设置ProgressDialog 是否可以按返回键取消；
        alertDialog.setCanceledOnTouchOutside(false);// 按对话框以外的地方起作用。按返回键不起作用

        /** 设置透明度 */
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;// 透明度
        lp.dimAmount = 0.5f;// 黑暗度
        window.setAttributes(lp);
        alertDialog.show();// 让ProgressDialog显示

        // 会报错：java.lang.IllegalArgumentException: Window type can not be
        // changed
        // after the window is added.
        // myDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);//屏蔽Home键,不过在这里貌似没有什么用

        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener()// 为按键设置监听
        {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event)// dialog:该密钥该密钥已经被派往对话已经被派往对话;keyCode:对于被按下的物理键的代码;event:包含完整的信息关于这个事件的keyEvent的对象

            {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:// 返回键
                        Log.i("aaa", "KEYCODE_BACK");
                        alertDialog.dismiss();
                        finish();
                        return true;
                }
                return false;
            }
        });

    }


    public void dismissProgressDialog() {
        if (myDialog != null) {
            myDialog.dismiss();
        }
    }

    public void dismissAlertDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void setFullAndTransparentScreen() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置应用不显示标题栏
        // 设置应用全屏(去掉信息栏)
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//设置宽填满屏幕
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置宽填满屏幕


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }

    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /****************************************************************/

    /**
     * 通过类名启动Activity，没有Bundle数据
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);

    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pbundle
     */
    protected void openActivity(Class<?> pClass, Bundle pbundle) {
        Intent intent = new Intent(this, pClass);
        if (pbundle != null) {
            intent.putExtras(pbundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action启动Activity，不含有Bundle数据
     *
     * @param pAction
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param pAction
     * @param pbundle
     */
    protected void openActivity(String pAction, Bundle pbundle) {
        Intent intent = new Intent(pAction);
        if (pbundle != null) {
            intent.putExtras(pbundle);
        }
        startActivity(intent);

    }

    /**
     * 创建等待时的dialog
     */
    public void showLoadingDialog(String msg) {
        if (cmd != null && cmd.isShowing()) return;
        cmd = DialogUtils.showLoading(this, msg);
        cmd.setCancelable(false);
        DialogUtils.show(this, cmd);
    }

    /**
     * 创建等待时的dialog
     */
    public void showLoadingDialog(String msg, boolean canCancel) {
        if (cmd != null && cmd.isShowing()) return;
        cmd = DialogUtils.showLoading(this, msg);
        cmd.setCancelable(canCancel);
        DialogUtils.show(this, cmd);
    }

    /**
     * 消去等待时的dialog
     */
    public void dismissLoadingDialog() {
        DialogUtils.dismiss(cmd);
        cmd = null;
    }

    /****************************************************************/
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        // ****** 不要忘了进行注销 ****
        EventBus.getDefault().unregister(this);
    }

}
