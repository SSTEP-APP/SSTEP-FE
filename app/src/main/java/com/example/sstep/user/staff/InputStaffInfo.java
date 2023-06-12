package com.example.sstep.user.staff;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.sstep.CalendarDialog;
import com.example.sstep.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InputStaffInfo extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener{

    ImageButton backib;
    LinearLayout ci_updownHL,pi_updownHL,yi_updownHL, ci_updownhidL,pi_updownhidL,yi_updownhidL,
            pi_rbhidL1, pi_rbhidL2, pi_rbhidL3, pi_tgcbhidL, pi_lawrbcbhidL;
    ImageView ci_upiconIv,pi_upiconIv,yi_upiconIv;
    CheckBox pi_hourCb1,pi_hourCb2,pi_hourCb3, pi_trainingCb;
    RadioGroup pi_payRg, pi_lawRg;
    RadioButton pi_payhourRb,pi_paydayRb,pi_paymonthRb, pi_lawRb1,pi_lawRb2,pi_lawRb3;
    Button ci_indateBtn, ci_wageBtn, pi_setBtn1, pi_setBtn2, pi_tgsetBtn1, pi_tgsetBtn2, pi_leftBtn, pi_rightBtn,
            yi_leftBtn,yi_rightBtn;
    TextView pi_ymTv,yi_ymTv, pi_insapplyTv1, pi_insapplyTv2, yi_applyTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputstaffinfo);

        backib = findViewById(R.id.isi_backib); backib.setOnClickListener(this);
        ci_updownHL = findViewById(R.id.isi_ci_updownHL); ci_updownHL.setOnClickListener(this);
        pi_updownHL = findViewById(R.id.isi_pi_updownHL); pi_updownHL.setOnClickListener(this);
        yi_updownHL = findViewById(R.id.isi_yi_updownHL); yi_updownHL.setOnClickListener(this);
        ci_updownhidL = findViewById(R.id.isi_ci_updownhidL);
        pi_updownhidL = findViewById(R.id.isi_pi_updownhidL);
        yi_updownhidL = findViewById(R.id.isi_yi_updownhidL);
        ci_upiconIv = findViewById(R.id.isi_ci_upiconIv);
        pi_upiconIv = findViewById(R.id.isi_pi_upiconIv);
        yi_upiconIv = findViewById(R.id.isi_yi_upiconIv);
        pi_payRg = findViewById(R.id.isi_pi_payRg); pi_payRg.setOnCheckedChangeListener(this);
        pi_payhourRb = findViewById(R.id.isi_pi_payhourRb);
        pi_paydayRb = findViewById(R.id.isi_pi_paydayRb);
        pi_paymonthRb = findViewById(R.id.isi_pi_paymonthRb);
        pi_rbhidL1 = findViewById(R.id.isi_pi_rbhidL1);
        pi_rbhidL2 = findViewById(R.id.isi_pi_rbhidL2);
        pi_rbhidL3 = findViewById(R.id.isi_pi_rbhidL3);
        pi_hourCb1 = findViewById(R.id.isi_pi_hourCb1); pi_hourCb1.setOnCheckedChangeListener(this);
        pi_hourCb2 = findViewById(R.id.isi_pi_hourCb2); pi_hourCb2.setOnCheckedChangeListener(this);
        pi_hourCb3 = findViewById(R.id.isi_pi_hourCb3); pi_hourCb3.setOnCheckedChangeListener(this);
        pi_setBtn1 = findViewById(R.id.isi_pi_setBtn1);
        pi_setBtn2 = findViewById(R.id.isi_pi_setBtn2);
        pi_trainingCb = findViewById(R.id.isi_pi_trainingCb); pi_trainingCb.setOnCheckedChangeListener(this);
        pi_tgcbhidL = findViewById(R.id.isi_pi_tgcbhidL);
        pi_lawRg = findViewById(R.id.isi_pi_lawRg); pi_lawRg.setOnCheckedChangeListener(this);
        pi_lawRb1 = findViewById(R.id.isi_pi_lawRb1); pi_lawRb1.setOnCheckedChangeListener(this);
        pi_lawRb2 = findViewById(R.id.isi_pi_lawRb2); pi_lawRb2.setOnCheckedChangeListener(this);
        pi_lawRb3 = findViewById(R.id.isi_pi_lawRb3); pi_lawRb3.setOnCheckedChangeListener(this);
        pi_lawrbcbhidL = findViewById(R.id.isi_pi_lawrbcbhidL);
        ci_indateBtn=findViewById(R.id.isi_ci_indateBtn); ci_indateBtn.setOnClickListener(this);
        ci_wageBtn=findViewById(R.id.isi_ci_wageBtn); ci_wageBtn.setOnClickListener(this);
        pi_tgsetBtn1=findViewById(R.id.isi_pi_tgsetBtn1); pi_tgsetBtn1.setOnClickListener(this);
        pi_tgsetBtn2=findViewById(R.id.isi_pi_tgsetBtn2); pi_tgsetBtn2.setOnClickListener(this);
        pi_leftBtn=findViewById(R.id.isi_pi_leftBtn);
        pi_rightBtn=findViewById(R.id.isi_pi_rightBtn);
        pi_ymTv=findViewById(R.id.isi_pi_ymTv);
        yi_leftBtn=findViewById(R.id.isi_yi_leftBtn);
        yi_rightBtn=findViewById(R.id.isi_yi_rightBtn);
        yi_ymTv=findViewById(R.id.isi_yi_ymTv);
        pi_insapplyTv1=findViewById(R.id.isi_pi_insapplyTv1);
        pi_insapplyTv2=findViewById(R.id.isi_pi_insapplyTv2);
        yi_applyTv=findViewById(R.id.isi_yi_applyTv);

        // 년도, 월 날짜 이동
        DateYearMonth_NavigationClickListener dateYearMonth_NavigationClickListener = new DateYearMonth_NavigationClickListener(pi_ymTv, pi_leftBtn, pi_rightBtn){
            @Override
            public void onClick(View view) {
                super.onClick(view);
                String data = pi_ymTv.getText().toString().trim();
                String month = data.substring(data.indexOf(" ") + 1);  // 월 정보 추출
                pi_insapplyTv1.setText("급여정산일은 13일입니다. \n " + data + " 14일부터 급여에 적용됩니다.");
                pi_insapplyTv2.setText("급여 적용 시작월은 " + month + "입니다.");
            }
        };
        pi_leftBtn.setOnClickListener(dateYearMonth_NavigationClickListener);
        pi_rightBtn.setOnClickListener(dateYearMonth_NavigationClickListener);

        // 년도 날짜 이동
        DateYear_NavigationClickListener dateYear_NavigationClickListener = new DateYear_NavigationClickListener(yi_ymTv, yi_leftBtn, yi_rightBtn){
            @Override
            public void onClick(View view) {
                super.onClick(view);
                // 추가로 수행할 작업
                String data = yi_ymTv.getText().toString().trim();
                yi_applyTv.setText(data + "에 연차가 적용됩니다.");
            }
        };
        yi_leftBtn.setOnClickListener(dateYear_NavigationClickListener);
        yi_rightBtn.setOnClickListener(dateYear_NavigationClickListener);

        setInitialDate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.isi_backib: // 뒤로가기
                break;
            case R.id.isi_ci_updownHL: // 출퇴근정보
                if (ci_updownhidL.getVisibility() == View.GONE) {
                    ci_updownhidL.setVisibility(View.VISIBLE);
                    ci_upiconIv.setBackgroundResource(R.drawable.yicon_down);
                }else{
                    ci_updownhidL.setVisibility(View.GONE);
                    ci_upiconIv.setBackgroundResource(R.drawable.yicon_up);
                }
                break;
            case R.id.isi_pi_updownHL: // 급여정보
                if (pi_updownhidL.getVisibility() == View.GONE) {
                    pi_updownhidL.setVisibility(View.VISIBLE);
                    pi_upiconIv.setBackgroundResource(R.drawable.yicon_down);
                }else{
                    pi_updownhidL.setVisibility(View.GONE);
                    pi_upiconIv.setBackgroundResource(R.drawable.yicon_up);
                }
                break;
            case R.id.isi_yi_updownHL: // (선택)연차
                if (yi_updownhidL.getVisibility() == View.GONE) {
                    yi_updownhidL.setVisibility(View.VISIBLE);
                    yi_upiconIv.setBackgroundResource(R.drawable.yicon_down);
                }else{
                    yi_updownhidL.setVisibility(View.GONE);
                    yi_upiconIv.setBackgroundResource(R.drawable.yicon_up);
                }
                break;
            case R.id.isi_ci_indateBtn: // 입사일
                showCalendarDialog(ci_indateBtn);
                break;
            case R.id.isi_ci_wageBtn: // 급여지급일
                showCalendarDialog(ci_wageBtn);
                break;
            case R.id.isi_pi_tgsetBtn1: // 수습종료일 설정
                showCalendarDialog(pi_tgsetBtn1);
                break;
            case R.id.isi_pi_tgsetBtn2: // 수습기간 급여비율 설정
                showSelectRateDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.isi_pi_hourCb1: // (+)주휴수당 자동 가산
                if (isChecked){
                    pi_setBtn1.setVisibility(View.VISIBLE);
                }else{
                    pi_setBtn1.setVisibility(View.GONE);
                }
                break;
            case R.id.isi_pi_hourCb2: // (-)휴게시간 자동 차감
                if (isChecked){
                    pi_setBtn2.setVisibility(View.VISIBLE);
                }else{
                    pi_setBtn2.setVisibility(View.GONE);
                }
                break;
            case R.id.isi_pi_trainingCb: // 수습기간 없음
                if (isChecked){
                    pi_tgcbhidL.setVisibility(View.GONE);
                }else{
                    pi_tgcbhidL.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()){
            case R.id.isi_pi_payRg: // '시급,일급,월급' Rg
                switch (checkedId){
                    case R.id.isi_pi_payhourRb: // 시급
                        pi_rbhidL1.setVisibility(View.VISIBLE);
                        pi_rbhidL2.setVisibility(View.GONE);
                        pi_rbhidL3.setVisibility(View.GONE);
                        break;
                    case R.id.isi_pi_paydayRb: // 일급
                        pi_rbhidL1.setVisibility(View.GONE);
                        pi_rbhidL2.setVisibility(View.VISIBLE);
                        pi_rbhidL3.setVisibility(View.GONE);
                        break;
                    case R.id.isi_pi_paymonthRb: // 월급
                        pi_rbhidL1.setVisibility(View.GONE);
                        pi_rbhidL2.setVisibility(View.GONE);
                        pi_rbhidL3.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.isi_pi_lawRg: // '4대보험,프리랜서,적용안함' Rg
                switch (checkedId){
                    case R.id.isi_pi_lawRb1: // 4대보험
                        pi_lawrbcbhidL.setVisibility(View.VISIBLE);
                        break;
                    case R.id.isi_pi_lawRb2: // 프리랜서
                    case R.id.isi_pi_lawRb3: // 적용안함
                        pi_lawrbcbhidL.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    // 달력 다이얼로그 띄우기
    public void showCalendarDialog(final Button button) {
        CalendarDialog calendarDialog = new CalendarDialog(InputStaffInfo.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 사용자가 날짜를 선택하면 호출되는 콜백 메서드
                        // 여기에 선택한 날짜 처리 코드를 작성합니다.
                        String dateString = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";
                        button.setText(dateString);

                        if(!pi_tgsetBtn1.getText().toString().equals("설정")){
                            pi_tgsetBtn1.setBackgroundResource(R.drawable.yroundrec_w_sblue);
                            pi_tgsetBtn1.setTextColor(ContextCompat.getColor(InputStaffInfo.this, R.color.blue));
                        }
                    }
                });
        // 다이얼로그 띄우기
        calendarDialog.show();

    }

    // 수습기간 급여비율 설정 다이얼로그 띄우기
    public void showSelectRateDialog() {
        InputStaffInfo_pselectDialog inputStaffInfo_pselectDialog = new InputStaffInfo_pselectDialog
                (InputStaffInfo.this, new InputStaffInfo_pselectDialog.InputStaffInfo_pselectDialogListener() {
                    public void clickBtn(String data) {
                        pi_tgsetBtn2.setText(data);
                        if(!pi_tgsetBtn2.getText().toString().equals("설정")){
                            pi_tgsetBtn2.setBackgroundResource(R.drawable.yroundrec_w_sblue);
                            pi_tgsetBtn2.setTextColor(ContextCompat.getColor(InputStaffInfo.this, R.color.blue));
                        }
                    }
                });
        inputStaffInfo_pselectDialog.show();
    }

    // 초기 날짜 설정
    public void setInitialDate() {
        // 초기 상태로 오늘 날짜의 해당 월 설정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 M월", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy년", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        String currentMonth = sdf.format(currentDate);
        String currentYear = sdf2.format(currentDate);
        pi_ymTv.setText(currentMonth); yi_ymTv.setText(currentYear);
        String data = pi_ymTv.getText().toString().trim();
        String month = data.substring(data.indexOf(" ") + 1);  // 월 정보 추출
        pi_insapplyTv1.setText("급여정산일은 13일입니다. \n " + data + " 14일부터 급여에 적용됩니다.");
        pi_insapplyTv2.setText("급여 적용 시작월은 " + month + "입니다.");
    }

}