package com.example.sstep.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class modifyStore extends AppCompatActivity {

    ImageButton back_Btn;
    EditText nameEt, addressEt, detailEt;
    Button addressBtn, paydayBtn, completeBtn;
    RadioGroup scaleRadioGroup, planRadioGroup;
    RadioButton under5, over5, pay, free;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_store);

        back_Btn = findViewById(R.id.modistore_back_Btn);
        nameEt = findViewById(R.id.modistore_nameEt);
        addressEt = findViewById(R.id.modistore_addressEt);
        detailEt = findViewById(R.id.modistore_detailEt);
        addressBtn = findViewById(R.id.modistore_addressbtn);
        paydayBtn = findViewById(R.id.modistore_paydaybtn);
        completeBtn = findViewById(R.id.modistore_completeBtn);
        scaleRadioGroup = findViewById(R.id.modistore_scaleRadioGroup);
        planRadioGroup = findViewById(R.id.modistore_planRadioGroup);
        under5 = findViewById(R.id.modistore_under5);
        over5 = findViewById(R.id.modistore_over5);
        pay = findViewById(R.id.modistore_pay);
        free = findViewById(R.id.modistore_free);

        //주소등록
        addressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Search_Store.class);
                startActivity(intent);
                finish();
            }
        });

        //사업장 규모 라디오 버튼
        scaleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.modistore_under5:
                        free.setEnabled(true);  //버튼 활성화
                        pay.setEnabled(true);  //버튼 활성화
                        break;
                    case R.id.modistore_over5:
                        pay.setChecked(true);   //유료로 선택
                        free.setEnabled(false); //버튼 비활성화
                        break;
                }
            }
        });
        paydayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterStore_calendarDialog checkList_photo_dialog = new RegisterStore_calendarDialog
                        (modifyStore.this, new RegisterStore_calendarDialog.RegisterStore_calendarDialogListener() {
                            public void clickBtn(String data) {
                                paydayBtn.setText(data);
                            }
                        });
                checkList_photo_dialog.show();

            }
        });

        //뒤로가기 버튼
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), .class);
                //startActivity(intent);
                //finish();
            }
        });
    }
}