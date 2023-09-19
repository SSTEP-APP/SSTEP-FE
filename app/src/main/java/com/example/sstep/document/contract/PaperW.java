package com.example.sstep.document.contract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class PaperW extends AppCompatActivity implements View.OnClickListener {

    ImageButton backib;
    FrameLayout workpF, photoF;
    long staffId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperw);

        backib=findViewById(R.id.paperw_backib); backib.setOnClickListener(this); //뒤로가기
        workpF=findViewById(R.id.paperw_workpF); workpF.setOnClickListener(this); //표준근로계약서 작성
        photoF=findViewById(R.id.paperw_photoF); photoF.setOnClickListener(this); //사진촬영
        Intent intent1 = getIntent();

        staffId = intent1.getLongExtra("staffId", 0);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.paperw_backib: // '뒤로가기' 선택 시
                intent = new Intent(getApplicationContext(), PaperWlist.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paperw_workpF: // '표준근로계약서' 선택 시
                intent = new Intent(getApplicationContext(), PaperWinput.class);
                intent.putExtra("staffId", staffId);
                startActivity(intent);
                finish();
                break;
            case R.id.paperw_photoF: // '사진 촬영' 선택 시
                intent = new Intent(getApplicationContext(), PaperWphoto.class);
                intent.putExtra("staffId", staffId);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}