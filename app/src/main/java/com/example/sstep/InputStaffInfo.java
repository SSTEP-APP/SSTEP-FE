package com.example.sstep;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class InputStaffInfo extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener{

    ImageButton backib;
    LinearLayout ci_updownHL,pi_updownHL,yi_updownHL, ci_updownhidL,pi_updownhidL,yi_updownhidL,
            pi_rbhidL1, pi_rbhidL2, pi_rbhidL3, pi_tgcbhidL, pi_lawrbcbhidL;
    ImageView ci_upiconIv,pi_upiconIv,yi_upiconIv;
    CheckBox pi_hourCb1,pi_hourCb2,pi_hourCb3, pi_trainingCb;
    RadioGroup pi_payRg, pi_lawRg;
    RadioButton pi_payhourRb,pi_paydayRb,pi_paymonthRb, pi_lawRb1,pi_lawRb2,pi_lawRb3;
    Button pi_setBtn1, pi_setBtn2;


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
                    pi_tgcbhidL.setVisibility(View.VISIBLE);
                }else{
                    pi_tgcbhidL.setVisibility(View.GONE);
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
}