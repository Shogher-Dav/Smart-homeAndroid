package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewFragment;
import android.support.v4.app.Fragment;

public class CameraPage extends AppCompatActivity {
 //   public static final String weburl="https://www.google.com/";
    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_page);
         mWebView = (WebView) findViewById(R.id.fragment_container);
        // включаем поддержку JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // указываем страницу загрузки
        mWebView.loadUrl("http://192.168.1.201/Video%20Browser");
//        public void loadWebPage(View view)
//        {
//
//            WebViewFragment webViewFragment=new WebViewFragment();
//            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,webViewFragment).commit();
//
//        }
    }
}
