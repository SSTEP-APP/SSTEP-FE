package com.example.sstep.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class Notice_view extends AppCompatActivity implements View.OnClickListener {

    ImageButton backib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_view);

        backib=findViewById(R.id.notice_view_backib); backib.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.notice_view_backib: // 뒤로가기
                intent = new Intent(getApplicationContext(), Notice.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}