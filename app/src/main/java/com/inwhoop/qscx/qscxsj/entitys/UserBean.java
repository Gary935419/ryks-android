package com.inwhoop.qscx.qscxsj.entitys;

import java.io.Serializable;

/**
 * Created by Lucky on 2016/11/21.
 */

public class UserBean implements Serializable {

    private String id;
    private String type;
    private String account;
    private String name;
    private String head_img;
    private String add_time;
    private String cards;
    private String times;
    private String car_number;
    private String img_cards_face;
    private String img_cards_side;
    private String img_drivers;
    private String img_vehicle;
    private String taker_type_id;
    private String route_city_id1;
    private String route_city_id2;
    private String car_type_id;
    private String attribute;
    private String reason;
    private String longitude;
    private String latitude;
    private String taker_type_font;
    private String route_city_font1;
    private String route_city_font2;
    private String user_check; //快送认证 1通过 2未通过 3审核中 0未认证
    private String driving_check; //代驾认证 1通过 2未通过 3审核中 0未认证
    private String credit_points;
    private String invitation_code1;
    private String invitation_code2;
    private String invitation_code1_up;
    private String invitation_code2_up;

    public String getDriving_check() {
        return driving_check;
    }

    public void setDriving_check(String driving_check) {
        this.driving_check = driving_check;
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

    public String getTaker_type_font() {
        return taker_type_font;
    }

    public void setTaker_type_font(String taker_type_font) {
        this.taker_type_font = taker_type_font;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getUser_check() {
        return user_check;
    }

    public void setUser_check(String user_check) {
        this.user_check = user_check;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getImg_cards_face() {
        return img_cards_face;
    }

    public void setImg_cards_face(String img_cards_face) {
        this.img_cards_face = img_cards_face;
    }

    public String getImg_cards_side() {
        return img_cards_side;
    }

    public void setImg_cards_side(String img_cards_side) {
        this.img_cards_side = img_cards_side;
    }

    public String getImg_drivers() {
        return img_drivers;
    }

    public void setImg_drivers(String img_drivers) {
        this.img_drivers = img_drivers;
    }

    public String getImg_vehicle() {
        return img_vehicle;
    }

    public void setImg_vehicle(String img_vehicle) {
        this.img_vehicle = img_vehicle;
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

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getCredit_points() {
        return credit_points;
    }

    public void setCredit_points(String credit_points) {
        this.credit_points = credit_points;
    }

    public String getInvitation_code1() {
        return invitation_code1;
    }

    public void setInvitation_code1(String invitation_code1) {
        this.invitation_code1 = invitation_code1;
    }

    public String getInvitation_code2() {
        return invitation_code2;
    }

    public void setInvitation_code2(String invitation_code2) {
        this.invitation_code2 = invitation_code2;
    }

    public String getInvitation_code1_up() {
        return invitation_code1_up;
    }

    public void setInvitation_code1_up(String invitation_code1_up) {
        this.invitation_code1_up = invitation_code1_up;
    }

    public String getInvitation_code2_up() {
        return invitation_code2_up;
    }

    public void setInvitation_code2_up(String invitation_code2_up) {
        this.invitation_code2_up = invitation_code2_up;
    }
}
