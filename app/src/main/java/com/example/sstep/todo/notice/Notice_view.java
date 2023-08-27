package com.example.sstep.todo.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class Notice_view extends AppCompatActivity implements View.OnClickListener {

    ImageButton backib;
    TextView nameTv, posiTv, dateTv, titleTv, contentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_view);

        backib=findViewById(R.id.notice_view_backib); backib.setOnClickListener(this);
        nameTv=findViewById(R.id.notice_view_nameTv);
        posiTv=findViewById(R.id.notice_view_posiTv);
        dateTv=findViewById(R.id.notice_view_dateTv);
        titleTv=findViewById(R.id.notice_view_titleTv);
        contentTv=findViewById(R.id.notice_view_contentTv);

        // Intent로 전달된 데이터 추출
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("date");

        // 추출한 데이터를 각 TextView에 설정
        titleTv.setText(title);
        contentTv.setText(content);
        nameTv.setText(name);
        dateTv.setText(date);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.notice_view_backib: // 뒤로가기
                intent = new Intent(getApplicationContext(), Notice.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 기존의 Notice 액티비티 스택을 모두 제거
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}