package com.example.sstep.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.sstep.R;
import com.example.sstep.user.login.Find_password;

public class Home_Ceo extends AppCompatActivity implements View.OnClickListener {

    ImageButton menuIBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_ceo);
        menuIBtn=findViewById(R.id.homeceo_menuIBtn); menuIBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.homeceo_menuIBtn: // 메뉴
                intent = new Intent(getApplicationContext(), Home_menu.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}