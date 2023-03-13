package com.example.sstep.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class Money_Staff extends AppCompatActivity {
    ImageButton backib;
    ImageView ceoupiv, ceodowniv ,staffupiv1, staffdowniv1, staffupiv2,staffdowniv2, staffupiv3, staffdowniv3, staffupiv4, staffdowniv4;
    LinearLayout ceohidL1, staffhidL1, staffhidL2, staffhidL3, staffhidL4, HL3, HL8, HL9, HL15, HL20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_staff);
        backib = findViewById(R.id.money_staff_backib);
        ceoupiv = findViewById(R.id.money_staff_ceoupiv); ceodowniv = findViewById(R.id.money_staff_ceodowniv);
        staffupiv1 = findViewById(R.id.money_staff_staffupiv1); staffdowniv1 = findViewById(R.id.money_staff_staffdowniv1);
        staffupiv2 = findViewById(R.id.money_staff_staffupiv2); staffdowniv2 = findViewById(R.id.money_staff_staffdowniv2);
        staffupiv3 = findViewById(R.id.money_staff_staffupiv3); staffdowniv3 = findViewById(R.id.money_staff_staffdowniv3);
        staffupiv4 = findViewById(R.id.money_staff_staffupiv4); staffdowniv4 = findViewById(R.id.money_staff_staffdowniv4);
        ceohidL1 = findViewById(R.id.money_staff_ceohidL1);
        staffhidL1 = findViewById(R.id.money_staff_staffhidL1); staffhidL2 = findViewById(R.id.money_staff_staffhidL2);
        staffhidL3 = findViewById(R.id.money_staff_staffhidL3); staffhidL4 = findViewById(R.id.money_staff_staffhidL4);
        HL3 = findViewById(R.id.money_staff_HL3); HL8 = findViewById(R.id.money_staff_HL8);
        HL9 = findViewById(R.id.money_staff_HL9); HL15 = findViewById(R.id.money_staff_HL15); HL20 = findViewById(R.id.money_staff_HL20);

        // '뒤로가기' 선택 시
        backib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Money_Ceo.class);
                startActivity(intent);
                finish();
            }
        });

        // 고용주 지출액 up&down 클릭시 내리기
        HL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ceoupiv.getVisibility() == View.VISIBLE) {
                    ceoupiv.setVisibility(View.GONE);
                    ceodowniv.setVisibility(View.VISIBLE);
                    ceohidL1.setVisibility(View.VISIBLE);
                } else {
                    ceoupiv.setVisibility(View.VISIBLE);
                    ceodowniv.setVisibility(View.GONE);
                    ceohidL1.setVisibility(View.GONE);
                }
            }
        });

        // 근로자 수령액 up&down 클릭시 내리기
        HL8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (staffupiv1.getVisibility() == View.VISIBLE) {
                    staffupiv1.setVisibility(View.GONE);
                    staffdowniv1.setVisibility(View.VISIBLE);
                    staffhidL1.setVisibility(View.VISIBLE);
                } else {
                    staffupiv1.setVisibility(View.VISIBLE);
                    staffdowniv1.setVisibility(View.GONE);
                    staffhidL1.setVisibility(View.GONE);
                }
            }
        });

        // 공제전 금액 up&down 클릭시 내리기
        HL9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (staffupiv2.getVisibility() == View.VISIBLE) {
                    staffupiv2.setVisibility(View.GONE);
                    staffdowniv2.setVisibility(View.VISIBLE);
                    staffhidL2.setVisibility(View.VISIBLE);
                } else {
                    staffupiv2.setVisibility(View.VISIBLE);
                    staffdowniv2.setVisibility(View.GONE);
                    staffhidL2.setVisibility(View.GONE);
                }
            }
        });
        // 4대보험 근로자부담금 up&down 클릭시 내리기
        HL15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (staffupiv3.getVisibility() == View.VISIBLE) {
                    staffupiv3.setVisibility(View.GONE);
                    staffdowniv3.setVisibility(View.VISIBLE);
                    staffhidL3.setVisibility(View.VISIBLE);
                } else {
                    staffupiv3.setVisibility(View.VISIBLE);
                    staffdowniv3.setVisibility(View.GONE);
                    staffhidL3.setVisibility(View.GONE);
                }
            }
        });
        // 원천세 up&down 클릭시 내리기
        HL20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (staffupiv4.getVisibility() == View.VISIBLE) {
                    staffupiv4.setVisibility(View.GONE);
                    staffdowniv4.setVisibility(View.VISIBLE);
                    staffhidL4.setVisibility(View.VISIBLE);
                } else {
                    staffupiv4.setVisibility(View.VISIBLE);
                    staffdowniv4.setVisibility(View.GONE);
                    staffhidL4.setVisibility(View.GONE);
                }
            }
        });
    }
}