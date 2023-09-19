package com.example.sstep.todo.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.document.work_doc_api.ByteArrayTypeAdapter;
import com.example.sstep.home.Home_Ceo;
import com.example.sstep.store.store_api.NullOnEmptyConverterFactory;
import com.example.sstep.todo.notice.notice_api.NoticeApiService;
import com.example.sstep.todo.notice.notice_api.NoticeResponseDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Set;

public class Notice extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Notice";
    private RecyclerView mRecyclerView;
    private Notice_RecyclerViewAdpater mRecyclerViewAdapter;
    private Set<Notice_recyclerViewItem> set = new HashSet<>();
    private LinearLayout nodataLayout, dataLayout;
    ImageButton plusbtn, backib;
    TextView listCountNumTv; // 공지사항 리스트 개수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);
        plusbtn = findViewById(R.id.notice_plusbtn); plusbtn.setOnClickListener(this);
        backib=findViewById(R.id.notice_backib); backib.setOnClickListener(this);
        listCountNumTv=findViewById(R.id.notice_listCountNumTv);

        // 리사이클 뷰
        mRecyclerView = (RecyclerView) findViewById(R.id.notice_recycleView);
        nodataLayout = (LinearLayout) findViewById(R.id.notice_nodataLayout);
        dataLayout = (LinearLayout)  findViewById(R.id.notice_dataLayout);
        mRecyclerView.setHasFixedSize(true); // 리사이클러뷰의 크기가 고정됨을 설정


        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY) // 대소문자 구분
                .registerTypeAdapter(byte[].class, new ByteArrayTypeAdapter())
                .create();

        // 공지사항 목록 조회
        try {
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            NoticeApiService apiService = retrofit.create(NoticeApiService.class);

            Call<Set<NoticeResponseDto>> call = apiService.getNotices(2L); // storeId
            call.enqueue(new Callback<Set<NoticeResponseDto>>() {
                @Override
                public void onResponse(Call<Set<NoticeResponseDto>> call, Response<Set<NoticeResponseDto>> response) {
                    if (response.isSuccessful()) {
                        Set<NoticeResponseDto> noticeSet = response.body();
                        if (noticeSet != null && !noticeSet.isEmpty()) {
                            set = createRecyclerViewItemSet(noticeSet); // 공지사항 데이터를 아이템으로 변환하여 Set에 저장
                            updateUI(); // UI 업데이트
                        } else {
                            showNoDataLayout(); // 데이터가 없는 경우 레이아웃 업데이트
                        }
                    } else {
                        // 기타 다른 상태 코드 처리
                        try {
                            String errorResponse = response.errorBody().string();
                            Toast.makeText(Notice.this, "공지사항 조회 실패!! 에러 메시지: " + errorResponse, Toast.LENGTH_SHORT).show();
                            // 에러 메시지를 사용하여 추가적인 처리 수행
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Set<NoticeResponseDto>> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = t != null ? t.getMessage() : "Unknown error";
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });


        }catch (Exception e) {
            e.printStackTrace();
        }

    }



    // 공지사항 데이터를 아이템으로 변환하여 Set에 저장하는 함수
    private Set<Notice_recyclerViewItem> createRecyclerViewItemSet(Set<NoticeResponseDto> noticeSet) {
        Set<Notice_recyclerViewItem> itemSet = new HashSet<>();
        for (NoticeResponseDto notice : noticeSet) {
            Notice_recyclerViewItem item = new Notice_recyclerViewItem(
                    notice.getTitle(),
                    notice.getWriteDate(),
                    notice.getContents()
            );
            itemSet.add(item);
        }
        return itemSet;
    }

    // UI를 업데이트하는 함수
    private void updateUI() {
        nodataLayout.setVisibility(View.GONE);
        dataLayout.setVisibility(View.VISIBLE);

        mRecyclerViewAdapter = new Notice_RecyclerViewAdpater(getApplicationContext(), new ArrayList<>(set));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listCountNumTv.setText(String.valueOf(set.size()));
        mRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
    }

    private void showNoDataLayout() {
        nodataLayout.setVisibility(View.VISIBLE);
        dataLayout.setVisibility(View.GONE);
    }

    private void showErrorToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.notice_backib:
                intent = new Intent(getApplicationContext(), Home_Ceo.class);
                startActivity(intent);
                finish();
                break;
            case R.id.notice_plusbtn: // '추가 plus'버튼 선택 시
                intent = new Intent(getApplicationContext(), Notice_input.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}