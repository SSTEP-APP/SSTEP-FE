package com.example.sstep.commute;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.CalendarDialog;
import com.example.sstep.R;
import com.example.sstep.commute.commute_api.CommuteApiService;
import com.example.sstep.commute.commute_api.CommuteRequestDto;
import com.example.sstep.home.Home_staff;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommuteDispute_WriteStaff extends AppCompatActivity implements View.OnClickListener{

    String disputeStartTime, disputeEndTime, disputeMessage, disputeDateStr;
    TextView disputeDateTv, workTimeTv, homeTimeTv, contentEt;
    ImageButton backib, disputeDateIBtn;
    Button completeBtn;
    Dialog timePickDialog, showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;
    boolean completeBtnState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commutedispute_writestaff);

        backib=findViewById(R.id.cdwstaff_backib); backib.setOnClickListener(this);
        disputeDateIBtn=findViewById(R.id.cdwstaff_disputeDateIBtn); disputeDateIBtn.setOnClickListener(this);
        disputeDateTv=findViewById(R.id.cdwstaff_disputeDateTv);
        workTimeTv=findViewById(R.id.cdwstaff_workTimeTv); workTimeTv.setOnClickListener(this);
        homeTimeTv=findViewById(R.id.cdwstaff_homeTimeTv); homeTimeTv.setOnClickListener(this);
        contentEt=findViewById(R.id.cdwstaff_contentEt);
        completeBtn=findViewById(R.id.cdwstaff_completeBtn); completeBtn.setOnClickListener(this);

        timePickDialog = new Dialog(CommuteDispute_WriteStaff.this);
        timePickDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        timePickDialog.setContentView(R.layout.dialog_time_setting);// xml 레이아웃 파일과 연결

        baseDialog_okCenter = new BaseDialog_OkCenter(CommuteDispute_WriteStaff.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(CommuteDispute_WriteStaff.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

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
        switch (view.getId()) {
            case R.id.cdwstaff_backib:
                Intent intent = new Intent(getApplicationContext(), CommuteDisputeList.class);
                startActivity(intent);
                finish();
                break;
            case R.id.cdwstaff_disputeDateIBtn:
                showCalendarDialog(disputeDateTv);
                break;
            case R.id.cdwstaff_workTimeTv:
                showTimePickerDialog(workTimeTv);
                break;
            case R.id.cdwstaff_homeTimeTv:
                showTimePickerDialog(homeTimeTv);
                break;
            case R.id.cdwstaff_completeBtn:
                disputeStartTime=workTimeTv.getText().toString().trim();
                disputeEndTime=homeTimeTv.getText().toString().trim();
                disputeMessage=contentEt.getText().toString().trim();
                disputeDateStr=disputeDateTv.getText().toString().trim();
                try {

                    //네트워크 요청 구현
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    CommuteApiService apiService = retrofit.create(CommuteApiService.class);

                    // 사업장등록에 필요한 데이터를 CommuteRequestDto 객체로 생성
                    CommuteRequestDto commuteRequestDto = new CommuteRequestDto(
                            "2023-08-23", //출퇴근 일자, disputeDateStr
                            null, //출퇴근 요일
                            null, //출근 시간
                            null, //퇴근 시간
                            false, //지각 여부
                            disputeMessage, //출퇴근 관련 이의 신청 메시지
                            disputeStartTime, //정정 출근 시간
                            disputeEndTime //정정 퇴근 시간
                    );

                    //적은 id를 기반으로 db에 검색
                    Call<Void> call = apiService.disputeCommute(1L, commuteRequestDto); // staffId에 해당하는 값을 설정해야 함
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                showCompleteDl();
                            } else {
                                try {
                                    String errorResponse = response.errorBody().string();
                                    Toast.makeText(CommuteDispute_WriteStaff.this, "출퇴근시간 이의신청 실패!! 에러 메시지: " + errorResponse, Toast.LENGTH_SHORT).show();
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

    // 달력 다이얼로그 띄우기
    public void showCalendarDialog(final TextView targetTextView) {
        CalendarDialog calendarDialog = new CalendarDialog(CommuteDispute_WriteStaff.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 사용자가 날짜를 선택하면 호출되는 콜백 메서드
                        // 여기에 선택한 날짜 처리 코드를 작성합니다.
                        String dateString = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";
                        targetTextView.setText(dateString);
                    }
                });

        // 다이얼로그 띄우기
        calendarDialog.show();
    }

    //시간을 누르면 다이얼로그 생성
    private void showTimePickerDialog(final TextView targetTextView) {
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

                targetTextView.setText( timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                timePickDialog.dismiss();

            }
        });
    }

    private void checkInputValidity() {
        // 버튼 활성화&비활성화
        boolean isContentEt = !contentEt.getText().toString().trim().isEmpty(); // true
        boolean isDisputeDateTv = !disputeDateTv.getText().toString().trim().equals("이의신청할 날짜"); // true

        completeBtnState = isContentEt && isDisputeDateTv;
        if (completeBtnState==true){
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
        }
    }

    // 완료'버튼 클릭 시
    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        join_okdl_commentTv.setText("출퇴근시간 이의신청을 완료하였습니다.");
        // '회원가입 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommuteDisputeList.class);
                startActivity(intent);
                finish();
            }
        });
    }

}