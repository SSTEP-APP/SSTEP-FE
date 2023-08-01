package com.example.sstep.todo.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.home.Home_Ceo;

public class Notice extends AppCompatActivity implements View.OnClickListener {

    ImageButton plusbtn, backib;
    LinearLayout listL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);
        plusbtn = findViewById(R.id.notice_plusbtn); plusbtn.setOnClickListener(this);
        backib=findViewById(R.id.notice_backib); backib.setOnClickListener(this);
        listL=findViewById(R.id.notice_listL); listL.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.notice_backib:
                intent = new Intent(getApplicationContext(), Home_Ceo.class);
                startActivity(intent);
                finish();
                break;
            case R.id.notice_plusbtn: // '추가 plus'버튼 선택 시
                intent = new Intent(getApplicationContext(), Notice_input.class);
                startActivity(intent);
                finish();
                break;
            case R.id.notice_listL: // 공지 리스트
                intent = new Intent(getApplicationContext(), Notice_view.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}