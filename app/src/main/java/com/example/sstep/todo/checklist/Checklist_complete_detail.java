package com.example.sstep.todo.checklist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.document.certificate.PaperHview;
import com.example.sstep.document.work_doc_api.ByteArrayTypeAdapter;
import com.example.sstep.document.work_doc_api.PhotoResponseDto;
import com.example.sstep.document.work_doc_api.WorkDocResponseDto;
import com.example.sstep.todo.checklist.checklist_api.CheckListResponseDto;
import com.example.sstep.todo.checklist.checklist_api.ChecklistApiService;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Checklist_complete_detail extends AppCompatActivity {

    TextView title, content, endTime, memo;
    ImageButton menuBtn, commentIBtn;
    ImageView backBtn, photoviewIv;
    EditText comment;
    FrameLayout photoF;
    long checkListId, staffId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_complete_detail);
        title = findViewById(R.id.checkComDetail_checkListNameText);
        menuBtn = findViewById(R.id.checkComDetail_menuBtn);
        content = findViewById(R.id.checkComDetail_checkListContentText);
        comment = findViewById(R.id.checkComDetail_commentET);
        backBtn = findViewById(R.id.checkComDetail_backBtn);
        photoviewIv = findViewById(R.id.checkComDetail_photoviewIv);
        photoF = findViewById(R.id.checkComDetail_photoF);
        commentIBtn = findViewById(R.id.checkComDetail_commentIBtn);
        endTime = findViewById(R.id.checkComDetail_checkListStaffTimeText);
        memo = findViewById(R.id.checkComDetail_checkListMemoText);

        //앱데이터 가져오기
        staffId = 22;

        // 인텐트를 가져옴
        Intent intent = getIntent();
        // 인텐트로부터 데이터를 추출
        checkListId = intent.getLongExtra("checkListId", -1); // "checkListId"라는 이름으로 long 데이터를 받음

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY) // 대소문자 구분
                .registerTypeAdapter(byte[].class, new ByteArrayTypeAdapter())
                .create();

        //정보 받기
        //db에서 정보 받기
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                    ChecklistApiService apiService = retrofit.create(ChecklistApiService.class);


                    Call<CheckListResponseDto> call = apiService.getCheckList(checkListId);

                    call.enqueue(new Callback<CheckListResponseDto>() {
                        @Override
                        public void onResponse(Call<CheckListResponseDto> call, Response<CheckListResponseDto> response) {
                            if (response.isSuccessful()) {
                                CheckListResponseDto checklistResponse = response.body();

                                endTime.setText(checklistResponse.getEndDay());
                                //제목 텍스트
                                title.setText(checklistResponse.getTitle());
                                //할일 내용 텍스트
                                content.setText(checklistResponse.getContents());
                                memo.setText(checklistResponse.getMemo());
                                try {
                                    // responseBody를 바이트 배열로 변환
                                    // photoDto에서 data 가져오기
                                    Set<PhotoResponseDto> checkPhoto= new HashSet<>();

                                    checkPhoto = checklistResponse.getPhotoResponseDto();

                                    for (PhotoResponseDto photoResponseDto : checkPhoto) {
                                        if (photoResponseDto != null) {
                                            byte[] imageData = photoResponseDto.getData();
                                            photoResponseDto.getFileName();
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                            photoviewIv.setImageBitmap(bitmap);
                                        } else {
                                            Toast.makeText(Checklist_complete_detail.this, "null", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                } catch (NullPointerException e) {
                                    // 널 포인터 예외 처리
                                    // 예외 발생 시 오류 메시지 출력 또는 다른 예외 처리 작업 수행
                                    e.printStackTrace();
                                    // 또는 기본 이미지 설정
                                    Toast.makeText(Checklist_complete_detail.this, "널포인터"+e, Toast.LENGTH_SHORT).show();
                                    // 예외 로그 출력
                                    Log.e("MyApp", "NullPointerException 발생", e);
                                }
                            } else {
                                // 오류 처리
                            }
                        }

                        @Override
                        public void onFailure(Call<CheckListResponseDto> call, Throwable t) {
                            // 실패 처리
                            Log.e("MyApp", "NullPointerException 발생", t);
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

        //메뉴 버튼
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                getMenuInflater().inflate(R.menu.checklist_detail_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.checklist_detail_delete) {
                            //db 삭제및 페이지 닫기?
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        //뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckList.class);
                startActivity(intent);
                finish();
            }
        });

        commentIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void handleError(String errorMsg) {
        Toast.makeText(this, errorMsg + "!!", Toast.LENGTH_SHORT).show();

    }


}