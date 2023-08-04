package com.example.sstep.document.certificate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.document.contract.PaperWlist;
import com.example.sstep.home.Home_Ceo;

public class Paper extends AppCompatActivity implements View.OnClickListener{

    FrameLayout workpF, healthpF;
    ImageButton backib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paper);
        workpF = findViewById(R.id.paper_workpF); workpF.setOnClickListener(this);
        healthpF = findViewById(R.id.paper_healthpF); healthpF.setOnClickListener(this);
        backib = findViewById(R.id.paper_backib); backib.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.paper_backib: // '뒤로가기' 선택 시
                intent = new Intent(getApplicationContext(), Home_Ceo.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paper_healthpF: // '보건증_F' 선택 시
                intent = new Intent(getApplicationContext(), PaperH.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paper_workpF: // '근로계약서_F' 선택 시
                intent = new Intent(getApplicationContext(), PaperWlist.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}