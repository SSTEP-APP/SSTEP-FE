package com.example.sstep.todo.notice;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.AppInData;
import com.example.sstep.R;
import com.example.sstep.alarm.Alarm1_RecyclerViewAdpater;
import com.example.sstep.alarm.Alarm1_recyclerViewWordItemData;
import com.example.sstep.home.Home_Ceo;
import com.example.sstep.store.SelectStore;
import com.example.sstep.store.SelectStore_RecyclerViewAdpater;
import com.example.sstep.store.SelectStore_recyclerViewItem;
import com.example.sstep.store.store_api.NullOnEmptyConverterFactory;
import com.example.sstep.store.store_api.StoreResponseDto;
import com.example.sstep.todo.notice.notice_api.NoticeApiService;
import com.example.sstep.todo.notice.notice_api.NoticeRequestDto;
import com.example.sstep.todo.notice.notice_api.NoticeResponseDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.alarm.Alarm1_RecyclerViewAdpater;
import com.example.sstep.alarm.Alarm1_recyclerViewWordItemData;
import com.example.sstep.home.Home_Ceo;

import java.util.ArrayList;
import java.util.Set;

public class Notice extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Notice";
    private RecyclerView mRecyclerView;
    private Notice_RecyclerViewAdpater mRecyclerViewAdapter;
    private Set<Notice_recyclerViewWordItemData> set = new HashSet<>();
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

        /*
        try {
            if(list.isEmpty()){
                nodataLayout.setVisibility(View.VISIBLE);
                dataLayout.setVisibility(View.GONE);
            }else{
                nodataLayout.setVisibility(View.GONE);
                dataLayout.setVisibility(View.VISIBLE);
                mRecyclerViewAdapter = new Notice_RecyclerViewAdpater(getApplicationContext(), list);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecyclerView.setAdapter(mRecyclerViewAdapter);

                // 리스트 개수를 텍스트뷰에 설정
                listCountNumTv.setText(String.valueOf(list.size()));
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("error", e.toString());
        }
         */

        // 공지사항 목록 조회
        fetchNoticeList();

    }

    // 공지사항 목록 조회
    private void fetchNoticeList() {
        try {
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
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
    private Set<Notice_recyclerViewWordItemData> createRecyclerViewItemSet(Set<NoticeResponseDto> noticeSet) {
        Set<Notice_recyclerViewWordItemData> itemSet = new HashSet<>();
        for (NoticeResponseDto notice : noticeSet) {
            Notice_recyclerViewWordItemData item = new Notice_recyclerViewWordItemData(
                    notice.getTitle(),
                    notice.getWriteDate(),
                    notice.getContents(),
                    0,
                    null
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