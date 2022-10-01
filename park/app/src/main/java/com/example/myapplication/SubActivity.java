package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SubActivity extends AppCompatActivity {

    private Button start_chapter1;
    private Button start_chapter2;
    private Button start_chapter3;
    private Button start_chapter4;
    private Button start_chapter5;
    private Button start_chapter6;
    private Button start_chapter7;
    private Button start_chapter8;
    private Button my_word1;
    private Button web_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        //activity_sub 파일에서 chapter1-8,dictionary,word note  버튼을 눌렀을 때
        // chapter 1-8은 시험을 시작하는 화면으로 넘어가고 dictionary버튼을 누르면 단어를 찾을 수 있는
        // WebView 가 나오는 페이지로 넘어가고 Word note 버튼을 누르면 단어를 저장할 수 있는 단어 메모장이 나오는 페이지로 넘어감


        start_chapter1=(Button) findViewById(R.id.start_chapter1);
        start_chapter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, Chapter1.class);
                startActivity(intent);
            }
        });

        start_chapter2=(Button) findViewById(R.id.start_chapter2);
        start_chapter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SubActivity.this, Chapter2.class);
                startActivity(intent);
            }
        });

        start_chapter3=(Button) findViewById(R.id.start_chapter3);
        start_chapter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, Chapter3.class);
                startActivity(intent);
            }
        });

        start_chapter4=(Button) findViewById(R.id.start_chapter4);
        start_chapter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, Chapter4.class);
                startActivity(intent);
            }
        });

        start_chapter5=(Button) findViewById(R.id.start_chapter5);
        start_chapter5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, Chapter5.class);
                startActivity(intent);
            }
        });

        start_chapter6=(Button) findViewById(R.id.start_chapter6);
        start_chapter6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, Chapter6.class);
                startActivity(intent);
            }
        });

        start_chapter7=(Button) findViewById(R.id.start_chapter7);
        start_chapter7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, Chapter7.class);
                startActivity(intent);
            }
        });

        start_chapter8=(Button) findViewById(R.id.start_chapter8);
        start_chapter8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, Chapter8.class);
                startActivity(intent);
            }
        });
        my_word1=(Button) findViewById(R.id.my_word1);
        my_word1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        web_1=(Button) findViewById(R.id.web_1);
        web_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, MainActivity3.class);
                startActivity(intent);
            }
        });
    }

}