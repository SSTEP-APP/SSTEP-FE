package com.example.sstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StaffInvite extends AppCompatActivity {

    Button staffInvite1_bottombtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffinvite1);
        staffInvite1_bottombtn = findViewById(R.id.staffInvite1_bottombtn);

        staffInvite1_bottombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StaffInvite2.class);
                startActivity(intent);
            }
        });

    }
}