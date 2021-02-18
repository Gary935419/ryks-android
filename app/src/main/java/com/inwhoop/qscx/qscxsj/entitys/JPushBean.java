package com.inwhoop.qscx.qscxsj.entitys;

import java.io.Serializable;

/**
 * Created by Lucky on 2016/12/1.
 */

public class JPushBean implements Serializable {

    private String taker_type_id;
    private String waiting_id;

    public String getTaker_type_id() {
        return taker_type_id;
    }

    public void setTaker_type_id(String taker_type_id) {
        this.taker_type_id = taker_type_id;
    }

    public String getWaiting_id() {
        return waiting_id;
    }

    public void setWaiting_id(String waiting_id) {
        this.waiting_id = waiting_id;
    }
}
