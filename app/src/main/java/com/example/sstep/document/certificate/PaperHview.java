package com.example.sstep.document.certificate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.document.contract.PaperWinput;
import com.example.sstep.document.healthdoc_api.HealthDocApiService;
import com.example.sstep.document.healthdoc_api.HealthDocResponseDto;
import com.example.sstep.document.work_doc_api.ByteArrayTypeAdapter;
import com.example.sstep.document.work_doc_api.PhotoResponseDto;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaperHview extends AppCompatActivity implements View.OnClickListener {

    ImageButton backib;
    Button modiBtn1, reBtn2;
    TextView nameTv,regDateTv, endDateTv;
    ImageView photo;
    long staffId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperhview);

        backib=findViewById(R.id.paperview_backib); backib.setOnClickListener(this);
        modiBtn1=findViewById(R.id.paperview_modiBtn1); modiBtn1.setOnClickListener(this);
        reBtn2=findViewById(R.id.paperview_reBtn2); reBtn2.setOnClickListener(this);
        nameTv = findViewById(R.id.paperview_nametv1);
        regDateTv = findViewById(R.id.paperview_numtv2);
        endDateTv = findViewById(R.id.paperview_datetv3);
        photo = findViewById(R.id.paperview_photoiv1);
        TextView test = findViewById(R.id.paperview_tv1);

        // Intent에서 데이터 받기
        Intent intent = getIntent();
        staffId = intent.getLongExtra("staffId",1);

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
                    HealthDocApiService apiService = retrofit.create(HealthDocApiService.class);

                    Call<HealthDocResponseDto> call = apiService.getHealthDoc(staffId);
                    retrofit2.Response<HealthDocResponseDto> response = call.execute();

                    if (response.isSuccessful()) {
                        final HealthDocResponseDto codeStaffs = response.body();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 성공적인 응답 처리
                                nameTv.setText(codeStaffs.getName());
                                regDateTv.setText(codeStaffs.getCheckUpDate());
                                endDateTv.setText(codeStaffs.getExpirationDate());
                                // responseBody를 바이트 배열로 변환

                                try {
                                    // responseBody를 바이트 배열로 변환
                                    // photoDto에서 data 가져오기
                                    PhotoResponseDto photoResponseDto = codeStaffs.getPhotoResponseDto();

                                    if (photoResponseDto != null) {
                                        byte[] imageData = photoResponseDto.getData();
                                        photoResponseDto.getFileName();
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                        photo.setImageBitmap(bitmap);
                                    } else {
                                        Toast.makeText(PaperHview.this, "null", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (NullPointerException e) {
                                    // 널 포인터 예외 처리
                                    // 예외 발생 시 오류 메시지 출력 또는 다른 예외 처리 작업 수행
                                    e.printStackTrace();
                                    // 또는 기본 이미지 설정
                                    Toast.makeText(PaperHview.this, "널포인터"+e, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.paperview_backib: // '뒤로가기' 선택 시
                intent = new Intent(getApplicationContext(), PaperH.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paperview_modiBtn1: // 수정하기
                intent = new Intent(getApplicationContext(), PaperHmodi.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paperview_reBtn2: // 갱신하기
                intent = new Intent(getApplicationContext(), PaperWinput.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
    private void handleError(String errorMsg) {
        Toast.makeText(this, errorMsg + "!!", Toast.LENGTH_SHORT).show();

    }
}