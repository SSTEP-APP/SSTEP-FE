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

import com.example.sstep.AppInData;
import com.example.sstep.R;
import com.example.sstep.alarm.Alarm;
import com.example.sstep.commute.Commute_map;
import com.example.sstep.commute.DisputeStaff_RecyclerViewAdpater;
import com.example.sstep.commute.DisputeStaff_recyclerViewItem;
import com.example.sstep.commute.commute_api.CommuteApiService;
import com.example.sstep.commute.commute_api.CommuteResponseDto;
import com.example.sstep.date.Date;
import com.example.sstep.date.Date_plus;
import com.example.sstep.date.Date_recyclerViewItem;
import com.example.sstep.date.date_api.CalendarApiService;
import com.example.sstep.date.date_api.CalendarResponseDto;
import com.example.sstep.document.certificate.Paper;
import com.example.sstep.document.work_doc_api.ByteArrayTypeAdapter;
import com.example.sstep.store.SelectStore;
import com.example.sstep.todo.checklist.CheckList;
import com.example.sstep.todo.checklist.Checklist_detail;
import com.example.sstep.todo.checklist.checklist_api.CheckListResponseDto;
import com.example.sstep.todo.checklist.checklist_api.ChecklistApiService;
import com.example.sstep.store.store_api.StoreApiService;
import com.example.sstep.store.store_api.StoreResponseDto;
import com.example.sstep.todo.notice.Notice;
import com.example.sstep.todo.notice.notice_api.NoticeApiService;
import com.example.sstep.todo.notice.notice_api.NoticeResponseDto;
import com.example.sstep.user.member.MemberApiService;
import com.example.sstep.user.member.MemberResponseDto;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;
import com.example.sstep.user.mypage.MyPage;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home_staff extends AppCompatActivity implements View.OnClickListener {

    AppInData appInData;

    String userId, userName, storeName, todayDateStr;
    long storeCode, storeId, staffId;

    private RecyclerView dateRecyclerView;
    private HomeDate_RecyclerViewAdpater dateRecyclerViewAdapter;
    private List<HomeDate_recyclerViewItem> dateList;
    private RecyclerView noticeRecyclerView;
    private HomeStaffNotice_RecyclerViewAdpater noticeRecyclerViewAdapter;
    private List<HomeStaffNotice_recyclerViewItem> noticeList;

    LinearLayout date_nodataLayout, date_dataLayout, notice_nodataLayout, notice_dataLayout;
    Button selectStoreBtn, mypageBtn, commuteBtn, dateBtn, dateplusBtn, noticeBtn;
    ImageButton menuIBtn, alarmIBtn;
    TextView storeNameTv, date_countNumTv, notice_countNumTv;

    boolean isGoingToWork = true; // 초기값 설정
    LocalDate currentDate = LocalDate.now(); // 오늘 날짜로 초기화
    DateTimeFormatter sdf_ymd = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());
    DayOfWeek dayOfWeek;

    private RecyclerView checkListRecyclerView;

    private Home_Staff_checkList_RecyclerViewAdpater checkListRecyclerViewAdapter;

    private List<Home_Staff_checkList_recyclerViewItem> checkListList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_staff);

        storeNameTv=findViewById(R.id.homestaff_storeNameTv);
        alarmIBtn=findViewById(R.id.homestaff_alarmIBtn); alarmIBtn.setOnClickListener(this);
        selectStoreBtn=findViewById(R.id.homestaff_selectStoreBtn); selectStoreBtn.setOnClickListener(this);
        mypageBtn=findViewById(R.id.homestaff_mypageBtn); mypageBtn.setOnClickListener(this);
        commuteBtn=findViewById(R.id.homestaff_commuteBtn); commuteBtn.setOnClickListener(this);
        menuIBtn=findViewById(R.id.homestaff_menuIBtn); menuIBtn.setOnClickListener(this);
        dateBtn=findViewById(R.id.homestaff_dateBtn); dateBtn.setOnClickListener(this);
        dateplusBtn=findViewById(R.id.homestaff_dateplusBtn); dateplusBtn.setOnClickListener(this);
        noticeBtn=findViewById(R.id.homestaff_noticeBtn); noticeBtn.setOnClickListener(this);
        date_nodataLayout=findViewById(R.id.homestaff_date_nodataLayout);
        date_dataLayout=findViewById(R.id.homestaff_date_dataLayout);
        date_countNumTv=findViewById(R.id.homestaff_date_countNumTv);
        notice_nodataLayout=findViewById(R.id.homestaff_notice_nodataLayout);
        notice_dataLayout=findViewById(R.id.homestaff_notice_dataLayout);
        notice_countNumTv=findViewById(R.id.homestaff_notice_countNumTv);

        // ID값 가지고 오기
        appInData = (AppInData) getApplication(); // MyApplication 클래스의 인스턴스 가져오기
        userId = appInData.getUserId();
        storeCode = appInData.getStoreCode();
        storeId = appInData.getStoreId();
        staffId = appInData.getStaffId();

        // userId 를 통해 name 가져오기
        try {
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MemberApiService apiService = retrofit.create(MemberApiService.class);
            //적은 id를 기반으로 db에 검색
            Call<MemberResponseDto> call = apiService.getMemberByUsername(userId); //username 아이디
            call.enqueue(new Callback<MemberResponseDto>() {
                @Override
                public void onResponse(Call<MemberResponseDto> call, Response<MemberResponseDto> response) {
                    if (response.isSuccessful()) {
                        MemberResponseDto data = response.body();
                        // 적은 id로 패스워드 데이터 가져오기
                        userName =data.getName(); // id에 id 설정
                        appInData.setUserName(userName); // appInData 정보 저장
                    } else {
                        // 오류 처리
                    }
                }

                @Override
                public void onFailure(Call<MemberResponseDto> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = "요청 실패: " + t.getMessage();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

        // storecode 를 통해 storeName 가져오기
        try {
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            StoreApiService apiService = retrofit.create(StoreApiService.class);
            //적은 id를 기반으로 db에 검색
            Call<StoreResponseDto> call = apiService.getStore(storeCode); //username 아이디
            call.enqueue(new Callback<StoreResponseDto>() {
                @Override
                public void onResponse(Call<StoreResponseDto> call, Response<StoreResponseDto> response) {
                    if (response.isSuccessful()) {
                        StoreResponseDto data = response.body();
                        // 적은 id로 패스워드 데이터 가져오기
                        storeName =data.getName(); // id에 id 설정
                        storeNameTv.setText(storeName);
                        appInData.setStoreName(storeName); // appInData 정보 저장
                    } else {
                        // 오류 처리
                    }
                }

                @Override
                public void onFailure(Call<StoreResponseDto> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = "요청 실패: " + t.getMessage();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

        // Commute_map으로부터 전달된 값 읽기
        isGoingToWork = getIntent().getBooleanExtra("isGoingToWork", true);
        if (isGoingToWork) {
            commuteBtn.setText("출근하기");
        } else {
            commuteBtn.setText("퇴근하기");
        }
        // ID값, storeId가지고 오기
        AppInData loginData = (AppInData) getApplication(); // AppInData 클래스의 인스턴스 가져오기
        String userId = loginData.getUserId(); // 사용자 ID 가져오기
        long storeCode = loginData.getStoreCode();
        long storeId = loginData.getStoreId();
        long staffId= loginData.getStaffId();

        regFirstInit();

        checkListRecyclerViewAdapter = new Home_Staff_checkList_RecyclerViewAdpater(checkListList);
        checkListRecyclerView.setAdapter(checkListRecyclerViewAdapter);
        checkListRecyclerView.setLayoutManager(new LinearLayoutManager(Home_staff.this, RecyclerView.VERTICAL, false)); //리사이클러뷰 양식지정

        Button checkListBtn = findViewById(R.id.homestaff_checklistBtn);
        checkListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getApplicationContext(), CheckList.class);
                startActivity(intent);
                finish();
            }
        });

        //체크리스트 리사이클러뷰 값 불러오기
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ChecklistApiService apiService = retrofit.create(ChecklistApiService.class);


                    Call<Set<CheckListResponseDto>> call = apiService.getStoreByStaffUnCompletedCheckListsByCategory(staffId);

                    retrofit2.Response<Set<CheckListResponseDto>> response = call.execute();

                    if (response.isSuccessful()) {
                        final Set<CheckListResponseDto> codeStaffs = response.body();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                regOnResume(codeStaffs);
                                LinearLayout checkListLL = findViewById(R.id.homestaff_checkListLL);
                                if(checkListRecyclerViewAdapter.getItemCount() == 0){
                                    checkListLL.setVisibility(View.VISIBLE);
                                    checkListRecyclerView.setVisibility(View.GONE);
                                }else{
                                    TextView checkNumTv;
                                    checkListLL.setVisibility(View.GONE);
                                    checkListRecyclerView.setVisibility(View.VISIBLE);
                                    checkNumTv = findViewById(R.id.homestaff_checkNumTv);
                                    checkNumTv.setText(""+checkListRecyclerViewAdapter.getItemCount());
                                }
                            }
                        });
                    } else {

                    }
                } catch (Exception e) {
                    final String errorMsg = e.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handleError(errorMsg);
                        }
                    });
                }
            }

        }).start();



        // 리사이클 뷰
        dateRecyclerView = (RecyclerView) findViewById(R.id.homestaff_date_recycleView);
        dateRecyclerView.setHasFixedSize(true); // 리사이클러뷰의 크기가 고정됨을 설정
        dateList = new ArrayList<>();
        dateRecyclerViewAdapter = new HomeDate_RecyclerViewAdpater(dateList);
        dateRecyclerView.setAdapter(dateRecyclerViewAdapter);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noticeRecyclerView = (RecyclerView) findViewById(R.id.homestaff_notice_recycleView);
        noticeRecyclerView.setHasFixedSize(true); // 리사이클러뷰의 크기가 고정됨을 설정
        noticeList = new ArrayList<>();
        noticeRecyclerViewAdapter = new HomeStaffNotice_RecyclerViewAdpater(noticeList);
        noticeRecyclerView.setAdapter(noticeRecyclerViewAdapter);
        noticeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 사업장 내 오늘 전체 일정
        fetchDate();

        // 전체 공지사항
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
            case R.id.homestaff_alarmIBtn: // 알림
                intent = new Intent(getApplicationContext(), Alarm.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homestaff_selectStoreBtn: // 사업장 선택
                intent = new Intent(getApplicationContext(), SelectStore.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homestaff_mypageBtn: // 마이페이지
                intent = new Intent(getApplicationContext(), Paper.class);
                startActivity(intent);
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
            case R.id.homestaff_dateBtn: // 일정
                intent = new Intent(getApplicationContext(), Date.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homestaff_dateplusBtn: // 일정
                intent = new Intent(getApplicationContext(), Date_plus.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homestaff_noticeBtn:
                intent = new Intent(getApplicationContext(), Notice.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
    public void regFirstInit(){
        checkListRecyclerView = (RecyclerView) findViewById(R.id.homestaff_checklist_recycleView); //리사이클뷰 아이디 받기
        checkListList = new ArrayList<>();
    }

    protected void regOnResume(Set<CheckListResponseDto> list) {
        super.onResume();

        // 이곳에서 리사이클러뷰 데이터를 업데이트하고 어댑터를 갱신합니다.
        RegUpdateRecyclerView(list); // 원하는 업데이트 로직을 여기에 작성

        checkListRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터 갱신
    }


    public void RegAddItem(String name, String date,long id){
        Home_Staff_checkList_recyclerViewItem item = new Home_Staff_checkList_recyclerViewItem();

        item.setHome_Check_title(name);
        item.setHome_Check_date(date);
        item.setHome_Check_checkListId(id);

        checkListList.add(item);
    }
    private void  RegUpdateRecyclerView(Set<CheckListResponseDto> list) {
        checkListList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신
        for (CheckListResponseDto clRes : list) {
            RegAddItem(clRes.getTitle(), clRes.getDate(),clRes.getId());
        }

        Home_Staff_checkList_RecyclerViewAdpater.setOnItemClickListener(new Home_Staff_checkList_RecyclerViewAdpater.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 해당 아이템 레이아웃 클릭 시 처리할 코드 이쪽 수정 필!
                Home_Staff_checkList_recyclerViewItem item = checkListList.get(position);
                Intent intent = new Intent(getApplicationContext(), Checklist_detail.class); //사장, 스테프 구분 필요
                //체크리스트 id 받아서 넘기기
                intent.putExtra("checkListId", item.getHome_Check_checkListId());
                //intent.putExtra("staffId", item.getPaperH_reg_staffId);

                startActivity(intent);
                finish();
            }
        });


        checkListRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림

    }


    // 일정 리스트
    private void fetchDate() {
        todayDateStr = currentDate.format(sdf_ymd); // 오늘 날짜
        dayOfWeek = currentDate.getDayOfWeek();
        try {
            // Retrofit 코드 작성
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CalendarApiService apiService = retrofit.create(CalendarApiService.class);

            Call<Set<CalendarResponseDto>> call = apiService.getDayWorkStaffs(storeId, todayDateStr, dayOfWeek);

            call.enqueue(new Callback<Set<CalendarResponseDto>>() {
                @Override
                public void onResponse(Call<Set<CalendarResponseDto>> call, Response<Set<CalendarResponseDto>> response) {
                    if (response.isSuccessful()) {
                        Set<CalendarResponseDto> calendars = response.body();
                        dateOnResume(calendars);
                    } else {
                        // 처리할 실패 시나리오 작성
                        handleError("데이터 가져오기 실패: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Set<CalendarResponseDto>> call, Throwable t) {
                    // 에러 처리 코드 작성
                    handleError("네트워크 오류: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            // 예외 처리 코드 작성
            handleError("예외 발생: " + e.getMessage());
        }
    }

    protected void dateOnResume(Set<CalendarResponseDto> datelist) {
        super.onResume();

        // 이곳에서 리사이클러뷰 데이터를 업데이트하고 어댑터를 갱신합니다.
        dateUpdateRecyclerView(datelist); // 원하는 업데이트 로직을 여기에 작성

        // 공지사항 개수를 설정
        int dateCount = datelist.size();
        date_countNumTv.setText("오늘 " + String.valueOf(dateCount));
        date_countNumTv.setVisibility(View.VISIBLE);

        dateRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터 갱신

    }

    public void dateRegAddItem(String calendarDate, DayOfWeek dayOfWeek, String startCalTime, String endCalTime, String staffName){
        HomeDate_recyclerViewItem item = new HomeDate_recyclerViewItem();

        item.setCalendarDate(calendarDate);
        item.setDayOfWeek(dayOfWeek);
        item.setStartCalTime(startCalTime);
        item.setEndCalTime(endCalTime);
        item.setStaffName(staffName);

        dateList.add(item);
    }

    private void  dateUpdateRecyclerView(Set<CalendarResponseDto> list) {
        dateList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신

        if (list.isEmpty()) {
            // 데이터가 없는 경우, nodataLayout을 보이도록 설정
            date_nodataLayout.setVisibility(View.VISIBLE);
            date_dataLayout.setVisibility(View.GONE);
        } else {
            date_nodataLayout.setVisibility(View.GONE);
            date_dataLayout.setVisibility(View.VISIBLE);
            // 데이터가 있는 경우, dataLayout을 보이도록 설정
            for (CalendarResponseDto calendar : list) {
                dateRegAddItem(calendar.getCalendarDate(), calendar.getDayOfWeek(),
                        calendar.getStartCalTime(), calendar.getEndCalTime(), calendar.getStaffName());
            }
        }

        dateRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
    }


    // 공지 리스트
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

    public void noticeRegAddItem(long noticeId, String writeDate, String title, String writerName){
        HomeStaffNotice_recyclerViewItem item = new HomeStaffNotice_recyclerViewItem();

        item.setNoticeId(noticeId);
        item.setWriteDate(writeDate);
        item.setTitle(title);
        item.setWriterName(writerName);

        noticeList.add(item);
    }

    private void  noticeUpdateRecyclerView(Set<NoticeResponseDto> list) {
        noticeList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신

        if (list.isEmpty()) {
            // 데이터가 없는 경우, nodataLayout을 보이도록 설정
            notice_nodataLayout.setVisibility(View.VISIBLE);
            notice_dataLayout.setVisibility(View.GONE);
        } else {
            notice_nodataLayout.setVisibility(View.GONE);
            notice_dataLayout.setVisibility(View.VISIBLE);
            // 데이터가 있는 경우, dataLayout을 보이도록 설정
            for (NoticeResponseDto notice : list) {
                noticeRegAddItem(notice.getId(), notice.getWriteDate(), notice.getTitle(), notice.getWriterName());
            }
        }

        noticeRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
    }

    private void handleError(String errorMsg) {
        Log.e("API Error", errorMsg); // 로그로 출력하여 디버그 정보 확인
        Toast.makeText(this, "API 오류: " + errorMsg, Toast.LENGTH_SHORT).show();
    }

}