package com.example.sstep.todo.checklist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class Checklist_detail extends AppCompatActivity {
    TextView endTimeText, staffName, checkTime;
    EditText memoEt;
    Button addImageBtn;
    ImageButton backBtn;
    private final int GET_GALLERY_IMAGE = 200;
    ImageView addImageview;
    private String selectType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_detail);
        endTimeText = findViewById(R.id.checkDetail_endTimeText);
        staffName = findViewById(R.id.checkDetail_staffNameText);
        checkTime = findViewById(R.id.checkDetail_checkTimeText);
        memoEt = findViewById(R.id.checkDetail_memoEt);
        addImageBtn = findViewById(R.id.checkDetail_addImageBtn);
        addImageview = findViewById(R.id.checkDetail_addImageview);
        backBtn = findViewById(R.id.checkList_backBtn);


        //뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckList.class);
                startActivity(intent);
                finish();
            }
        });


        //마감예정시간 텍스트
        endTimeText.setText("2023년 2월 10일 13:00까지"); //db연동시 수정

        //담당직원 텍스트
        staffName.setText("000"); //db연동시 수정

        //확인시간 텍스트
        checkTime.setText("확인전"); //db연동시 수정

        //dialog
        try {
            addImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckList_photo_dialog checkList_photo_dialog = new CheckList_photo_dialog
                            (Checklist_detail.this, new CheckList_photo_dialog.CheckList_photo_dialogListener() {
                                public void clickBtn(String data) {
                                    selectType = data;
                                    //사진 등록
                                    if (selectType.equals("gallery")) {
                                        Intent intent = new Intent(Intent.ACTION_PICK);
                                        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                        startActivityForResult(intent, GET_GALLERY_IMAGE);
                                        selectType="";
                                    } else if (selectType.equals("camera")) {
                                        // 카메라 기능을 Intent
                                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(i, 0);
                                        selectType="";
                                    }
                                    else {
                                        selectType="";
                                    }
                                }
                            });
                    checkList_photo_dialog.show();

                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            Log.i("error", e+"에러");
        }



        //메모 텍스트
        memoEt.getText();


    }

    //사진 받아서 이미지뷰에 적용하기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //갤러리에서 이미지뷰 업로드
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            addImageview.setImageURI(selectedImageUri);

        }
        //사진 찍어서 이미지뷰에 업로드
        if (requestCode == 0 && resultCode == RESULT_OK) {
            // Bundle로 데이터를 입력
            Bundle extras = data.getExtras();

            // Bitmap으로 컨버전
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // 이미지뷰에 Bitmap으로 이미지를 입력
            addImageview.setImageBitmap(imageBitmap);
        }
    }
}