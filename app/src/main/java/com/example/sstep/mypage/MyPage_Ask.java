package com.example.sstep.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class MyPage_Ask extends AppCompatActivity {

    ImageButton backib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_ask);
        backib = findViewById(R.id.mypage_ask_backib);

        // '뒤로가기' 선택 시
        backib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}