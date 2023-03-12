package com.example.sstep_yuuuky02.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep_yuuuky02.R;

public class Notice extends AppCompatActivity {

    ImageButton plusbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);
        plusbtn = findViewById(R.id.notice_plusbtn);

        // '추가 plus'버튼 선택 시
        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Notice_input.class);
                startActivity(intent);
                finish();
            }
        });
    }
}