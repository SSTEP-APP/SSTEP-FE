package com.example.sstep.user.staff;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.sstep.CalendarDialog;
import com.example.sstep.R;
import com.example.sstep.document.certificate.Paper;
import com.example.sstep.store.RegisterStore;
import com.example.sstep.store.RegisterStore_calendarDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InputStaffInfo extends AppCompatActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener{

    ImageButton backib;
    LinearLayout ci_updownHL,pi_updownHL, ci_updownhidL,pi_updownhidL,
             pi_rbhidL2, pi_lawrbcbhidL;
    ImageView ci_upiconIv,pi_upiconIv;
    RadioGroup pi_payRg;
    RadioButton pi_payhourRb,pi_paydayRb,pi_paymonthRb;
    Button ci_indateBtn, ci_wageBtn, pi_tgsetBtn1, pi_leftBtn, pi_rightBtn, completeBtn;
    TextView pi_ymTv, pi_insapplyTv1, pi_insapplyTv2, pi_wageTv;
    EditText pi_rbdayEt;
    boolean completeBtnState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputstaffinfo);

        pi_wageTv=findViewById(R.id.isi_pi_wageTv);
        pi_rbdayEt=findViewById(R.id.isi_pi_rbdayEt);
        completeBtn = findViewById(R.id.isi_completeBtn); completeBtn.setOnClickListener(this);
        backib = findViewById(R.id.isi_backib); backib.setOnClickListener(this);
        ci_updownHL = findViewById(R.id.isi_ci_updownHL); ci_updownHL.setOnClickListener(this);
        pi_updownHL = findViewById(R.id.isi_pi_updownHL); pi_updownHL.setOnClickListener(this);
        ci_updownhidL = findViewById(R.id.isi_ci_updownhidL);
        pi_updownhidL = findViewById(R.id.isi_pi_updownhidL);
        ci_upiconIv = findViewById(R.id.isi_ci_upiconIv);
        pi_upiconIv = findViewById(R.id.isi_pi_upiconIv);
        pi_payRg = findViewById(R.id.isi_pi_payRg); pi_payRg.setOnCheckedChangeListener(this);
        pi_payhourRb = findViewById(R.id.isi_pi_payhourRb);
        pi_paydayRb = findViewById(R.id.isi_pi_paydayRb);
        pi_paymonthRb = findViewById(R.id.isi_pi_paymonthRb);
        pi_rbhidL2 = findViewById(R.id.isi_pi_rbhidL2);
        pi_lawrbcbhidL = findViewById(R.id.isi_pi_lawrbcbhidL);
        ci_indateBtn=findViewById(R.id.isi_ci_indateBtn); ci_indateBtn.setOnClickListener(this);
        ci_wageBtn=findViewById(R.id.isi_ci_wageBtn); ci_wageBtn.setOnClickListener(this);
        pi_leftBtn=findViewById(R.id.isi_pi_leftBtn);
        pi_rightBtn=findViewById(R.id.isi_pi_rightBtn);
        pi_ymTv=findViewById(R.id.isi_pi_ymTv);
        pi_insapplyTv1=findViewById(R.id.isi_pi_insapplyTv1);
        pi_insapplyTv2=findViewById(R.id.isi_pi_insapplyTv2);

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

        setInitialDate();


        pi_rbdayEt.addTextChangedListener(new TextWatcher() {
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
            case R.id.isi_ci_indateBtn: // 입사일
                showCalendarDialog(ci_indateBtn);
                break;
            case R.id.isi_ci_wageBtn: // 급여지급일
                showWageDialog();
                break;
            case R.id.isi_completeBtn: // 완료 버튼
                Intent intent = new Intent(getApplicationContext(), staff_infoInput.class);
                startActivity(intent);
                finish();
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
                        pi_wageTv.setText("시급");
                        break;
                    case R.id.isi_pi_paydayRb: // 일급
                        pi_wageTv.setText("일급");
                        break;
                    case R.id.isi_pi_paymonthRb: // 월급
                        pi_wageTv.setText("월급");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    // 급여지급일 다이얼로그
    public void showWageDialog() {
        RegisterStore_calendarDialog checkList_photo_dialog = new RegisterStore_calendarDialog
                (InputStaffInfo.this, new RegisterStore_calendarDialog.RegisterStore_calendarDialogListener() {
                    public void clickBtn(String data) {
                        ci_wageBtn.setText(data);
                        checkInputValidity();
                    }
                });
        checkList_photo_dialog.show();
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

    // 초기 날짜 설정
    public void setInitialDate() {
        // 초기 상태로 오늘 날짜의 해당 월 설정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 M월", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        String currentMonth = sdf.format(currentDate);
        pi_ymTv.setText(currentMonth);
        String data = pi_ymTv.getText().toString().trim();
        String month = data.substring(data.indexOf(" ") + 1);  // 월 정보 추출
        pi_insapplyTv1.setText("급여정산일은 13일입니다. \n " + data + " 14일부터 급여에 적용됩니다.");
        pi_insapplyTv2.setText("급여 적용 시작월은 " + month + "입니다.");
    }

    private void checkInputValidity() {
        // 버튼 활성화&비활성화
        boolean isIndateBtn = ci_indateBtn.getText() != "설정"; // true
        boolean isWageBtn = ci_wageBtn.getText() != "설정"; // true
        boolean isPi_rbdayEt = !pi_rbdayEt.getText().toString().trim().isEmpty(); // true

        completeBtnState = isIndateBtn && isWageBtn && isPi_rbdayEt;
        if (completeBtnState==true){
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
        }
    }
}