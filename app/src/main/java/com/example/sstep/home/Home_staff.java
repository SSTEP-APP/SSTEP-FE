package com.example.sstep.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.alarm.Alarm;
import com.example.sstep.commute.Commute_map;
import com.example.sstep.commute.DisputeStaff_RecyclerViewAdpater;
import com.example.sstep.commute.DisputeStaff_recyclerViewItem;
import com.example.sstep.commute.commute_api.CommuteApiService;
import com.example.sstep.commute.commute_api.CommuteResponseDto;
import com.example.sstep.document.work_doc_api.ByteArrayTypeAdapter;
import com.example.sstep.store.SelectStore;
import com.example.sstep.todo.notice.notice_api.NoticeApiService;
import com.example.sstep.todo.notice.notice_api.NoticeResponseDto;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;
import com.example.sstep.user.mypage.MyPage;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home_staff extends AppCompatActivity implements View.OnClickListener {

    String userId;
    long storeId;
    private RecyclerView noticeRecyclerView;
    private HomeStaffNotice_RecyclerViewAdpater noticeRecyclerViewAdapter;
    private List<HomeStaffNotice_recyclerViewItem> noticeList;

    LinearLayout notice_nodataLayout, notice_dataLayout;
    Button commuteBtn, mypageBtn, selectStoreBtn;
    ImageButton menuIBtn, alarmIBtn;
    TextView notice_countNumTv;

    boolean isGoingToWork = true; // 초기값 설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_staff);

        alarmIBtn=findViewById(R.id.homestaff_alarmIBtn); alarmIBtn.setOnClickListener(this);
        selectStoreBtn=findViewById(R.id.homestaff_selectStoreBtn); selectStoreBtn.setOnClickListener(this);
        mypageBtn=findViewById(R.id.homestaff_mypageBtn); mypageBtn.setOnClickListener(this);
        commuteBtn=findViewById(R.id.homestaff_commuteBtn); commuteBtn.setOnClickListener(this);
        menuIBtn=findViewById(R.id.homestaff_menuIBtn); menuIBtn.setOnClickListener(this);
        notice_nodataLayout=findViewById(R.id.homestaff_notice_nodataLayout);
        notice_dataLayout=findViewById(R.id.homestaff_notice_dataLayout);
        notice_countNumTv=findViewById(R.id.homestaff_notice_countNumTv);

        // Commute_map으로부터 전달된 값 읽기
        isGoingToWork = getIntent().getBooleanExtra("isGoingToWork", true);
        if (isGoingToWork) {
            commuteBtn.setText("출근하기");
        } else {
            commuteBtn.setText("퇴근하기");
        }

        // 리사이클 뷰
        noticeRecyclerView = (RecyclerView) findViewById(R.id.homestaff_notice_recycleView);
        noticeRecyclerView.setHasFixedSize(true); // 리사이클러뷰의 크기가 고정됨을 설정
        noticeList = new ArrayList<>();
        noticeRecyclerViewAdapter = new HomeStaffNotice_RecyclerViewAdpater(noticeList);
        noticeRecyclerView.setAdapter(noticeRecyclerViewAdapter);
        noticeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //직원의 전체 출퇴근 정보
        fetchNotice();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.homestaff_menuIBtn: // 메뉴
                Animation slideInLeft = AnimationUtils.loadAnimation(Home_staff.this, R.anim.slide_in_left);
                menuIBtn.startAnimation(slideInLeft);

                intent = new Intent(Home_staff.this, Home_menu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                break;
            case R.id.homestaff_commuteBtn: // 출근하기
                if (commuteBtn.getText().toString().equals("출근하기")) {
                    intent = new Intent(getApplicationContext(), Commute_map.class);
                    intent.putExtra("inout", "in");
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(getApplicationContext(), Commute_map.class);
                    intent.putExtra("inout", "out");
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.homestaff_selectStoreBtn: // 사업장 선택
                intent = new Intent(getApplicationContext(), SelectStore.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homestaff_mypageBtn: // 마이페이지
                intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homestaff_alarmIBtn: // 알림
                intent = new Intent(getApplicationContext(), Alarm.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    private void fetchNotice() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY) // 대소문자 구분
                .registerTypeAdapter(byte[].class, new ByteArrayTypeAdapter())
                .create();
        try {
            // Retrofit 코드 작성
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            NoticeApiService apiService = retrofit.create(NoticeApiService.class);
            storeId = 2;

            Call<Set<NoticeResponseDto>> call = apiService.getNotices(storeId); // Long storeId

            call.enqueue(new Callback<Set<NoticeResponseDto>>() {
                @Override
                public void onResponse(Call<Set<NoticeResponseDto>> call, Response<Set<NoticeResponseDto>> response) {
                    if (response.isSuccessful()) {
                        Set<NoticeResponseDto> notices = response.body();
                        noticeOnResume(notices);
                    } else {
                        // 처리할 실패 시나리오 작성
                        handleError("데이터 가져오기 실패: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Set<NoticeResponseDto>> call, Throwable t) {
                    // 에러 처리 코드 작성
                    handleError("네트워크 오류: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            // 예외 처리 코드 작성
            handleError("예외 발생: " + e.getMessage());
        }
    }

    protected void noticeOnResume(Set<NoticeResponseDto> noticelist) {
        super.onResume();

        // 이곳에서 리사이클러뷰 데이터를 업데이트하고 어댑터를 갱신합니다.
        noticeUpdateRecyclerView(noticelist); // 원하는 업데이트 로직을 여기에 작성

        // 공지사항 개수를 설정
        int noticeCount = noticelist.size();
        notice_countNumTv.setText(String.valueOf(noticeCount));
        notice_countNumTv.setVisibility(View.VISIBLE);

        noticeRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터 갱신

    }

    public void noticeRegAddItem(long noticeId, String writeDate, String title){
        HomeStaffNotice_recyclerViewItem item = new HomeStaffNotice_recyclerViewItem();

        item.setNoticeId(noticeId);
        item.setWriteDate(writeDate);
        item.setTitle(title);

        noticeList.add(item);
    }

    private void  noticeUpdateRecyclerView(Set<NoticeResponseDto> list) {
        noticeList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신

        if (list.isEmpty()) {
            // 데이터가 없는 경우, nodataLayout을 보이도록 설정
            notice_nodataLayout.setVisibility(View.VISIBLE);
            notice_dataLayout.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "데이터 없음", Toast.LENGTH_SHORT).show();
        } else {
            notice_nodataLayout.setVisibility(View.GONE);
            notice_dataLayout.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "데이터 있음", Toast.LENGTH_SHORT).show();
            // 데이터가 있는 경우, dataLayout을 보이도록 설정
            for (NoticeResponseDto notice : list) {
                noticeRegAddItem(notice.getId(), notice.getWriteDate(), notice.getTitle());
            }
        }

        noticeRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
    }

    private void handleError(String errorMsg) {
        Log.e("API Error", errorMsg); // 로그로 출력하여 디버그 정보 확인
        Toast.makeText(this, "API 오류: " + errorMsg, Toast.LENGTH_SHORT).show();
    }

}