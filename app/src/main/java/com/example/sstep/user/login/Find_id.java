package com.example.sstep.user.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sstep.R;
import com.example.sstep.user.join.JoinActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Find_id extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutL;
    EditText nameEt, phonumEt; String name, phonum;
    ImageButton back_Btn; Button certbtn, completeBtn;
    Dialog showComplete_dialog;
    Intent intent;
    Boolean completeBtn_state=Boolean.FALSE;
    FrameLayout certF;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 100;
    // 생성된 인증번호를 저장할 리스트 선언
    private List<String> generatedCodes = new ArrayList<>();
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
        certF=findViewById(R.id.findId_certF);
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
                intent = new Intent(getApplicationContext(), Login_test.class);
                startActivity(intent);
                finish();
                break;
            case R.id.findId_certbtn: // 인증확인
                sendSMS();
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
        // '아이디 찾기 dialog' _ 확인 버튼 클릭 시
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

    // 휴대폰 인증 처리 메서드
    private void sendSMS() {
        // 휴대폰 인증 처리 코드 작성

        // 휴대폰 인증번호 발송
        String phoneNumber = phonumEt.getText().toString().trim().replace("-","");
        if (!phoneNumber.isEmpty()) {
            // 권한 체크
            if (ContextCompat.checkSelfPermission(Find_id.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                // 인증번호 생성 및 발송
                String verificationCode = generateUniqueVerificationCode();
                sendVerificationCode(phoneNumber, "인증번호는 "+verificationCode+" 입니다.");
                certF.setVisibility(View.VISIBLE);
            } else {
                // 권한 요청
                ActivityCompat.requestPermissions(Find_id.this, new String[]{android.Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            Toast.makeText(Find_id.this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    // 인증번호 생성 메서드 (중복 방지)
    private String generateUniqueVerificationCode() {
        String verificationCode = generateVerificationCode();
        // Check if the code already exists in the list
        while (generatedCodes.contains(verificationCode)) {
            verificationCode = generateVerificationCode();
        }
        // Add the newly generated code to the list
        generatedCodes.add(verificationCode);
        return verificationCode;
    }

    // 인증번호 생성 메서드
    private String generateVerificationCode() {
        Random random = new Random();
        int verificationCode = random.nextInt(900000) + 100000;
        return String.valueOf(verificationCode);
    }

    // 인증번호 발송 메서드
    private void sendVerificationCode(String phoneNumber, String verificationCode) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, verificationCode, null, null);
        } catch (Exception e) {
            Toast.makeText(Find_id.this, "인증번호 발송에 실패했습니다." + verificationCode, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}