package com.example.sstep.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sstep.R;

public class MyPage_Profile extends AppCompatActivity implements View.OnClickListener{

    EditText nameEt, phoneEt, emailEt;
    ImageButton cameraIb;
    ImageButton backib;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_profile);

        nameEt = findViewById(R.id.mypage_profile_nameEt);
        phoneEt = findViewById(R.id.mypage_profile_phoneEt);
        emailEt = findViewById(R.id.mypage_profile_emailEt);

        backib = findViewById(R.id.mypage_profile_backib);
        backib.setOnClickListener(this);

        // 카메라 권한 요청
        cameraIb = findViewById(R.id.mypage_profile_cameraIb);
        cameraIb.setOnClickListener(this);
        ActivityCompat.requestPermissions(this, new String[]
                {android.Manifest.permission.CAMERA}, MODE_PRIVATE);

        // 전화번호 입력시 자동 '-' 입력
        phoneEt.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mypage_profile_backib: // '뒤로가기'
                intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
                finish();
                break;
            case R.id.mypage_profile_cameraIb: // '카메라'
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 30);
                break;
            default:
                break;
        }
    }
}