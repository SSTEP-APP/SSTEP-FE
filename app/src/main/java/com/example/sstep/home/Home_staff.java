package com.example.sstep.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sstep.R;
import com.example.sstep.commute.Commute_map;
import com.example.sstep.performance.MonthState;

public class Home_staff extends AppCompatActivity implements View.OnClickListener {

    Button commuteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_staff);

        commuteBtn=findViewById(R.id.homestaff_commuteBtn); commuteBtn.setOnClickListener(this);

        // Commute_map으로부터 전달된 값 읽기
        boolean isGoingToWork = getIntent().getBooleanExtra("isGoingToWork", true);
        if (isGoingToWork) {
            commuteBtn.setText("퇴근하기");
        } else {
            commuteBtn.setText("출근하기");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.homestaff_commuteBtn: // 출근하기
                if (commuteBtn.getText().toString().equals("출근하기")) {
                    intent = new Intent(getApplicationContext(), Commute_map.class);
                    intent.putExtra("inout", "in");
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(getApplicationContext(), Commute_map.class);
                    intent.putExtra("inout", "out");
                    startActivity(intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }

}