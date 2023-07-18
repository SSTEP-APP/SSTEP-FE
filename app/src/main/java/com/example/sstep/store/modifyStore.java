package com.example.sstep.store;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;

public class modifyStore extends AppCompatActivity {

    ImageButton back_Btn;
    EditText nameEt, addressEt, detailEt;
    Button addressBtn, paydayBtn, completeBtn;
    RadioGroup scaleRadioGroup, planRadioGroup;
    RadioButton under5, over5, pay, free;
    TextView closeStoreTv;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_store);

        baseDialog_okCenter = new BaseDialog_OkCenter(modifyStore.this, R.layout.mypage_dropdl);
        showComplete_dialog = new Dialog(modifyStore.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.mypage_dropdl); // xml 레이아웃 파일과 연결

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
        closeStoreTv = findViewById(R.id.modistore_closeStoreTv);

        //주소등록
        addressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Search_Address.class);
                startActivity(intent);
                finish();
            }
        });

        //사업장 규모 라디오 버튼
        scaleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
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

        // 사업장 폐업하기
        closeStoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCompleteDl();
            }
        });
    }

    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView dropdl_tv1; Button cancleBtn, dropBtn;
        cancleBtn = showComplete_dialog.findViewById(R.id.mypage_dropdl_cancleBtn);
        dropBtn = showComplete_dialog.findViewById(R.id.mypage_dropdl_dropBtn);
        dropdl_tv1 = showComplete_dialog.findViewById(R.id.mypage_dropdl_tv1);
        dropdl_tv1.setText("사업장을 폐업상태로 변경하시겠습니까?");
        dropBtn.setText("폐업");
        // '로그인 dialog' _ 확인 버튼 클릭 시
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComplete_dialog.dismiss();
            }
        });
        dropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComplete_dialog.dismiss();
            }
        });
    }
}