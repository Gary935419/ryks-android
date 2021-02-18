package com.inwhoop.qscx.qscxsj.entitys;

import java.io.Serializable;

/**
 * Created by Lucky on 2016/11/22.
 */

public class LineBean implements Serializable {
    private String id;
    private String route_city_id1;
    private String route_city_id2;
    private String real_price;
    private String sale_price;
    private String paixu;
    private String status;
    private String route_city_name1;
    private String route_city_name2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getReal_price() {
        return real_price;
    }

    public void setReal_price(String real_price) {
        this.real_price = real_price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getPaixu() {
        return paixu;
    }

    public void setPaixu(String paixu) {
        this.paixu = paixu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoute_city_name1() {
        return route_city_name1;
    }

    public void setRoute_city_name1(String route_city_name1) {
        this.route_city_name1 = route_city_name1;
    }

    public String getRoute_city_name2() {
        return route_city_name2;
    }

    public void setRoute_city_name2(String route_city_name2) {
        this.route_city_name2 = route_city_name2;
    }
}
