package com.example.sstep.joinlogin;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class JoinActivity extends AppCompatActivity {

    EditText idEt, nameEt, phonumEt, passEt, checkPassEt;
    Button completeBtn;
    RadioGroup userTypeRG; RadioButton ownerRadio, staffRadio;
    TextView checkText;
    String id, name, phonum, pass, checkPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        idEt=findViewById(R.id.join_idEt);
        nameEt=findViewById(R.id.join_nameEt);
        phonumEt=findViewById(R.id.join_phonumEt);
        passEt=findViewById(R.id.join_passEt);
        checkPassEt=findViewById(R.id.join_checkPassEt);
        completeBtn=findViewById(R.id.join_completeBtn);
        userTypeRG=findViewById(R.id.join_userTypeRG);
        ownerRadio=findViewById(R.id.join_ownerRadio);
        staffRadio=findViewById(R.id.join_staffRadio);
        checkText=findViewById(R.id.join_checkText);

        idEt.addTextChangedListener(textWatcher);
        nameEt.addTextChangedListener(textWatcher);
        phonumEt.addTextChangedListener(textWatcher);
        passEt.addTextChangedListener(textWatcher);
        checkPassEt.addTextChangedListener(textWatcher);

        // 전화번호 입력시 자동 '-' 입력
        phonumEt.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),pass+checkPass+"클릭됨",Toast.LENGTH_SHORT).show();
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
            id = idEt.getText().toString().trim();
            name = nameEt.getText().toString().trim();
            phonum = phonumEt.getText().toString().trim();
            pass = passEt.getText().toString().trim();
            checkPass = checkPassEt.getText().toString().trim();

            if (id.length()>0 && name.length()>0 && phonum.length()>0 && pass.length()>0 && checkPass.length()>0
                    && pass.equals(checkPass)){
                checkText.setVisibility(View.VISIBLE);
                checkText.setText("비밀번호가 일치합니다.");
                checkText.setTextColor(Color.BLUE);
                checkPassEt.setBackgroundResource(R.drawable.yedittext_w_sb);
                completeBtn.setEnabled(true);
                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
            }else if(pass.equals(checkPass)){
                checkText.setVisibility(View.VISIBLE);
                checkText.setText("비밀번호가 일치합니다.");
                checkText.setTextColor(Color.BLUE);
                checkPassEt.setBackgroundResource(R.drawable.yedittext_w_sb);
            }else if(checkPass.equals("")){
                checkText.setVisibility(View.INVISIBLE);
                checkPassEt.setBackgroundResource(R.drawable.yedittext_w_sg);
            }else if(!pass.equals(checkPass)){
                checkText.setVisibility(View.VISIBLE);
                checkText.setText("비밀번호가 일치하지 않습니다.");
                checkText.setTextColor(Color.RED);
                checkPassEt.setBackgroundResource(R.drawable.yedittext_w_sr);
                completeBtn.setEnabled(true);
                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
            }else{
                checkText.setVisibility(View.INVISIBLE);
                completeBtn.setEnabled(true);
                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}