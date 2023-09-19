package com.example.sstep.user.staff;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.home.Home_Ceo;
import com.example.sstep.store.store_api.NullOnEmptyConverterFactory;
import com.example.sstep.store.store_api.StoreApiService;
import com.example.sstep.store.store_api.StoreRegisterReqDto;
import com.example.sstep.user.staff_api.ScheduleRequestDto;
import com.example.sstep.user.staff_api.StaffApiService;
import com.example.sstep.user.staff_api.StaffRequestDto;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.zone.ZoneRules;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class staff_infoInput extends AppCompatActivity {

    Intent intent;
    Button addBtn, chanBtn, delBtn, schedule_addBtn, completeBtn;
    ImageButton preMonthBtn, nextMonthBtn, back_Btn;
    TextView yearMonthText, termText, staff_info_local_text12, hourlyWageText, startDayTv; //오늘 날짜 표시 텍스트뷰

    SimpleDateFormat ymFormat = new SimpleDateFormat("yyyy년 MM월");
    SimpleDateFormat mdFormat = new SimpleDateFormat("MM월 dd일");

    static int changeDate = 0;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;

    Calendar calendar = new GregorianCalendar(); //오늘날짜 받기
    Calendar mCalendar = new GregorianCalendar(); //1달후 날짜 받기

    String ymDate = ymFormat.format(calendar.getTime());
    String mdDate = mdFormat.format(calendar.getTime());
    java.sql.Date startDay;
    String testStartDay;
    int wageType, hourMoney;
    long staffId;
    String paymentDate;

    private RecyclerView mRecyclerView;
    private ArrayList<Staff_infoInput_recyclerViewItem> mList;
    private Staff_infoInput_RecyclerViewAdpater mRecyclerViewAdapter;
    private List<String> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_info_input);

        addBtn = findViewById(R.id.staff_info_addBtn);
        chanBtn = findViewById(R.id.staff_info_chanBtn);
        delBtn = findViewById(R.id.staff_info_delBtn);
        preMonthBtn = findViewById(R.id.staff_info_preMonthBtn);
        nextMonthBtn = findViewById(R.id.staff_info_nextMonthBtn);
        yearMonthText =findViewById(R.id.staff_info_yearMonthText);
        termText = findViewById(R.id.staff_info_termText);
        schedule_addBtn = findViewById(R.id.staff_info_schedule_addBtn);
        staff_info_local_text12 = findViewById(R.id.staff_info_local_text12);
        back_Btn = findViewById(R.id.staff_info_back_Btn);
        completeBtn =findViewById(R.id.staff_info_completeBtn);
        hourlyWageText = findViewById(R.id.staff_info_hourlyWageText);
        startDayTv = findViewById(R.id.staff_info_startDay);

        baseDialog_okCenter = new BaseDialog_OkCenter(staff_infoInput.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(staff_infoInput.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        LinearLayout noworkScheduleLayout = findViewById(R.id.staff_info_noworkScheduleLayout);
        LinearLayout workCheckLayout = findViewById(R.id.staff_info_workCheckLayout);

        //1달후 날짜 구하기
        mCalendar.add(Calendar.MONTH, 1);
        String m1dDate = mdFormat.format(mCalendar.getTime());

        //날짜 기간 표시
        termText.setText(mdDate+"~"+m1dDate);
        yearMonthText.setText(ymDate);

        //받은 데이터 변환
        //intent로 넘긴 staffId받기
        Intent intent1 = getIntent();

        staffId = intent1.getLongExtra("staffId", 0);
        String inStartDay = intent1.getStringExtra("startDay");
        paymentDate = intent1.getStringExtra("paymentDate");
        hourMoney = getIntent().getIntExtra("hourMoney", 8000);
        wageType = getIntent().getIntExtra("wageType", 1);
        hourlyWageText.setText(""+hourMoney);


        if (inStartDay != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());
            try {
                Date formattedDate= sdf.parse(inStartDay);

                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDateStr = outputFormat.format(formattedDate);

                testStartDay = formattedDateStr;
                //startDay = java.sql.Date.valueOf(formattedDateStr);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        startDayTv.setText(testStartDay+"~ 계속");

        //이전달로 가기
        preMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDate -=1;
                calendar.add(Calendar.MONTH, -1);
                ymDate = ymFormat.format(calendar.getTime());
                yearMonthText.setText(ymDate);
            }
        });
        //다음달로 가기
        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDate +=1;
                calendar.add(Calendar.MONTH, 1);
                ymDate = ymFormat.format(calendar.getTime());
                yearMonthText.setText(ymDate);
            }
        });

        //이름, 전화번호, 날짜 받기

        //일정이 없을 때 일정 추가
        schedule_addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), addSchedule.class);
                intent.putExtra("staffId", staffId);
                intent.putExtra("hourMoney", hourMoney);
                intent.putExtra("wageType", wageType);
                intent.putExtra("startDay", inStartDay);
                intent.putExtra("paymentDate",paymentDate);
                startActivity(intent);
                finish();
            }
        });


        try {
            Intent intent= getIntent();
            ArrayList<String> listData = intent.getStringArrayListExtra("LIST_DATA");


            if (listData != null && listData.size() > 0) {
                noworkScheduleLayout.setVisibility(View.GONE);
                workCheckLayout.setVisibility(View.VISIBLE);

                //리사이클 뷰
                firstInit();
                for (int i = 0; i < listData.size(); i++) {
                    String[] dataList = listData.get(i).split(",");
                    addItem(dataList[0],dataList[1],dataList[2]);
                }

                mRecyclerViewAdapter = new Staff_infoInput_RecyclerViewAdpater(mList);
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            } else {
                noworkScheduleLayout.setVisibility(View.VISIBLE);
                workCheckLayout.setVisibility(View.GONE);
                Toast.makeText(this, "일정이 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(staff_infoInput.this,
                    e.toString(), Toast.LENGTH_SHORT).show();
        }




        //근무일정 추가
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), addSchedule.class);
                intent.putExtra("staffId", staffId);
                intent.putExtra("hourMoney", hourMoney);
                intent.putExtra("wageType", wageType);
                intent.putExtra("startDay", inStartDay);
                intent.putExtra("paymentDate",paymentDate);
                startActivity(intent);
                finish();
            }
        });
        //일정 수정
        //chanBtn.setOnClickListener(new View.OnClickListener() {
         //   @Override
        //    public void onClick(View view) {
        //        Intent intent = new Intent(getApplicationContext(), modifySchedule.class);
        //        startActivity(intent);
        //        finish();
        //    }
        //});
        //삭제 버튼
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstInit();
                mRecyclerViewAdapter = new Staff_infoInput_RecyclerViewAdpater(mList);
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(staff_infoInput.this));
                mRecyclerView.setLayoutManager(new LinearLayoutManager(staff_infoInput.this, RecyclerView.VERTICAL, false));

                noworkScheduleLayout.setVisibility(View.VISIBLE);
                workCheckLayout.setVisibility(View.GONE);
            }
        });

        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), InputStaffInfo.class);
                startActivity(intent);
                finish();
            }
        });

        // 완료 버튼
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCompleteDl();
            }
        });

    }
    public void firstInit(){
        mRecyclerView = (RecyclerView) findViewById(R.id.staff_info_recyclerView);
        mList = new ArrayList<>();
    }

    public void addItem(String day_of_week, String time, String workTime){
        Staff_infoInput_recyclerViewItem item = new Staff_infoInput_recyclerViewItem();

        item.setStaff_infoInput_days(day_of_week);
        item.setStaff_infoInput_time(time);
        item.setStaff_infoInput_cancelImg(workTime);

        mList.add(item);
    }

    // '계약서 작성완료'버튼 클릭 시
    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        try {

            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            StoreApiService storeapiService = retrofit.create(StoreApiService.class);

            // 사업장등록에 필요한 데이터를 StoreRequestDto 객체로 생성
            StaffRequestDto staffRequestDto = new StaffRequestDto(
                    staffId,
                    testStartDay,
                    paymentDate,
                    hourMoney,
                    wageType
            );

            Call<Void> call = storeapiService.addStaffToStore(staffRequestDto);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    int statusCode = response.code();
                    if (statusCode == 200 || statusCode == 201) {
                        registerScheduleOnServer();
                        join_okdl_commentTv.setText("직원 합류를 완료하였습니다.");

                        // 성공적인 응답 처리
                    } else {
                        // 기타 다른 상태 코드 처리
                        join_okdl_commentTv.setText("직원 등록이 실패했습니다!"+staffId +"오류코드:"+statusCode);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // 네트워크 오류나 기타 이유로 등록 실패
                    String errorMessage = t != null ? t.getMessage() : "Unknown error";

                    join_okdl_commentTv.setText("직원 등록이 실패했습니다\n 오류메시지: " + errorMessage);
                    t.printStackTrace();
                }
            });


        }catch (Exception e) {
            e.printStackTrace();
        }

        // '회원가입 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home_Ceo.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void registerScheduleOnServer() {
        // 스케줄 정보를 리사이클러뷰에서 가져옴
        for (Staff_infoInput_recyclerViewItem item : mList) {
            String dayOfWeek = item.getStaff_infoInput_days(); // 요일
            String time = item.getStaff_infoInput_time(); // 시간
            // workTime 정보는 어떻게 처리할지에 대한 추가적인 정보가 필요하므로 이 부분을 적절하게 처리해야 합니다.

            // 서버에 등록하기 위해 스케줄 정보를 객체로 만들기
            ScheduleRequestDto scheduleRequestDto = createScheduleRequest(dayOfWeek, time);

            // 서버에 스케줄 등록 요청 보내기
            registerSchedule(scheduleRequestDto);
        }
    }
    private ScheduleRequestDto createScheduleRequest(String dayOfWeek, String time) {
        // ScheduleRequestDto 객체 생성 및 데이터 설정
        // 여기에서 dayOfWeek와 time을 파싱하여 DayOfWeek 및 LocalTime 형식으로 변환해야 합니다.

        DayOfWeek day = parseDayOfWeek(dayOfWeek);
        String[] parts = time.split("~");
        String startTime = parts[0].trim(); // "03:00"
        String endTime = parts[1].trim();   // "05:00"


        // ScheduleRequestDto 객체 생성
        return new ScheduleRequestDto(day, startTime, endTime);
    }

    //스케쥴등록하는 레트로핏
    private void registerSchedule(ScheduleRequestDto scheduleRequestDto) {
        try {

            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            StaffApiService staffApiService = retrofit.create(StaffApiService.class);

            // 사업장등록에 필요한 데이터를 StoreRequestDto 객체로 생성
            Call<Void> call = staffApiService.registerSchedule(staffId, scheduleRequestDto);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    int statusCode = response.code();
                    if (statusCode == 200 || statusCode == 201) {

                        // 성공적인 응답 처리
                    } else {
                        try {
                            String errorResponse = response.errorBody().string();
                            Toast.makeText(staff_infoInput.this, "일정 등록 실패!! 에러 메시지: " + errorResponse, Toast.LENGTH_SHORT).show();
                            // 에러 메시지를 사용하여 추가적인 처리 수행
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // 네트워크 오류나 기타 이유로 등록 실패
                    String errorMessage = t != null ? t.getMessage() : "Unknown error";
                    Toast.makeText(staff_infoInput.this, "일정등록실패" + errorMessage, Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });


        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(staff_infoInput.this, "오류" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    //요일 분류
    public static DayOfWeek parseDayOfWeek(String dayOfWeekString) {
        String lowercaseInput = dayOfWeekString.toLowerCase(Locale.getDefault());

        switch (lowercaseInput) {
            case "일":
                return DayOfWeek.SUNDAY;
            case "월":
                return DayOfWeek.MONDAY;
            case "화":
                return DayOfWeek.TUESDAY;
            case "수":
                return DayOfWeek.WEDNESDAY;
            case "목":
                return DayOfWeek.THURSDAY;
            case "금":
                return DayOfWeek.FRIDAY;
            case "토":
                return DayOfWeek.SATURDAY;
            default:
                throw new IllegalArgumentException("Invalid DayOfWeek: " + dayOfWeekString);
        }
    }

    //시간 분류
    public LocalTime[] parseLocalTime(String localTimeString) {
        // 원하는 시간 형식으로 문자열을 LocalTime으로 파싱

        String[] parts = localTimeString.split("~");
        String startTime = parts[0].trim();
        String endTime = parts[1].trim();
        // startTime과 endTime을 사용하실 수 있습니다.

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startLocalTime = LocalTime.parse(startTime, formatter);
        LocalTime endLocalTime = LocalTime.parse(endTime, formatter);

        return new LocalTime[]{startLocalTime, endLocalTime};
    }


}