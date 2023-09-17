package com.example.sstep.commute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sstep.R;

public class Dispute_WriteCeo extends AppCompatActivity implements View.OnClickListener {

    ImageButton backib;
    TextView staffNameTv, disputeDateTv, workTimeTv, homeTimeTv, contentTv;
    Button noBtn, yesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispute_writeceo);

        backib=findViewById(R.id.cdwceo_backib); backib.setOnClickListener(this);
        workTimeTv=findViewById(R.id.cdwceo_workTimeTv); workTimeTv.setOnClickListener(this);
        homeTimeTv=findViewById(R.id.cdwceo_homeTimeTv); homeTimeTv.setOnClickListener(this);
        noBtn=findViewById(R.id.cdwceo_noBtn); noBtn.setOnClickListener(this);
        yesBtn=findViewById(R.id.cdwceo_yesBtn); yesBtn.setOnClickListener(this);
        staffNameTv=findViewById(R.id.cdwceo_staffNameTv);
        disputeDateTv=findViewById(R.id.cdwceo_disputeDateTv);
        contentTv=findViewById(R.id.cdwceo_contentTv);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.cdwceo_backib:
                intent = new Intent(getApplicationContext(), Dispute_CeoList.class);
                startActivity(intent);
                finish();
                break;
            case R.id.cdwceo_noBtn: // 반려
                break;
            case R.id.cdwceo_yesBtn: // 승인
                break;
            default:
                break;
        }
    }
}