package com.example.sstep.document.certificate;

import android.app.DatePickerDialog;
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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.CalendarDialog;
import com.example.sstep.R;
import com.example.sstep.document.PhotoDialog;
import com.example.sstep.user.login.Login;

import java.io.IOException;

public class PaperHinput extends AppCompatActivity implements View.OnClickListener, PhotoDialog.PhotoDialogListener {

    ImageButton backib,cameraIb,deletePhotoBtn;
    EditText nameEt;
    Button dateBtn, completeBtn;
    TextView enddateTv;
    FrameLayout photoF;
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private ImageView photoviewIv;
    boolean completeBtnState;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperhinput);

        backib=findViewById(R.id.paperhinput_backib); backib.setOnClickListener(this);
        cameraIb=findViewById(R.id.paperhinput_cameraIb); cameraIb.setOnClickListener(this);
        dateBtn=findViewById(R.id.paperhinput_dateBtn); dateBtn.setOnClickListener(this);
        completeBtn=findViewById(R.id.paperhinput_completeBtn); completeBtn.setOnClickListener(this);
        deletePhotoBtn=findViewById(R.id.paperhinput_deletePhotoBtn); deletePhotoBtn.setOnClickListener(this);

        photoF=findViewById(R.id.paperhinput_photoF);
        nameEt=findViewById(R.id.paperhinput_nameEt);
        enddateTv=findViewById(R.id.paperhinput_enddateTv);
        photoviewIv=findViewById(R.id.paperhinput_photoviewIv);

        baseDialog_okCenter = new BaseDialog_OkCenter(PaperHinput.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(PaperHinput.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        nameEt.addTextChangedListener(new TextWatcher() {
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

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.paperhinput_backib: // 뒤로가기
                intent = new Intent(getApplicationContext(), PaperH.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paperhinput_cameraIb: // 사진촬영
                showPhotoDialog();
                break;
            case R.id.paperhinput_dateBtn: // 검진일 버튼
                showCalendarDialog();
                break;
            case R.id.paperhinput_completeBtn: // 입력완료 버튼
                showCompleteDl();
                break;
            case R.id.paperhinput_deletePhotoBtn: // 사진 삭제 버튼
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
        join_okdl_commentTv.setText("보건증 작성 완료하였습니다.");
        // '회원가입 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Paper.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 달력 다이얼로그 띄우기
    public void showCalendarDialog() {
        CalendarDialog calendarDialog = new CalendarDialog(PaperHinput.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 사용자가 날짜를 선택하면 호출되는 콜백 메서드
                        // 여기에 선택한 날짜 처리 코드를 작성합니다.
                        String dateString = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";
                        dateBtn.setText(dateString);

                        // '만료일'에 1년 더해 입력
                        enddateTv.setText(year+1 + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                        checkInputValidity();
                    }
                });

        // 다이얼로그 띄우기
        calendarDialog.show();
    }

    // 사진 촬영 다이얼로그 띄우기
    public void showPhotoDialog() {
        PhotoDialog photoDialog = new PhotoDialog(PaperHinput.this, PaperHinput.this);
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
        boolean isNameEt = !nameEt.getText().toString().trim().isEmpty(); // true
        boolean isDateBtn = !enddateTv.getText().toString().equals("만료일"); // true

        completeBtnState = isPhotoviewIv && isNameEt && isDateBtn;
        if (completeBtnState==true){
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
        }
    }
}