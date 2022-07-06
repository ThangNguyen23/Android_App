package com.tacopronto.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class tacopronto extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tacopronto);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new Callback());
        webView.loadUrl("https://tacopronto.net/");
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }

}