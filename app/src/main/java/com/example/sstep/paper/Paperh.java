package com.example.sstep.paper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class Paperh extends AppCompatActivity implements View.OnClickListener {

    ImageButton backib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperh);
        backib = findViewById(R.id.paperh_backib);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.paperh_backib: // '뒤로가기'
                Intent intent = new Intent(getApplicationContext(), Paper.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}