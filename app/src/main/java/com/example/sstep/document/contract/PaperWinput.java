package com.example.sstep.document.contract;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
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

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.CalendarDialog;
import com.example.sstep.CustomTextWatcher_Comma;
import com.example.sstep.R;
import com.example.sstep.document.certificate.Paper;
import com.example.sstep.user.join.JoinActivity;
import com.example.sstep.user.login.Login;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PaperWinput extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{

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
    LinearLayout workTerms_hidL,wage_hidL, workTerms_breakhidL;
    CheckBox restdayMonCb, restdayTheCb, restdayWedCb, restdayThuCb,restdayFriCb, restdaySatCb,restdaySunCb, privacyCb;
    ImageButton backib, workTerms_workstartDateIb, storeCeoSignRefreshIb, writeDayIb;
    Button completeBtn;
    RadioGroup breaktimeRg;
    RadioButton NobreaktimeRb, YesbreaktimeRb;
    public CheckBox[] restdayCheckedList;
    int workdayCount = 0;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;

    Boolean isStartTimeSp=false,isendTimeSp=false, isworkDaySp=false, isprivacyCb=false,
            isStoreNameEt=false,isStoreAddressEt=false,isStorePhoneEt=false,isStoreCeoNameEt=false;
    EditTextValidator storeNameValidator, storeAddressValidator, storePhoneValidator, storeCeoNameValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperwinput);

        scrollView=findViewById(R.id.paperwinput_scroll);
        storeCeoSignRefreshIb = findViewById(R.id.paperwinput_storeInfo_storeCeoSignRefreshIb);
        customDrawingView=findViewById(R.id.customDrawingView);

        breaktimeRg=findViewById(R.id.paperwinput_workTerms_breaktimeRg);
        NobreaktimeRb=findViewById(R.id.paperwinput_workTerms_NobreaktimeRb);
        YesbreaktimeRb=findViewById(R.id.paperwinput_workTerms_YesbreaktimeRb);
        workTerms_breakhidL=findViewById(R.id.paperwinput_workTerms_breakhidL);
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
        privacyCb=findViewById(R.id.paperwinput_privacyCb);

        backib=findViewById(R.id.paperwinput_backib); backib.setOnClickListener(this);
        completeBtn=findViewById(R.id.paperwinput_completeBtn); completeBtn.setOnClickListener(this);
        workTerms_updownIv=findViewById(R.id.paperwinput_workTerms_updownIv);workTerms_updownIv.setOnClickListener(this);
        wage_updownIv=findViewById(R.id.paperwinput_wage_updownIv);wage_updownIv.setOnClickListener(this);
        workTerms_hidL=findViewById(R.id.paperwinput_workTerms_hidL);
        wage_hidL=findViewById(R.id.paperwinput_wage_hidL);
        restdaySelectTv = findViewById(R.id.paperwinput_workTerms_restdaySelectTv);
        restdayTv1 = findViewById(R.id.paperwinput_workTerms_restdayTv1);
        restdayTv2 = findViewById(R.id.paperwinput_workTerms_restdayTv2);
        restdayMonCb = findViewById(R.id.paperwinput_workTerms_restdayMonCb); restdayMonCb.setOnCheckedChangeListener(this);
        restdayTheCb = findViewById(R.id.paperwinput_workTerms_restdayTheCb); restdayTheCb.setOnCheckedChangeListener(this);
        restdayWedCb = findViewById(R.id.paperwinput_workTerms_restdayWedCb); restdayWedCb.setOnCheckedChangeListener(this);
        restdayThuCb = findViewById(R.id.paperwinput_workTerms_restdayThuCb); restdayThuCb.setOnCheckedChangeListener(this);
        restdayFriCb = findViewById(R.id.paperwinput_workTerms_restdayFriCb); restdayFriCb.setOnCheckedChangeListener(this);
        restdaySatCb = findViewById(R.id.paperwinput_workTerms_restdaySatCb); restdaySatCb.setOnCheckedChangeListener(this);
        restdaySunCb = findViewById(R.id.paperwinput_workTerms_restdaySunCb); restdaySunCb.setOnCheckedChangeListener(this);
        restdayCheckedList = new CheckBox[] {restdayMonCb, restdayTheCb, restdayWedCb, restdayThuCb, restdayFriCb, restdaySatCb, restdaySunCb};

        workTerms_workstartDateIb = findViewById(R.id.paperwinput_workTerms_workstartDateIb); workTerms_workstartDateIb.setOnClickListener(this);
        writeDayIb=findViewById(R.id.paperwinput_writeDayIb); writeDayIb.setOnClickListener(this);

        setupEditTextListeners();
        checkCompleteBtnState();

        baseDialog_okCenter = new BaseDialog_OkCenter(PaperWinput.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(PaperWinput.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        breaktimeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.paperwinput_workTerms_NobreaktimeRb:
                        workTerms_breakhidL.setVisibility(View.GONE);
                        break;
                    case R.id.paperwinput_workTerms_YesbreaktimeRb:
                        workTerms_breakhidL.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });


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
        workTerms_workDaySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(int i=0; i<restdayCheckedList.length; i++) {
                    restdayCheckedList[i].setChecked(false);
                    restdayCheckedList[i].setEnabled(true);
                }
                if(position==0) {
                    workTerms_workDaySp.setBackgroundResource(R.drawable.yedittext_w_sg);
                    ((TextView) view.findViewById(R.id.paperwinput_spinner_view_text)).setTextColor(getResources().getColor(R.color.yedittext_sg));
                    isworkDaySp = false;
                } else {
                    workTerms_workDaySp.setBackgroundResource(R.drawable.yedittext_w_sblack);
                    workdayCount = position;
                    isworkDaySp = true;
                }
                checkCompleteBtnState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

        // '개인정보 수집 및 이용 동의' 체크 시 true/false
        privacyCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isprivacyCb = isChecked;
                checkCompleteBtnState();
            }
        });
    }


    // 휴일 _선택된 CheckBox 색상변경 및 textView 에 값 넣기
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String restdayChecked="";
        int restdayCount = 0;
        switch (buttonView.getId()){
            case R.id.paperwinput_workTerms_restdayMonCb: // 월
                if (isChecked) {
                    restdayMonCb.setTextColor(getResources().getColor(R.color.black));
                } else {
                    restdayMonCb.setTextColor(getResources().getColor(R.color.ypaperwinput_cboff));
                }
                break;
            case R.id.paperwinput_workTerms_restdayTheCb: // 화
                if (isChecked) {
                    restdayTheCb.setTextColor(getResources().getColor(R.color.black));
                } else {
                    restdayTheCb.setTextColor(getResources().getColor(R.color.ypaperwinput_cboff));
                }
                break;
            case R.id.paperwinput_workTerms_restdayWedCb: // 수
                if (isChecked) {
                    restdayWedCb.setTextColor(getResources().getColor(R.color.black));
                } else {
                    restdayWedCb.setTextColor(getResources().getColor(R.color.ypaperwinput_cboff));
                }
                break;
            case R.id.paperwinput_workTerms_restdayThuCb: // 목
                if (isChecked) {
                    restdayThuCb.setTextColor(getResources().getColor(R.color.black));
                } else {
                    restdayThuCb.setTextColor(getResources().getColor(R.color.ypaperwinput_cboff));
                }
                break;
            case R.id.paperwinput_workTerms_restdayFriCb: // 금
                if (isChecked) {
                    restdayFriCb.setTextColor(getResources().getColor(R.color.black));
                } else {
                    restdayFriCb.setTextColor(getResources().getColor(R.color.ypaperwinput_cboff));
                }
                break;
            case R.id.paperwinput_workTerms_restdaySatCb: // 토
                if (isChecked) {
                    restdaySatCb.setTextColor(getResources().getColor(R.color.black));
                } else {
                    restdaySatCb.setTextColor(getResources().getColor(R.color.ypaperwinput_cboff));
                }
                break;
            case R.id.paperwinput_workTerms_restdaySunCb: // 일
                if (isChecked) {
                    restdaySunCb.setTextColor(getResources().getColor(R.color.black));
                } else {
                    restdaySunCb.setTextColor(getResources().getColor(R.color.ypaperwinput_cboff));
                }
                break;

            default:

                break;
        }
        String restdaySelect = "";
        for(int i=0; i<restdayCheckedList.length; i++) {
            if(restdayCheckedList[i].isChecked()==true) {
                restdayChecked += restdayCheckedList[i].getText().toString() + ", ";
                restdaySelect= restdayChecked.substring(0, restdayChecked.length() - 2);
                restdayCount += 1;
            }
        }
        if (restdaySelect==""){
            restdayTv1.setVisibility(View.INVISIBLE);
            restdayTv2.setVisibility(View.INVISIBLE);
            restdaySelectTv.setText(restdaySelect);
        } else {
            restdayTv1.setVisibility(View.VISIBLE);
            restdayTv2.setVisibility(View.VISIBLE);
            restdaySelectTv.setText(restdaySelect);
        }

        // 휴일 선택 개수 제한
        for(int i=0; i<restdayCheckedList.length; i++) {
            if (restdayCount >= 7-workdayCount) {
                if (restdayCheckedList[i].isChecked() == false) {
                    restdayCheckedList[i].setEnabled(false);
                } else {
                    restdayCheckedList[i].setEnabled(true);
                }
            } else {
                restdayCheckedList[i].setEnabled(true);
            }
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
        join_okdl_commentTv.setText("근로계약서 작성 완료하였습니다.");
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
            customDrawingView.isCeoSign() && isStartTimeSp && isendTimeSp && isworkDaySp && isprivacyCb){
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
        }
    }
}