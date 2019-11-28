package com.lifebox.Activity;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lifebox.R;

public class Koutu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.koutu_page);
        WebView webView = findViewById(R.id.koutu_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);//主要是这句启用HTML5 DOM storage API
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.supportMultipleWindows();
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSaveFormData(true);
        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
        webSettings.setNeedInitialFocus(true);// 是否当webview调用requestFocus时为页面的某个元素设置焦点，默认值 true
        webSettings.setUseWideViewPort(true);
        webView.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉
        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 接受所有证书
            }

        });
        webView.loadUrl("https://m.gaoding.com/koutu");
    }
    public void Finish(View v){
        finish();
        return;
    }
}