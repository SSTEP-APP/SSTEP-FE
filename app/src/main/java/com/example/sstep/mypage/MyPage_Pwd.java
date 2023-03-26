package com.example.sstep.mypage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class MyPage_Pwd extends AppCompatActivity implements View.OnClickListener{

    LinearLayout layoutL;
    ImageButton backib; Button bottomoffbtn;
    EditText currentPwdEt, newPwdEt, newcheckPwdEt; String currentPwd, newPwd, newcheckPwd;
    CheckBox newPwdCb, newcheckPwdCb;
    TextView currentPwdtv, newPwdTv, newcheckPwdTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_pwd);

        layoutL = findViewById(R.id.mypage_pwd_layoutL);
        backib = findViewById(R.id.mypage_pwd_backib);
        bottomoffbtn = findViewById(R.id.mypage_pwd_bottomoffbtn);
        currentPwdEt = findViewById(R.id.mypage_pwd_currentPwdEt);
        newPwdEt = findViewById(R.id.mypage_pwd_newPwdEt);
        newcheckPwdEt = findViewById(R.id.mypage_pwd_newcheckPwdEt);
        newPwdCb = findViewById(R.id.mypage_pwd_newPwdCb);
        newcheckPwdCb = findViewById(R.id.mypage_pwd_newcheckPwdCb);
        currentPwdtv = findViewById(R.id.mypage_pwd_currentPwdtv);
        newPwdTv = findViewById(R.id.mypage_pwd_newPwdTv);
        newcheckPwdTv = findViewById(R.id.mypage_pwd_newcheckPwdTv);

        currentPwdEt.addTextChangedListener(textWatcher);
        newPwdEt.addTextChangedListener(textWatcher);
        newcheckPwdCb.addTextChangedListener(textWatcher);


        layoutL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(); // EditText 밖에 터치 시 키보드 내리기
                return false;
            }
        });

        // 비밀번호 textPassword 보이게/안보이게
        newPwdCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    newPwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); // 보이게
                } else {
                    newPwdEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //안 보이게
                }
            }
        });

        // 비밀번호 확인 textPassword 보이게/안보이게
        newcheckPwdCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    newcheckPwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); // 보이게
                } else {
                    newcheckPwdEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); // 안 보이게
                }
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
            currentPwdEt.setTypeface(typeface); currentPwdEt.setTextSize(13);
            newPwdEt.setTypeface(typeface); newPwdEt.setTextSize(13);
            newcheckPwdEt.setTypeface(typeface); newcheckPwdEt.setTextSize(13);

            currentPwd = currentPwdEt.getText().toString().trim();
            newPwd = newPwdEt.getText().toString().trim();
            newcheckPwd = newcheckPwdEt.getText().toString().trim();

            if (currentPwd.length()>0 && newPwd.length()>0 && newcheckPwd.length()>0
                    && newPwd.equals(newcheckPwd)){
                newcheckPwdTv.setVisibility(View.VISIBLE);
                newcheckPwdTv.setTypeface(typeface); newcheckPwdTv.setTextSize(11);
                newcheckPwdTv.setText("비밀번호가 일치합니다.");
                newcheckPwdTv.setTextColor(Color.parseColor("#2375F2"));
                newcheckPwdEt.setBackgroundResource(R.drawable.yedittext_w_sb);
                bottomoffbtn.setEnabled(true);
                bottomoffbtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
            }else if(currentPwd.length()>=0 && newPwd.length()>0 && newcheckPwd.length()>0 && newPwd.equals(newcheckPwd)){
                newcheckPwdTv.setVisibility(View.VISIBLE);
                newcheckPwdTv.setTypeface(typeface); newcheckPwdTv.setTextSize(11);
                newcheckPwdTv.setText("비밀번호가 일치합니다.");
                newcheckPwdTv.setTextColor(Color.parseColor("#2375F2"));
                newcheckPwdEt.setBackgroundResource(R.drawable.yedittext_w_sb);
            }else if(newcheckPwd.equals("")){
                newcheckPwdTv.setVisibility(View.INVISIBLE);
                newcheckPwdEt.setBackgroundResource(R.drawable.yedittext_w_sg);
                bottomoffbtn.setEnabled(true);
                bottomoffbtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
            }else if(!newPwd.equals(newcheckPwd)){
                newcheckPwdTv.setVisibility(View.VISIBLE);
                newcheckPwdTv.setTypeface(typeface); newcheckPwdTv.setTextSize(11);
                newcheckPwdTv.setText("* 비밀번호가 일치하지 않습니다.");
                newcheckPwdTv.setTextColor(Color.parseColor("#DF1515"));
                newcheckPwdEt.setBackgroundResource(R.drawable.yedittext_w_sr);
                bottomoffbtn.setEnabled(true);
                bottomoffbtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
            }else{
                newcheckPwdTv.setVisibility(View.INVISIBLE);
                bottomoffbtn.setEnabled(true);
                bottomoffbtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.mypage_pwd_backib: // 뒤로가기
                intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
                finish();
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