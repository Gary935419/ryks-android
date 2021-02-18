package com.inwhoop.qscx.qscxsj.activitys.ordertaking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.ComplainBean;
import com.inwhoop.qscx.qscxsj.entitys.OrderDetailBean;
import com.inwhoop.qscx.qscxsj.entitys.TownOrderBean;
import com.inwhoop.qscx.qscxsj.navi.DrivingRouteOverlay;
import com.inwhoop.qscx.qscxsj.tools.SystemManager;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.PicUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.utils.Utils;
import com.inwhoop.qscx.qscxsj.utils.gson.GsonUtil;
import com.inwhoop.qscx.qscxsj.views.CircleImageView;
import com.inwhoop.qscx.qscxsj.views.dialog.OrderDetailDialog;

/**
 * 订单详情
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private final int ROUTE_TYPE_DRIVE = 2;
    public static final String ORDER = "order";
    public static final String TAKER_TYPE_ID = "taker_type_id";

    private TextView title_center_text;
    private ImageView title_back_img;
    private CircleImageView iv_myPortrait;
    private TextView tv_driverName;
    private TextView tv_cost;
    private TextView tv_orderTime;
    private Button btn_report;
    private Button btn_complaint;
    private Button btn_more;
    private ImageView im_userTelephone;
    private TextView tv_start;
    private TextView tv_end;

    private TownOrderBean townOrderBean;
    private String takerTypeId;
    private OrderDetailBean orderDetailBean;
    private MapView mapView;
    private AMap aMap;
    private DriveRouteResult mDriveRouteResult;
    private RouteSearch mRouteSearch;
    private UiSettings mUiSettings;
    private LatLonPoint mStartPoint;
    private LatLonPoint mEndPoint;
    private MyLocationStyle myLocationStyle;

    private DrivingRouteOverlay mDrivingRouteOverlay;

    /**
     * 跳转方法
     *
     * @param context     上下文
     * @param orderBean   订单信息
     * @param takerTypeId taker_type_id 1送 2代驾
     */
    public static void startIntent(Context context, TownOrderBean orderBean, String takerTypeId) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ORDER, orderBean);
        bundle.putString(TAKER_TYPE_ID, takerTypeId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        getBundle();
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        initView();
        initMap();
    }

    /**
     * 获取intent数据
     */
    private void getBundle() {
        townOrderBean = (TownOrderBean) getIntent().getExtras().getSerializable(ORDER);
        takerTypeId = getIntent().getExtras().getString(TAKER_TYPE_ID);
    }

    private void initView() {
        title_center_text = findViewById(R.id.tv_title_main);
        title_back_img = findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);
        title_back_img.setOnClickListener(this);
        iv_myPortrait = findViewById(R.id.iv_myPortrait);
        tv_driverName = findViewById(R.id.tv_driverName);
        tv_cost = findViewById(R.id.tv_cost);
        tv_orderTime = findViewById(R.id.tv_orderTime);
        btn_report = findViewById(R.id.btn_report);
        btn_complaint = findViewById(R.id.btn_complaint);
        im_userTelephone = findViewById(R.id.im_userTelephone);
        tv_start = findViewById(R.id.tv_start);
        tv_end = findViewById(R.id.tv_end);
        btn_more = findViewById(R.id.btn_more);
        btn_report.setOnClickListener(this);
        btn_complaint.setOnClickListener(this);
        im_userTelephone.setOnClickListener(this);
        btn_more.setOnClickListener(this);
        if (townOrderBean != null) {
            PicUtil.displayImage(townOrderBean.getHead_img(), iv_myPortrait);
            tv_driverName.setText(townOrderBean.getName());
            tv_orderTime.setText(townOrderBean.getTimes());
            tv_cost.setText(Utils.getPriceWithUnit(townOrderBean.getOrder_driver_price()));
        }
        requestOrderDetail();
    }

    private void setTitle() {
        String status = orderDetailBean.getOrder_status();
        if (status.equals("3")) {
            //订单撤销
            title_center_text.setText("已接单");
        } else if (status.equals("4")) {
            //订单取消
            title_center_text.setText("待前往出发地");
        } else if (status.equals("5")) {
            //订单取消
            title_center_text.setText("已到达出发地");
        } else if (status.equals("6")) {
            //订单取消
            title_center_text.setText("待验证提货码");
        } else if (status.equals("7")) {
            //订单取消
            title_center_text.setText("待前往目的地");
        } else if (status.equals("8")) {
            title_center_text.setText("订单已完成");
        } else if (status.equals("9")) {
            title_center_text.setText("已取消");
        } else {
            //订单未完成
            title_center_text.setText("订单未完成");
        }
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }
        aMap.setTrafficEnabled(false);     // 显示实时交通状况
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);  // 矢量地图模式
        myLocationStyle = new MyLocationStyle();
        // 只定位，不进行其他操作
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW));
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(mSearchListener);
        initOther();
    }

    /**
     * 订单详情
     */
    private void requestOrderDetail() {
        showLoadingDialog("加载中...");
        String order_id = townOrderBean.getOrder_small_id();
        DriverOrderService.get_order(context, order_id, takerTypeId, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "requestOrderDetail: " + result);
                dismissLoadingDialog();
                orderDetailBean = GsonUtil.toBean(result, OrderDetailBean.class);
                LatLonPoint start = new LatLonPoint(
                        Double.parseDouble(orderDetailBean.getStart_latitude()),
                        Double.parseDouble(orderDetailBean.getStart_longitude())
                );
                LatLonPoint end = new LatLonPoint(
                        Double.parseDouble(orderDetailBean.getEnd_latitude()),
                        Double.parseDouble(orderDetailBean.getEnd_longitude())
                );
                mDrivingRouteOverlay = new DrivingRouteOverlay(context, aMap, null, start, end, null);
                mDrivingRouteOverlay.zoomToSpan();
                setTitle();
                setFromAndToMarker();
                tv_start.setText(orderDetailBean.getStart_location());
                tv_end.setText(orderDetailBean.getEnd_location());
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

    private void setFromAndToMarker() {
        mStartPoint = new LatLonPoint(Double.parseDouble(orderDetailBean.getStart_latitude()), Double.parseDouble(orderDetailBean.getStart_longitude()));
        mEndPoint = new LatLonPoint(Double.parseDouble(orderDetailBean.getEnd_latitude()), Double.parseDouble(orderDetailBean.getEnd_longitude()));
        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT);
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            Toast.makeText(context, "起点未设置", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mEndPoint == null) {
            Toast.makeText(context, "终点未设置", Toast.LENGTH_SHORT).show();
            return;
        }
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");       // 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);   // 异步路径规划驾车模式查询
        }
    }

    private void initOther() {
        // 显示3D 楼块
        aMap.showBuildings(true);
        // 显示底图文字
        aMap.showMapText(true);
        // 设置地图初始缩放大小
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        // 设置地图默认的指南针是否显示
        mUiSettings.setCompassEnabled(true);
        // 是否显示默认的定位按钮
        mUiSettings.setMyLocationButtonEnabled(false);
        // 设置地图默认的缩放按钮是否显示
        mUiSettings.setZoomControlsEnabled(false);
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.btn_report:
                break;
            //投诉/报备
            case R.id.btn_complaint:
                if (orderDetailBean == null) {
                    ToastUtil.showShortToast(context, "订单异常!!");
                    return;
                }
                ComplainBean complainBean = new ComplainBean();
                complainBean.setOrderId(townOrderBean.getOrder_small_id());
                complainBean.setOrderType(orderDetailBean.getOrder_type().equals(Constants.ORDER_TYPE_DRIVE) ? "2" : "1");
                ComplainActivity.startIntent(this, complainBean);
                break;
            //打电话
            case R.id.im_userTelephone:
                SystemManager.callPhone(context, townOrderBean.getAccount());
                break;
            //查看详情
            case R.id.btn_more:
                OrderDetailDialog dialog = new OrderDetailDialog(this, orderDetailBean, townOrderBean);
                dialog.show();
                break;
            default:
                break;

        }
    }

    /**
     * 查询路线以及路径回调
     */
    private final RouteSearch.OnRouteSearchListener mSearchListener = new RouteSearch.OnRouteSearchListener() {

        @Override
        public void onDriveRouteSearched(DriveRouteResult result, int i) {
            aMap.clear();      // 清理地图上的所有覆盖物
            if (i != AMapException.CODE_AMAP_SUCCESS) {
                Toast.makeText(context, i, Toast.LENGTH_SHORT).show();
                return;
            }
            if (result == null || result.getPaths() == null || result.getPaths().size() <= 0) {
                ToastUtil.showShortToast(context, getString(R.string.no_result));
                return;
            }
            if (result.getPaths().size() > 0) {
                mDriveRouteResult = result;
                final DrivePath drivePath = mDriveRouteResult.getPaths().get(0);
                if (mDrivingRouteOverlay == null || drivePath == null) {
                    return;
                }
                mDrivingRouteOverlay.setDrivePath(drivePath);
                mDrivingRouteOverlay.setNodeIconVisibility(false);       //设置节点marker是否显示
                mDrivingRouteOverlay.setIsColorfulline(false);        //是否用颜色展示交通拥堵情况，默认true
                mDrivingRouteOverlay.removeFromMap();
                mDrivingRouteOverlay.addToMap();
//                        int dis = (int) drivePath.getDistance();
//                        int dur = (int) drivePath.getDuration();
//                        String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
//                        int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                if (mapView.getVisibility() == View.INVISIBLE) {
                    mapView.setVisibility(View.VISIBLE);
                }
            } else if (result != null && result.getPaths() == null) {
                ToastUtil.showShortToast(context, getString(R.string.no_result));
            }
        }

        @Override
        public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
        }

        @Override
        public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
        }

        @Override
        public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
