package com.example.sstep.document.contract;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.AppInData;
import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.CalendarDialog;
import com.example.sstep.CustomTextWatcher_Comma;
import com.example.sstep.R;
import com.example.sstep.document.certificate.Paper;
import com.example.sstep.document.work_doc_api.PhotoApiService;
import com.example.sstep.document.work_doc_api.WorkDocApiService;
import com.example.sstep.document.work_doc_api.WorkDocRequestDto;
import com.example.sstep.document.work_doc_api.WorkDocResponseDto;
import com.example.sstep.todo.checklist.CheckList_photo_dialog;
import com.example.sstep.todo.checklist.Checklist_detail;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaperWinput extends AppCompatActivity implements View.OnClickListener{

    ScrollView scrollView;
    CustomDrawingView customDrawingView;
    PwiStartTimeAdapterSpinner startTimeAdapter;
    PwiEndTimeAdapterSpinner endTimeAdapter;
    com.example.sstep.document.contract.PwiWorkdayAdapterSpinner PwiWorkdayAdapterSpinner;
    Spinner workTerms_startTimeSp, workTerms_endTimeSp, workTerms_workDaySp;
    String[] startTimeSpDatas, endTimeSpDatas, workTerms_workDaySpDatas;
    EditText workTerms_workPlaceEt,workTerms_workContentEt,wageEt,
            storeNameEt,storeAddressEt,storePhoneEt,storeCeoNameEt;
    TextView workTerms_workPlaceLimitTv, workTerms_workContentLimitTv, restdaySelectTv,
            restdayTv1,restdayTv2, workTerms_workstartDateTv,writeDayTv;
    ImageView workTerms_updownIv,wage_updownIv;
    LinearLayout workTerms_hidL,wage_hidL;
    ImageButton backib, workTerms_workstartDateIb, storeCeoSignRefreshIb, writeDayIb;
    Button completeBtn;
    int workdayCount = 0;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;

    RadioGroup wageTypeRG;
    RadioButton dayRB, weekRB, monthRB;
    File file;

    long storeId, staffId;


    Boolean isStartTimeSp=false,isendTimeSp=false, isworkDaySp=false, isprivacyCb=false;
    EditTextValidator storeNameValidator, storeAddressValidator, storePhoneValidator, storeCeoNameValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperwinput);

        scrollView=findViewById(R.id.paperwinput_scroll);
        storeCeoSignRefreshIb = findViewById(R.id.paperwinput_storeInfo_storeCeoSignRefreshIb);
        customDrawingView=findViewById(R.id.customDrawingView);

        storeNameEt=findViewById(R.id.paperwinput_storeInfo_storeNameEt);
        storeAddressEt=findViewById(R.id.paperwinput_storeInfo_storeAddressEt);
        storePhoneEt=findViewById(R.id.paperwinput_storeInfo_storePhoneEt);
        storeCeoNameEt=findViewById(R.id.paperwinput_storeInfo_storeCeoNameEt);
        workTerms_startTimeSp = findViewById(R.id.paperwinput_workTerms_startTimeSp);
        workTerms_endTimeSp = findViewById(R.id.paperwinput_workTerms_endTimeSp);
        workTerms_workDaySp = findViewById(R.id.paperwinput_workTerms_workDaySp);
        workTerms_workPlaceEt = findViewById(R.id.paperwinput_workTerms_workPlaceEt);
        workTerms_workContentEt = findViewById(R.id.paperwinput_workTerms_workContentEt);
        workTerms_workPlaceLimitTv = findViewById(R.id.paperwinput_workTerms_workPlaceLimitTv);
        workTerms_workContentLimitTv = findViewById(R.id.paperwinput_workTerms_workContentLimitTv);
        workTerms_workstartDateTv=findViewById(R.id.paperwinput_workTerms_workstartDateTv);
        writeDayTv=findViewById(R.id.paperwinput_writeDayTv);

        wageEt=findViewById(R.id.paperwinput_wage_wageEt);

        wageTypeRG = findViewById(R.id.paperwinput_wage_paydayRg);
        monthRB = findViewById(R.id.paperwinput_wage_paydayMonthRb);
        weekRB = findViewById(R.id.paperwinput_wage_paydayWeekRb);
        dayRB = findViewById(R.id.paperwinput_wage_paydayDayRb);

        backib=findViewById(R.id.paperwinput_backib); backib.setOnClickListener(this);
        completeBtn=findViewById(R.id.paperwinput_completeBtn); completeBtn.setOnClickListener(this);
        workTerms_updownIv=findViewById(R.id.paperwinput_workTerms_updownIv);workTerms_updownIv.setOnClickListener(this);
        wage_updownIv=findViewById(R.id.paperwinput_wage_updownIv);wage_updownIv.setOnClickListener(this);
        workTerms_hidL=findViewById(R.id.paperwinput_workTerms_hidL);
        wage_hidL=findViewById(R.id.paperwinput_wage_hidL);
        workTerms_workstartDateIb = findViewById(R.id.paperwinput_workTerms_workstartDateIb); workTerms_workstartDateIb.setOnClickListener(this);
        writeDayIb=findViewById(R.id.paperwinput_writeDayIb); writeDayIb.setOnClickListener(this);

        setupEditTextListeners();
        checkCompleteBtnState();

        baseDialog_okCenter = new BaseDialog_OkCenter(PaperWinput.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(PaperWinput.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        // 앱데이터 저장된 값 가져오기
        AppInData appInData = (AppInData) getApplication(); // MyApplication 클래스의 인스턴스 가져오기
        storeId = appInData.getStoreId();
        if(storeId <3){
            storeId=3;
        }

        Intent intent1 = getIntent();
        staffId = intent1.getLongExtra("staffId", 0);

        // EditText 천단위 콤마(,)
        wageEt.addTextChangedListener(new CustomTextWatcher_Comma(wageEt));

        // 전화번호 입력시 자동 '-' 입력
        storePhoneEt.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // 소정근로시간_ 시작시간, 종료시간_ spinner
        startTimeSpDatas = getResources().getStringArray(R.array.paperw_workHour_startTime);
        endTimeSpDatas = getResources().getStringArray(R.array.paperw_workHour_endTime);
        ArrayList<String> startTimeSpList = new ArrayList<>();
        for (int i = 0; i < startTimeSpDatas.length; i++) {
            startTimeSpList.add(startTimeSpDatas[i]);
        }
        ArrayList<String> endTimeSpList = new ArrayList<>();
        for (int i = 0; i < endTimeSpDatas.length; i++) {
            endTimeSpList.add(endTimeSpDatas[i]);
        }

        // 시작시간 어댑터 설정, // 종료시간 어댑터 설정
        startTimeAdapter = new PwiStartTimeAdapterSpinner(this, startTimeSpList);
        endTimeAdapter = new PwiEndTimeAdapterSpinner(this, endTimeSpList);
        workTerms_startTimeSp.setAdapter(startTimeAdapter);
        workTerms_endTimeSp.setAdapter(endTimeAdapter);


        // 소정근로시간_시작시간
        workTerms_startTimeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    workTerms_startTimeSp.setBackgroundResource(R.drawable.yedittext_w_sg);
                    ((TextView) view.findViewById(R.id.paperwinput_spinner_view_text)).setTextColor(getResources().getColor(R.color.yedittext_sg));
                    isStartTimeSp=false;
                } else {
                    workTerms_startTimeSp.setBackgroundResource(R.drawable.yedittext_w_sblack);
                    isStartTimeSp=true;
                }

                // 선택된 시작시간 전달하여 종료시간 어댑터 업데이트
                String selectedStartTime = parent.getSelectedItem().toString();
                endTimeAdapter.updateEndTimeList(selectedStartTime);
                checkCompleteBtnState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 소정근로시간_종료시간
        workTerms_endTimeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    workTerms_endTimeSp.setBackgroundResource(R.drawable.yedittext_w_sg);
                    ((TextView) view.findViewById(R.id.paperwinput_spinner_view_text)).setTextColor(getResources().getColor(R.color.yedittext_sg));
                    isendTimeSp=false;
                } else {
                    workTerms_endTimeSp.setBackgroundResource(R.drawable.yedittext_w_sblack);
                    isendTimeSp=true;
                }
                checkCompleteBtnState();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 근무일 _spinner
        workTerms_workDaySpDatas=getResources().getStringArray(R.array.paperw_workDay);
        ArrayList<String> workTerms_workDaySpList = new ArrayList<>();
        for(int i=0; i<workTerms_workDaySpDatas.length; i++) {
            workTerms_workDaySpList.add(workTerms_workDaySpDatas[i]);
        }
        PwiWorkdayAdapterSpinner = new PwiWorkdayAdapterSpinner(this,workTerms_workDaySpList);
        workTerms_workDaySp.setAdapter(PwiWorkdayAdapterSpinner);


        // 근무장소 _실시간 글자수
        workTerms_workPlaceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String PlacewordLimit = workTerms_workPlaceEt.getText().toString();
                workTerms_workPlaceLimitTv.setText(PlacewordLimit.length()+"/50");
                if(PlacewordLimit.length() > 50) {
                    workTerms_workPlaceLimitTv.setTextColor(getResources().getColor(R.color.red));
                } else {
                    workTerms_workPlaceLimitTv.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 업무의 내용 _실시간 글자수
        workTerms_workContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String PlacewordLimit = workTerms_workContentEt.getText().toString();
                workTerms_workContentLimitTv.setText(PlacewordLimit.length()+"/50");
                if(PlacewordLimit.length() > 50) {
                    workTerms_workContentLimitTv.setTextColor(getResources().getColor(R.color.red));
                } else {
                    workTerms_workContentLimitTv.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 서명 새로고침 버튼
        storeCeoSignRefreshIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDrawingView.clearCanvas();
                checkCompleteBtnState();
            }
        });

        // 서명할때는 스크롤 방지
        customDrawingView.setParentScrollView(scrollView);

        scrollView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                customDrawingView.getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                customDrawingView.getParent().requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });

        // 그림 변경될때마다 '계약서 작성완료 버튼' 상태 업데이트
        customDrawingView.setOnDrawingChangeListener(new CustomDrawingView.OnDrawingChangeListener() {
            @Override
            public void onDrawingChanged(boolean hasDrawing) {
                checkCompleteBtnState();
            }
        });

        // '작성일' textview 오늘 날짜 데이터 넣기
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 M월 dd일", Locale.getDefault());
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
        writeDayTv.setText(currentDate);


        //정보입력하기
        try {

            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WorkDocApiService apiService = retrofit.create(WorkDocApiService.class);
            //적은 id를 기반으로 db에 검색
            Call<WorkDocResponseDto> call = apiService.getInfoForWorkDoc(staffId, storeId);
            call.enqueue(new Callback<WorkDocResponseDto>() {
                @Override
                public void onResponse(Call<WorkDocResponseDto> call, Response<WorkDocResponseDto> response) {
                    if (response.isSuccessful()) {
                        WorkDocResponseDto workDocResponseDto  = response.body();
                        // 적은 id로 패스워드 데이터 가져오기
                        storeNameEt.setText(workDocResponseDto.getStoreName());
                        storeAddressEt.setText(workDocResponseDto.getStoreAddress());
                        storePhoneEt.setText(workDocResponseDto.getStorePhoneNum());
                        storeCeoNameEt.setText(workDocResponseDto.getStoreOwnerName());
                        workTerms_workstartDateTv.setText(workDocResponseDto.getStartDay());
                        wageEt.setText(""+workDocResponseDto.getHourMoney());

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "실패"+ response.toString(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<WorkDocResponseDto> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = "요청 실패: " + t.getMessage();
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    storeAddressEt.setText(errorMessage);
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }





    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.paperwinput_backib: // 뒤로가기
                intent = new Intent(getApplicationContext(), PaperW.class);
                startActivity(intent);
                finish();
                break;
            case R.id.paperwinput_workTerms_workstartDateIb:
                showCalendarDialog(workTerms_workstartDateTv);
                break;
            case R.id.paperwinput_writeDayIb:
                showCalendarDialog(writeDayTv);
                break;
            case R.id.paperwinput_workTerms_updownIv: // 근무조건 updown 버튼
                if (workTerms_hidL.getVisibility() == View.GONE) {
                    workTerms_hidL.setVisibility(View.VISIBLE);
                    workTerms_updownIv.setBackgroundResource(R.drawable.yicon_down);
                }else{
                    workTerms_hidL.setVisibility(View.GONE);
                    workTerms_updownIv.setBackgroundResource(R.drawable.yicon_up);
                }
                break;
            case R.id.paperwinput_wage_updownIv: // 임금 updown 버튼
                if (wage_hidL.getVisibility() == View.GONE) {
                    wage_hidL.setVisibility(View.VISIBLE);
                    wage_updownIv.setBackgroundResource(R.drawable.yicon_down);
                }else{
                    wage_hidL.setVisibility(View.GONE);
                    wage_updownIv.setBackgroundResource(R.drawable.yicon_up);
                }
                break;
            case R.id.paperwinput_completeBtn:
                showCompleteDl();
                break;
            default:
                break;
        }
    }

    // '계약서 작성완료'버튼 클릭 시
    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);

        join_okdl_commentTv.setText("로딩중");

        try {
            PhotoTask photoTask = new PhotoTask(); // PhotoTask 인스턴스 생성
            photoTask.execute();

            long photoId = photoTask.get();
            Toast.makeText(PaperWinput.this, ""+photoId, Toast.LENGTH_SHORT).show();
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WorkDocRequestDto workDocRequestDto = new WorkDocRequestDto(photoId);

            WorkDocApiService apiService = retrofit.create(WorkDocApiService.class);

            Call<Void> call = apiService.registerWorkDocFirst(staffId, workDocRequestDto);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        join_okdl_commentTv.setText("작성에 성공했습니다.");
                    }else {
                        join_okdl_commentTv.setText("작성 실패"+response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
        // '회원가입 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Paper.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 달력 다이얼로그 띄우기
    public void showCalendarDialog(final TextView targetTextView) {
        CalendarDialog calendarDialog = new CalendarDialog(PaperWinput.this,
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

    private void setupEditTextListeners() {
        EditTextValidator.ValidationListener validationListener = new EditTextValidator.ValidationListener() {
            @Override
            public void onValidationResult(boolean hasContent) {
                checkCompleteBtnState();
            }
        };

        EditTextValidator storeNameValidator = new EditTextValidator(storeNameEt, validationListener);
        storeNameEt.addTextChangedListener(storeNameValidator);

        EditTextValidator storeAddressValidator = new EditTextValidator(storeAddressEt, validationListener);
        storeAddressEt.addTextChangedListener(storeAddressValidator);

        EditTextValidator storePhoneValidator = new EditTextValidator(storePhoneEt, validationListener);
        storePhoneEt.addTextChangedListener(storePhoneValidator);

        EditTextValidator storeCeoNameValidator = new EditTextValidator(storeCeoNameEt, validationListener);
        storeCeoNameEt.addTextChangedListener(storeCeoNameValidator);
    }


    // 계약서 작성완료 버튼' 활성화/비활성화
    public void checkCompleteBtnState() {
        boolean isStoreNameEt = !storeNameEt.getText().toString().trim().isEmpty();
        boolean isStoreAddressEt = !storeAddressEt.getText().toString().trim().isEmpty();
        boolean isStorePhoneEt = !storePhoneEt.getText().toString().trim().isEmpty();
        boolean isStoreCeoNameEt = !storeCeoNameEt.getText().toString().trim().isEmpty();

        if(isStoreNameEt && isStoreAddressEt && isStorePhoneEt && isStoreCeoNameEt &&
            customDrawingView.isCeoSign() && isStartTimeSp && isendTimeSp /*&& isworkDaySp && isprivacyCb*/){
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
        }
    }


    private class PhotoTask extends AsyncTask<Void, Void, Long> {
        @Override
        protected Long doInBackground(Void... params) {

            scrollView = findViewById(R.id.paperwinput_scroll);
            int totalHeight = scrollView.getChildAt(0).getHeight();
            int totalWidth = scrollView.getChildAt(0).getWidth();
            Bitmap bitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            scrollView.draw(canvas);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); // Bitmap을 JPEG 바이트 배열로 압축
            byte[] byteArray = stream.toByteArray();
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", "paperW.jpeg", requestFile); // 파일 이름을 "19hdoc.jpeg"로 변경

            // 네트워크 요청을 백그라운드 스레드에서 수행
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PhotoApiService apiService2 = retrofit.create(PhotoApiService.class);
            Call<Long> call2 = apiService2.savePhoto(body);

            try {
                Response<Long> response = call2.execute();

                if (response.isSuccessful()) {
                    return response.body(); // 성공한 경우 이미지 ID 반환
                } else {

                    return response.body();

                }
            } catch (IOException e) {
                e.printStackTrace();
                return -1L; // 예외 발생한 경우 -1 반환 또는 다른 실패 코드 반환
            }
        }

        @Override
        protected void onPostExecute(Long result) {
            showComplete_dialog.show();
            // 다이얼로그의 배경을 투명으로 만든다.
            showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView join_okdl_commentTv;
            Button join_okdl_okBtn;
            join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
            join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
            join_okdl_commentTv.setText("로딩중");

            if (result != null) {
                if (result != -1L) {
                    String message = "보건증 작성을 완료하였습니다. 이미지 ID: " + result;
                    join_okdl_commentTv.setText(message);
                    // 이미지 ID를 사용하여 이미지를 가져오거나 다른 작업 수행

                    // Toast로 결과를 표시
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = "보건증 작성이 실패했습니다!";
                    join_okdl_commentTv.setText(errorMessage);

                    // Toast로 실패 메시지 표시
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            } else {
                String nullMessage = "서버 응답이 null입니다.";
                join_okdl_commentTv.setText(nullMessage);

                // Toast로 null 메시지 표시
                Toast.makeText(getApplicationContext(), nullMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }

}