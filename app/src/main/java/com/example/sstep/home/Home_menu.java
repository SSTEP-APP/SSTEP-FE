package com.example.sstep.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.sstep.R;

public class Home_menu extends AppCompatActivity implements View.OnClickListener {

    ImageButton closeIb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_menu);
        closeIb=findViewById(R.id.homemenu_closeIb);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.homemenu_closeIb:

                break;
            default:
                break;
        }
    }
}