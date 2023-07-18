package com.example.sstep.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sstep.R;

public class Home_staff extends AppCompatActivity implements View.OnClickListener {

    Button commuteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_staff);

        commuteBtn=findViewById(R.id.homestaff_commuteBtn); commuteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.homestaff_commuteBtn:
                break;
            default:
                break;
        }
    }

}