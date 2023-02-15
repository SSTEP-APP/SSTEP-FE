package com.example.sstep;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class InputStaffInfo extends AppCompatActivity {

    RadioGroup pi_payrg1, pi_lawrg2;
    RadioButton pi_hourrb1, pi_dayrb2, pi_monthrb3, pi_lawrb4, pi_lawrb5, pi_lawrb6;
    LinearLayout inputStaffInfo_pi_hidL3, inputStaffInfo_pi_hidL5, inputStaffInfo_pi_hidL6;
    LinearLayout inputStaffInfo_ci_hidL2,inputStaffInfo_pi_hidL2,inputStaffInfo_yi_hidL2;
    LinearLayout inputStaffInfo_ci_hidHL3;
    LinearLayout inputStaffInfo_pi_hidL8;
    LinearLayout inputStaffInfo_pi_hidL9, inputStaffInfo_pi_hidL10;
    FrameLayout ci_changeiconF1,pi_changeiconF2, yi_changeiconF3;
    ImageView ci_upicon1,ci_downicon1, pi_upicon2,pi_downicon2, yi_downicon3,yi_upicon3;
    CheckBox ci_outdatechbox1, pi_pluspaych1,pi_minuspaych2, pi_notermch4;
    Button inputStaffInfo_pi_setbtn1, inputStaffInfo_pi_setbtn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputstaffinfo);
        ci_changeiconF1 = findViewById(R.id.inputStaffInfo_ci_changeiconF1);
        pi_changeiconF2 = findViewById(R.id.inputStaffInfo_pi_changeiconF2);
        yi_changeiconF3 = findViewById(R.id.inputStaffInfo_yi_changeiconF3);
        inputStaffInfo_ci_hidL2 = findViewById(R.id.inputStaffInfo_ci_hidL2);
        inputStaffInfo_pi_hidL2 = findViewById(R.id.inputStaffInfo_pi_hidL2);
        inputStaffInfo_yi_hidL2 = findViewById(R.id.inputStaffInfo_yi_hidL2);
        ci_outdatechbox1 = findViewById(R.id.inputStaffInfo_ci_outdatechbox1);
        ci_upicon1 = findViewById(R.id.inputStaffInfo_ci_upicon1); ci_downicon1 = findViewById(R.id.inputStaffInfo_ci_downicon1);
        pi_upicon2 = findViewById(R.id.inputStaffInfo_pi_upicon2); pi_downicon2 = findViewById(R.id.inputStaffInfo_pi_downicon2);
        yi_upicon3 = findViewById(R.id.inputStaffInfo_yi_upicon3); yi_downicon3 = findViewById(R.id.inputStaffInfo_yi_downicon3);
        inputStaffInfo_ci_hidHL3 = findViewById(R.id.inputStaffInfo_ci_hidHL3);
        pi_payrg1 = findViewById(R.id.inputStaffInfo_pi_payrg1);
        pi_hourrb1 = findViewById(R.id.inputStaffInfo_pi_hourrb1);
        pi_dayrb2 = findViewById(R.id.inputStaffInfo_pi_dayrb2);
        pi_monthrb3 = findViewById(R.id.inputStaffInfo_pi_monthrb3);
        inputStaffInfo_pi_hidL3 = findViewById(R.id.inputStaffInfo_pi_hidL3); inputStaffInfo_pi_hidL5 = findViewById(R.id.inputStaffInfo_pi_hidL5); inputStaffInfo_pi_hidL6 = findViewById(R.id.inputStaffInfo_pi_hidL6);
        pi_pluspaych1 = findViewById(R.id.inputStaffInfo_pi_pluspaych1);
        pi_minuspaych2 = findViewById(R.id.inputStaffInfo_pi_minuspaych2);
        inputStaffInfo_pi_setbtn1 = findViewById(R.id.inputStaffInfo_pi_setbtn1);
        inputStaffInfo_pi_setbtn2 = findViewById(R.id.inputStaffInfo_pi_setbtn2);
        pi_notermch4 = findViewById(R.id.inputStaffInfo_pi_notermch4);
        inputStaffInfo_pi_hidL8 = findViewById(R.id.inputStaffInfo_pi_hidL8);
        inputStaffInfo_pi_hidL9 = findViewById(R.id.inputStaffInfo_pi_hidL9); inputStaffInfo_pi_hidL10 = findViewById(R.id.inputStaffInfo_pi_hidL10);
        pi_lawrg2 = findViewById(R.id.inputStaffInfo_pi_lawrg2);
        pi_lawrb4 = findViewById(R.id.inputStaffInfo_pi_lawrb4);
        pi_lawrb5 = findViewById(R.id.inputStaffInfo_pi_lawrb5);
        pi_lawrb6 = findViewById(R.id.inputStaffInfo_pi_lawrb6);



        // 출퇴근정보_up&down 아이콘 변경
        ci_changeiconF1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ci_upicon1.getVisibility() == View.VISIBLE){
                    ci_upicon1.setVisibility(View.INVISIBLE);
                    ci_downicon1.setVisibility(View.VISIBLE);
                    inputStaffInfo_ci_hidL2.setVisibility(View.VISIBLE);
                } else {
                    ci_upicon1.setVisibility(View.VISIBLE);
                    ci_downicon1.setVisibility(View.INVISIBLE);
                    inputStaffInfo_ci_hidL2.setVisibility(View.GONE);
                }
            }
        });
        // 급여정보_up&down 아이콘 변경
        pi_changeiconF2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pi_upicon2.getVisibility() == View.VISIBLE){
                    pi_upicon2.setVisibility(View.INVISIBLE);
                    pi_downicon2.setVisibility(View.VISIBLE);
                    inputStaffInfo_pi_hidL2.setVisibility(View.VISIBLE);
                } else {
                    pi_upicon2.setVisibility(View.VISIBLE);
                    pi_downicon2.setVisibility(View.INVISIBLE);
                    inputStaffInfo_pi_hidL2.setVisibility(View.GONE);
                }
            }
        });
        // 연차_up&down 아이콘 변경
        yi_changeiconF3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yi_upicon3.getVisibility() == View.VISIBLE){
                    yi_upicon3.setVisibility(View.INVISIBLE);
                    yi_downicon3.setVisibility(View.VISIBLE);
                    inputStaffInfo_yi_hidL2.setVisibility(View.VISIBLE);
                } else {
                    yi_upicon3.setVisibility(View.VISIBLE);
                    yi_downicon3.setVisibility(View.INVISIBLE);
                    inputStaffInfo_yi_hidL2.setVisibility(View.GONE);
                }
            }
        });

        // 퇴사일 미정 checkbox
        ci_outdatechbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (ci_outdatechbox1.isChecked() == true) {
                    inputStaffInfo_ci_hidHL3.setVisibility(View.GONE);
                } else {
                    inputStaffInfo_ci_hidHL3.setVisibility(View.VISIBLE);
                }
            }
        });
        
        // 시급/일급/월급
        pi_payrg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (pi_hourrb1.isChecked() == true) {
                    inputStaffInfo_pi_hidL3.setVisibility(View.VISIBLE);
                    inputStaffInfo_pi_hidL5.setVisibility(View.GONE);
                    inputStaffInfo_pi_hidL6.setVisibility(View.GONE);
                } else if (pi_dayrb2.isChecked() == true ) {
                    inputStaffInfo_pi_hidL3.setVisibility(View.GONE);
                    inputStaffInfo_pi_hidL5.setVisibility(View.VISIBLE);
                    inputStaffInfo_pi_hidL6.setVisibility(View.GONE);
                } else {
                    inputStaffInfo_pi_hidL3.setVisibility(View.GONE);
                    inputStaffInfo_pi_hidL5.setVisibility(View.GONE);
                    inputStaffInfo_pi_hidL6.setVisibility(View.VISIBLE);
                }
            }
        });

        // +주휴수당 설정 버튼
        pi_pluspaych1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (pi_pluspaych1.isChecked() == true) {// 체크박스가 체크가 되어있으면,
                    inputStaffInfo_pi_setbtn1.setVisibility(View.VISIBLE);
                } else {
                    inputStaffInfo_pi_setbtn1.setVisibility(View.INVISIBLE);
                }
            }
        });
        // -휴게시간 설정 버튼
        pi_minuspaych2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (pi_minuspaych2.isChecked() == true) {// 체크박스가 체크가 되어있으면,
                    inputStaffInfo_pi_setbtn2.setVisibility(View.VISIBLE);
                } else {
                    inputStaffInfo_pi_setbtn2.setVisibility(View.INVISIBLE);
                }
            }
        });

        // 수습기간 없음
        pi_notermch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (pi_notermch4.isChecked() == true) {
                    inputStaffInfo_pi_hidL8.setVisibility(View.GONE);
                } else {
                    inputStaffInfo_pi_hidL8.setVisibility(View.VISIBLE);
                }
            }
        });

        // 4대보험,프리랜서,적용안함
        pi_lawrg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (pi_lawrb4.isChecked() == true) {
                    inputStaffInfo_pi_hidL9.setVisibility(View.GONE);
                    inputStaffInfo_pi_hidL10.setVisibility(View.VISIBLE);
                } else if (pi_lawrb5.isChecked() == true ) {
                    inputStaffInfo_pi_hidL9.setVisibility(View.VISIBLE);
                    inputStaffInfo_pi_hidL10.setVisibility(View.GONE);
                } else {
                    inputStaffInfo_pi_hidL9.setVisibility(View.VISIBLE);
                    inputStaffInfo_pi_hidL10.setVisibility(View.GONE);
                }
            }
        });
    }
}