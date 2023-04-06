package com.example.sstep.paper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class Paper extends AppCompatActivity implements View.OnClickListener{

    FrameLayout paper_wF2, paper_hF3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paper);
        paper_wF2 = findViewById(R.id.paper_wF2); paper_hF3 = findViewById(R.id.paper_hF3);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.paper_hF3: // '보건증_F' 선택 시
                Intent intent = new Intent(getApplicationContext(), Paperh.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}