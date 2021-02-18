package com.inwhoop.qscx.qscxsj.entitys;

import java.io.Serializable;

/**
 * Created by Lucky on 2016/12/13.
 */

public class DriverPickTownOrderBean implements Serializable {

    private String status;
    private String order_small_id;
    private String head_img;
    private String name;
    private String account;
    private String user_account;
    private String user_name;
    private String start_location;
    private String start_longitude;
    private String start_latitude;
    private String end_location;
    private String end_longitude;
    private String end_latitude;
    private String longitude;
    private String latitude;
    /*1  发单，未付款，2 发单，已付款 （待接单) ，3 已接单
    4，带前往出发地 5到达出发地 6待验证提货码 7前往目的地 8完成*/
    private String order_status;
    private String order_type;
    //Extra
    private String taker_type_id = "1";

    public String getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(String start_latitude) {
        this.start_latitude = start_latitude;
    }

    public String getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(String start_longitude) {
        this.start_longitude = start_longitude;
    }

    public String getEnd_latitude() {
        return end_latitude;
    }

    public void setEnd_latitude(String end_latitude) {
        this.end_latitude = end_latitude;
    }

    public String getEnd_longitude() {
        return end_longitude;
    }

    public void setEnd_longitude(String end_longitude) {
        this.end_longitude = end_longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_small_id() {
        return order_small_id;
    }

    public void setOrder_small_id(String order_small_id) {
        this.order_small_id = order_small_id;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getEnd_location() {
        return end_location;
    }

    public void setEnd_location(String end_location) {
        this.end_location = end_location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getTaker_type_id() {
        return taker_type_id;
    }

    public void setTaker_type_id(String taker_type_id) {
        this.taker_type_id = taker_type_id;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
