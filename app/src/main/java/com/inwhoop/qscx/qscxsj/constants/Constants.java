package com.inwhoop.qscx.qscxsj.constants;

import android.os.Build;
import android.os.Environment;

/**
 * 功能说明:静态常量类【系统公用】
 */
public interface Constants {

    //是否有DeBugger
    boolean IS_DEBUG = false;

    //日志标记
    String LOG_TAG = "com.inwhoop.rzxfordriver.rongzhouxingfordriver";
    //文件保存路径
    String FILE_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/rongzhouxingfordriver/cache/";
    String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/rongzhouxingfordriver/file/";
    //版本
    int SDK_INT = Build.VERSION.SDK_INT;


    //成功
    int REQUEST_STATUS_SUCCESS = 1;
    //失败
    int REQUEST_STATUS_FAIL = 0;
    String DEFAULT_CITY_ID = "3";


    int CROP_HEAD_IMG_WIDTH = 512;//裁剪头像宽度
    int CROP_HEAD_IMG_HEIGHT = 512;//裁剪头像高度
    int ITEM_COUNTS = 10;//列表请求条数
    String PARAM_SEX = "param_sex";

    //法律声明
    String URL_LAW = "http://rysy.demo.dlshangcai.com/legal_statement.html";
    //隐私政策
    String URL_PRIVATE = "http://rysy.demo.dlshangcai.com/privacy_policy.html";
    //注册协议
    String URL_DRIVER = "http://rysy.demo.dlshangcai.com/drver_reg.html";
    //信息服务协议
    String URL_GENERATION = "http://rysy.demo.dlshangcai.com/driving_reg.html";

    String FILE_PROVIDER = "com.inwhoop.qscx.qscxsj.fileProvider";

    /**
     * 音频文件
     */
    String SOUND_START_GET_ORDER = "start_get_order.mp3";
    String SOUND_STOP_GET_ORDER = "stop_get_order.mp3";
    String SOUND_ORDER_FINISH = "order_finish.mp3";
    String SOUND_NEW_ORDER = "new_order.mp3";
    String SOUND_WELCOME = "welcome.mp3";
    String SOUND_ORDER_START = "order_start.mp3";

    /**
     * 订单类型
     */
    String ORDER_TYPE_ZHUAN = "1";    //专车送
    String ORDER_TYPE_SHUN = "2";    //顺风车送
    String ORDER_TYPE_BUY = "3";    //代买
    String ORDER_TYPE_DRIVE = "4";    //代驾

    /**
     * 工作状态
     */
    String WORK_STATUS_RECEIVING_NOT = "0";   //未开启接单
    String WORK_STATUS_RECEIVING = "1";   //正在接单
    String WORK_STATUS_IN_ORDER = "2";   //订单行程中

    /**
     * 订单大类（表）
     */
    String TAKER_TYPE_ID_SONG = "1";   //订单类型-送
    String TAKER_TYPE_ID_DRIVE = "2";   //订单类型-代驾

    /**
     * 接单操作
     */
    String RECEIVE_ORDER_SURE = "1";   //接单
    String RECEIVE_ORDER_REFUSE = "2";   //拒绝接单

    /**
     * 送货订单 status
     */
    String STATUS_SONG_FINISH = "6";    //已完成
    String STATUS_SONG_CANCEL = "7";    //已取消
    String STATUS_SONG_CANCEL_BY_PASSANGER = "8";    //已撤销

    /**
     * 送货订单 order_status
     */
    String ORDER_STATUS_SONG_NOT_PAY = "1";   //发单未付款
    String ORDER_STATUS_SONG_PAY = "2";   //发单已付款未接单
    String ORDER_STATUS_SONG_RECEIVE = "3";   //已接单
    String ORDER_STATUS_SONG_TO_START_LOCATION = "4";   //前往始发地
    String ORDER_STATUS_SONG_ARRIVE_LOCATION = "5";   //到达始发地
    String ORDER_STATUS_SONG_AUTH_GOODS_CODE = "6";   //待验证提货码
    String ORDER_STATUS_SONG_TO_END_LOCATION = "7";   //前往目的地
    String ORDER_STATUS_SONG_FINISH = "8";   //完成

    /**
     * 代驾订单 status
     */
    String STATUS_DRIVE_NOT_RECEIVE = "1";   //待接单
    String STATUS_DRIVE_RECEIVE = "2";   //已接单（开始行程之前）
    String STATUS_DRIVE_TO_START_LOCATION = "4";   //前往始发地
    String STATUS_DRIVE_GET_CAR = "3";   //上车
    String STATUS_DRIVE_TO_END_LOCATION = "2.5";   //前往目的地
    String STATUS_DRIVE_FINISH = "6";   //下车/已完成
    String STATUS_DRIVE_CANCEL = "7";   //已取消
    String STATUS_DRIVE_PASSENGER_CANCEL = "8";   //没人接（撤销）
}
