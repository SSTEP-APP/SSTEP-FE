package com.example.sstep;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterStore extends AppCompatActivity {
    ImageButton backBtn;
    EditText nameEt, addressEt, detailEt;
    Button addressBtn, choiceBtn, paydayBtn, completeBtn;
    RadioGroup scaleRadioGroup, planRadioGroup;
    RadioButton under5, over5, pay, free;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_store);
        backBtn = findViewById(R.id.regiStore_back_Btn);
        nameEt = findViewById(R.id.regiStore_nameEt);
        addressEt = findViewById(R.id.regiStore_addressEt);
        detailEt = findViewById(R.id.regiStore_detailEt);
        addressBtn = findViewById(R.id.regiStore_addressbtn);
        choiceBtn = findViewById(R.id.regiStore_choicebtn);
        paydayBtn = findViewById(R.id.regiStore_paydaybtn);
        completeBtn = findViewById(R.id.regiStore_completeBtn);
        scaleRadioGroup = findViewById(R.id.regiStore_scaleRadioGroup);
        planRadioGroup = findViewById(R.id.regiStore_planRadioGroup);
        under5 = findViewById(R.id.regiStore_under5);
        over5 = findViewById(R.id.regiStore_over5);
        pay = findViewById(R.id.regiStore_pay);
        free = findViewById(R.id.regiStore_free);

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
                    case R.id.regiStore_under5:
                        free.setEnabled(true);  //버튼 활성화
                        break;

                    case R.id.regiStore_over5:
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
                        (RegisterStore.this, new RegisterStore_calendarDialog.RegisterStore_calendarDialogListener() {
                            public void clickBtn(String data) {
                                paydayBtn.setText(data);
                            }
                        });
                checkList_photo_dialog.show();
            }
        });
        //뒤로가기 버튼 메인
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), .class);
                //startActivity(intent);
                //finish();
            }
        });


    }
}