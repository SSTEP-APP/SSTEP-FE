package com.example.sstep.document.contract;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.document.work_doc_api.ByteArrayTypeAdapter;
import com.example.sstep.document.work_doc_api.PhotoResponseDto;
import com.example.sstep.document.work_doc_api.WorkDocApiService;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaperWceoview extends AppCompatActivity {
    ImageButton backIb;

    long staffId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperwceoview);

        Intent intent = getIntent();
        staffId = intent.getLongExtra("staffId", 0);
        backIb = findViewById(R.id.paperWceoview_backib);

        backIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getApplicationContext(), PaperWlist.class);
                startActivity(intent);
                finish();
            }
        });

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY) // 대소문자 구분
                .registerTypeAdapter(byte[].class, new ByteArrayTypeAdapter())
                .create();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                    WorkDocApiService apiService = retrofit.create(WorkDocApiService.class);

                    Call<PhotoResponseDto> call = apiService.getSecondWorkDoc(staffId);
                    retrofit2.Response<PhotoResponseDto> response = call.execute();

                    if (response.isSuccessful()) {
                        final PhotoResponseDto codeStaffs = response.body();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // responseBody를 바이트 배열로 변환
                                    // photoDto에서 data 가져오기

                                    if (codeStaffs != null) {
                                        byte[] imageData = codeStaffs.getData();
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                        ImageView photo = findViewById(R.id.paperWceoview_imageView);
                                        photo.setImageBitmap(bitmap);
                                        Toast.makeText(PaperWceoview.this, codeStaffs.getFileName()+codeStaffs.getId(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(PaperWceoview.this, "null", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (NullPointerException e) {
                                    // 널 포인터 예외 처리
                                    // 예외 발생 시 오류 메시지 출력 또는 다른 예외 처리 작업 수행
                                    e.printStackTrace();
                                    // 또는 기본 이미지 설정
                                    Toast.makeText(PaperWceoview.this, "널포인터"+e, Toast.LENGTH_SHORT).show();
                                    // 예외 로그 출력
                                    Log.e("MyApp", "NullPointerException 발생", e);
                                }
                            }
                        });
                    } else {
                        // 서버 응답 코드 확인
                        int responseCode = response.code();
                        // 서버 응답 데이터 확인
                        String responseData = response.errorBody().string();
                        // 에러 메시지 출력 또는 처리
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                handleError("서버 응답 오류 - 코드: " + responseCode + ", 데이터: " + responseData);
                            }
                        });
                        // 예외 로그 출력
                        Log.e("MyApp", "서버 응답 오류 - 코드: " + responseCode + ", 데이터: " + responseData);
                    }
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

    }

    private void handleError(String errorMsg) {
        Toast.makeText(this, errorMsg + "!!", Toast.LENGTH_SHORT).show();

    }
}