package com.inwhoop.qscx.qscxsj.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.tools.TopBarColorManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.simple.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseFragmentActivity extends FragmentActivity {

    public Context context;

    public static ProgressDialog myDialog;// 加载时的进度条
    public static DisplayImageOptions options;

    private android.app.AlertDialog.Builder conflictBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

//		setFullAndTransparentScreen();// 设置应用不显示标题栏 + 透明状态栏

        // 将对象注册到事件总线中， ****** 注意要在onDestory中进行注销 ****
        EventBus.getDefault().register(this);

        context = this;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                // .displayer(new RoundedBitmapDisplayer(20))
                .bitmapConfig(Bitmap.Config.RGB_565).build();


        TopBarColorManager.applyKitKatTranslucency(this);


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

        myDialog.setOnKeyListener(new OnKeyListener()// 为按键设置监听
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
        Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
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
