package com.inwhoop.qscx.qscxsj.entitys;

import java.io.Serializable;

/**
 * Created by Lucky on 2016/12/14.
 */
public class TownOrderBean implements Serializable {

    private String order_small_id;
    private String head_img;
    private String name;
    private String account;
    private String times;
    private String status;
    private String start_location;
    private String end_location;
    private String price;
    private String order_status;
    private String order_driver_price;
    private String tip_price;

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

    public String getOrder_driver_price() {
        return order_driver_price;
    }

    public void setOrder_driver_price(String order_driver_price) {
        this.order_driver_price = order_driver_price;
    }

    public String getTip_price() {
        return tip_price;
    }

    public void setTip_price(String tip_price) {
        this.tip_price = tip_price;
    }
}
