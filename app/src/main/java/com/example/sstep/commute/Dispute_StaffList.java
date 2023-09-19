package com.example.sstep.commute;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.commute.commute_api.CommuteApiService;
import com.example.sstep.commute.commute_api.CommuteResponseDto;
import com.example.sstep.date.Date_RecyclerViewAdpater;
import com.example.sstep.date.Date_recyclerViewItem;
import com.example.sstep.date.date_api.CalendarApiService;
import com.example.sstep.date.date_api.CalendarResponseDto;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dispute_StaffList extends AppCompatActivity {

    long staffId;
    private RecyclerView mRecyclerView;
    private DisputeStaff_RecyclerViewAdpater mRecyclerViewAdapter;
    private List<DisputeStaff_recyclerViewItem> mList;
    ImageButton backib;
    LinearLayout nodataLayout, dataLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispute_stafflist);

        backib=findViewById(R.id.cdsl_backib);
        nodataLayout=findViewById(R.id.cdsl_nodataLayout);
        dataLayout=findViewById(R.id.cdsl_dataLayout);

        // 리사이클 뷰
        mRecyclerView = (RecyclerView) findViewById(R.id.cdsl_recycleView);
        mRecyclerView.setHasFixedSize(true); // 리사이클러뷰의 크기가 고정됨을 설정
        mList = new ArrayList<>();
        mRecyclerViewAdapter = new DisputeStaff_RecyclerViewAdpater(mList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //직원의 전체 출퇴근 정보
        fetchDataForDate();
    }

    private void fetchDataForDate() {
        try {
            // Retrofit 코드 작성
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CommuteApiService apiService = retrofit.create(CommuteApiService.class);
            staffId = 2;

            Call<Set<CommuteResponseDto>> call = apiService.getCommutes(staffId); // Long staffId

            call.enqueue(new Callback<Set<CommuteResponseDto>>() {
                @Override
                public void onResponse(Call<Set<CommuteResponseDto>> call, Response<Set<CommuteResponseDto>> response) {
                    if (response.isSuccessful()) {
                        Set<CommuteResponseDto> commutes = response.body();
                        mOnResume(commutes);
                    } else {
                        // 처리할 실패 시나리오 작성
                        handleError("데이터 가져오기 실패: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Set<CommuteResponseDto>> call, Throwable t) {
                    // 에러 처리 코드 작성
                    handleError("네트워크 오류: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            // 예외 처리 코드 작성
            handleError("예외 발생: " + e.getMessage());
        }
    }

    protected void mOnResume(Set<CommuteResponseDto> list) {
        super.onResume();

        // 이곳에서 리사이클러뷰 데이터를 업데이트하고 어댑터를 갱신합니다.
        mUpdateRecyclerView(list); // 원하는 업데이트 로직을 여기에 작성
        mRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터 갱신

    }

    public void RegAddItem(String commuteDate, DayOfWeek dayOfWeek, String startTime, String endTime){
        DisputeStaff_recyclerViewItem item = new DisputeStaff_recyclerViewItem();

        item.setCommuteDate(commuteDate);
        item.setDayOfWeek(dayOfWeek);
        item.setStartTime(startTime);
        item.setEndTime(endTime);

        mList.add(item);
    }

    private void  mUpdateRecyclerView(Set<CommuteResponseDto> list) {
        mList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신

        if (list.isEmpty()) {
            // 데이터가 없는 경우, nodataLayout을 보이도록 설정
            nodataLayout.setVisibility(View.VISIBLE);
            dataLayout.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "데이터 없음", Toast.LENGTH_SHORT).show();
        } else {
            nodataLayout.setVisibility(View.GONE);
            dataLayout.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "데이터 있음", Toast.LENGTH_SHORT).show();
            // 데이터가 있는 경우, dataLayout을 보이도록 설정
            for (CommuteResponseDto commute : list) {
                RegAddItem(commute.getCommuteDate(), commute.getDayOfWeek(), commute.getStartTime(), commute.getEndTime());
            }
        }

        mRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
    }

    private void handleError(String errorMsg) {
        Log.e("API Error", errorMsg); // 로그로 출력하여 디버그 정보 확인
        Toast.makeText(this, "API 오류: " + errorMsg, Toast.LENGTH_SHORT).show();
    }
}