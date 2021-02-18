package com.inwhoop.qscx.qscxsj.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucky on 2016/12/6.
 */

public class DriverPickOrderBean implements Serializable {

    private String route;
    private String connected;
    private String surplus_seat;
    private String status;
    private List<PassengerBean> lists;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }

    public String getSurplus_seat() {
        return surplus_seat;
    }

    public void setSurplus_seat(String surplus_seat) {
        this.surplus_seat = surplus_seat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PassengerBean> getLists() {
        return lists;
    }

    public void setLists(List<PassengerBean> lists) {
        this.lists = lists;
    }

    public class PassengerBean implements Serializable {
        private String order_small_id;
        private String head_img;
        private String name;
        private String account;
        private String people_num;
        private String location;
        private String arrival_position;
        private String longitude;
        private String latitude;
        private String route;
        private String status;

        public String getArrival_position() {
            return arrival_position;
        }

        public void setArrival_position(String arrival_position) {
            this.arrival_position = arrival_position;
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

        public String getPeople_num() {
            return people_num;
        }

        public void setPeople_num(String people_num) {
            this.people_num = people_num;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
