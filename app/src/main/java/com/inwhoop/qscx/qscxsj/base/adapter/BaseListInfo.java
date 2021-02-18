package com.inwhoop.qscx.qscxsj.base.adapter;

import java.io.Serializable;

public abstract class BaseListInfo implements Serializable {

    protected boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
