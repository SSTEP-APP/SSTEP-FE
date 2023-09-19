package com.example.sstep.date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.CalendarDialog;
import com.example.sstep.R;
import com.example.sstep.date.date_api.CalendarApiService;
import com.example.sstep.date.date_api.CalendarResponseDto;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Date extends AppCompatActivity implements View.OnClickListener{

    LinearLayout date_nodataLayout, date_dataLayout;

    long storeId;
    String date;
    DayOfWeek day;

    private RecyclerView mRecyclerView;
    private Date_RecyclerViewAdpater mRecyclerViewAdapter;
    private List<Date_recyclerViewItem> mList;

    ImageButton backBtn, plusBtn, preDayBtn, refreshBtn, calendarBtn, nextDayBtn;
    TextView todayTv, weekDayTv, date_topText;
    static int changeDate = 0;
    DateTimeFormatter sdf_ymdh = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());
    DateTimeFormatter sdf_dayOfWeek = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault());

    LocalDate currentDate = LocalDate.now(); // 오늘 날짜로 초기화

    String chkDate = sdf_ymdh.format(LocalDate.now());

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        date_topText=findViewById(R.id.date_topText);
        backBtn = findViewById(R.id.date_backBtn); backBtn.setOnClickListener(this);
        plusBtn = findViewById(R.id.date_plusBtn); plusBtn.setOnClickListener(this);
        preDayBtn=findViewById(R.id.date_preDayBtn); preDayBtn.setOnClickListener(this);
        refreshBtn=findViewById(R.id.date_refreshBtn); refreshBtn.setOnClickListener(this);
        calendarBtn=findViewById(R.id.date_calendarBtn); calendarBtn.setOnClickListener(this);
        nextDayBtn=findViewById(R.id.date_nextDayBtn); nextDayBtn.setOnClickListener(this);
        todayTv=findViewById(R.id.date_yearMonthDayText);
        weekDayTv=findViewById(R.id.date_weekofdayText);
        date_nodataLayout=findViewById(R.id.date_nodataLayout);
        date_dataLayout=findViewById(R.id.date_dataLayout);

        // 리사이클 뷰
        mRecyclerView = (RecyclerView) findViewById(R.id.date_recycleView);
        mRecyclerView.setHasFixedSize(true); // 리사이클러뷰의 크기가 고정됨을 설정
        mList = new ArrayList<>();
        mRecyclerViewAdapter = new Date_RecyclerViewAdpater(mList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //오늘 날짜 표시
        todayTv.setText(currentDate.format(sdf_ymdh));

        //오늘 요일 표시
        try {
            weekDayTv.setText(getDateDay(currentDate));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        //storeId지정
        //AppInData appInData = (AppInData) getApplication(); // MyApplication 클래스의 인스턴스 가져오기
        //storeId = appInData.getStoreId(); // 사용자 ID 가져오기

        // 해당 날짜에 근무하는 직원 리스트 가져오기
        fetchDataForDate(currentDate.format(sdf_ymdh));

        // 변경된 데이터를 RecyclerView에 반영
        mRecyclerViewAdapter.notifyDataSetChanged();

    }

    private void fetchDataForDate(String selectedDate) {
        date = currentDate.format(sdf_ymdh);
        String dayStr = currentDate.format(sdf_dayOfWeek);
        day = changeDayOfWeek(dayStr);
        try {
            // Retrofit 코드 작성
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CalendarApiService apiService = retrofit.create(CalendarApiService.class);
            storeId = 1;

            Call<Set<CalendarResponseDto>> call = apiService.getDayWorkStaffs(storeId, date, day);

            call.enqueue(new Callback<Set<CalendarResponseDto>>() {
                @Override
                public void onResponse(Call<Set<CalendarResponseDto>> call, Response<Set<CalendarResponseDto>> response) {
                    if (response.isSuccessful()) {
                        Set<CalendarResponseDto> calendars = response.body();
                        mOnResume(calendars);
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

    protected void mOnResume(Set<CalendarResponseDto> list) {
        super.onResume();

        // 이곳에서 리사이클러뷰 데이터를 업데이트하고 어댑터를 갱신합니다.
        mUpdateRecyclerView(list); // 원하는 업데이트 로직을 여기에 작성
        mRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터 갱신

    }

    public void RegAddItem(String staffName, String startCalTime, String endCalTime){
        Date_recyclerViewItem item = new Date_recyclerViewItem();

        item.setStaffName(staffName);
        item.setStartCalTime(startCalTime);
        item.setEndCalTime(endCalTime);

        mList.add(item);
    }

    private void  mUpdateRecyclerView(Set<CalendarResponseDto> list) {
        mList.clear(); // 기존 데이터를 모두 지우고 새로운 데이터로 갱신

        if (list.isEmpty()) {
            // 데이터가 없는 경우, nodataLayout을 보이도록 설정
            date_nodataLayout.setVisibility(View.VISIBLE);
            date_dataLayout.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "데이터 없음", Toast.LENGTH_SHORT).show();
        } else {
            date_nodataLayout.setVisibility(View.GONE);
            date_dataLayout.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "데이터 있음", Toast.LENGTH_SHORT).show();
            // 데이터가 있는 경우, dataLayout을 보이도록 설정
            for (CalendarResponseDto calendar : list) {
                RegAddItem(calendar.getStaffName(), calendar.getStartCalTime(), calendar.getEndCalTime());
            }
        }

        mRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.date_backBtn: // 뒤로가기
//                intent = new Intent(getApplicationContext(), Home_Ceo.class); 홈화면으로 가기
//                startActivity(intent);
//                finish();
                break;
            case R.id.date_plusBtn: // +추가 버튼
                intent = new Intent(getApplicationContext(), Date_plus.class);
                startActivity(intent);
                finish();
                break;
            case R.id.date_preDayBtn: // 이전날 이동
                currentDate = currentDate.minusDays(1); // 하루 이전으로 이동
                todayTv.setText(currentDate.format(sdf_ymdh));
                try {
                    weekDayTv.setText(getDateDay(currentDate));
                    fetchDataForDate(currentDate.format(sdf_ymdh));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.date_nextDayBtn: // 다음날로 이동
                currentDate = currentDate.plusDays(1); // 하루 다음으로 이동
                todayTv.setText(currentDate.format(sdf_ymdh));
                try {
                    weekDayTv.setText(getDateDay(currentDate));
                    fetchDataForDate(chkDate);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.date_refreshBtn: // 날짜 새로 고침 (오늘 날짜로)
                currentDate = LocalDate.now(); // 오늘 날짜로 설정
                todayTv.setText(currentDate.format(sdf_ymdh));
                try {
                    weekDayTv.setText(getDateDay(currentDate));
                    fetchDataForDate(currentDate.format(sdf_ymdh));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.date_calendarBtn: // 달력
                showCalendarDialog(calendarBtn);
                break;
            default:
                break;
        }
    }

    //날짜에 해당하는 요일 설정_String
    private String getDateDay(LocalDate date) throws Exception {
        java.util.Calendar cal = java.util.Calendar.getInstance();
//        cal.setTime(java.sql.Date.valueOf(date));
        cal.setTime(java.sql.Date.valueOf(String.valueOf(date)));
        String day = "";
        int dayNum = cal.get(java.util.Calendar.DAY_OF_WEEK);

        switch (dayNum) {
            case java.util.Calendar.SUNDAY:
                day="일요일";
                break;
            case java.util.Calendar.MONDAY:
                day="월요일";
                break;
            case java.util.Calendar.TUESDAY:
                day="화요일";
                break;
            case java.util.Calendar.WEDNESDAY:
                day="수요일";
                break;
            case java.util.Calendar.THURSDAY:
                day="목요일";
                break;
            case java.util.Calendar.FRIDAY:
                day="금요일";
                break;
            case java.util.Calendar.SATURDAY:
                day="토요일";
                break;
        }
        return day;
    }

    //요일 문자열을 DayOfWeekfh 변환
    private DayOfWeek changeDayOfWeek(String dayOfWeekString) {
        switch (dayOfWeekString) {
            case "일요일":
                return DayOfWeek.SUNDAY;
            case "월요일":
                return DayOfWeek.MONDAY;
            case "화요일":
                return DayOfWeek.TUESDAY;
            case "수요일":
                return DayOfWeek.WEDNESDAY;
            case "목요일":
                return DayOfWeek.THURSDAY;
            case "금요일":
                return DayOfWeek.FRIDAY;
            case "토요일":
                return DayOfWeek.SATURDAY;
            default:
                throw new IllegalArgumentException("유효하지 않은 요일 문자열: " + dayOfWeekString);
        }
    }

    // 달력 다이얼로그 띄우기
    public void showCalendarDialog(final ImageButton imageButton) {
        CalendarDialog calendarDialog = new CalendarDialog(Date.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 사용자가 날짜를 선택하면 호출되는 콜백 메서드
                        // 여기에 선택한 날짜 처리 코드를 작성합니다.
                        String dateString = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";

                        try {
                            String dayOfWeek = getDateDay(year, month, dayOfMonth);
                            weekDayTv.setText(dayOfWeek);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        currentDate = LocalDate.of(year, month + 1, dayOfMonth);
                        todayTv.setText(currentDate.format(sdf_ymdh));
                        fetchDataForDate(currentDate.format(sdf_ymdh));
                    }
                });
        // 다이얼로그 띄우기
        calendarDialog.show();

    }

    // 선택한 날짜의 요일 가져오기
    public String getDateDay(int year, int month, int dayOfMonth) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        String[] weekDays = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeekString = weekDays[dayOfWeek - 1];

        return dayOfWeekString;
    }


    private void handleError(String errorMsg) {
        Log.e("API Error", errorMsg); // 로그로 출력하여 디버그 정보 확인
        Toast.makeText(this, "API 오류: " + errorMsg, Toast.LENGTH_SHORT).show();
    }


}