package com.example.sstep.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class Money_Staff extends AppCompatActivity implements View.OnClickListener{
    ImageButton backib;
    LinearLayout ceoupdownHL1, workupdownHL1, workupdownHL2, workupdownHL3, workupdownHL4
            , ceohidL1, workhidL1, workhidL2, workhidL3, workhidL4;
    ImageView ceoupiv, workupiv1, workupiv2, workupiv3,workupiv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_staff);

        backib=findViewById(R.id.money_staff_backib); backib.setOnClickListener(this);
        ceoupdownHL1=findViewById(R.id.money_staff_ceoupdownHL1); ceoupdownHL1.setOnClickListener(this);
        workupdownHL1=findViewById(R.id.money_staff_workupdownHL1); workupdownHL1.setOnClickListener(this);
        workupdownHL2=findViewById(R.id.money_staff_workupdownHL2); workupdownHL2.setOnClickListener(this);
        workupdownHL3=findViewById(R.id.money_staff_workupdownHL3); workupdownHL3.setOnClickListener(this);
        workupdownHL4=findViewById(R.id.money_staff_workupdownHL4); workupdownHL4.setOnClickListener(this);
        ceohidL1=findViewById(R.id.money_staff_ceohidL1);
        workhidL1=findViewById(R.id.money_staff_workhidL1);
        workhidL2=findViewById(R.id.money_staff_workhidL2);
        workhidL3=findViewById(R.id.money_staff_workhidL3);
        workhidL4=findViewById(R.id.money_staff_workhidL4);
        ceoupiv=findViewById(R.id.money_staff_ceoupiv);
        workupiv1=findViewById(R.id.money_staff_workupiv1);
        workupiv2=findViewById(R.id.money_staff_workupiv2);
        workupiv3=findViewById(R.id.money_staff_workupiv3);
        workupiv4=findViewById(R.id.money_staff_workupiv4);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.money_staff_backib: // '뒤로가기'
                Intent intent = new Intent(getApplicationContext(), Money_Ceo.class);
                startActivity(intent);
                finish();
                break;
            case R.id.money_staff_ceoupdownHL1: // 고용주 지출액 up&down 클릭시 내리기
                if (ceohidL1.getVisibility() == View.GONE) {
                    ceohidL1.setVisibility(View.VISIBLE);
                    ceoupiv.setBackgroundResource(R.drawable.yicon_down);
                }else{
                    ceohidL1.setVisibility(View.GONE);
                    ceoupiv.setBackgroundResource(R.drawable.yicon_up);
                }
                break;
            case R.id.money_staff_workupdownHL1: // 근로자 수령액 up&down 클릭시 내리기
                if (workhidL1.getVisibility() == View.GONE) {
                    workhidL1.setVisibility(View.VISIBLE);
                    workupiv1.setBackgroundResource(R.drawable.yicon_down);
                }else{
                    workhidL1.setVisibility(View.GONE);
                    workupiv1.setBackgroundResource(R.drawable.yicon_up);
                }
                break;
            case R.id.money_staff_workupdownHL2: // 공제전 금액 up&down 클릭시 내리기
                if (workhidL2.getVisibility() == View.GONE) {
                    workhidL2.setVisibility(View.VISIBLE);
                    workupiv2.setBackgroundResource(R.drawable.yicon_down);
                }else{
                    workhidL2.setVisibility(View.GONE);
                    workupiv2.setBackgroundResource(R.drawable.yicon_up);
                }
                break;
            case R.id.money_staff_workupdownHL3: // 4대보험 근로자부담금 up&down 클릭시 내리기
                if (workhidL3.getVisibility() == View.GONE) {
                    workhidL3.setVisibility(View.VISIBLE);
                    workupiv3.setBackgroundResource(R.drawable.yicon_down);
                }else{
                    workhidL3.setVisibility(View.GONE);
                    workupiv3.setBackgroundResource(R.drawable.yicon_up);
                }
                break;
            case R.id.money_staff_workupdownHL4: // 원천세 up&down 클릭시 내리기
                if (workhidL4.getVisibility() == View.GONE) {
                    workhidL4.setVisibility(View.VISIBLE);
                    workupiv4.setBackgroundResource(R.drawable.yicon_down);
                }else{
                    workhidL4.setVisibility(View.GONE);
                    workupiv4.setBackgroundResource(R.drawable.yicon_up);
                }
                break;
            default:
                break;
        }
    }
}