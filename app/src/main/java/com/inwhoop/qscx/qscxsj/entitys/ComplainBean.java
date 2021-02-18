package com.inwhoop.qscx.qscxsj.entitys;

import java.io.Serializable;

/**
 * @Author: Samson.Sun
 * @time: 2020年8月4日20:28:44 20:28
 * @UpdateAuthor:
 * @UpdateTime: 2020年8月4日20:28:44 20:28
 * @Description:
 **/
public class ComplainBean implements Serializable {
    private String orderId;
    private String content;
    private int type;
    private String orderType;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
