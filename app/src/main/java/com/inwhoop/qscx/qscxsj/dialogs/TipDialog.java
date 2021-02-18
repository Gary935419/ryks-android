package com.inwhoop.qscx.qscxsj.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.inwhoop.qscx.qscxsj.R;

public class TipDialog extends Dialog {
    private Context context;
    private String url;
    private WebView wv_web;
    private OnOutSideListener onOutSideListener;

    public TipDialog(@NonNull Context context) {
        super(context);
    }

    public TipDialog(@NonNull Context context, int themeResId, String url) {
        super(context, themeResId);
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);
        wv_web = findViewById(R.id.wv_web);
        initWeb();
    }

    @SuppressLint("JavascriptInterface")
    private void initWeb() {
        WebSettings webSettings = wv_web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setUseWideViewPort(true);// 放大缩小
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setBuiltInZoomControls(true);
        wv_web.addJavascriptInterface(this, "nativeMethod");
        wv_web.setWebChromeClient(new MyWebChromeClient());
        wv_web.setWebViewClient(new WebViewClient());
        if (url != null && url.contains("http")) {
            wv_web.loadUrl(url);
        }
    }

    class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            result.confirm();
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    public interface OnOutSideListener{
        void onOutSide();
    }
}
