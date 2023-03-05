package com.example.sstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StaffInvite extends AppCompatActivity {

    Button staffInvite1_bottomonbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffinvite1);
        staffInvite1_bottomonbtn = findViewById(R.id.staffInvite1_bottomonbtn);

        // 맨 아래 '직원초대' 클릭 시
        staffInvite1_bottomonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StaffInvite2.class);
                startActivity(intent);
                finish();
            }
        });

    }
}