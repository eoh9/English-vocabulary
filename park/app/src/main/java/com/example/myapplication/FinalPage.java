package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FinalPage extends AppCompatActivity {

    private Button go_home;

    protected void onCreate( Bundle savedInstanceState) {
        //activity_final 파일에서 홈 버튼을 눌렀을 때 Chapter,Dictionary,word note 가 나오는 파일로 넘어감
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_final);


            go_home=findViewById(R.id.go_home);
            go_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FinalPage.this, SubActivity.class);
                    startActivity(intent);
                }
            });
        }

    }


