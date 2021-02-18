package com.inwhoop.qscx.qscxsj.entitys;

import java.io.Serializable;

/**
 * Created by Lucky on 2016/12/13.
 */

public class TownBean implements Serializable {

    private String head_img;
    private String name;
    private String start_location;
    private String end_location;

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
}
