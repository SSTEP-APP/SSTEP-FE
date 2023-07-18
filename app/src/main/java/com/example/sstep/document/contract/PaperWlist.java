package com.example.sstep.document.contract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.document.certificate.Paper;

public class PaperWlist extends AppCompatActivity implements View.OnClickListener{

    LinearLayout reglistL, unreglistL;
    ImageButton backib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperwlist);

        reglistL=findViewById(R.id.paperwlist_reglistL); reglistL.setOnClickListener(this);
        unreglistL=findViewById(R.id.paperwlist_unreglistL); unreglistL.setOnClickListener(this);
        backib=findViewById(R.id.paperwlist_backib); backib.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.paperwlist_backib: // '뒤로가기' 선택 시
                intent = new Intent(getApplicationContext(), PaperW.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paperwlist_reglistL: // 등록 직원 리스트
                intent = new Intent(getApplicationContext(), PaperWlist.class);
                startActivity(intent);
                finish();
            case R.id.paperwlist_unreglistL: // 미등록 직원 리스트
                intent = new Intent(getApplicationContext(), PaperWinput.class);
                startActivity(intent);
                finish();
            default:
                break;
        }
    }
}