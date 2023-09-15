package com.example.sstep.document.certificate;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.CalendarDialog;
import com.example.sstep.R;
import com.example.sstep.document.PhotoDialog;
import com.example.sstep.document.healthdoc_api.HealthDocApiService;
import com.example.sstep.document.healthdoc_api.HealthDocRequestDto;
import com.example.sstep.document.work_doc_api.PhotoApiService;
import com.example.sstep.document.work_doc_api.PhotoResponseDto;
import com.example.sstep.document.work_doc_api.WorkDocApiService;
import com.example.sstep.store.store_api.StoreRegisterReqDto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;

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

    long photoId;

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
        new PhotoTask().execute();
    }

    // 달력 다이얼로그 띄우기
    public void showCalendarDialog() {
        CalendarDialog calendarDialog = new CalendarDialog(PaperHinput.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 사용자가 날짜를 선택하면 호출되는 콜백 메서드
                        // 여기에 선택한 날짜 처리 코드를 작성합니다.
                        String dateString = year + "-" + (month + 1) + "-" + dayOfMonth;
                        dateBtn.setText(dateString);

                        // '만료일'에 1년 더해 입력
                        enddateTv.setText(year+1 + "-" + (month + 1) + "-" + dayOfMonth);
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

    /*
    private void savePhoto(Bitmap photo){

        try {
            //file을 requestBody타입으로 변경
            Bitmap photoBitmap = ((BitmapDrawable) photoviewIv.getDrawable()).getBitmap();
            // 1. Bitmap을 파일로 저장
            File bitmapFile = convertBitmapToFile(photoBitmap);
            // 2. 파일을 RequestBody로 변환
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), bitmapFile);
            // 3. RequestBody를 MultipartBody.Part로 변환
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", bitmapFile.getName(), requestFile);

            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PhotoApiService apiService = retrofit.create(PhotoApiService.class);
            Call<PhotoResponseDto> call = apiService.savePhoto(body);

            call.enqueue(new Callback<PhotoResponseDto>() {
                @Override
                public void onResponse(Call<PhotoResponseDto> call, Response<PhotoResponseDto> response) {
                    if (response.isSuccessful()) {
                        PhotoResponseDto photoResponse = response.body();
                        photoId = photoResponse.getId();
                        // 업로드 성공 처리
                    } else {
                        // 업로드 실패 처리
                        Toast.makeText(PaperHinput.this, "사진 저장에 실패했습니다." + response, Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<PhotoResponseDto> call, Throwable t) {
                    Toast.makeText(PaperHinput.this, "사진 저장에 실패했습니다!!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }catch (Exception e) {
            e.printStackTrace();
        }


    }

     */


    private class PhotoTask extends AsyncTask<Void, Void, Long> {
        @Override
        protected Long doInBackground(Void... params) {

            Bitmap photoBitmap = ((BitmapDrawable) photoviewIv.getDrawable()).getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); // CompressFormat을 JPEG로 변경
            byte[] byteArray = stream.toByteArray();
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray); // MediaType을 "image/jpeg"로 변경
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", 19 + "hdoc.jpeg", requestFile); // 파일 이름을 "hdoc.jpg"로 변경

            // 네트워크 요청을 백그라운드 스레드에서 수행
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PhotoApiService apiService2 = retrofit.create(PhotoApiService.class);
            Call<Long> call2 = apiService2.savePhoto(body);

            try {
                Response<Long> response = call2.execute();

                if (response.isSuccessful()) {
                    return response.body(); // 성공한 경우 이미지 ID 반환
                } else {

                    return response.body();

                }
            } catch (IOException e) {
                e.printStackTrace();
                return -1L; // 예외 발생한 경우 -1 반환 또는 다른 실패 코드 반환
            }
        }

        @Override
        protected void onPostExecute(Long result) {
            showComplete_dialog.show();
            // 다이얼로그의 배경을 투명으로 만든다.
            showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView join_okdl_commentTv;
            Button join_okdl_okBtn;
            join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
            join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
            join_okdl_commentTv.setText("로딩중");

            if (result != null) {
                if (result != -1L) {
                    //네트워크 요청 구현
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    HealthDocRequestDto healthDocRequestDto = new HealthDocRequestDto(
                            dateBtn.getText().toString().trim(),
                            enddateTv.getText().toString().trim(),
                            result
                    );
                    HealthDocApiService apiService = retrofit.create(HealthDocApiService.class);
                    Call<Void> call = apiService.registerHealthDoc(22L, healthDocRequestDto);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {

                                // 파일 업로드 성공
                                join_okdl_commentTv.setText("보건증 작성을 완료하였습니다.");
                                Toast.makeText(getApplicationContext(), "보건증 작성을 완료하였습니다.", Toast.LENGTH_SHORT).show();

                            } else {
                                // 파일 업로드 실패
                                join_okdl_commentTv.setText("보건증 작성에 실패했습니다." + response);
                                Toast.makeText(getApplicationContext(), "보건증 작성을 실패했습니다." + response.body(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            join_okdl_commentTv.setText("보건증 작성이 실패했습니다."+t.getMessage());
                            Toast.makeText(getApplicationContext(), "보건증 작성이 실패했습니다."+t.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    });
                    // 이미지 ID를 사용하여 이미지를 가져오거나 다른 작업 수행

                    // Toast로 결과를 표시
                } else {
                    String errorMessage = "보건증 작성이 실패했습니다!";
                    join_okdl_commentTv.setText(errorMessage);

                    // Toast로 실패 메시지 표시
                }
            } else {
                String nullMessage = "서버 응답이 null입니다.";
                join_okdl_commentTv.setText(nullMessage);

            }
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
    }

}