package com.example.sstep.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class Money_Staff extends AppCompatActivity implements View.OnClickListener{
    ImageButton backib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_staff);

        backib=findViewById(R.id.money_staff_backib); backib.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.money_staff_backib: // '뒤로가기'
                Intent intent = new Intent(getApplicationContext(), Money_Ceo.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}