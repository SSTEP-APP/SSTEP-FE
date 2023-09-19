package com.example.sstep.document.contract;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.document.PhotoDialog;

import java.io.IOException;

public class PaperWphoto extends AppCompatActivity implements View.OnClickListener, PhotoDialog.PhotoDialogListener {

    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    ImageButton backib, cameraIb, deletePhotoBtn;
    Button completeBtn;
    private ImageView photoviewIv;
    boolean completeBtnState;
    FrameLayout photoF;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperwphoto);

        backib=findViewById(R.id.paperwphoto_backib); backib.setOnClickListener(this);
        cameraIb=findViewById(R.id.paperwphoto_cameraIb); cameraIb.setOnClickListener(this);
        completeBtn=findViewById(R.id.paperwphoto_completeBtn); completeBtn.setOnClickListener(this);
        deletePhotoBtn=findViewById(R.id.paperwphoto_deletePhotoBtn); deletePhotoBtn.setOnClickListener(this);
        photoviewIv=findViewById(R.id.paperwphoto_photoviewIv);
        photoF=findViewById(R.id.paperwphoto_photoF);

        baseDialog_okCenter = new BaseDialog_OkCenter(PaperWphoto.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(PaperWphoto.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.paperwphoto_backib: // '뒤로가기' 선택 시
                intent = new Intent(getApplicationContext(), PaperW.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paperwphoto_cameraIb: // '사진촬영' 선택 시
                showPhotoDialog();
                break;
            case R.id.paperwphoto_completeBtn: // 입력완료 버튼
                showCompleteDl();
                break;
            case R.id.paperwphoto_deletePhotoBtn: // 사진 삭제 버튼
                photoviewIv.setImageBitmap(null);
                photoF.setVisibility(View.GONE);
                cameraIb.setVisibility(View.VISIBLE);
                checkInputValidity();
                break;
            default:
                break;
        }
    }

    // '계약서 작성완료'버튼 클릭 시
    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        join_okdl_commentTv.setText("표준 근로계약서 등록 완료하였습니다.");
        // '회원가입 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaperWphoto.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 사진 촬영 다이얼로그 띄우기
    public void showPhotoDialog() {
        PhotoDialog photoDialog = new PhotoDialog(PaperWphoto.this, PaperWphoto.this);
        photoDialog.show();
    }

    public void onPhotoSelected(Bitmap bitmap) {
        photoviewIv.setImageBitmap(bitmap);
        photoviewIv.setClipToOutline(true);
        photoF.setVisibility(View.VISIBLE);
        cameraIb.setVisibility(View.GONE);
        checkInputValidity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE: // 카메라
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    onPhotoSelected(bitmap);
                    break;
                case GALLERY_REQUEST_CODE: // 갤러리
                    Uri uri = data.getData();
                    try {
                        Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        onPhotoSelected(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
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
}