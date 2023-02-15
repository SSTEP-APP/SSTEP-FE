package com.example.sstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StaffInvite3 extends AppCompatActivity {

    ImageView staffInvite3_backiv;
    TextView staffInvite3_nobtn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffinvite3);
        staffInvite3_backiv = findViewById(R.id.staffInvite3_backiv);

        staffInvite3_backiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StaffInvite.class);
                startActivity(intent);
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