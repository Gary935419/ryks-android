package com.inwhoop.qscx.qscxsj.business.callback;

public interface OnHttpCallbackListener {

    void onHttpSuccess(String result, String msg);

    void onHttpFailure(int status, String msg);

    void onHttpError(String errorMsg);
}