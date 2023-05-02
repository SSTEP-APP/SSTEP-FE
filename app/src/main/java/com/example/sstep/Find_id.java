package com.example.sstep;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.checklist.CheckList;
import com.example.sstep.joinlogin.Login;

public class Find_id extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutL;
    EditText nameEt, phonumEt; String name, phonum;
    ImageButton back_Btn; Button certbtn, completeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_id);

        back_Btn=findViewById(R.id.findId_BackBtn);
        back_Btn.setOnClickListener(this);
        certbtn=findViewById(R.id.findId_certbtn);
        certbtn.setOnClickListener(this);
        completeBtn=findViewById(R.id.findId_completeBtn);
        completeBtn.setOnClickListener(this);

        layoutL=findViewById(R.id.findId_layoutL);
        nameEt=findViewById(R.id.findId_nameEt);
        phonumEt=findViewById(R.id.findId_phonumEt);

        nameEt.addTextChangedListener(textWatcher);
        phonumEt.addTextChangedListener(textWatcher);

        // 전화번호 입력시 자동 '-' 입력
        phonumEt.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        layoutL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(); // EditText 밖에 터치 시 키보드 내리기
                return false;
            }
        });
    }

    // EditText 다 채워지면 아래 버튼 활성화
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            name = nameEt.getText().toString().trim();
            phonum = phonumEt.getText().toString().trim();

            if (name.length()>0 && phonum.length()>0){
                completeBtn.setEnabled(true);
                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
            }else{
                completeBtn.setEnabled(true);
                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.findId_BackBtn: // 뒤로가기
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
                break;
            case R.id.findId_certbtn: // 인증확인
                break;
            case R.id.findId_completeBtn: // 완료버튼
                Toast.makeText(getApplicationContext(),"클릭됨",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    // EditText 밖에 터치 시 키보드 내리기
    void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}