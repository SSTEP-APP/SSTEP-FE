package com.example.sstep.joinlogin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.mypage.MyPage_Profile;
import com.example.sstep.store.SelectStore;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button logIn_completeBtn, logIn_sighIn, logIn_searchId, logIn_searchPass;
    ImageButton logIn_kakaoBtn, logIn_naverBtn, logIn_googleBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        logIn_completeBtn = findViewById(R.id.logIn_completeBtn); logIn_completeBtn.setOnClickListener(this);
        logIn_sighIn = findViewById(R.id.logIn_sighIn); logIn_sighIn.setOnClickListener(this);
        logIn_searchId = findViewById(R.id.logIn_searchId); logIn_searchId.setOnClickListener(this);
        logIn_searchPass = findViewById(R.id.logIn_searchPass); logIn_searchPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.logIn_completeBtn:
                intent = new Intent(getApplicationContext(), SelectStore.class);
                startActivity(intent);
                finish();
                break;
            case R.id.logIn_sighIn:
                intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.logIn_searchId:
                intent = new Intent(getApplicationContext(), Find_id.class);
                startActivity(intent);
                finish();
                break;
            case R.id.logIn_searchPass:
                intent = new Intent(getApplicationContext(), Find_password.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}