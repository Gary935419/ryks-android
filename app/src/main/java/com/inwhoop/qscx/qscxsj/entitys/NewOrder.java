package com.inwhoop.qscx.qscxsj.entitys;

import java.io.Serializable;

/**
 * Created by Lucky on 2016/12/5.
 */
public class NewOrder implements Serializable {

    private String head_img;
    private String name;
    private String start_location;
    private String end_location;
    private String order_type;
    private String pickup_distance;
    private String delivery_distance;
    private String appointment_time;

    private String order_small_id;
    private String account;
    private String times;
    private String status;
    private String price;
    private String order_status;
    private String number;
    private String tip_price;
    private String distribution_km;
    private String item_information;
    private String order_price;
    private String order_tip_price;
    private String order_remark;

    //Extra
    private String waiting_id;   //极光推送消息id
    private String taker_type_id;   //极光推送taker_type_id
    private boolean isSelect;

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getEnd_location() {
        return end_location;
    }

    public void setEnd_location(String end_location) {
        this.end_location = end_location;
    }

    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWaiting_id() {
        return waiting_id;
    }

    public void setWaiting_id(String waiting_id) {
        this.waiting_id = waiting_id;
    }

    public String getPickup_distance() {
        return pickup_distance;
    }

    public void setPickup_distance(String pickup_distance) {
        this.pickup_distance = pickup_distance;
    }

    public String getDelivery_distance() {
        return delivery_distance;
    }

    public void setDelivery_distance(String delivery_distance) {
        this.delivery_distance = delivery_distance;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getTaker_type_id() {
        return taker_type_id;
    }

    public void setTaker_type_id(String taker_type_id) {
        this.taker_type_id = taker_type_id;
    }

    public String getOrder_small_id() {
        return order_small_id;
    }

    public void setOrder_small_id(String order_small_id) {
        this.order_small_id = order_small_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTip_price() {
        return tip_price;
    }

    public void setTip_price(String tip_price) {
        this.tip_price = tip_price;
    }

    public String getDistribution_km() {
        return distribution_km;
    }

    public void setDistribution_km(String distribution_km) {
        this.distribution_km = distribution_km;
    }

    public String getItem_information() {
        return item_information;
    }

    public void setItem_information(String item_information) {
        this.item_information = item_information;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getOrder_tip_price() {
        return order_tip_price;
    }

    public void setOrder_tip_price(String order_tip_price) {
        this.order_tip_price = order_tip_price;
    }

    public String getOrder_remark() {
        return order_remark;
    }

    public void setOrder_remark(String order_remark) {
        this.order_remark = order_remark;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    /**
     * 判断是不是当前进行中的订单
     */
    public boolean isCurrentOrder(DriverPickBasicBean order) {
        return order.getOrder_id().equals(order_small_id);
    }
}
