package com.example.sstep.paper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class Paper extends AppCompatActivity implements View.OnClickListener{

    FrameLayout paper_workpF, paper_healthpF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paper);
        paper_workpF = findViewById(R.id.paper_workpF); paper_healthpF = findViewById(R.id.paper_healthpF);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.paper_healthpF: // '보건증_F' 선택 시
                Intent intent = new Intent(getApplicationContext(), PaperH.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}