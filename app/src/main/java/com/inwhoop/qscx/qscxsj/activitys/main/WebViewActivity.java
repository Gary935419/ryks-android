package com.inwhoop.qscx.qscxsj.activitys.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;

public class WebViewActivity extends BaseActivity {
    private TextView title_center_text;
    private ImageView title_back_img;
    private String url;
    private WebView wv_web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);
        title_center_text = findViewById(R.id.tv_title_main);
        title_back_img = findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);
        wv_web = findViewById(R.id.wv_web);
        url = getIntent().getStringExtra("url");
        title_center_text.setText(getIntent().getStringExtra("title"));
        initWeb();
        title_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
}
