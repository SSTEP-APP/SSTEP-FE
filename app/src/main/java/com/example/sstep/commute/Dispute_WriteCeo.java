package com.example.sstep.commute;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sstep.AppInData;
import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.commute.commute_api.CommuteApiService;
import com.example.sstep.commute.commute_api.CommuteRequestDto;
import com.example.sstep.commute.commute_api.CommuteResponseDto;
import com.example.sstep.document.work_doc_api.PhotoResponseDto;
import com.example.sstep.store.store_api.NullOnEmptyConverterFactory;
import com.example.sstep.todo.notice.Notice_view;
import com.example.sstep.todo.notice.notice_api.NoticeApiService;
import com.example.sstep.todo.notice.notice_api.NoticeResponseDto;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dispute_WriteCeo extends AppCompatActivity implements View.OnClickListener {

    AppInData appInData;
    String commuteDate, dayOfWeekStr, staffNameStr, workTime, homeTime;
    DayOfWeek dayOfWeek;
    long staffId, commuteId;
    ImageButton backib;
    TextView staffNameTv, disputeDateTv, workTimeTv, homeTimeTv, contentTv; TextView join_okdl_commentTv;
    Button noBtn, yesBtn;

    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispute_writeceo);

        backib=findViewById(R.id.cdwceo_backib); backib.setOnClickListener(this);
        workTimeTv=findViewById(R.id.cdwceo_workTimeTv); workTimeTv.setOnClickListener(this);
        homeTimeTv=findViewById(R.id.cdwceo_homeTimeTv); homeTimeTv.setOnClickListener(this);
        noBtn=findViewById(R.id.cdwceo_noBtn); noBtn.setOnClickListener(this);
        yesBtn=findViewById(R.id.cdwceo_yesBtn); yesBtn.setOnClickListener(this);
        staffNameTv=findViewById(R.id.cdwceo_staffNameTv);
        disputeDateTv=findViewById(R.id.cdwceo_disputeDateTv);
        contentTv=findViewById(R.id.cdwceo_contentTv);

        baseDialog_okCenter = new BaseDialog_OkCenter(Dispute_WriteCeo.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(Dispute_WriteCeo.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        // ID값 가지고 오기
        appInData = (AppInData) getApplication(); // MyApplication 클래스의 인스턴스 가져오기

        // Intent로 전달받은 데이터 가져오기
        Intent intent = getIntent();
        commuteId = intent.getLongExtra("commuteId", 0L);
        staffId = intent.getLongExtra("staffId", 0L);

        staffNameTv.setText(staffNameStr);
        disputeDateTv.setText(commuteDate + " (" + dayOfWeekStr + ")");

        //사장에게 이의신청 내용 보여주기
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new com.example.sstep.user.member.NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    CommuteApiService apiService = retrofit.create(CommuteApiService.class);

                    Call<CommuteResponseDto> call = apiService.getDispute(commuteId); // commuteId
                    Response<CommuteResponseDto> response = call.execute();

                    if (response.isSuccessful()) {
                        // 서버로부터 NoticeResponseDto 가져오기
                        final CommuteResponseDto commutes = response.body();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 성공적인 응답 처리
                                commuteDate = commutes.getCommuteDate();
                                dayOfWeek=commutes.getDayOfWeek();
                                dayOfWeekStr = convertdayOfWeekStr(dayOfWeek);
                                workTime = commutes.getDisputeStartTime();
                                homeTime = commutes.getDisputeEndTime();

                                staffNameTv.setText(commutes.getStaffName());
                                disputeDateTv.setText(commuteDate + " (" + dayOfWeekStr + ")");
                                workTimeTv.setText(workTime);
                                if(homeTime != null) {
                                    homeTimeTv.setText(homeTime);
                                }else{
                                    homeTimeTv.setText("미퇴근");
                                }
                                contentTv.setText(commutes.getDisputeMessage());
                                // responseBody를 바이트 배열로 변환
                            }
                        });
                    } else {
                        // 서버 응답 코드 확인
                        int responseCode = response.code();
                        // 서버 응답 데이터 확인
                        String responseData = response.errorBody().string();
                        // 에러 메시지 출력 또는 처리
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                handleError("서버 응답 오류 - 코드: " + responseCode + ", 데이터: " + responseData);
                            }
                        });
                        // 예외 로그 출력
                        Log.e("MyApp", "서버 응답 오류 - 코드: " + responseCode + ", 데이터: " + responseData);
                    }
                } catch (Exception e) {
                    final String errorMsg = e.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handleError(errorMsg);
                        }
                    });
                    // 예외 로그 출력
                    Log.e("MyApp", "예외 발생", e);
                }
            }
        }).start();

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.cdwceo_backib:
                intent = new Intent(getApplicationContext(), Dispute_CeoList.class);
                startActivity(intent);
                finish();
                break;
            case R.id.cdwceo_noBtn: // 반려
                break;
            case R.id.cdwceo_yesBtn: // 승인
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
                            null, //출퇴근 관련 이의 신청 메시지
                            workTime, //정정 출근 시간
                            homeTime //정정 퇴근 시간
                    );
                    Call<Void> call = apiService.updateDisputeCommute(staffId, commuteRequestDto); // staffId, commuteRequestDto

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                showCompleteDl("출퇴근시간 이의신청을 완료하였습니다.");
                                // 성공적인 응답 처리
                            } else {
                                // 기타 다른 상태 코드 처리
                                try {
                                    String errorResponse = response.errorBody().string();
                                    Toast.makeText(Dispute_WriteCeo.this, "이의신청 실패!! 에러 메시지: " + errorResponse, Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(getApplicationContext(), Dispute_CeoList.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 요일을 문자열로 변환하는 메서드
    private DayOfWeek convertDayOfWeek(String dayOfWeekStr) {
        switch (dayOfWeekStr) {
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
            case "일":
                return DayOfWeek.SUNDAY;
            default:
                return dayOfWeek; // 다른 경우에는 열거형 이름을 반환
        }
    }

    // 요일을 DayOfWeek로 변환하는 메서드
    private String convertdayOfWeekStr(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "월";
            case TUESDAY:
                return "화";
            case WEDNESDAY:
                return "수";
            case THURSDAY:
                return "목";
            case FRIDAY:
                return "금";
            case SATURDAY:
                return "토";
            case SUNDAY:
                return "일";
            default:
                return "변환 안됨"; // 다른 경우에는 열거형 이름을 반환
        }
    }

    private void handleError(String errorMsg) {
        Toast.makeText(this, errorMsg + "!!", Toast.LENGTH_SHORT).show();

    }
}