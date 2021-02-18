package com.inwhoop.qscx.qscxsj.activitys;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapCarInfo;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.services.core.LatLonPoint;
import com.google.gson.Gson;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.activitys.logins.LoginActivity;
import com.inwhoop.qscx.qscxsj.activitys.main.MyOrderListActivity;
import com.inwhoop.qscx.qscxsj.activitys.main.MessageInterfaceActivity;
import com.inwhoop.qscx.qscxsj.activitys.main.MyBalanceActivity;
import com.inwhoop.qscx.qscxsj.activitys.main.PersonalDataActivity;
import com.inwhoop.qscx.qscxsj.activitys.main.SetActivity;
import com.inwhoop.qscx.qscxsj.activitys.ordertaking.OrderListCanReceiveActivity;
import com.inwhoop.qscx.qscxsj.activitys.ordertaking.OrderListIngActivity;
import com.inwhoop.qscx.qscxsj.activitys.ordertaking.SubmitCodeActivity;
import com.inwhoop.qscx.qscxsj.activitys.ordertaking.UploadGoodsActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.business.DriverPickService;
import com.inwhoop.qscx.qscxsj.business.MemberService;
import com.inwhoop.qscx.qscxsj.business.MessageService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.constants.EventBusMsg;
import com.inwhoop.qscx.qscxsj.dialogs.NewOrderComeDialog;
import com.inwhoop.qscx.qscxsj.entitys.NewOrder;
import com.inwhoop.qscx.qscxsj.entitys.DriverPickBasicBean;
import com.inwhoop.qscx.qscxsj.entitys.DriverPickTownOrderBean;
import com.inwhoop.qscx.qscxsj.entitys.UserBean;
import com.inwhoop.qscx.qscxsj.navi.RouteNaviActivity;
import com.inwhoop.qscx.qscxsj.navi.SimpleAMapNaviListener;
import com.inwhoop.qscx.qscxsj.sp.SaveInfo;
import com.inwhoop.qscx.qscxsj.utils.DialogUtils;
import com.inwhoop.qscx.qscxsj.utils.FileUtils;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.PicUtil;
import com.inwhoop.qscx.qscxsj.utils.SharedPreferencesUtil;
import com.inwhoop.qscx.qscxsj.utils.SoundPoolHelper;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.utils.Utils;
import com.inwhoop.qscx.qscxsj.utils.statusbar.StatusBarUtil;
import com.inwhoop.qscx.qscxsj.views.CircleImageView;
import com.liangmayong.text2speech.Text2Speech;
import com.qihoo.appstore.common.updatesdk.lib.UpdateHelper;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static MainActivity instance;

    private RelativeLayout layout_title_root;
    private TextView tv_title_main;
    private ImageView iv_title_left;
    private ImageView iv_title_right;
    private Button btn_change_route;

    private DrawerLayout drawerLayout;
    private LinearLayout layoutUser;
    private CircleImageView iv_portrait;
    private TextView tv_username;     //用户昵称
    private TextView tv_score;     //用户分数
    private TextView tv_invite_code;     //推荐码
    private TextView tv_invite_person_code;     //推荐人码
    private LinearLayout layoutMyOrder;      //我的订单
    private LinearLayout layoutMyBalance;     //我的余额
    private LinearLayout layoutContactUs;    //联系我们
    private LinearLayout layoutSetting;     //设置

    private LinearLayout layout_change_order;
    private LinearLayout layout_tel;
    private LinearLayout layout_cancel_order;

    private Button btn_main_work;     //工作状态按钮（开始接单，停止接单）
    private Button btn_main_order;     //订单状态中的主按钮

    private AMapNavi mAMapNavi;
    private MapView mapView;
    private AMap aMap;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private MyLocationStyle locationStyle;
    private UiSettings mUiSettings;
    private final List<NaviLatLng> startList = new ArrayList<>();
    private final List<NaviLatLng> endList = new ArrayList<>();

    private boolean isRequestUpdateCoordinate = false;    //是否正在请求更新坐标位置
    /**
     * 途径点坐标集合
     */
    private List<NaviLatLng> wayList = new ArrayList<NaviLatLng>();
    /**
     * 保存当前算好的路线
     */
    private final SparseArray<RouteOverLay> routeOverlays = new SparseArray<>();
    /**
     * 当前用户选中的路线，在下个页面进行导航
     */
    private int routeIndex = 0;
    /**
     * 路线的权值，重合路线情况下，权值高的路线会覆盖权值低的路线
     **/
    private int zindex = 1;
    /**
     * 路线计算成功标志位
     */
    private boolean calculateSuccess = false;

    private RelativeLayout layout_current_order_info;
    private CircleImageView iv_current_order_head;
    private TextView tv_current_order_username;
    private TextView tv_current_order_start_location;
    private TextView tv_current_order_end_location;
    private ImageView iv_change_route;
    private LinearLayout layout_working;
    private LinearLayout layout_order_ing_list;
    private LinearLayout layout_order_can_receive_list;

    private DriverPickBasicBean mBasicBean;
    private DriverPickTownOrderBean mOrderBean, mChangeOrderBean;
    private UserBean mUserBean;
    private NewOrderComeDialog comeDialog;
    private LatLng location;

    public static String order_id;
    private boolean startOrder = false;

    private boolean isUserRoude;
    private boolean needLocation;

    private boolean isFirstLoad = true;
    //地图初始缩放大小
    private static final float initZoom = 18;

    private String fileNameGoods = "";

    public static MainActivity getInstance() {
        if (instance == null) {
            synchronized (MainActivity.class) {
                if (instance == null) {
                    instance = new MainActivity();
                }
            }
        }
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.setTranslucentStatus(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UpdateHelper.getInstance().init(getApplicationContext(), Color.parseColor("#fd8625"));
        UpdateHelper.getInstance().autoUpdate(getPackageName(), false, 3000);
        instance = this;
        initUserBean();
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        initMap();
        initView();
        initData();
        initListener();
        initLocation();
        if (SaveInfo.getInstance(this).isAuthentication()) {
            requestGetBasic();
        }
        requestUnreadMsgNum();
        startLocation();
    }

    private void initUserBean() {
        mUserBean = SaveInfo.getInstance(this).getLoginUserInfo();    //获取个人信息
        if (mUserBean == null) {
            openActivity(LoginActivity.class);
            finish();
            return;
        }
        String alias = mUserBean.getId();
        JPushInterface.init(getApplicationContext());
        JPushInterface.setAlias(getApplicationContext(), 0, alias);
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }
        setUpMap();
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(aMapNaviListener);
        aMap.setTrafficEnabled(true);// 显示实时交通状况
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
        mAMapNavi.setUseInnerVoice(true);   //设置使用内部语音播报
        initOther();
    }

    private void setUpMap() {
        // 如果要设置定位的默认状态，可以在此处进行设置
        locationStyle = new MyLocationStyle();
        locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);  // 设置定位的类型为跟随模式
        locationStyle.showMyLocation(true);
        locationStyle.strokeColor(ContextCompat.getColor(this, R.color.location_circle_stroke));
        locationStyle.radiusFillColor(ContextCompat.getColor(this, R.color.location_circle));
        locationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.arrow));
        aMap.setMyLocationStyle(locationStyle);
    }

    private void initOther() {
        aMap.showBuildings(true);       // 显示3D 楼块
        aMap.showMapText(true);         // 显示底图文字
        aMap.moveCamera(CameraUpdateFactory.zoomTo(initZoom));        // 设置地图初始缩放大小
        mUiSettings.setCompassEnabled(true);               // 设置地图默认的指南针是否显示
        mUiSettings.setMyLocationButtonEnabled(true);        // 是否显示默认的定位按钮
        mUiSettings.setZoomControlsEnabled(false);        // 设置地图默认的缩放按钮是否显示
        aMap.setMyLocationEnabled(true);               // 是否可触发定位并显示定位层
    }

    private void initView() {
        layout_title_root = findViewById(R.id.layout_title_root);
        layout_title_root.setPadding(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
        tv_title_main = findViewById(R.id.tv_title_main);
        iv_title_left = findViewById(R.id.iv_title_left);
        iv_title_right = findViewById(R.id.iv_title_right);
        btn_change_route = findViewById(R.id.btn_change_route);
        tv_title_main.setText(getString(R.string.app_name));
        iv_title_left.setImageResource(R.mipmap.icon_mine);
        iv_title_left.setVisibility(View.VISIBLE);
        iv_title_right.setImageResource(R.mipmap.icon_message);
        iv_title_right.setVisibility(View.VISIBLE);

        //初始化抽屉控件/
        drawerLayout = findViewById(R.id.id_drawerlayout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        layoutUser = findViewById(R.id.ll_userCenter);
        iv_portrait = findViewById(R.id.iv_myPortrait);
        iv_portrait.setBorderWidth(0);
        tv_username = findViewById(R.id.tv_myName);
        tv_score = findViewById(R.id.tv_score);
        tv_invite_code = findViewById(R.id.tv_invite_code);
        tv_invite_person_code = findViewById(R.id.tv_invite_person_code);
        layoutMyOrder = findViewById(R.id.ll_myOrder);
        layoutMyBalance = findViewById(R.id.ll_balance);
        layoutContactUs = findViewById(R.id.ll_callMe);
        layoutSetting = findViewById(R.id.ll_set);

        btn_main_work = findViewById(R.id.btn_main_work);
        setStartBtn();
        btn_main_order = findViewById(R.id.btn_main_order);
        btn_main_order.setVisibility(View.GONE);

        layout_current_order_info = findViewById(R.id.layout_current_order_info);
        layout_current_order_info.setVisibility(View.GONE);
        iv_current_order_head = findViewById(R.id.town_user_headimg_iv);
        tv_current_order_username = findViewById(R.id.user_name_tv);
        tv_current_order_start_location = findViewById(R.id.user_start_tv);
        tv_current_order_end_location = findViewById(R.id.user_end_tv);
        layout_tel = findViewById(R.id.layout_tel);
        layout_cancel_order = findViewById(R.id.layout_cancel_order);
        layout_change_order = findViewById(R.id.layout_change_order);
        iv_change_route = findViewById(R.id.iv_change_route);
        iv_change_route.setVisibility(View.GONE);
        layout_working = findViewById(R.id.layout_working);
        layout_order_ing_list = findViewById(R.id.layout_order_ing_list);
        layout_order_can_receive_list = findViewById(R.id.layout_order_can_receive_list);
    }

    private void setStartBtn() {
        if (mUserBean.getUser_check().equals("0") || mUserBean.getUser_check().equals("2")) {
            btn_main_work.setText("开始认证");
            btn_main_work.setVisibility(View.VISIBLE);
        } else if (mUserBean.getUser_check().equals("3")) {
            btn_main_work.setText("审核中");
            btn_main_work.setVisibility(View.VISIBLE);
        } else {
            btn_main_work.setText("开始接单");
            btn_main_work.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        PicUtil.displayImage(LoginUserInfoUtil.getLoginUserInfoBean(this).getHead_img(), iv_portrait);
        tv_username.setText(LoginUserInfoUtil.getLoginUserInfoBean(this).getName());
        String score = LoginUserInfoUtil.getLoginUserInfoBean(this).getCredit_points();
        String inviteCode = LoginUserInfoUtil.getLoginUserInfoBean(this).getInvitation_code2();
        String invitePersonCode = LoginUserInfoUtil.getLoginUserInfoBean(this).getInvitation_code2_up();
        tv_score.setText(TextUtils.isEmpty(score) ? "0" : score);
        tv_invite_code.setText(TextUtils.isEmpty(inviteCode) ? "无" : inviteCode);
        tv_invite_person_code.setText(TextUtils.isEmpty(invitePersonCode) ? "无" : invitePersonCode);
    }

    private void initListener() {
        btn_change_route.setOnClickListener(this);
        btn_main_work.setOnClickListener(this);
        btn_main_order.setOnClickListener(this);
        iv_title_right.setOnClickListener(this);
        iv_change_route.setOnClickListener(this);
        iv_title_left.setOnClickListener(this);
        layout_order_ing_list.setOnClickListener(this);
        layout_order_can_receive_list.setOnClickListener(this);
        layout_cancel_order.setOnClickListener(this);
        //抽屉菜单
        layoutMyOrder.setOnClickListener(mDrawClickListener);
        layoutMyBalance.setOnClickListener(mDrawClickListener);
        layoutContactUs.setOnClickListener(mDrawClickListener);
        layoutSetting.setOnClickListener(mDrawClickListener);
        layoutUser.setOnClickListener(mDrawClickListener);
    }

    /**
     * 初始化 定位
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        Log.d("====SHA1====", Utils.SHA1(context));
        String latitude = SaveInfo.getInstance(this).getLat();
        String longitude = SaveInfo.getInstance(this).getLng();
        if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude)) {
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(Double.parseDouble(SharedPreferencesUtil.getLat(context)),
                            Double.parseDouble(SharedPreferencesUtil.getLng(context))), initZoom));
        }
    }

    /**
     * 根据工作状态（working_status）显示布局
     */
    private void setWorkStatusLayoutStatus(String working_status) {
        switch (working_status) {
            //未开启接单
            case Constants.WORK_STATUS_RECEIVING_NOT:
                btn_main_work.setText("开始接单");
                btn_main_work.setVisibility(View.VISIBLE);
                btn_main_order.setVisibility(View.GONE);
                layout_current_order_info.setVisibility(View.GONE);
                iv_title_right.setVisibility(View.VISIBLE);
                iv_change_route.setVisibility(View.GONE);
                layout_working.setVisibility(View.VISIBLE);
                tv_title_main.setText(getString(R.string.app_name));
                startOrder = false;
                isUserRoude = false;
                initLocation();
                break;
            //正在接单
            case Constants.WORK_STATUS_RECEIVING:
                btn_main_work.setText("停止接单");
                btn_main_work.setVisibility(View.VISIBLE);
                btn_main_order.setVisibility(View.GONE);
                layout_current_order_info.setVisibility(View.GONE);
                iv_title_right.setVisibility(View.VISIBLE);
                iv_change_route.setVisibility(View.GONE);
                iv_change_route.setVisibility(View.GONE);
                layout_working.setVisibility(View.VISIBLE);
                tv_title_main.setText(getString(R.string.app_name));
                startOrder = false;
                isUserRoude = false;
                initLocation();
                break;
            //订单进行中
            default:
                layout_working.setVisibility(View.VISIBLE);
                btn_main_work.setVisibility(View.GONE);
                btn_main_order.setVisibility(View.VISIBLE);
                tv_title_main.setText(getString(R.string.app_name));
                break;
        }
    }


    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 高德地图 定位选项Options
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(true);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    private final AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            try {
                if (location == null) {
                    L.d("定位失败，loc is null");
                    return;
                }
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    MainActivity.this.location = new LatLng(location.getLatitude(), location.getLongitude());
                    SharedPreferencesUtil.saveLat(context, location.getLatitude() + "");
                    SharedPreferencesUtil.saveLng(context, location.getLongitude() + "");
                    if (mBasicBean != null && !mBasicBean.getWorking_status().equals(Constants.WORK_STATUS_RECEIVING_NOT)) {
                        requestUpdateCoordinate(location.getLongitude(), location.getLatitude());
                    }
                    if (aMap != null) {
                        //aMap.clear();
//                        addMarkersToMap();
                    }
                    if (needLocation) {
                        needLocation = false;
                        setMarker();
                    }
                    if (!startOrder) {
                    } else {
                    }
                }
                //解析定位结果，
//                L.d(TAG, "定位结果: " + Utils.getLocationInfo(location));
            } catch (Exception exception) {

            }
        }
    };

    /**
     * 抽屉菜单点击事件
     */
    private final View.OnClickListener mDrawClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    //个人信息
                    case R.id.ll_userCenter:
                        openActivity(PersonalDataActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    //我的订单
                    case R.id.ll_myOrder:
                        openActivity(MyOrderListActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    //我的余额
                    case R.id.ll_balance:
                        openActivity(MyBalanceActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    //联系我们
                    case R.id.ll_callMe:
                        requestContactUs();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    //设置
                    case R.id.ll_set:
                        openActivity(SetActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ToastUtil.showShortToast(MainActivity.this, "操作异常，请退出重试");
            }
        }
    };

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                //抽屉菜单
                case R.id.iv_title_left:
                    drawerLayout.openDrawer(GravityCompat.START);
                    break;
                //消息列表
                case R.id.iv_title_right:
                    openActivity(MessageInterfaceActivity.class);
                    break;
                //切换路线
                case R.id.iv_change_route:
                    if (mOrderBean != null) {
                        changeRoute(false);
                    }
                    break;
                //开始接单/停止接单
                case R.id.btn_main_work:
                    String text = ((Button) v).getText().toString();
                    switch (text) {
                        case "开始接单":
                            requestStartWorking();
                            playMedia(Constants.SOUND_START_GET_ORDER);
                            break;
                        case "停止接单":
                            requestOffDuty();
                            playMedia(Constants.SOUND_STOP_GET_ORDER);
                            break;
                        case "开始认证":
                            if (mUserBean.getUser_check().equals("0") || mUserBean.getUser_check().equals("2")) {
                                startActivity(new Intent(this, PersonalDataActivity.class));
                                drawerLayout.closeDrawer(GravityCompat.START);
                            }
                            break;
                    }
                    break;
                //订单状态主按钮
                case R.id.btn_main_order:
                    if (mBasicBean == null) {
                        ToastUtil.showShortToast(MainActivity.this, "basic bean为空");
                        return;
                    }
                    if (mOrderBean == null) {
                        ToastUtil.showShortToast(MainActivity.this, "order bean为空");
                        return;
                    }
                    String takerTypeId = mBasicBean.getTaker_type_id();
                    //送货订单
                    if (takerTypeId.equals(Constants.TAKER_TYPE_ID_SONG)) {
                        String status = mOrderBean.getOrder_status();
                        switch (status) {
                            //当前状态已接单-开始行程
                            case Constants.ORDER_STATUS_SONG_RECEIVE:
                                requestStartTrip();
                                break;
                            //当前状态前往始发地-导航
                            case Constants.ORDER_STATUS_SONG_TO_START_LOCATION:
                                RouteNaviActivity.startIntent(this, mOrderBean, takerTypeId);
                                break;
                            //当前状态到达出发地-提交物品图片
                            case Constants.ORDER_STATUS_SONG_ARRIVE_LOCATION:
                                clearRoute();
                                //TODO start UploadGoodsActivity
                                UploadGoodsActivity.startIntent(MainActivity.this, mOrderBean);
                                break;
                            //当前状态前往目的地-开始配送导航
                            case Constants.ORDER_STATUS_SONG_TO_END_LOCATION:
                                clearRoute();
                                RouteNaviActivity.startIntent(this, mOrderBean, takerTypeId);
                                break;
                            //当前状态验证货物码-提交提货码(终点？)
                            case Constants.ORDER_STATUS_SONG_AUTH_GOODS_CODE:
                                clearRoute();
                                Intent intent = new Intent(context, SubmitCodeActivity.class);
                                intent.putExtra("orderId", mOrderBean.getOrder_small_id());
                                startActivity(intent);
                                break;
                        }
                    } else {
                        //代驾订单
                        String status = mOrderBean.getStatus();
                        switch (status) {
                            //已接单-开始行程
                            case Constants.STATUS_DRIVE_RECEIVE:
                                requestStartTrip();
                                break;
                            //前往始发地
                            case Constants.STATUS_DRIVE_TO_START_LOCATION:
                                RouteNaviActivity.startIntent(this, mOrderBean, takerTypeId);
                                break;
                            //前往目的地
                            case Constants.STATUS_DRIVE_GET_CAR:
                            case Constants.STATUS_DRIVE_TO_END_LOCATION:
                                clearRoute();
                                RouteNaviActivity.startIntent(this, mOrderBean, takerTypeId);
                                break;
                        }
                    }
                    break;
                //取消订单
                case R.id.layout_cancel_order:
                    if (mBasicBean == null) {
                        ToastUtil.showShortToast(MainActivity.this, "basic bean为空");
                        return;
                    }
                    if (mOrderBean == null) {
                        ToastUtil.showShortToast(MainActivity.this, "order bean为空");
                        return;
                    }
                    AlertDialog dialogConfirm = DialogUtils.showConfirmDialog(this, "确定取消当前订单？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            requestCancelOrder();
                        }
                    });
                    DialogUtils.show(this, dialogConfirm);
                    break;
                //可接订单列表
                case R.id.layout_order_can_receive_list:
                    openActivity(OrderListCanReceiveActivity.class);
                    break;
                //进行中订单列表
                case R.id.layout_order_ing_list:
                    openActivity(OrderListIngActivity.class);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ToastUtil.showShortToast(MainActivity.this, "操作异常，请退出重试");
        }
    }

    /**
     * 设置标记
     */
    private void setMarker() {
        if (mOrderBean != null) {
            String status = mOrderBean.getOrder_status();
            if (status.equals("3") || status.equals("4") || status.equals("5") || status.equals("6")) {
                if (location != null) {
                    // 构建Marker图标 添加司机图标
                    aMap.addMarker(new MarkerOptions().position(location)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_position_sq3)));
                    LatLonPoint mStartPoint = new LatLonPoint(location.latitude, location.longitude);
                    if (!isUserRoude) {
                        String startLat = mOrderBean.getStart_latitude();
                        String startLng = mOrderBean.getStart_longitude();
                        LatLonPoint mEndPoint = new LatLonPoint(
                                TextUtils.isEmpty(startLat) ? location.latitude : Double.parseDouble(startLat),
                                TextUtils.isEmpty(startLng) ? location.longitude : Double.parseDouble(startLng)
                        );
                        searchRouteResult(mStartPoint, mEndPoint);
                    }
                } else {
                    needLocation = true;
                }
            } else if (status.equals("7")) {
                if (location != null) {
                    // 构建Marker图标 添加司机图标
                    aMap.addMarker(new MarkerOptions().position(location)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_position_sq3)));
                    LatLonPoint mStartPoint = new LatLonPoint(location.latitude, location.longitude);
                    if (!isUserRoude) {
                        LatLonPoint mEndPoint = new LatLonPoint(Double.parseDouble(mOrderBean.getEnd_latitude()),
                                Double.parseDouble(mOrderBean.getEnd_longitude()));
                        searchRouteResult(mStartPoint, mEndPoint);
                    }
                } else {
                    needLocation = true;
                }
            }
            setOrderStatusButton();
        }
    }

    private void setOrderStatusButton() {
        btn_main_work.setVisibility(View.GONE);
        btn_main_order.setVisibility(View.VISIBLE);
        layout_cancel_order.setVisibility(View.VISIBLE);
        if (mBasicBean.getTaker_type_id().equals(Constants.TAKER_TYPE_ID_SONG)) {
            String status = mOrderBean.getOrder_status();
            switch (status) {
                case Constants.ORDER_STATUS_SONG_RECEIVE:
                case Constants.ORDER_STATUS_SONG_TO_START_LOCATION:
                    btn_main_order.setText("导航到起点");
                    iv_change_route.setVisibility(View.VISIBLE);
                    break;
                case Constants.ORDER_STATUS_SONG_ARRIVE_LOCATION:
                    btn_main_order.setText("上传货物照片");
                    iv_change_route.setVisibility(View.GONE);
                    break;
                case Constants.ORDER_STATUS_SONG_AUTH_GOODS_CODE:
                    btn_main_order.setText("完成订单");
                    iv_change_route.setVisibility(View.GONE);
                    break;
                case Constants.ORDER_STATUS_SONG_TO_END_LOCATION:
                    btn_main_order.setText("导航到终点");
                    iv_change_route.setVisibility(View.VISIBLE);
                    break;
                default:
                    layout_cancel_order.setVisibility(View.GONE);
                    iv_change_route.setVisibility(View.GONE);
                    break;
            }
        } else {
            String status = mOrderBean.getStatus();
            switch (status) {
                case Constants.STATUS_DRIVE_RECEIVE:
                case Constants.STATUS_DRIVE_TO_START_LOCATION:
                    btn_main_order.setText("导航到起点");
                    iv_change_route.setVisibility(View.VISIBLE);
                    break;
                case Constants.STATUS_DRIVE_GET_CAR:
                case Constants.STATUS_DRIVE_TO_END_LOCATION:
                    btn_main_order.setText("导航到终点");
                    iv_change_route.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    /**
     * 显示订单信息
     */
    private void setOrderInfoLayout(String taker_type_id) {
        String currentName = "";
        String currentPhone = "";
        if (taker_type_id.equals(Constants.TAKER_TYPE_ID_SONG)) {
            //送货订单
            String status = mOrderBean.getOrder_status();
            if (!status.equals("8")) {
                layout_cancel_order.setVisibility(View.VISIBLE);
            } else
                layout_cancel_order.setVisibility(View.GONE);
            if (status.equals(Constants.ORDER_STATUS_SONG_TO_END_LOCATION) ||
                    status.equals(Constants.ORDER_STATUS_SONG_FINISH) ||
                    status.equals(Constants.ORDER_STATUS_SONG_AUTH_GOODS_CODE)) {
                currentName = mOrderBean.getUser_name();
                currentPhone = mOrderBean.getUser_account();
            } else {
                currentName = mOrderBean.getName();
                currentPhone = mOrderBean.getAccount();
            }
        } else {
            //代驾订单
            String status = mOrderBean.getStatus();
            if (!status.equals("6") && !status.equals("7")) {
                layout_cancel_order.setVisibility(View.VISIBLE);
            } else
                layout_cancel_order.setVisibility(View.GONE);
            currentName = mOrderBean.getName();
            currentPhone = mOrderBean.getAccount();
        }
        layout_current_order_info.setVisibility(View.VISIBLE);    //乘客布局
        iv_title_right.setVisibility(View.GONE);            //消息图标
        PicUtil.displayImage(mOrderBean.getHead_img(), iv_current_order_head);
        tv_current_order_username.setText(currentName);
        tv_current_order_start_location.setText(TextUtils.isEmpty(mOrderBean.getStart_location()) ? "附近地点" : mOrderBean.getStart_location());
        tv_current_order_end_location.setText(mOrderBean.getEnd_location());
        final String finalCurrentPhone = currentPhone;
        layout_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + finalCurrentPhone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        layout_change_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(OrderListIngActivity.class);
            }
        });
        //根据orderType判断订单类型显示标题
        switch (mOrderBean.getOrder_type()) {
            case Constants.ORDER_TYPE_ZHUAN:
                tv_title_main.setText("专车送订单");
                break;
            case Constants.ORDER_TYPE_SHUN:
                tv_title_main.setText("顺路送订单");
                break;
            case Constants.ORDER_TYPE_BUY:
                tv_title_main.setText("代买订单");
                break;
            case Constants.ORDER_TYPE_DRIVE:
                tv_title_main.setText("代驾订单");
                break;
            default:
                tv_title_main.setText(getString(R.string.app_name));
                break;
        }
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(LatLonPoint mStartPoint, LatLonPoint mEndPoint) {
        if (location == null) {
            Toast.makeText(context, "定位中，稍后再试...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mStartPoint == null) {
            Toast.makeText(context, "未设置起点，请重试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mEndPoint == null) {
            Toast.makeText(context, "未设置终点，请重试", Toast.LENGTH_SHORT).show();
            return;
        }
        clearRoute();
        showLoadingDialog("路径规划中...");
        int strategyFlag = 0;
        try {
            strategyFlag = mAMapNavi.strategyConvert(true, true, false, false, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //起点
        NaviLatLng startLatlng = new NaviLatLng(mStartPoint.getLatitude(), mStartPoint.getLongitude());
        startList.clear();
        startList.add(startLatlng);
        //终点
        NaviLatLng endLatlng = new NaviLatLng(mEndPoint.getLatitude(), mEndPoint.getLongitude());
        endList.clear();
        endList.add(endLatlng);

        if (strategyFlag >= 0) {
            AMapCarInfo carInfo = new AMapCarInfo();
            //TODO 设置车牌
            //carInfo.setCarNumber(carNumber);
            //设置车牌是否参与限行算路
            carInfo.setRestriction(true);
            mAMapNavi.setCarInfo(carInfo);
            mAMapNavi.calculateDriveRoute(startList, endList, wayList, strategyFlag);
        }
    }

    /**
     * 开始行程(第一次)
     */
    private void requestStartTrip() {
        showLoadingDialog("开始订单...");
        //开始导航
        DriverPickBasicBean bean = SaveInfo.getInstance(this).getBasicInfo();
        final String taker_type_id = bean.getTaker_type_id();
        String order_id = bean.getOrder_id();
        DriverPickService.start_trip(this, taker_type_id, order_id, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                dismissLoadingDialog();
                initData();
                RouteNaviActivity.startIntent(MainActivity.this, mOrderBean, taker_type_id);
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(context, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(context, errorMsg);

            }
        }));
    }

    /**
     * 取消订单
     */
    private void requestCancelOrder() {
        String taker_type_id = mBasicBean.getTaker_type_id();
        String order_id = mBasicBean.getOrder_id();
        showLoadingDialog("正在取消订单...");
        DriverPickService.cancel(context, taker_type_id, order_id, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(context, "订单取消成功");
                layout_current_order_info.setVisibility(View.GONE);
                tv_title_main.setText(getString(R.string.app_name));
                clearRoute();
//                showLoadingDialog("查询进行中订单...");
//                requestGetBasic();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(context, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(context, errorMsg);
            }
        }));
    }

    /**
     * 更新当前位置
     */
    private void requestUpdateCoordinate(double lng, double lat) {
        if (isRequestUpdateCoordinate) return;
        String user_id = mUserBean.getId();
        DriverPickService.update_coordinate(this, user_id, lng + "", lat + "", new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                isRequestUpdateCoordinate = false;
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                isRequestUpdateCoordinate = false;
            }

            @Override
            public void onHttpError(String errorMsg) {
                isRequestUpdateCoordinate = false;
            }
        }));
    }

    /**
     * 获取联系我们信息
     */
    private void requestContactUs() {
        MemberService.contact_us(this, "2", new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + result));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                ToastUtil.showShortToast(context, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                ToastUtil.showShortToast(context, errorMsg);
            }
        }));
    }

    /**
     * 司机接单信息
     */
    public void requestGetBasic() {
        showLoadingDialog("正在加载...", false);
        String user_id = mUserBean.getId();
        DriverPickService.get_basic(this, user_id, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "requestGetBasic: " + result);
                mBasicBean = new Gson().fromJson(result, DriverPickBasicBean.class);
                setWorkStatusLayoutStatus(mBasicBean.getWorking_status());   //根据工作状态显示布局
                //进行中订单列表-送
                List<DriverPickBasicBean.OrderBasicBean> songList = mBasicBean.getOrder_traffic_list();
                List<DriverPickBasicBean.OrderBasicBean> driveList = mBasicBean.getOrder_town_list();
                if (mChangeOrderBean != null) {
                    //如果当前订单不为空，查询当前进行订单信息
                    requestTownGetOrder(mChangeOrderBean.getOrder_small_id(), mChangeOrderBean.getTaker_type_id());
                } else if (mOrderBean != null) {
                    requestTownGetOrder(mOrderBean.getOrder_small_id(), mOrderBean.getTaker_type_id());
                } else {
                    //为空，随便找一个默认的当作当前订单
                    if (songList != null && songList.size() > 0) {
                        startOrder = true;
                        //进行中的订单-送
                        String id = songList.get(0).getId();
                        requestTownGetOrder(id, Constants.TAKER_TYPE_ID_SONG);
                        startLocation();
                    } else if (driveList != null && driveList.size() > 0) {
                        startOrder = true;
                        //进行中的订单-代驾
                        String id = driveList.get(0).getId();
                        requestTownGetOrder(id, Constants.TAKER_TYPE_ID_DRIVE);
                        startLocation();
                    } else {
                        //无进行中订单-送
                        dismissLoadingDialog();
                        if (mBasicBean.getWorking_status().equals(Constants.WORK_STATUS_IN_ORDER)) {
                            setWorkStatusLayoutStatus(Constants.WORK_STATUS_RECEIVING);   //根据工作状态显示布局
                        }
                    }
                }
                SaveInfo.getInstance(context).setBasicInfo(mBasicBean);
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(context, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(context, errorMsg);
            }
        }));
    }

    /**
     * 进行中的订单详情
     *
     * @param order_id      订单id
     * @param taker_type_id 订单大类id
     */
    private void requestTownGetOrder(final String order_id, final String taker_type_id) {
        MainActivity.order_id = order_id;
        DriverPickService.town_get_order(this, order_id, taker_type_id, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "requestTownGetOrder: " + result);
                mOrderBean = null;
                mOrderBean = new Gson().fromJson(result, DriverPickTownOrderBean.class);   //当前进行中订单信息
                mChangeOrderBean = null;
                if (mOrderBean != null) {
                    mOrderBean.setTaker_type_id(taker_type_id);
                    mBasicBean.setOrder_id(order_id);
                    mBasicBean.setTaker_type_id(taker_type_id);
                    SaveInfo.getInstance(context).setBasicInfo(mBasicBean);
                    setMarker();
                    setOrderInfoLayout(taker_type_id);
                }
                dismissLoadingDialog();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(context, "当前订单信息获取失败，请重试");
                mOrderBean = null;
                mChangeOrderBean = null;
            }

            @Override
            public void onHttpError(String errorMsg) {
                dismissLoadingDialog();
                mOrderBean = null;
                mChangeOrderBean = null;
            }
        }));
    }

    /**
     * 未读消息
     */
    private void requestUnreadMsgNum() {
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();
        MessageService.unread_num(this, "2", user_id, new AsyncHttpHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(responseString);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(responseString);
                    if (jsonObject.optBoolean("result")) {
                        iv_title_right.setImageResource(R.mipmap.icon_message_new);
                    } else {
                        iv_title_right.setImageResource(R.mipmap.icon_message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 上班/开始接单
     */
    private void requestStartWorking() {
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();
        showLoadingDialog("开始接单...");
        DriverPickService.working(this, user_id, "2", location.longitude + "", location.latitude + "",
                mBasicBean.getCar_type_id(), null, null, null,
                new TextHttpResCallback(new OnHttpCallbackListener() {
                    @Override
                    public void onHttpSuccess(String result, String msg) {
                        ToastUtil.showShortToast(context, msg);
                        btn_main_work.setText("停止接单");
                        iv_change_route.setVisibility(View.GONE);
                        requestGetBasic();
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onHttpFailure(int status, String msg) {
                        dismissLoadingDialog();
                        ToastUtil.showShortToast(context, msg);
                    }

                    @Override
                    public void onHttpError(String errorMsg) {
                        dismissLoadingDialog();
                        ToastUtil.showShortToast(context, errorMsg);
                    }
                }));
    }

    /**
     * 下班/停止接单
     */
    private void requestOffDuty() {
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();
        showLoadingDialog("停止接单...");
        DriverPickService.off_duty(this, user_id, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                ToastUtil.showShortToast(context, msg);
                requestGetBasic();
                dismissLoadingDialog();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(context, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(context, errorMsg);
            }
        }));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
        if (LoginUserInfoUtil.isAuthentication(context)) {
            requestGetBasic();
        }
    }

    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mapView.onPause();
        super.onPause();
        if (isFirstLoad) {
            isFirstLoad = false;
        }
    }

    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mapView.onResume();
        super.onResume();
        if (isFirstLoad) {
            playMedia(Constants.SOUND_WELCOME);
        }
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        if (null != locationClient) {
            locationClient.onDestroy();
        }
        startList.clear();
        wayList.clear();
        endList.clear();
        routeOverlays.clear();
        mapView.onDestroy();
        /**
         * 当前页面只是展示地图，activity销毁后不需要再回调导航的状态
         */
        mAMapNavi.removeAMapNaviListener(aMapNaviListener);
        mAMapNavi.destroy();
        DialogUtils.dismiss(comeDialog);
        comeDialog = null;
        super.onDestroy();
    }

    @Subscriber(tag = "MainActivityAuthentication")
    private void MainActivityAuthentication(String tag) {
        if (mUserBean != null) {
            mUserBean.setUser_check("3");
            SaveInfo.getInstance(this).setLoginUserInfo(mUserBean);
            setStartBtn();
        }
    }

    @Subscriber(tag = "MainActivityGetBase")
    private void MainActivityGetBasic(String atge) {
        requestGetBasic();
    }

    /**
     * EventBus刷新消息列表
     */
    @Subscriber(tag = EventBusMsg.EVENT_UNREAD_MSG)
    private void MainActivityUnreadNum(String tag) {
        requestUnreadMsgNum();
    }

    /**
     * EventBus新订单推送消息
     */
    @Subscriber(tag = EventBusMsg.EVENT_PUSH)
    private void eventNewOrderPush(NewOrder bean) {
        if (bean != null) {
            Text2Speech.speech(context, "您有新的订单", false);
            comeDialog = new NewOrderComeDialog(this, bean);
            DialogUtils.show(this, comeDialog);
        } else {
            startOrder = false;
            initLocation();
        }
    }

    /**
     * EventBus确认接单后
     *
     * @param strings 订单id跟taker_type_id
     */
    @Subscriber(tag = EventBusMsg.EVENT_RECEIVE_ORDER)
    private void MainActivityGetOrder(String[] strings) {
        setWorkStatusLayoutStatus(Constants.WORK_STATUS_IN_ORDER);
        startOrder = true;
        //查询该订单信息
        requestTownGetOrder(strings[0], strings[1]);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playMedia(Constants.SOUND_ORDER_START);
            }
        });
    }

    /**
     * EventBus切换当前进行中订单
     *
     * @param strings 订单id跟taker_type_id
     */
    @Subscriber(tag = EventBusMsg.EVENT_CHANGE_ORDER)
    private void eventChangeOrder(String[] strings) {
        setWorkStatusLayoutStatus(Constants.WORK_STATUS_IN_ORDER);
        startOrder = true;
        //查询该订单信息
        mChangeOrderBean = new DriverPickTownOrderBean();
        mChangeOrderBean.setOrder_small_id(strings[0]);
        mChangeOrderBean.setTaker_type_id(strings[1]);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playMedia(Constants.SOUND_ORDER_START);
            }
        });
    }

    /**
     * EventBus代驾订单完成
     *
     * @param str 没啥用
     */
    @Subscriber(tag = EventBusMsg.EVENT_GET_OFF_CAR)
    private void eventGetOffCar(String str) {
        mOrderBean = null;
        mChangeOrderBean = null;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playMedia(Constants.SOUND_ORDER_FINISH);
            }
        });
    }

    /**
     * EventBus跑腿订单完成
     *
     * @param str 没啥用
     */
    @Subscriber(tag = EventBusMsg.EVENT_UPLOAD_CODE)
    private void eventUploadCode(String str) {
        mOrderBean = null;
        mChangeOrderBean = null;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playMedia(Constants.SOUND_ORDER_FINISH);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * 播放铃声
     */
    private void playMedia(final String fileName) {
        L.i("+++++++" + fileName);
        SoundPoolHelper.getInstance().play(fileName, false);
    }

    /**
     * 计算路径回调
     */
    private final SimpleAMapNaviListener aMapNaviListener = new SimpleAMapNaviListener() {
        @Override
        public void calculateRouteSuccess(int[] ints) {
            aMap.setMyLocationStyle(locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW));
            dismissLoadingDialog();
            routeOverlays.clear();
            HashMap<Integer, AMapNaviPath> paths = mAMapNavi.getNaviPaths();
            for (int anInt : ints) {
                AMapNaviPath path = paths.get(anInt);
                if (path != null) {
                    drawRoutes(anInt, path);
                }
            }
            changeRoute(true);
            iv_change_route.setVisibility(View.VISIBLE);
        }

        @Override
        public void calculateRouteFailure(AMapCalcRouteResult result) {
            dismissLoadingDialog();
            calculateSuccess = false;
            ToastUtil.showShortToast(MainActivity.this, "计算路线失败，errorcode＝" + result);
        }
    };

    /**
     * 画路线
     */
    private void drawRoutes(int routeId, AMapNaviPath path) {
        calculateSuccess = true;
        aMap.moveCamera(CameraUpdateFactory.changeTilt(0));
        RouteOverLay routeOverLay = new RouteOverLay(aMap, path, this);
        routeOverLay.setTrafficLine(false);
        routeOverLay.addToMap();
        routeOverlays.put(routeId, routeOverLay);
    }

    /**
     * 切换路线
     */
    public void changeRoute(boolean showDefault) {
        if (!calculateSuccess) {
            ToastUtil.showShortToast(this, "请先算路");
            return;
        }
        if (routeOverlays.size() == 0) {
            ToastUtil.showShortToast(this, "当前无路径信息，无法切换");
            return;
        }
        //计算出来的路径只有一条
        if (routeOverlays.size() == 1) {
            //必须告诉AMapNavi 你最后选择的哪条路
            mAMapNavi.selectRouteId(routeOverlays.keyAt(0));
            ToastUtil.showShortToast(this, "导航距离:" + (mAMapNavi.getNaviPath()).getAllLength() + "m"
                    + "\n" + "导航时间:" + (mAMapNavi.getNaviPath()).getAllTime() + "s");
            return;
        }
        //如果切换的index大于总长度了，从头开始切
        if (routeIndex >= routeOverlays.size()) {
            routeIndex = 0;
        }
        //第一次 显示默认的 就显示第一条路线
        if (showDefault) {
            routeIndex = 0;
        }
        int routeID = routeOverlays.keyAt(routeIndex);
        //突出选择的那条路
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            routeOverlays.get(key).setTransparency(0.4f);
        }
        RouteOverLay routeOverlay = routeOverlays.get(routeID);
        if (routeOverlay != null) {
            routeOverlay.setTransparency(1);
            //把用户选择的那条路的权值弄高，使路线高亮显示的同时，重合路段不会变的透明**/
            routeOverlay.setZindex(zindex++);
        }
        //必须告诉AMapNavi 你最后选择的哪条路
        mAMapNavi.selectRouteId(routeID);
        //Toast.makeText(this, "路线标签:" + mAMapNavi.getNaviPath().getLabels(), Toast.LENGTH_SHORT).show();
        routeIndex++;
        /**选完路径后判断路线是否是限行路线**/
        /*AMapRestrictionInfo info = mAMapNavi.getNaviPath().getRestrictionInfo();
        if (!TextUtils.isEmpty(info.getRestrictionTitle())) {
            Toast.makeText(this, info.getRestrictionTitle(), Toast.LENGTH_SHORT).show();
        }*/
    }


    /**
     * 清除当前地图上算好的路线
     */
    private void clearRoute() {
        aMap.clear();
        initMap();
        for (int i = 0; i < routeOverlays.size(); i++) {
            RouteOverLay routeOverlay = routeOverlays.valueAt(i);
            routeOverlay.removeFromMap();
        }
        routeOverlays.clear();
    }
}
