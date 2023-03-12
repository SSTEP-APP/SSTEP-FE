package com.example.sstep_yuuuky02.paper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep_yuuuky02.R;

public class Paperh extends AppCompatActivity {

    ImageButton backib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperh);
        backib = findViewById(R.id.paperh_backib);

        // '뒤로가기' 선택 시
        backib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Paper.class);
                startActivity(intent);
                finish();
            }
        });
    }
}