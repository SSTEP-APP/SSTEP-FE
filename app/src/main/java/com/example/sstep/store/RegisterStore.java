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
import com.example.sstep.document.contract.EditTextValidator;
import com.example.sstep.user.login.Login;

public class RegisterStore extends AppCompatActivity {
    ImageButton backBtn;
    EditText nameEt, addressEt, detailEt;
    Button addressBtn, completeBtn;
    RadioGroup scaleRadioGroup, planRadioGroup;
    RadioButton under5, over5, pay, free;
    Boolean ispaydayBtn=false;
    BaseDialog_OkCenter baseDialog_okCenter;
    Dialog showComplete_dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_store);
        backBtn = findViewById(R.id.regiStore_back_Btn);
        nameEt = findViewById(R.id.regiStore_nameEt);
        addressEt = findViewById(R.id.regiStore_addressEt);
        detailEt = findViewById(R.id.regiStore_detailEt);
        addressBtn = findViewById(R.id.regiStore_addressbtn);
        completeBtn = findViewById(R.id.regiStore_completeBtn);
        scaleRadioGroup = findViewById(R.id.regiStore_scaleRadioGroup);
        planRadioGroup = findViewById(R.id.regiStore_planRadioGroup);
        under5 = findViewById(R.id.regiStore_under5);
        over5 = findViewById(R.id.regiStore_over5);
        pay = findViewById(R.id.regiStore_pay);
        free = findViewById(R.id.regiStore_free);

        baseDialog_okCenter = new BaseDialog_OkCenter(RegisterStore.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(RegisterStore.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        setupEditTextListeners();
        checkCompleteBtnState();

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
                switch (i){
                    case R.id.regiStore_under5:
                        free.setEnabled(true);  //버튼 활성화
                        pay.setEnabled(true);  //버튼 활성화
                        break;
                    case R.id.regiStore_over5:
                        pay.setChecked(true);   //유료로 선택
                        free.setEnabled(false); //버튼 비활성화
                        break;
                }
            }
        });

        //뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectStore.class);
                startActivity(intent);
                finish();
            }
        });

        // '등록 완료'버튼
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComplete_dialog.show();
                // 다이얼로그의 배경을 투명으로 만든다.
                showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView join_okdl_commentTv; Button join_okdl_okBtn;
                join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
                join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
                join_okdl_commentTv.setText("사업장 등록이 완료되었습니다.");
                // 'dialog' _ 확인 버튼 클릭 시
                join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), SelectStore.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }

    private void setupEditTextListeners() {
        EditTextValidator.ValidationListener validationListener = new EditTextValidator.ValidationListener() {
            @Override
            public void onValidationResult(boolean hasContent) {
                checkCompleteBtnState();
            }
        };

        EditTextValidator storeNameValidator = new EditTextValidator(nameEt, validationListener);
        nameEt.addTextChangedListener(storeNameValidator);

        EditTextValidator storeAddressValidator = new EditTextValidator(addressEt, validationListener);
        addressEt.addTextChangedListener(storeAddressValidator);

        EditTextValidator storePhoneValidator = new EditTextValidator(detailEt, validationListener);
        detailEt.addTextChangedListener(storePhoneValidator);
    }

    // 계약서 작성완료 버튼' 활성화/비활성화
    public void checkCompleteBtnState() {
        boolean isnameEt = !nameEt.getText().toString().trim().isEmpty();
        boolean isaddressEt = !addressEt.getText().toString().trim().isEmpty();
        boolean isdetailEt = !detailEt.getText().toString().trim().isEmpty();

        if(isnameEt && isaddressEt && isdetailEt){
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
        }
    }
}