package com.example.sstep.document.certificate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.document.contract.PaperWinput;

public class PaperHview extends AppCompatActivity implements View.OnClickListener {

    ImageButton backib;
    Button modiBtn1, reBtn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperhview);

        backib=findViewById(R.id.paperview_backib); backib.setOnClickListener(this);
        modiBtn1=findViewById(R.id.paperview_modiBtn1); modiBtn1.setOnClickListener(this);
        reBtn2=findViewById(R.id.paperview_reBtn2); reBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.paperview_backib: // '뒤로가기' 선택 시
                intent = new Intent(getApplicationContext(), PaperH.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paperview_modiBtn1: // 수정하기
                intent = new Intent(getApplicationContext(), PaperHmodi.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paperview_reBtn2: // 갱신하기
                intent = new Intent(getApplicationContext(), PaperWinput.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}