package com.example.sstep.store;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sstep.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView browser;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_web_view);

            browser = findViewById(R.id.webView);
            browser.getSettings().setJavaScriptEnabled(true);

            browser.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

            browser.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    browser.loadUrl("javascript:sample2_execDaumPostcode();");
                }
            });

            browser.loadUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com/daum.html");

        }

        public class MyJavaScriptInterface {
            @JavascriptInterface
            public void processDATA(String data) {
                // 자바 스크립트로부터 다음 카카오 주소 검색 API 결과를 전달 받는다.
                Bundle extra = new Bundle();
                Intent intent = new Intent();

                extra.putString("data", data);
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }