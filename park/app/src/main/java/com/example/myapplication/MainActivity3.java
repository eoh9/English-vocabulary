package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity3 extends AppCompatActivity {

    private WebView webView;
    private String url = "https://dict.naver.com/";//웹뷰를 어떤 주소로 보여줄지 정함

    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate(), Create Table 명령으로 테이블을 생성
        super.onCreate(savedInstanceState);//savedInstanceState를 임의로 호출
        setContentView(R.layout.activity_main3); //activity_main.xml에 만든 레이아웃이 출력될 수 있도록 불러옴

        webView = (WebView)findViewById(R.id.webView); //activity main의 ID값을 불러옴
        webView.getSettings().setJavaScriptEnabled(true);//부가적인 언어를 허용해줌
        webView.loadUrl(url); //특정 url주소를 틀어줌
        webView.setWebChromeClient(new WebChromeClient()); //webview 환경을 chrome으로 설정해줌
        webView.setWebViewClient(new WebViewClientClass()); //webview에서 일어나는 일을 조작할 수 있게해줌
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) //key가 눌러졌을때 호출되는 함수
    {
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) //뒤로가기를 눌렀을때, 뒤로갈때
        {
            webView.goBack(); //웹뷰가 뒤로 돌아간다
            return true;
        } //뒤로가기 버튼을 누르면 웹뷰에서 다시 뒤로 가짐

        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }// 현재페이지 url을 읽어올 수 있는 메소드 ex) 이 페이지에서 다른 페이지로 넘어갈 수 있음
    }
}