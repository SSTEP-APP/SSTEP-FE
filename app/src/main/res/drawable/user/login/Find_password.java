package drawable.user.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sstep.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Find_password extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutL;
    EditText nameEt, phonumEt, passEt, checkPassEt; String name, phonum, pass, checkPass;
    CheckBox passEyeCb, checkPassEyeCb;
    TextView checkText;
    ImageButton back_Btn; Button certbtn, completeBtn;
    FrameLayout certF;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 100;
    // 생성된 인증번호를 저장할 리스트 선언
    private List<String> generatedCodes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password);

        back_Btn=findViewById(R.id.findPass_backBtn);
        back_Btn.setOnClickListener(this);
        certbtn=findViewById(R.id.findPass_certbtn);
        certbtn.setOnClickListener(this);
        completeBtn=findViewById(R.id.findPass_completeBtn);
        completeBtn.setOnClickListener(this);

        layoutL=findViewById(R.id.findPass_layoutL);
        nameEt=findViewById(R.id.findPass_nameEt);
        phonumEt=findViewById(R.id.findPass_phonumEt);
        passEt=findViewById(R.id.findPass_passEt);
        checkPassEt=findViewById(R.id.findPass_checkPassEt);
        passEyeCb=findViewById(R.id.findPass_passEyeCb);
        checkPassEyeCb=findViewById(R.id.findPass_checkPassEyeCb);
        checkText=findViewById(R.id.findPass_checkText);
        certF=findViewById(R.id.findPass_certF);

        nameEt.addTextChangedListener(textWatcher);
        phonumEt.addTextChangedListener(textWatcher);
        passEt.addTextChangedListener(textWatcher);
        checkPassEt.addTextChangedListener(textWatcher);

        // 전화번호 입력시 자동 '-' 입력
        phonumEt.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        layoutL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(); // EditText 밖에 터치 시 키보드 내리기
                return false;
            }
        });

        // 비밀번호 textPassword 보이게/안보이게
        passEyeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); // 보이게
                } else {
                    passEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //안 보이게
                }
            }
        });

        // 비밀번호 확인 textPassword 보이게/안보이게
        checkPassEyeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkPassEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); // 보이게
                } else {
                    checkPassEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); // 안 보이게
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
            passEt.setTypeface(typeface); passEt.setTextSize(13);
            checkPassEt.setTypeface(typeface); checkPassEt.setTextSize(13);

            name = nameEt.getText().toString().trim();
            phonum = phonumEt.getText().toString().trim();
            pass = passEt.getText().toString().trim();
            checkPass = checkPassEt.getText().toString().trim();

            if (name.length()>0 && phonum.length()>0 && pass.length()>0 && checkPass.length()>0
                    && pass.equals(checkPass)){
                checkText.setVisibility(View.VISIBLE);
                checkText.setTypeface(typeface); checkText.setTextSize(11);
                checkText.setText("비밀번호가 일치합니다.");
                checkText.setTextColor(Color.parseColor("#2375F2"));
                checkPassEt.setBackgroundResource(R.drawable.yedittext_w_sb);
                completeBtn.setEnabled(true);
                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
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
            }else if(!pass.equals(checkPass)){
                checkText.setVisibility(View.VISIBLE);
                checkText.setTypeface(typeface); checkText.setTextSize(11);
                checkText.setText("* 비밀번호가 일치하지 않습니다.");
                checkText.setTextColor(Color.parseColor("#DF1515"));
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.findPass_backBtn: // 뒤로가기
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
                break;
            case R.id.findPass_certbtn: // 인증확인
                sendSMS();
                break;
            case R.id.findPass_completeBtn: // 완료버튼
                Toast.makeText(getApplicationContext(),pass+checkPass+"클릭됨",Toast.LENGTH_SHORT).show();
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

    // 휴대폰 인증 처리 메서드
    private void sendSMS() {
        // 휴대폰 인증 처리 코드 작성

        // 휴대폰 인증번호 발송
        String phoneNumber = phonumEt.getText().toString().trim().replace("-","");
        if (!phoneNumber.isEmpty()) {
            // 권한 체크
            if (ContextCompat.checkSelfPermission(Find_password.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                // 인증번호 생성 및 발송
                String verificationCode = generateUniqueVerificationCode();
                sendVerificationCode(phoneNumber, "인증번호는 "+verificationCode+" 입니다.");
                certF.setVisibility(View.VISIBLE);
            } else {
                // 권한 요청
                ActivityCompat.requestPermissions(Find_password.this, new String[]{android.Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            Toast.makeText(Find_password.this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(Find_password.this, "인증번호 발송에 실패했습니다." + verificationCode, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}