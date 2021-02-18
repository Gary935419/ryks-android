package com.inwhoop.qscx.qscxsj.app;

import com.inwhoop.qscx.qscxsj.utils.LogUtils;
import com.loopj.android.http.TextHttpResponseHandler;

public abstract class AsyncHttpHandler extends TextHttpResponseHandler {

    protected void onSuccess(String responseString){
        LogUtils.d("post response--\n" + responseString + "\n\n");
    }
}
