package com.inwhoop.qscx.qscxsj.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.utils.SoundPoolHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getName();

    private static MyApplication instance;

    public static double longitude;
    public static double latitude;

    private static Handler mHandler;

    // 记录是否已经初始化
    private boolean isInit = false;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化音频文件
        SoundPoolHelper.getInstance().setRingtoneType(SoundPoolHelper.RING_TYPE_MUSIC)
                .load(this, Constants.SOUND_WELCOME, R.raw.welcome)
                .load(this, Constants.SOUND_START_GET_ORDER, R.raw.start_get_order)
                .load(this, Constants.SOUND_STOP_GET_ORDER, R.raw.stop_get_order)
                .load(this, Constants.SOUND_ORDER_START, R.raw.order_start)
                .load(this, Constants.SOUND_ORDER_FINISH, R.raw.order_finish)
                .load(this, Constants.SOUND_NEW_ORDER, R.raw.new_order);

        CrashReport.initCrashReport(getApplicationContext(), "5e873ecfc6", true);
        //初始化handler
        mHandler = new Handler();

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);

        initImageCache();
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void runOnUIThread(Runnable r) {
        MyApplication.getMainHandler().post(r);
    }

    private void initImageCache() {
        ImageLoader loader = ImageLoader.getInstance();
        if (!loader.isInited()) {
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                    .defaultDisplayImageOptions(
                            new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build())
                    .build();
            loader.init(configuration);
        }
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public void init() {
        // 设置该CrashHandler为程序的默认处理器
        UnCeHandler catchExcep = new UnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }
}
