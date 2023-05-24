package com.example.sstep.user.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class Find_id extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutL;
    EditText nameEt, phonumEt; String name, phonum;
    ImageButton back_Btn; Button certbtn, completeBtn;
    Dialog showComplete_dialog;
    Intent intent;
    Boolean completeBtn_state=Boolean.FALSE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_id);

        showComplete_dialog = new Dialog(Find_id.this); // Dialog 초기화
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

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
                completeBtn_state=Boolean.TRUE;
            }else{
                completeBtn.setEnabled(true);
                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
                completeBtn_state=Boolean.FALSE;
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.findId_BackBtn: // 뒤로가기
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
                break;
            case R.id.findId_certbtn: // 인증확인
                break;
            case R.id.findId_completeBtn: // 완료버튼
                if(completeBtn_state == Boolean.TRUE){
                    showCompleteDl();
                }
                break;
            default:
                break;
        }
    }

    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        join_okdl_commentTv.setText("김유경 님의 아이디는 ididid 입니다.");
        // '회원가입 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // EditText 밖에 터치 시 키보드 내리기
    void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}