package com.inwhoop.qscx.qscxsj.constants;

/**
 * 订单类型
 */
public enum OrderType {

    ORDER_TYPE_ZHUAN("1"),    //专车送
    ORDER_TYPE_SHUN("2"),    //顺风车送
    ORDER_TYPE_BUY("3"),     //代买
    ORDER_TYPE_DRIVE("4");    //代驾

    private final String mValue;

    OrderType(String value) {
        this.mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
