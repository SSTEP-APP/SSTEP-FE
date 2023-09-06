package com.example.sstep.commute;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sstep.R;
import com.example.sstep.commute.commute_api.CommuteApiService;
import com.example.sstep.commute.commute_api.CommuteResponseDto;
import com.example.sstep.store.store_api.NullOnEmptyConverterFactory;
import com.example.sstep.todo.notice.Notice;
import com.example.sstep.todo.notice.Notice_RecyclerViewAdpater;
import com.example.sstep.todo.notice.Notice_recyclerViewWordItemData;
import com.example.sstep.todo.notice.notice_api.NoticeApiService;
import com.example.sstep.todo.notice.notice_api.NoticeResponseDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommuteDisputeList extends AppCompatActivity {

    private static final String TAG = "CommuteDispute";
    private RecyclerView mRecyclerView;
    private CommuteDispute_RecyclerViewAdpater mRecyclerViewAdapter;
    private Set<CommuteDispute_recyclerViewWordItemData> set = new HashSet<>();
    private LinearLayout nodataLayout, dataLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commutedispute_list);

        // 리사이클 뷰
        mRecyclerView = (RecyclerView) findViewById(R.id.cdl_recycleView);
        nodataLayout = (LinearLayout) findViewById(R.id.cdl_nodataLayout);
        dataLayout = (LinearLayout)  findViewById(R.id.cdl_dataLayout);
        mRecyclerView.setHasFixedSize(true); // 리사이클러뷰의 크기가 고정됨을 설정

        // 이의신청 목록 조회
        fetchNoticeList();
    }

    // 이의신청 목록 조회
    private void fetchNoticeList() {
        try {
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CommuteApiService apiService = retrofit.create(CommuteApiService.class);

            Call<Set<CommuteResponseDto>> call = apiService.getDisputeList(1L); // storeId
            call.enqueue(new Callback<Set<CommuteResponseDto>>() {
                @Override
                public void onResponse(Call<Set<CommuteResponseDto>> call, Response<Set<CommuteResponseDto>> response) {
                    if (response.isSuccessful()) {
                        Set<CommuteResponseDto> commuteSet = response.body();
                        if (commuteSet != null && !commuteSet.isEmpty()) {
                            set = createRecyclerViewItemSet(commuteSet); // 공지사항 데이터를 아이템으로 변환하여 Set에 저장
                            updateUI(); // UI 업데이트
                        } else {
                            showNoDataLayout(); // 데이터가 없는 경우 레이아웃 업데이트
                        }
                    } else {
                        // 기타 다른 상태 코드 처리
                        try {
                            String errorResponse = response.errorBody().string();
                            Toast.makeText(CommuteDisputeList.this, "이의신청 조회 실패!! 에러 메시지: " + errorResponse, Toast.LENGTH_SHORT).show();
                            // 에러 메시지를 사용하여 추가적인 처리 수행
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Set<CommuteResponseDto>> call, Throwable t) {
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

    // 이의신청 데이터를 아이템으로 변환하여 Set에 저장하는 함수
    private Set<CommuteDispute_recyclerViewWordItemData> createRecyclerViewItemSet(Set<CommuteResponseDto> commuteSet) {
        Set<CommuteDispute_recyclerViewWordItemData> itemSet = new HashSet<>();
        for (CommuteResponseDto commute : commuteSet) {
            CommuteDispute_recyclerViewWordItemData item = new CommuteDispute_recyclerViewWordItemData(
                    commute.getStartTime(),
                    commute.getDisputeMessage() // 변경 필요: 이의신청 날짜, 이름
            );
            itemSet.add(item);
        }
        return itemSet;
    }

    // UI를 업데이트하는 함수
    private void updateUI() {
        nodataLayout.setVisibility(View.GONE);
        dataLayout.setVisibility(View.VISIBLE);

        mRecyclerViewAdapter = new CommuteDispute_RecyclerViewAdpater(getApplicationContext(), new ArrayList<>(set));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mRecyclerViewAdapter.updateData(new ArrayList<>(set));
        mRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
    }

    private void showNoDataLayout() {
        nodataLayout.setVisibility(View.VISIBLE);
        dataLayout.setVisibility(View.GONE);
    }
    private void showErrorToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}