package com.example.sstep.date;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.AppInData;
import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.date.date_api.CalendarApiService;
import com.example.sstep.date.date_api.CalendarRequestDto;
import com.example.sstep.store.store_api.NullOnEmptyConverterFactory;
import com.example.sstep.store.store_api.StoreApiService;
import com.example.sstep.user.staff_api.StaffResponseDto;

import java.io.IOException;
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

public class Date_plus extends AppCompatActivity {

    // 선택한 직원의 아이디를 담을 리스트
    List<Long> selectedStaffIds = new ArrayList<>();
    Dialog timePickDialog;
    TextView workTimeText, homeTimeText; TextView join_okdl_commentTv;
    LinearLayout SelectedStaffLayout;

    private static final String TAG = "Date_plus";
    private RecyclerView mRecyclerView;
    private DatePlus_RecyclerViewAdpater mRecyclerViewAdapter;
    private ArrayList<DatePlus_recyclerViewItem> mList;

    String calendarDate, startCalTime, endCalTime;
    DayOfWeek dayOfWeek;
    CalendarView calendarView;
    Button completeBtn;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;
    long storeId;
    AppInData appInData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_plus);

        calendarView=findViewById(R.id.datePlus_calendar);
        completeBtn=findViewById(R.id.datePlus_completeBtn);
        workTimeText=findViewById(R.id.datePlus_workTimeText);
        homeTimeText=findViewById(R.id.datePlus_homeTimeText);
        SelectedStaffLayout=findViewById(R.id.datePlus_SelectedStaffLayout);

        baseDialog_okCenter = new BaseDialog_OkCenter(Date_plus.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(Date_plus.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        timePickDialog = new Dialog(Date_plus.this);
        timePickDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        timePickDialog.setContentView(R.layout.dialog_time_setting);// xml 레이아웃 파일과 연결


        // 리사이클 뷰
        mRecyclerView = (RecyclerView) findViewById(R.id.datePlus_recycleView);
        mRecyclerView.setHasFixedSize(true); // 리사이클러뷰의 크기가 고정됨을 설정
        mList = new ArrayList<>();

        // 리사이클러뷰 어댑터 설정
        mRecyclerViewAdapter = new DatePlus_RecyclerViewAdpater(mList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        // LinearLayoutManager를 사용하여 레이아웃 방향을 가로로 설정합니다.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        appInData = (AppInData) getApplication(); // MyApplication 클래스의 인스턴스 가져오기
        storeId = appInData.getStoreId(); // 사용자 ID 가져오기


        //직원 리스트 불러오기
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                        .addConverterFactory(new NullOnEmptyConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // StoreService 인터페이스 구현체 생성
                StoreApiService storeService = retrofit.create(StoreApiService.class);

                Call<Set<StaffResponseDto>> call = storeService.getStaffsByStoreId(storeId);
                call.enqueue(new Callback<Set<StaffResponseDto>>() {
                    @Override
                    public void onResponse(Call<Set<StaffResponseDto>> call, Response<Set<StaffResponseDto>> response) {
                        if (response.isSuccessful()) {
                            Set<StaffResponseDto> staffs = response.body();

                            for (StaffResponseDto staff : staffs) {
                                selectedStaffIds.add(staff.getId()); // 모든 직원 아이디 추가
                                addItem(staff.getStaffName(), staff.getPhoneNum(), staff.getId());
                            }

                        } else {
                            // 요청 실패
                            Toast.makeText(Date_plus.this, "데이터를 가져오는 데 실패했습니다."+response.message() ,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Set<StaffResponseDto>> call, Throwable t) {
                        // 네트워크 오류 또는 예외 발생
                        Toast.makeText(Date_plus.this, "데이터를 가져오는 데 실패했습니다."+t.getMessage() ,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();

        //직원 리스트 내용을 누르면 제거
        mRecyclerViewAdapter.setOnItemClickListener(new DatePlus_RecyclerViewAdpater.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                long staffId = mList.get(position).getDateplus_recycle_staffId();
                selectedStaffIds.remove(staffId); // 선택된 직원 아이디 제거
                mRecyclerViewAdapter.removeItem(position);
            }
        });


        //시간을 누르면 다이얼로그 생성
        workTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(workTimeText);
            }
        });

        //시간을 누르면 다이얼로그 생성
        homeTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(homeTimeText);
            }
        });

        // 근무일 입력
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                // 선택한 날짜를 LocalDate로 변환
                LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth);

                // 선택한 날짜를 원하는 형식으로 포맷
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());
                calendarDate = selectedDate.format(formatter);

                // 선택한 날짜의 요일을 얻기
                dayOfWeek = selectedDate.getDayOfWeek();
            }
        });

        // 일정 저장하기
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCalTime = workTimeText.getText().toString().trim();
                endCalTime = homeTimeText.getText().toString().trim();

                // 선택한 직원 ID를 반복하면서 각 직원에 대한 API 호출을 수행합니다.
                for (long staffId : selectedStaffIds) {
                    //레트로핏 작동
                    try {
                        //네트워크 요청 구현
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                                .addConverterFactory(new NullOnEmptyConverterFactory())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        CalendarApiService apiService = retrofit.create(CalendarApiService.class);
                        // 사업장등록에 필요한 데이터를 StoreRequestDto 객체로 생성
                        CalendarRequestDto calendarRequestDto = new CalendarRequestDto(
                                calendarDate, //일자
                                dayOfWeek, //요일,
                                startCalTime, //근무 시작 시간
                                endCalTime //근무 종료 시간
                        );
                        Call<Void> call = apiService.registerCalendar(staffId, calendarRequestDto); // staffId, calendarRequestDto

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    // 각 직원의 일정이 성공적으로 저장되었을 때 메시지를 표시합니다.
                                    String successMessage = "직원 ID " + staffId + calendarDate +dayOfWeek+startCalTime+ "의 일정이 성공적으로 저장되었습니다.";
                                    // TextView에 성공 메시지 추가
                                    showComplete_dialog.show();
                                    // 다이얼로그의 배경을 투명으로 만든다.
                                    showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    Button join_okdl_okBtn;
                                    join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
                                    join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
                                    join_okdl_commentTv.append("\n" +successMessage);

                                    // '공지사항 dialog' _ 확인 버튼 클릭 시
                                    join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getApplicationContext(), Date.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    Toast.makeText(Date_plus.this, "성공"+ successMessage, Toast.LENGTH_SHORT).show();
                                    // 성공적인 응답 처리
                                } else {
                                    // 기타 다른 상태 코드 처리
                                    try {
                                        String errorResponse = response.errorBody().string();
                                        Toast.makeText(Date_plus.this, "일정 추가 실패!! 에러 메시지: " + errorResponse, Toast.LENGTH_SHORT).show();
                                        // 에러 메시지를 사용하여 추가적인 처리 수행
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
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



            }
        });

    }

    // 시간 선택 다이얼로그 표시
    private void showTimePickerDialog(TextView textView) {
        timePickDialog.show();
        TimePicker timePicker = timePickDialog.findViewById(R.id.dialog_time_set_timePicker);
        timePicker.setIs24HourView(true);

        Button dialog_time_set_okBtn = timePickDialog.findViewById(R.id.dialog_time_set_okBtn);
        Button dialog_time_set_cancleBtn = timePickDialog.findViewById(R.id.dialog_time_set_cancleBtn);

        dialog_time_set_cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickDialog.dismiss();
            }
        });

        dialog_time_set_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                timePickDialog.dismiss();
            }
        });
    }


    // 직원 조회
    private void fetchStaffList() {
        try {
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            StoreApiService apiService = retrofit.create(StoreApiService.class);

            Call<Set<StaffResponseDto>> call = apiService.getStaffsByStoreId(storeId); // storeId
            call.enqueue(new Callback<Set<StaffResponseDto>>() {
                @Override
                public void onResponse(Call<Set<StaffResponseDto>> call, Response<Set<StaffResponseDto>> response) {
                    if (response.isSuccessful()) {
                        Set<StaffResponseDto> staffSet = response.body();
                        if (staffSet != null && !staffSet.isEmpty()) {
                            mList.clear(); // 이전 데이터 지우기
                            mList.addAll(createRecyclerViewItemList(staffSet)); // 직원 목록 업데이트
                            mRecyclerViewAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
                        } else {
                            Toast.makeText(getApplicationContext(), "없음", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // 기타 다른 상태 코드 처리
                        try {
                            String errorResponse = response.errorBody().string();
                            Toast.makeText(Date_plus.this, "직원 조회 실패!! 에러 메시지: " + errorResponse, Toast.LENGTH_SHORT).show();
                            // 에러 메시지를 사용하여 추가적인 처리 수행
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Set<StaffResponseDto>> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = t != null ? t.getMessage() : "Unknown error";
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // 직원 목록을 아이템으로 변환
    private List<DatePlus_recyclerViewItem> createRecyclerViewItemList(Set<StaffResponseDto> staffSet) {
        List<DatePlus_recyclerViewItem> itemList = new ArrayList<>();
        for (StaffResponseDto staff : staffSet) {
            DatePlus_recyclerViewItem item = new DatePlus_recyclerViewItem();
            item.setDateplus_recycle_staffNameText(staff.getStaffName());
            itemList.add(item);
        }
        return itemList;
    }

    // RecyclerView에 아이템 추가
    private void addItem(String subText, String phoneNum, long staffId) {
        DatePlus_recyclerViewItem item = new DatePlus_recyclerViewItem();

        item.setDateplus_recycle_staffNameText(subText);
        item.setDateplus_recycle_phoneNum(phoneNum);
        item.setDateplus_recycle_staffId(staffId);
        mList.add(item);
        mRecyclerViewAdapter.notifyDataSetChanged(); // 데이터가 변경되었음을 어댑터에 알림
    }


    // 완료'버튼 클릭 시
    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        join_okdl_commentTv.setText("일정 추가를 완료하였습니다.");

        // '공지사항 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Date.class);
                startActivity(intent);
                finish();
            }
        });
    }
}