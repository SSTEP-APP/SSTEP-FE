package com.example.sstep.todo.notice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.AppInData;
import com.example.sstep.R;
import com.example.sstep.document.work_doc_api.ByteArrayTypeAdapter;
import com.example.sstep.document.work_doc_api.PhotoResponseDto;
import com.example.sstep.todo.notice.notice_api.NoticeApiService;
import com.example.sstep.todo.notice.notice_api.NoticeResponseDto;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notice_view extends AppCompatActivity implements View.OnClickListener {

    AppInData appInData;
    long staffId, noticeId;
    String staffName;
    boolean isOwner;
    ImageButton backib;
    TextView nameTv, dateTv, titleTv, contentTv;
    String title, content, date;
    private ImageView[] photos = new ImageView[4]; // 이미지뷰 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_view);

        backib=findViewById(R.id.notice_view_backib); backib.setOnClickListener(this);
        nameTv=findViewById(R.id.notice_view_nameTv);
        dateTv=findViewById(R.id.notice_view_dateTv);
        titleTv=findViewById(R.id.notice_view_titleTv);
        contentTv=findViewById(R.id.notice_view_contentTv);
        photos[0]=findViewById(R.id.notice_view_pictureIv1);
        photos[1]=findViewById(R.id.notice_view_pictureIv2);
        photos[2]=findViewById(R.id.notice_view_pictureIv3);
        photos[3]=findViewById(R.id.notice_view_pictureIv4);

        // ID값 가지고 오기
        appInData = (AppInData) getApplication(); // MyApplication 클래스의 인스턴스 가져오기
        staffId = appInData.getStaffId();

        // Intent로 전달된 데이터 추출
        Intent intent = getIntent();
        noticeId = intent.getLongExtra("noticeId", 0L);
//        String content = intent.getStringExtra("content");
//        String name = intent.getStringExtra("name");
//        String date = intent.getStringExtra("date");

        // 추출한 데이터를 각 TextView에 설정
//        titleTv.setText(title);
//        contentTv.setText(content);
//        nameTv.setText(name);
//        dateTv.setText(date);



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

                    NoticeApiService apiService = retrofit.create(NoticeApiService.class);

                    Call<NoticeResponseDto> call = apiService.getNotice(noticeId); // noticeId
                    retrofit2.Response<NoticeResponseDto> response = call.execute();

                    if (response.isSuccessful()) {
                        // 서버로부터 NoticeResponseDto 가져오기
                        final NoticeResponseDto notices = response.body();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 성공적인 응답 처리
                                nameTv.setText(notices.getWriterName());
                                titleTv.setText(notices.getTitle());
                                dateTv.setText("작성일 : " + notices.getWriteDate());
                                contentTv.setText(notices.getContents());
                                // responseBody를 바이트 배열로 변환

                                try {
                                    // responseBody를 바이트 배열로 변환
                                    // photoDto에서 data 가져오기
                                    Set<PhotoResponseDto> photoResponseDtos = notices.getPhotoResponseDtos();

                                    if (photoResponseDtos != null) {
                                        int i = 0;
                                        for (PhotoResponseDto photoResponseDto : photoResponseDtos) {
                                            byte[] imageData = photoResponseDto.getData();
                                            // 바이트 배열을 Bitmap으로 변환
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                            // ImageView에 Bitmap 설정
                                            photos[i].setImageBitmap(bitmap);
                                            i++;
                                        }
                                    } else {
                                        Toast.makeText(Notice_view.this, "null", Toast.LENGTH_SHORT).show();
                                    }


                                } catch (NullPointerException e) {
                                    // 널 포인터 예외 처리
                                    // 예외 발생 시 오류 메시지 출력 또는 다른 예외 처리 작업 수행
                                    e.printStackTrace();
                                    // 또는 기본 이미지 설정
                                    Toast.makeText(Notice_view.this, "널포인터"+e, Toast.LENGTH_SHORT).show();
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

    private void loadImagesFromServer() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY) // 대소문자 구분
                .registerTypeAdapter(byte[].class, new ByteArrayTypeAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        NoticeApiService apiService = retrofit.create(NoticeApiService.class);

        Call<NoticeResponseDto> call = apiService.getNotice(noticeId); // noticeId
        call.enqueue(new retrofit2.Callback<NoticeResponseDto>() {
            @Override
            public void onResponse(Call<NoticeResponseDto> call, Response<NoticeResponseDto> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 NoticeResponseDto 가져오기
                    NoticeResponseDto noticeResponseDto = response.body();
                    if (noticeResponseDto != null) {
                        // 제목, 작성일자, 내용 가져오기
                        title = noticeResponseDto.getTitle();
                        date = noticeResponseDto.getWriteDate();
                        content = noticeResponseDto.getContents();

                        // TextView에 데이터 설정
                        titleTv.setText(title);
                        dateTv.setText(date);
                        contentTv.setText(content);

                        // 이미지 데이터 가져오기
                        Set<PhotoResponseDto> photoResponseDtos = noticeResponseDto.getPhotoResponseDtos();

                        if (photoResponseDtos != null) {
                            int totalImages = photoResponseDtos.size();
                            int displayedImages = 0;
                            int i = 0;
                            for (PhotoResponseDto photoResponseDto : photoResponseDtos) {
                                byte[] imageData = photoResponseDto.getData();
                                if (imageData != null) { // 이미지 데이터가 널이 아닌 경우에만 처리
                                    // 바이트 배열을 Bitmap으로 변환
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                    // ImageView에 Bitmap 설정
                                    if (i < 4) {
                                        photos[i].setImageBitmap(bitmap);
                                        i++;
                                        displayedImages++;
                                    }
                                } else {
                                    // 이미지 데이터가 널인 경우 오류 메시지 표시
                                    Toast.makeText(Notice_view.this, "이미지 데이터가 널입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            // Toast 메시지로 이미지 개수 표시
                            Toast.makeText(Notice_view.this, "이미지 " + displayedImages + "개 중 " + totalImages + "개 가져옴", Toast.LENGTH_SHORT).show();
                        } else {
                            // photoResponseDtos가 널인 경우 오류 메시지 표시
                            Toast.makeText(Notice_view.this, "이미지 목록이 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // 실패한 경우 처리
                    Toast.makeText(Notice_view.this, "데이터 가져오기 실패" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NoticeResponseDto> call, Throwable t) {
                // 실패한 경우 처리
                Toast.makeText(Notice_view.this, "데이터 가져오기 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void handleError(String errorMsg) {
        Toast.makeText(this, errorMsg + "!!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.notice_view_backib: // 뒤로가기
                intent = new Intent(getApplicationContext(), Notice.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 기존의 Notice 액티비티 스택을 모두 제거
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}