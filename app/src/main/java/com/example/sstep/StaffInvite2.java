package com.example.sstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class StaffInvite2 extends AppCompatActivity {

    ImageView staffInvite2_backiv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffinvite2);
        staffInvite2_backiv = findViewById(R.id.staffInvite2_backiv);

        staffInvite2_backiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StaffInvite.class);
                startActivity(intent);
            }
        });
    }
}