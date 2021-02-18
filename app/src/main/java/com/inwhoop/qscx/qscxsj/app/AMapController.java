package com.inwhoop.qscx.qscxsj.app;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.SparseArray;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.MyTrafficStyle;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapCarInfo;
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
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.services.core.LatLonPoint;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.constants.EventBusMsg;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.Utils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AMapController {

    private static final String TAG = AMapController.class.getSimpleName();

    private Context context;
    private MapView mapView;
    private AMap aMap;

    private UiSettings uiSettings;
    private MyLocationStyle locationStyle;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private AMapNavi mAMapNavi = null;

    private MarkerOptions markerOption;

    private LatLng location;

    private final SparseArray<RouteOverLay> routeOverlays = new SparseArray<>();    //保存当前算好的路线

    private List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
    private List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
    private List<NaviLatLng> wayList = new ArrayList<NaviLatLng>();

    public AMapController(Context context, MapView mapView) {
        this.context = context;
        this.mapView = mapView;
        initLocation();
    }

    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(context);
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            // 如果要设置定位的默认状态，可以在此处进行设置
            locationStyle = new MyLocationStyle();
            uiSettings = aMap.getUiSettings();
            aMap.showBuildings(true);            // 显示3D 楼块
            aMap.showMapText(true);         // 显示底图文字
            aMap.moveCamera(CameraUpdateFactory.zoomTo(18));         // 设置地图初始缩放大小
            aMap.setMyLocationStyle(locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE));       // 设置定位的类型为 跟随模式
            uiSettings.setCompassEnabled(true);       // 设置地图默认的指南针是否显示
            uiSettings.setMyLocationButtonEnabled(true);         // 是否显示默认的定位按钮
            uiSettings.setZoomControlsEnabled(false);         // 设置地图默认的缩放按钮是否显示
            // 是否可触发定位并显示定位层
            aMap.setMyLocationEnabled(true);
            aMap.setTrafficEnabled(true);// 显示实时交通状况
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
            aMap.setMyLocationStyle(locationStyle);
        }
        mAMapNavi = AMapNavi.getInstance(context);
        mAMapNavi.addAMapNaviListener(aMapNaviListener);
        mAMapNavi.setUseInnerVoice(true);
        //自定义实时交通信息的颜色样式
        MyTrafficStyle style = new MyTrafficStyle();
        style.setSeriousCongestedColor(0xff92000a);
        style.setCongestedColor(0xffea0312);
        style.setSlowColor(0xffff7508);
        style.setSmoothColor(0xff00a209);
        aMap.setMyTrafficStyle(style);
        //地图添加marker
        markerOption = new MarkerOptions();
        markerOption.position(location);
        markerOption.draggable(true);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.arrow)));
        // 将Marker设置为贴地显示，可以双指下拉看效果
        markerOption.setFlat(true);
    }

    public void startLocation() {
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
        /*RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        //TODO 车牌号
        //fromAndTo.setPlateNumber("A6BN05");
        //fromAndTo.setPlateProvince("京");

        // 第一个参数表示路径规划的起点和终点，第二个参数表示计算路径的模式，第三个参数表示途经点，第四个参数货车大小 必填
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
        //异步查询
        mRouteSearch.calculateDriveRouteAsyn(query);*/
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
     * 清除当前地图上算好的路线
     */
    private void clearRoute() {
        aMap.clear();
        for (int i = 0; i < routeOverlays.size(); i++) {
            RouteOverLay routeOverlay = routeOverlays.valueAt(i);
            routeOverlay.removeFromMap();
        }
        routeOverlays.clear();
    }

    public AMapNavi getAMapNavi() {
        return mAMapNavi;
    }

    public void onDestroy() {
        if (null != locationClient) {
            locationClient.onDestroy();
        }
        startList.clear();
        wayList.clear();
        endList.clear();
        routeOverlays.clear();
        mAMapNavi.removeAMapNaviListener(aMapNaviListener);
        mAMapNavi.destroy();
    }

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }

    /**
     * 定位监听
     */
    private final AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    EventBus.getDefault().post(location, EventBusMsg.EVENT_LOCATION_SUCCESS);
                }
                //解析定位结果，
                L.d(TAG, "定位结果: " + Utils.getLocationInfo(location));
            } else {
                L.d("定位失败，loc is null");
            }
        }
    };

    private final AMapNaviListener aMapNaviListener = new AMapNaviListener() {

        @Override
        public void onCalculateRouteSuccess(int[] ints) {
            aMap.setMyLocationStyle(locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW));
            EventBus.getDefault().post(ints, EventBusMsg.EVENT_CALCULATE_ROUTE_SUCCESS);
        }

        @Override
        public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {
            EventBus.getDefault().post(aMapCalcRouteResult, EventBusMsg.EVENT_CALCULATE_ROUTE_FAIL);
        }

        @Override
        public void onInitNaviFailure() {

        }

        @Override
        public void onInitNaviSuccess() {

        }

        @Override
        public void onStartNavi(int i) {

        }

        @Override
        public void onTrafficStatusUpdate() {

        }

        @Override
        public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

        }

        @Override
        public void onGetNavigationText(int i, String s) {

        }

        @Override
        public void onGetNavigationText(String s) {

        }

        @Override
        public void onEndEmulatorNavi() {

        }

        @Override
        public void onArriveDestination() {

        }

        @Override
        public void onCalculateRouteFailure(int i) {

        }

        @Override
        public void onReCalculateRouteForYaw() {

        }

        @Override
        public void onReCalculateRouteForTrafficJam() {

        }

        @Override
        public void onArrivedWayPoint(int i) {

        }

        @Override
        public void onGpsOpenStatus(boolean b) {

        }

        @Override
        public void onNaviInfoUpdate(NaviInfo naviInfo) {

        }

        @Override
        public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

        }

        @Override
        public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

        }

        @Override
        public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

        }

        @Override
        public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

        }

        @Override
        public void showCross(AMapNaviCross aMapNaviCross) {

        }

        @Override
        public void hideCross() {

        }

        @Override
        public void showModeCross(AMapModelCross aMapModelCross) {

        }

        @Override
        public void hideModeCross() {

        }

        @Override
        public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

        }

        @Override
        public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

        }

        @Override
        public void hideLaneInfo() {

        }

        @Override
        public void notifyParallelRoad(int i) {

        }

        @Override
        public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

        }

        @Override
        public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

        }

        @Override
        public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

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
        public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

        }

        @Override
        public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

        }

        @Override
        public void onGpsSignalWeak(boolean b) {

        }
    };
}
