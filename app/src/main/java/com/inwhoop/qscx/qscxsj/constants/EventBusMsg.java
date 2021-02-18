package com.inwhoop.qscx.qscxsj.constants;

public interface EventBusMsg {

    String EVENT_PUSH = "MainActivityGetPopup";   //极光推送消息
    String EVENT_RECEIVE_ORDER = "MainActivityGetOrder";     //接单
    String EVENT_UNREAD_MSG = "MainActivityUnreadNum";      //未读消息
    String EVENT_MSG_READ = "MessageInterfaceActivityLists";      //消息已读
    String EVENT_CHANGE_ORDER = "action.event.change.order";    //切换订单

    String EVENT_LOCATION_SUCCESS = "action.event.locationSuccess";    //定位成功
    String EVENT_CALCULATE_ROUTE_SUCCESS = "action.event.calculateRouteSuccess";   //计算路线成功
    String EVENT_CALCULATE_ROUTE_FAIL = "action.event.calculateRouteFail";   //计算路线失败

    String EVENT_GET_OFF_CAR = "action.event.getOffCar";   //下车（代驾订单完成）
    String EVENT_UPLOAD_CODE = "action.event.uploadCode";   //提交货物码（跑腿订单完成）
}
