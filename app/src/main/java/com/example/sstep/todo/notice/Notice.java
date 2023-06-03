package com.example.sstep.todo.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.user.login.Login;

public class Notice extends AppCompatActivity implements View.OnClickListener {

    ImageButton plusbtn, backib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);
        plusbtn = findViewById(R.id.notice_plusbtn); plusbtn.setOnClickListener(this);
        backib=findViewById(R.id.notice_backib); backib.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.notice_backib:
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
                break;
            case R.id.notice_plusbtn: // '추가 plus'버튼 선택 시
                intent = new Intent(getApplicationContext(), Notice_input.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}