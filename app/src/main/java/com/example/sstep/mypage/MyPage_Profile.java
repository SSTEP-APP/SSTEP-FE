package com.example.sstep.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sstep.R;

public class MyPage_Profile extends AppCompatActivity {

    Button camerabtn;
    ImageButton backib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_profile);
        ActivityCompat.requestPermissions(this, new String[]
                {android.Manifest.permission.CAMERA}, MODE_PRIVATE);
        backib = findViewById(R.id.mypage_profile_backib);
        camerabtn = findViewById(R.id.mypage_profile_camerabtn);

        onCamera();
        // '뒤로가기' 선택 시
        backib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void onCamera(){
        
    }
}