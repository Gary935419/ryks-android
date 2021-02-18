package com.inwhoop.qscx.qscxsj.entitys;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 订单详情
 **/
public class OrderDetailBean implements Serializable {

    private String id;
    private String driver_id;
    private String status;
    private String user_id;
    private String car_type_id;
    private String start_location;
    private String start_longitude;
    private String start_latitude;
    private String end_location;
    private String end_longitude;
    private String end_latitude;
    private String price;
    private String add_time;
    private String number;
    private String evaluate;
    private String file_type;
    private String order_type;
    private String coupon_id;
    private String order_status;
    private String distribution_km;
    private String preferential_price;
    private String protect_price;
    private String tip_price;
    private String order_driver_price;
    private String getorder_time;
    private String takegoods_time;
    private String complete_time;

    private String appointment_time;
    private String takeup_time;
    private String remarks;
    private String order_evaluation;
    private String name;
    private String tel;
    private String order_number;
    private String address1;
    private String address2;
    private String big_order_id;
    private String name1;
    private String tel1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCar_type_id() {
        return car_type_id;
    }

    public void setCar_type_id(String car_type_id) {
        this.car_type_id = car_type_id;
    }

    public String getStart_location() {
        return TextUtils.isEmpty(start_location) ? "附近地点" : start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(String start_longitude) {
        this.start_longitude = start_longitude;
    }

    public String getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(String start_latitude) {
        this.start_latitude = start_latitude;
    }

    public String getEnd_location() {
        return end_location;
    }

    public void setEnd_location(String end_location) {
        this.end_location = end_location;
    }

    public String getEnd_longitude() {
        return end_longitude;
    }

    public void setEnd_longitude(String end_longitude) {
        this.end_longitude = end_longitude;
    }

    public String getEnd_latitude() {
        return end_latitude;
    }

    public void setEnd_latitude(String end_latitude) {
        this.end_latitude = end_latitude;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getDistribution_km() {
        return distribution_km;
    }

    public void setDistribution_km(String distribution_km) {
        this.distribution_km = distribution_km;
    }

    public String getPreferential_price() {
        return preferential_price;
    }

    public void setPreferential_price(String preferential_price) {
        this.preferential_price = preferential_price;
    }

    public String getProtect_price() {
        return protect_price;
    }

    public void setProtect_price(String protect_price) {
        this.protect_price = protect_price;
    }

    public String getTip_price() {
        return tip_price;
    }

    public void setTip_price(String tip_price) {
        this.tip_price = tip_price;
    }

    public String getGetorder_time() {
        return getorder_time;
    }

    public void setGetorder_time(String getorder_time) {
        this.getorder_time = getorder_time;
    }

    public String getTakegoods_time() {
        return takegoods_time;
    }

    public void setTakegoods_time(String takegoods_time) {
        this.takegoods_time = takegoods_time;
    }

    public String getComplete_time() {
        return complete_time;
    }

    public void setComplete_time(String complete_time) {
        this.complete_time = complete_time;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getTakeup_time() {
        return takeup_time;
    }

    public void setTakeup_time(String takeup_time) {
        this.takeup_time = takeup_time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrder_evaluation() {
        return order_evaluation;
    }

    public void setOrder_evaluation(String order_evaluation) {
        this.order_evaluation = order_evaluation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getBig_order_id() {
        return big_order_id;
    }

    public void setBig_order_id(String big_order_id) {
        this.big_order_id = big_order_id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getOrder_driver_price() {
        return order_driver_price;
    }

    public void setOrder_driver_price(String order_driver_price) {
        this.order_driver_price = order_driver_price;
    }
}
