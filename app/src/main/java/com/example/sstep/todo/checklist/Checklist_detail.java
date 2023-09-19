package com.example.sstep.todo.checklist;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.example.sstep.document.certificate.Paper;
import com.example.sstep.document.certificate.PaperHinput;
import com.example.sstep.document.certificate.PaperHview;
import com.example.sstep.document.healthdoc_api.HealthDocApiService;
import com.example.sstep.document.healthdoc_api.HealthDocRequestDto;
import com.example.sstep.document.healthdoc_api.HealthDocResponseDto;
import com.example.sstep.document.work_doc_api.PhotoApiService;
import com.example.sstep.document.work_doc_api.PhotoResponseDto;
import com.example.sstep.todo.checklist.checklist_api.CheckListResponseDto;
import com.example.sstep.todo.checklist.checklist_api.ChecklistApiService;
import com.example.sstep.todo.checklist.checklist_api.ChecklistRequestDto;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    long checkListId, staffId;

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

        //앱데이터 가져오기
        staffId = 22;

        // 인텐트를 가져옴
        Intent intent = getIntent();
        // 인텐트로부터 데이터를 추출
        checkListId = intent.getLongExtra("checkListId", -1); // "checkListId"라는 이름으로 long 데이터를 받음

        //db에서 정보 받기
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ChecklistApiService apiService = retrofit.create(ChecklistApiService.class);


                    Call<CheckListResponseDto> call = apiService.getCheckList(checkListId);

                    call.enqueue(new Callback<CheckListResponseDto>() {
                        @Override
                        public void onResponse(Call<CheckListResponseDto> call, Response<CheckListResponseDto> response) {
                            if (response.isSuccessful()) {
                                CheckListResponseDto checklistResponse = response.body();
                                // 처리할 작업을 수행합니다.
                                endTimeText.setText(checklistResponse.getEndDay()); //db연동시 수정

                                //제목 텍스트
                                titleTv.setText(checklistResponse.getTitle()); //db연동시 수정

                                //할일 내용 텍스트
                                contentTv.setText(checklistResponse.getContents()); //db연동시 수정
                                Log.d("Myapp", checklistResponse.getContents());
                            } else {
                                // 오류 처리
                            }
                        }

                        @Override
                        public void onFailure(Call<CheckListResponseDto> call, Throwable t) {
                            // 실패 처리
                        }
                    });
                } catch (Exception e) {
                    final String errorMsg = e.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handleError(errorMsg);
                        }
                    });
                    // 예외 로그 출력
                    Log.e("MyApp", "예외 발생", e);
                }
            }
        }).start();

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
                new PhotoTask().execute();
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
    private void handleError(String errorMsg) {
        Toast.makeText(this, errorMsg + "!!", Toast.LENGTH_SHORT).show();

    }


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

                    ChecklistRequestDto checkListRequestDto = new ChecklistRequestDto(
                            true,
                            memoEt.getText().toString().trim(),
                            result
                    );

                    ChecklistApiService apiService = retrofit.create(ChecklistApiService.class);
                    Call<Void> call = apiService.completeCheckList(staffId, checkListId,checkListRequestDto);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {

                                // 파일 업로드 성공
                                join_okdl_commentTv.setText("할 일을 완료하였습니다.");
                                Toast.makeText(getApplicationContext(), "할 일을 완료하였습니다.", Toast.LENGTH_SHORT).show();

                            } else {
                                // 파일 업로드 실패
                                join_okdl_commentTv.setText("할 일 작성에 실패했습니다." + response);
                                Toast.makeText(getApplicationContext(), "보건증 작성을 실패했습니다." + response.body(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            join_okdl_commentTv.setText("할 일 작성이 실패했습니다!!"+t.getMessage());
                            Toast.makeText(getApplicationContext(), "보건증 작성이 실패했습니다!!"+t.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    });
                    // 이미지 ID를 사용하여 이미지를 가져오거나 다른 작업 수행

                    // Toast로 결과를 표시
                } else {
                    String errorMessage = "할 일 작성이 실패했습니다!";
                    join_okdl_commentTv.setText(errorMessage);

                    // Toast로 실패 메시지 표시
                }
            } else {
                String nullMessage = "서버 응답이 null입니다.";
                join_okdl_commentTv.setText(nullMessage);

            }

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
}