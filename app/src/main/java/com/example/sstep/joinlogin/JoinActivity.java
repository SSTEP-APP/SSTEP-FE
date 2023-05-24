package com.example.sstep.joinlogin;

import static android.app.PendingIntent.getActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    ScrollView scroll;
    EditText idEt, nameEt, phonumEt, passEt, checkPassEt; String id, name, phonum, pass, checkPass;
    ImageButton back_Btn; Button completeBtn, certBtn;
    CheckBox passEyeCb, checkPassEyeCb;
    TextView checkText;
    Boolean completeBtn_state=Boolean.FALSE;
    Intent intent;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;
    String testId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        baseDialog_okCenter = new BaseDialog_OkCenter(JoinActivity.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(JoinActivity.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        back_Btn=findViewById(R.id.join_back_Btn);
        back_Btn.setOnClickListener(this);
        completeBtn=findViewById(R.id.join_completeBtn);
        completeBtn.setOnClickListener(this);
        certBtn=findViewById(R.id.join_certBtn);
        certBtn.setOnClickListener(this);

        scroll=findViewById(R.id.join_scroll);
        passEyeCb=findViewById(R.id.join_passEyeCb); passEyeCb.setOnCheckedChangeListener(this);
        checkPassEyeCb=findViewById(R.id.join_checkPassEyeCb); checkPassEyeCb.setOnCheckedChangeListener(this);
        idEt=findViewById(R.id.join_idEt);
        nameEt=findViewById(R.id.join_nameEt);
        phonumEt=findViewById(R.id.join_phonumEt);
        passEt=findViewById(R.id.join_passEt);
        checkPassEt=findViewById(R.id.join_checkPassEt);
        checkText=findViewById(R.id.join_checkText);

        idEt.addTextChangedListener(textWatcher);
        nameEt.addTextChangedListener(textWatcher);
        phonumEt.addTextChangedListener(textWatcher);
        passEt.addTextChangedListener(textWatcher);
        checkPassEt.addTextChangedListener(textWatcher);

        // 전화번호 입력시 자동 '-' 입력
        phonumEt.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        scroll.setOnTouchListener(new View.OnTouchListener() {
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
            Typeface typeface = Typeface.createFromAsset(getAssets(), "nanumsquareneo_brg.ttf");
            passEt.setTypeface(typeface); passEt.setTextSize(13);
            checkPassEt.setTypeface(typeface); checkPassEt.setTextSize(13);

            id = idEt.getText().toString().trim();
            name = nameEt.getText().toString().trim();
            phonum = phonumEt.getText().toString().trim();
            pass = passEt.getText().toString().trim();
            checkPass = checkPassEt.getText().toString().trim();

            if (id.length()>0 && name.length()>0 && phonum.length()>0 && pass.length()>0 && checkPass.length()>0
                    && pass.equals(checkPass)){
                checkText.setVisibility(View.VISIBLE);
                checkText.setTypeface(typeface); checkText.setTextSize(11);
                checkText.setText("비밀번호가 일치합니다.");
                checkText.setTextColor(Color.parseColor("#2375F2"));
                checkPassEt.setBackgroundResource(R.drawable.yedittext_w_sb);
                completeBtn.setEnabled(true);
                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
                completeBtn_state = Boolean.TRUE;
            }else if(pass.length()>0 && checkPass.length()>0 && pass.equals(checkPass)){
                checkText.setVisibility(View.VISIBLE);
                checkText.setTypeface(typeface); checkText.setTextSize(11);
                checkText.setText("비밀번호가 일치합니다.");
                checkText.setTextColor(Color.parseColor("#2375F2"));
                checkPassEt.setBackgroundResource(R.drawable.yedittext_w_sb);
            }else if(checkPass.equals("")){
                checkText.setVisibility(View.INVISIBLE);
                checkPassEt.setBackgroundResource(R.drawable.yedittext_w_sg);
                completeBtn.setEnabled(true);
                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
                completeBtn_state = Boolean.FALSE;
            }else if(!pass.equals(checkPass)){
                checkText.setVisibility(View.VISIBLE);
                checkText.setTypeface(typeface); checkText.setTextSize(11);
                checkText.setText("* 비밀번호가 일치하지 않습니다.");
                checkText.setTextColor(Color.parseColor("#DF1515"));
                checkPassEt.setBackgroundResource(R.drawable.yedittext_w_sr);
                completeBtn.setEnabled(true);
                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
                completeBtn_state = Boolean.FALSE;
            }else{
                checkText.setVisibility(View.INVISIBLE);
                completeBtn.setEnabled(true);
                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
                completeBtn_state = Boolean.FALSE;
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void onClick(View v) {
        // db에서 받은 id
        testId="111";
        switch(v.getId()){
            case R.id.join_back_Btn: // 뒤로가기
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
                break;
            case R.id.join_completeBtn: // 완료버튼
                if(completeBtn_state == Boolean.TRUE){
                    showCompleteDl();
                }
                break;
            case R.id.join_certBtn: // 중복확인 버튼
                onCertDl();
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


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.join_passEyeCb: // 비밀번호 textPassword 보이게/안보이게
                if (isChecked) {
                    passEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); // 보이게
                } else {
                    passEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //안 보이게
                }
                break;
            case R.id.join_checkPassEyeCb: // '비밀번호 확인' textPassword 보이게/안보이게
                if (isChecked) {
                    checkPassEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); // 보이게
                } else {
                    checkPassEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); // 안 보이게
                }
                break;
            default:
                break;
        }
    }

    // '회원가입'버튼 클릭 시
    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        join_okdl_commentTv.setText("회원가입이 완료되었습니다.\n 로그인해 주세요.");
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

    // '중복확인' 버튼 클릭 시
    public void onCertDl() {
        baseDialog_okCenter.show();
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = baseDialog_okCenter.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = baseDialog_okCenter.findViewById(R.id.join_okdl_okBtn);
        if(testId.equals(idEt.getText().toString().trim())){
            join_okdl_commentTv.setText("중복된 아이디가 있습니다.\n다른 아이디를 입력해 주세요.");
            idEt.setText("");
        }else if(idEt.getText().toString().trim().equals("")){
            join_okdl_commentTv.setText("입력된 아이디가 없습니다.\n아이디를 입력해 주세요.");
        }else{
            join_okdl_commentTv.setText("사용 가능한 아이디 입니다.");
        }
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDialog_okCenter.dismiss();
            }
        });
    }

}