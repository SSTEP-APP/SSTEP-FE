package com.example.sstep.commute;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sstep.AppInData;
import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.commute.commute_api.CommuteApiService;
import com.example.sstep.commute.commute_api.CommuteRequestDto;
import com.example.sstep.date.Date_plus;
import com.example.sstep.date.date_api.CalendarApiService;
import com.example.sstep.date.date_api.CalendarRequestDto;
import com.example.sstep.store.store_api.NullOnEmptyConverterFactory;
import com.example.sstep.todo.notice.Notice;
import com.example.sstep.user.staff_api.StaffApiService;
import com.example.sstep.user.staff_api.StaffResponseDto;

import java.io.IOException;
import java.time.DayOfWeek;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dispute_WriteStaff extends AppCompatActivity implements View.OnClickListener{

    AppInData appInData;
    long commuteId, staffId;
    String commuteDate, disputeStartTime, disputeEndTime, disputeMessage, dayOfWeekStr;
    DayOfWeek dayOfWeek;
    ImageButton backib;
    TextView staffNameTv, disputeDateTv, workTimeTv, homeTimeTv; TextView join_okdl_commentTv;
    EditText contentEt;
    Button completeBtn;
    Dialog timePickDialog, showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispute_writestaff);

        backib=findViewById(R.id.cdwstaff_backib); backib.setOnClickListener(this);
        workTimeTv=findViewById(R.id.cdwstaff_workTimeTv); workTimeTv.setOnClickListener(this);
        homeTimeTv=findViewById(R.id.cdwstaff_homeTimeTv); homeTimeTv.setOnClickListener(this);
        completeBtn=findViewById(R.id.cdwstaff_completeBtn); completeBtn.setOnClickListener(this);
        staffNameTv=findViewById(R.id.cdwstaff_staffNameTv);
        disputeDateTv=findViewById(R.id.cdwstaff_disputeDateTv);
        contentEt=findViewById(R.id.cdwstaff_contentEt);

        baseDialog_okCenter = new BaseDialog_OkCenter(Dispute_WriteStaff.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(Dispute_WriteStaff.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결
        timePickDialog = new Dialog(Dispute_WriteStaff.this);
        timePickDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        timePickDialog.setContentView(R.layout.dialog_time_setting);// xml 레이아웃 파일과 연결

        // Intent로 전달받은 데이터 가져오기
        Intent intent = getIntent();
        commuteDate = intent.getStringExtra("commuteDate");
        dayOfWeekStr = intent.getStringExtra("dayOfWeek");
        disputeStartTime = intent.getStringExtra("startTime");
        disputeEndTime = intent.getStringExtra("endTime");
        commuteId = intent.getLongExtra("commuteId", 0L);
        staffId = intent.getLongExtra("staffId", 0L);

        disputeDateTv.setText(commuteDate + " (" + dayOfWeekStr + ")");

        if (disputeStartTime != null) {
            workTimeTv.setText(disputeStartTime);
        }else{
            workTimeTv.setText("미출근");
        }
        if (disputeEndTime != null) {
            homeTimeTv.setText(disputeEndTime);
        }else{
            homeTimeTv.setText("미퇴근");
        }

        // staffId 를 통해 직급 가져오기
        try {
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            StaffApiService apiService = retrofit.create(StaffApiService.class);
            //적은 id를 기반으로 db에 검색
            Call<StaffResponseDto> call = apiService.getStaffByStaffId(staffId); //staffId 아이디
            call.enqueue(new Callback<StaffResponseDto>() {
                @Override
                public void onResponse(Call<StaffResponseDto> call, Response<StaffResponseDto> response) {
                    if (response.isSuccessful()) {
                        StaffResponseDto data = response.body();
                        // 적은 id로 패스워드 데이터 가져오기
                        String staffName = data.getStaffName();
                        staffNameTv.setText(staffName);
                    } else {
                        // 오류 처리
                    }
                }

                @Override
                public void onFailure(Call<StaffResponseDto> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = "요청 실패: " + t.getMessage();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

        // '사유' 글자 수 제한
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputValidity();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.cdwstaff_backib:  // 뒤로가기
                intent = new Intent(getApplicationContext(), Dispute_StaffList.class);
                startActivity(intent);
                finish();
                break;
            case R.id.cdwstaff_workTimeTv:  // 출근시간
                showTimePickerDialog(workTimeTv);
                break;
            case R.id.cdwstaff_homeTimeTv:  // 퇴근시간
                showTimePickerDialog(homeTimeTv);
                break;
            case R.id.cdwstaff_completeBtn: // 등록하기
                disputeStartTime = workTimeTv.getText().toString().trim();
                disputeEndTime = homeTimeTv.getText().toString().trim();
                disputeMessage = contentEt.getText().toString().trim();
                try {
                    //네트워크 요청 구현
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    CommuteApiService apiService = retrofit.create(CommuteApiService.class);
                    // 사업장등록에 필요한 데이터를 StoreRequestDto 객체로 생성
                    CommuteRequestDto commuteRequestDto = new CommuteRequestDto(
                            commuteDate, //출퇴근 일자
                            dayOfWeek, //출퇴근 요일
                            null, //출근 시간
                            null, //근무 종료 시간
                            false, //지각 여부
                            disputeMessage, //출퇴근 관련 이의 신청 메시지
                            disputeStartTime, //정정 출근 시간
                            disputeEndTime //정정 퇴근 시간
                    );
                    Call<Void> call = apiService.disputeCommute(commuteId, commuteRequestDto); // commuteId, commuteRequestDto

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                showCompleteDl("이의신청을 완료하였습니다.");
                                // 성공적인 응답 처리
                            } else {
                                // 기타 다른 상태 코드 처리
                                try {
                                    String errorResponse = response.errorBody().string();
                                    Toast.makeText(Dispute_WriteStaff.this, "이의신청 실패!! 에러 메시지: " + errorResponse, Toast.LENGTH_SHORT).show();
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

                break;
            default:
                break;
        }
    }

    // '완료'버튼 클릭 시
    public void showCompleteDl(String message){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        join_okdl_commentTv.setText(message);

        // dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dispute_StaffList.class);
                startActivity(intent);
                finish();
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

    // '제목'과 '내용' 이 모두 채워지면 버튼 활성화
    private void checkInputValidity() {
        String content = contentEt.getText().toString();
        boolean isContentValid = content.length() > 0;

        if(isContentValid){ // 채워져있으면 버튼 활성화
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
        }
    }
}