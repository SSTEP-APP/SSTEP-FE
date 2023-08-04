package com.example.sstep.todo.checklist;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;

import java.io.IOException;

public class Checklist_detail extends AppCompatActivity {
    TextView endTimeText, titleTv, contentTv;
    EditText memoEt;
    ImageButton backBtn, addImageBtn, deletePhotoBtn;
    private final int GET_GALLERY_IMAGE = 200;
    ImageView photoviewIv;
    private String selectType="";
    FrameLayout photoF;
    boolean completeBtnState;
    Button completeBtn;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_detail);
        endTimeText = findViewById(R.id.checkDetail_endDateTv);
        titleTv = findViewById(R.id.checkDetail_titleTv);
        contentTv = findViewById(R.id.checkDetail_contentTv);
        memoEt = findViewById(R.id.checkDetail_memoEt);
        addImageBtn = findViewById(R.id.checkDetail_addImageBtn);
        photoviewIv = findViewById(R.id.checkDetail_photoviewIv);
        backBtn = findViewById(R.id.checkList_backBtn);
        photoF = findViewById(R.id.checkDetail_photoF);
        deletePhotoBtn = findViewById(R.id.checkDetail_deletePhotoBtn);
        completeBtn = findViewById(R.id.checkDetail_completeBtn);

        baseDialog_okCenter = new BaseDialog_OkCenter(Checklist_detail.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(Checklist_detail.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결


        //뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckList.class);
                startActivity(intent);
                finish();
            }
        });

        // 사진 삭제 버튼
        deletePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoviewIv.setImageBitmap(null);
                photoF.setVisibility(View.GONE);
                addImageBtn.setVisibility(View.VISIBLE);
                checkInputValidity();
            }
        });

        // 완료 버튼
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCompleteDl();
            }
        });

        memoEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputValidity();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //마감예정시간 텍스트
        endTimeText.setText("2023년 2월 10일 13:00까지"); //db연동시 수정

        //담당직원 텍스트
        titleTv.setText("000"); //db연동시 수정

        //확인시간 텍스트
        contentTv.setText("확인전"); //db연동시 수정

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
                                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
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
            photoviewIv.setImageURI(selectedImageUri);
            try {
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                onPhotoSelected(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //사진 찍어서 이미지뷰에 업로드
        if (requestCode == 0 && resultCode == RESULT_OK) {
            // Bundle로 데이터를 입력
            Bundle extras = data.getExtras();

            // Bitmap으로 컨버전
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // 이미지뷰에 Bitmap으로 이미지를 입력
            photoviewIv.setImageBitmap(imageBitmap);
            onPhotoSelected(imageBitmap);
        }
    }

    public void onPhotoSelected(Bitmap bitmap) {
        photoviewIv.setImageBitmap(bitmap);
        photoviewIv.setClipToOutline(true);
        photoF.setVisibility(View.VISIBLE);
        addImageBtn.setVisibility(View.GONE);
        checkInputValidity();
    }

    private void checkInputValidity() {
        // 버튼 활성화&비활성화
        boolean isPhotoviewIv = photoF.getVisibility() == View.VISIBLE; // true

        completeBtnState = isPhotoviewIv;
        if (completeBtnState==true){
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
        }
    }

    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        join_okdl_commentTv.setText("해야할 일을 완료하였습니다.");
        // '로그인 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckList.class);
                startActivity(intent);
                finish();
            }
        });
    }
}