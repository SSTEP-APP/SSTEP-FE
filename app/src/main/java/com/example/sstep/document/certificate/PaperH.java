package com.example.sstep.document.certificate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.document.certificate.Paper;

public class PaperH extends AppCompatActivity implements View.OnClickListener {

    ImageButton backib;
    LinearLayout reglistL,unreglistL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperh);
        backib = findViewById(R.id.paperh_backib); backib.setOnClickListener(this);
        reglistL = findViewById(R.id.paperh_reglistL); reglistL.setOnClickListener(this);
        unreglistL = findViewById(R.id.paperh_unreglistL); unreglistL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.paperh_backib: // '뒤로가기'
                intent = new Intent(getApplicationContext(), Paper.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paperh_reglistL: // 등록 직원 리스트
                intent = new Intent(getApplicationContext(), PaperHview.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paperh_unreglistL: // 미등록 직원 리스트
                intent = new Intent(getApplicationContext(), PaperHinput.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}