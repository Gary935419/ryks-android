package com.inwhoop.qscx.qscxsj.constants;

/**
 * 工作状态
 */
public enum WorkStatus {

    RECEIVING_NOT("0"),  //未开启接单
    RECEIVING("1"),     //接单中
    ORDERING_3("2"),      //订单中3
    ORDERING_4("3");     //订单中4

    private final String mValue;

    WorkStatus(String value) {
        this.mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
