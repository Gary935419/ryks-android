package com.inwhoop.qscx.qscxsj.navi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.constants.EventBusMsg;
import com.inwhoop.qscx.qscxsj.entitys.DriverPickTownOrderBean;
import com.inwhoop.qscx.qscxsj.entitys.TownOrderBean;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LogUtils;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.utils.statusbar.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import cz.msebera.android.httpclient.Header;

public class RouteNaviActivity extends BaseActivity implements AMapNaviListener, AMapNaviViewListener {

    private static final String TAG = RouteNaviActivity.class.getSimpleName();

    public static final String ORDER = "order";
    public static final String TAKER_TYPE_ID = "taker_type_id";

    private DriverPickTownOrderBean orderBean;
    private String orderStatus;
    private String takerTypeId;

    AMapNaviView mAMapNaviView;
    AMapNavi mAMapNavi;
    private Button btn_navi;
    //true 为模拟导航
    private boolean isTest = false;

    /**
     * 跳转方法
     */
    public static void startIntent(Context context, DriverPickTownOrderBean bean, String takerTypeId) {
        if (bean == null || TextUtils.isEmpty(takerTypeId)) {
            ToastUtil.showShortToast(context, "Intent信息为空，请退出重试");
            return;
        }
        Intent intent = new Intent(context, RouteNaviActivity.class);
        intent.putExtra(ORDER, bean);
        intent.putExtra(TAKER_TYPE_ID, takerTypeId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_navi);
        //intent
        orderBean = (DriverPickTownOrderBean) getIntent().getSerializableExtra(ORDER);
        takerTypeId = getIntent().getStringExtra(TAKER_TYPE_ID);
        //view
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        btn_navi = (Button) findViewById(R.id.btn_navi);
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.setUseInnerVoice(true);
        mAMapNavi.setEmulatorNaviSpeed(60);
        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setScreenAlwaysBright(true);
        mAMapNaviView.setViewOptions(options);
        if (!isTest) {
            mAMapNavi.startNavi(NaviType.GPS);
        } else {
            mAMapNavi.startNavi(NaviType.EMULATOR);
        }
        initListener();
        setButtonText();
    }

    /**
     * 设置button文字
     */
    private void setButtonText() {
        switch (takerTypeId) {
            case Constants.TAKER_TYPE_ID_SONG:
                orderStatus = orderBean.getOrder_status();
                if (orderStatus.equals(Constants.ORDER_STATUS_SONG_RECEIVE)
                        || orderStatus.equals(Constants.ORDER_STATUS_SONG_TO_START_LOCATION)) {
                    btn_navi.setText("到达起点");
                } else if (orderStatus.equals(Constants.ORDER_STATUS_SONG_TO_END_LOCATION)) {
                    btn_navi.setText("到达终点");
                }
                break;
            case Constants.TAKER_TYPE_ID_DRIVE:
                orderStatus = orderBean.getStatus();
                if (orderStatus.equals(Constants.STATUS_DRIVE_TO_START_LOCATION)) {
                    btn_navi.setText("到达起点");
                } else if (orderStatus.equals(Constants.STATUS_DRIVE_TO_END_LOCATION)
                        || orderStatus.equals(Constants.STATUS_DRIVE_GET_CAR)) {
                    btn_navi.setText("到达终点");
                }
                break;
        }
    }

    private void initListener() {
        btn_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (takerTypeId.equals(Constants.TAKER_TYPE_ID_SONG)) {
                    if (orderStatus.equals(Constants.ORDER_STATUS_SONG_RECEIVE)
                            || orderStatus.equals(Constants.ORDER_STATUS_SONG_TO_START_LOCATION)) {
                        //到达始发地
                        requestChangeOrderStatus(Constants.ORDER_STATUS_SONG_ARRIVE_LOCATION);
                    } else if (orderStatus.equals(Constants.ORDER_STATUS_SONG_TO_END_LOCATION)) {
                        //验证提货码（到达目的地）
                        requestChangeOrderStatus(Constants.ORDER_STATUS_SONG_AUTH_GOODS_CODE);
                    }
                } else {
                    if (orderStatus.equals(Constants.STATUS_DRIVE_TO_START_LOCATION)) {
                        //上车
                        requestGetOnCar();
                    } else if (orderStatus.equals(Constants.STATUS_DRIVE_TO_END_LOCATION)
                            || orderStatus.equals(Constants.STATUS_DRIVE_GET_CAR)) {
                        //下车
                        requestGetOffCar();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();
    }

    /**
     * 改变订单状态（到达目的地）
     */
    private void requestChangeOrderStatus(String orderStatus) {
        if (orderStatus.equals(Constants.ORDER_STATUS_SONG_RECEIVE)
                || orderStatus.equals(Constants.ORDER_STATUS_SONG_TO_START_LOCATION)) {
            //到达始发地
            showLoadingDialog("到达始发地...");
        } else if (orderStatus.equals(Constants.ORDER_STATUS_SONG_TO_END_LOCATION)) {
            //验证提货码（到达目的地）
            showLoadingDialog("到达目的地...");
        }
        String order_id = LoginUserInfoUtil.getDriverPickBasicBean(this).getOrder_id();
        DriverOrderService.changeOrderStatus(this, order_id, orderStatus, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                dismissLoadingDialog();
                L.i(TAG, "requestChangeOrderStatus: " + result);
                finish();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(RouteNaviActivity.this, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(RouteNaviActivity.this, errorMsg);
            }
        }));
    }

    /**
     * 上车
     */
    private void requestGetOnCar() {
        showLoadingDialog("到达始发地...");
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();
        String order_id = orderBean.getOrder_small_id();
        DriverOrderService.line_on_car(this, user_id, order_id, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                dismissLoadingDialog();
                L.i(TAG, "requestGetOnCar: " + result);
                finish();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(RouteNaviActivity.this, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(RouteNaviActivity.this, errorMsg);
            }
        }));
    }

    /**
     * 下车
     */
    private void requestGetOffCar() {
        showLoadingDialog("到达目的地...");
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();
        String order_id = orderBean.getOrder_small_id();
        DriverOrderService.line_on_car_ok(this, user_id, order_id, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                dismissLoadingDialog();
                ToastUtil.showLongToast(context, "订单已完成，即将查询其它进行中订单");
                EventBus.getDefault().post("", EventBusMsg.EVENT_GET_OFF_CAR);
                L.i(TAG, "requestGetOffCar: " + result);
                finish();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(RouteNaviActivity.this, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(RouteNaviActivity.this, errorMsg);
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
        mAMapNavi.stopNavi();
        /**
         * 当前页面不销毁AmapNavi对象。
         * 因为可能会返回到RestRouteShowActivity页面再次进行路线选择，然后再次进来导航。
         * 如果销毁了就没办法在上一个页面进行选择路线了。
         * 但是AmapNavi对象始终销毁，那我们就需要在上一个页面用户回退时候销毁了。
         */
        mAMapNavi.removeAMapNaviListener(this);
//		mAMapNavi.destroy();
    }

    @Override
    public void onInitNaviFailure() {
        Toast.makeText(this, "init navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess() {
    }

    @Override
    public void onStartNavi(int type) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {

    }

    @Override
    public void onGetNavigationText(int type, String text) {
        LogUtils.d("播报:" + text);
    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {
    }

    @Override
    public void onArriveDestination() {
        LogUtils.d("到达终点");
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int wayID) {

    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
    }

    @Override
    public void onNaviSetting() {
    }

    @Override
    public void onNaviMapMode(int isLock) {

    }

    @Override
    public void onNaviCancel() {
        finish();
    }

    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {

    }

    @Override
    public void onScanViewButtonClick() {
    }

    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
    }

    @Override
    public void hideCross() {
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onLockMap(boolean isLock) {
    }

    @Override
    public void onNaviViewLoaded() {
    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public void onNaviViewShowMode(int i) {

    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }


    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }

    @Override
    public void onGpsSignalWeak(boolean b) {

    }
}
