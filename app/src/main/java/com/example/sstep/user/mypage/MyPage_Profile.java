package com.example.sstep.user.mypage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sstep.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPage_Profile extends AppCompatActivity implements View.OnClickListener{

    EditText nameEt, phoneEt, emailEt;
    ImageButton backib, cameraIb;
    CircleImageView profileIv;
    Intent intent;
    private static final int REQ_CODE_SELECT_CAMERA = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_profile);

        nameEt = findViewById(R.id.mypage_profile_nameEt);
        phoneEt = findViewById(R.id.mypage_profile_phoneEt);
        emailEt = findViewById(R.id.mypage_profile_emailEt);
        profileIv = findViewById(R.id.mypage_profile_profileIv);

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
                startActivityForResult(intent, REQ_CODE_SELECT_CAMERA);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 카메라 촬영을 하면 이미지뷰에 사진 삽입
        if(requestCode == REQ_CODE_SELECT_CAMERA && resultCode == RESULT_OK) {
            // Bundle로 데이터를 입력
            Bundle extras = data.getExtras();

            // Bitmap으로 컨버전
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // 이미지뷰에 Bitmap으로 이미지를 입력
            profileIv.setImageBitmap(imageBitmap);
        }
    }
}