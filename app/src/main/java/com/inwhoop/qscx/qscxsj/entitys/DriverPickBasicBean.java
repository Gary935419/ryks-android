package com.inwhoop.qscx.qscxsj.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucky on 2016/11/29.
 */

public class DriverPickBasicBean implements Serializable {

    private String taker_type_id;
    private String route_city_id1;
    private String route_city_id2;
    private String car_type_id;
    private String route_city_font1;
    private String route_city_font2;
    private String seat;
    //1 停止接单 0 开始接单
    private String working_status;      //工作状态
    private String working_status_type;
    private String order_id;     //当前进行中的订单id

    private List<OrderBasicBean> order_traffic_list;    //专车送/顺丰送/代买 进行中订单列表
    private List<OrderBasicBean> order_town_list;    //代驾 进行中订单列表

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getWorking_status() {
        return working_status;
    }

    public void setWorking_status(String working_status) {
        this.working_status = working_status;
    }

    public String getWorking_status_type() {
        return working_status_type;
    }

    public void setWorking_status_type(String working_status_type) {
        this.working_status_type = working_status_type;
    }

    public String getTaker_type_id() {
        return taker_type_id;
    }

    public void setTaker_type_id(String taker_type_id) {
        this.taker_type_id = taker_type_id;
    }

    public String getRoute_city_id1() {
        return route_city_id1;
    }

    public void setRoute_city_id1(String route_city_id1) {
        this.route_city_id1 = route_city_id1;
    }

    public String getRoute_city_id2() {
        return route_city_id2;
    }

    public void setRoute_city_id2(String route_city_id2) {
        this.route_city_id2 = route_city_id2;
    }

    public String getCar_type_id() {
        return car_type_id;
    }

    public void setCar_type_id(String car_type_id) {
        this.car_type_id = car_type_id;
    }

    public String getRoute_city_font1() {
        return route_city_font1;
    }

    public void setRoute_city_font1(String route_city_font1) {
        this.route_city_font1 = route_city_font1;
    }

    public String getRoute_city_font2() {
        return route_city_font2;
    }

    public void setRoute_city_font2(String route_city_font2) {
        this.route_city_font2 = route_city_font2;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public List<OrderBasicBean> getOrder_traffic_list() {
        return order_traffic_list;
    }

    public void setOrder_traffic_list(List<OrderBasicBean> order_traffic_list) {
        this.order_traffic_list = order_traffic_list;
    }

    public List<OrderBasicBean> getOrder_town_list() {
        return order_town_list;
    }

    public void setOrder_town_list(List<OrderBasicBean> order_town_list) {
        this.order_town_list = order_town_list;
    }

    public static class OrderBasicBean implements Serializable {

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
