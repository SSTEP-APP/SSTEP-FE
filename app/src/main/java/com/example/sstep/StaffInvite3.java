package com.example.sstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StaffInvite3 extends AppCompatActivity {

    ImageButton staffInvite3_backib;
    TextView staffInvite3_nobtn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffinvite3);
        staffInvite3_backib = findViewById(R.id.staffInvite3_backib);

        // '뒤로가기' 선택 시
        staffInvite3_backib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StaffInvite.class);
                startActivity(intent);
                finish();
            }
        });

        // '거절' dialog
        staffInvite3_nobtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}