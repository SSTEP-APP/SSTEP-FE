package drawable.user.join;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.user.login.Login;
import com.example.sstep.user.member.MemberApiService;
import com.example.sstep.user.member.MemberModel;
import com.example.sstep.user.member.MemberRequestDto;
import com.example.sstep.user.member.MemberResponseDto;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    ScrollView scroll;
    EditText idEt, nameEt, phonumEt, passEt, checkPassEt, certNumEt; String id, name, phonum, pass, checkPass;
    ImageButton back_Btn; Button completeBtn, idcertBtn, phonecertBtn, certNumBtn;
    CheckBox passEyeCb, checkPassEyeCb;
    TextView checkText;
    Boolean completeBtn_state=Boolean.FALSE;
    Intent intent;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;
    String testId, certCode;
    int checkPhoneNum = 0; int checkId = 0;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 100;
    FrameLayout certF;

    // 생성된 인증번호를 저장할 리스트 선언
    private List<String> generatedCodes = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
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
        idcertBtn=findViewById(R.id.join_idcertBtn);
        idcertBtn.setOnClickListener(this);
        phonecertBtn=findViewById(R.id.join_phonecertBtn);
        phonecertBtn.setOnClickListener(this);
        certNumBtn = findViewById(R.id.join_certNumBtn);
        certNumBtn.setOnClickListener(this);


        scroll=findViewById(R.id.join_scroll);
        passEyeCb=findViewById(R.id.join_passEyeCb); passEyeCb.setOnCheckedChangeListener(this);
        checkPassEyeCb=findViewById(R.id.join_checkPassEyeCb); checkPassEyeCb.setOnCheckedChangeListener(this);
        idEt=findViewById(R.id.join_idEt);
        nameEt=findViewById(R.id.join_nameEt);
        phonumEt=findViewById(R.id.join_phonumEt);
        passEt=findViewById(R.id.join_passEt);
        checkPassEt=findViewById(R.id.join_checkPassEt);
        checkText=findViewById(R.id.join_checkText);
        certNumEt = findViewById(R.id.join_certNumEt);
        certF=findViewById(R.id.join_certF);

        idEt.addTextChangedListener(textWatcher);
        nameEt.addTextChangedListener(textWatcher);
        phonumEt.addTextChangedListener(textWatcher);
        passEt.addTextChangedListener(textWatcher);
        checkPassEt.addTextChangedListener(textWatcher);

        // SMS 보내기 권한 확인
        if (ContextCompat.checkSelfPermission(JoinActivity.this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // 권한 요청
            ActivityCompat.requestPermissions(JoinActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }

        // 전화번호 입력시 자동 '-' 입력
        phonumEt.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

    }

    // EditText 다 채워지고 인증완료시 아래 버튼 활성화
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
                    && pass.equals(checkPass) && checkId==1 && checkPhoneNum == 1){
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
            case R.id.join_idcertBtn: // id 중복확인 버튼
                onCertDl();
                break;
            case R.id.join_phonecertBtn: // phone 중복확인 버튼
                sendSMS();

<<<<<<< HEAD:app/src/main/res/drawable/user/join/JoinActivity.java
                break;

            case R.id.join_certNumBtn: // phone 인증완료 버튼
                checkSMS();
=======
>>>>>>> 008feb5 (출퇴근관리 7/31):app/src/main/java/com/example/sstep/user/join/JoinActivity.java
                break;

            case R.id.join_certNumBtn: // phone 인증완료 버튼
                checkSMS();
                break;
            default:
                break;
        }
    }

    // 휴대폰 인증 처리 메서드
    private void sendSMS() {
        // 휴대폰 인증 처리 코드 작성
        // 휴대폰 인증번호 발송
        String phoneNumber = phonumEt.getText().toString().trim().replace("-","");
        if (!phoneNumber.isEmpty()) {
            // 권한 체크
            if (ContextCompat.checkSelfPermission(JoinActivity.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                // 인증번호 생성 및 발송
                String verificationCode = generateUniqueVerificationCode();
                sendVerificationCode(phoneNumber, "인증번호는 "+verificationCode+" 입니다.");
                certF.setVisibility(View.VISIBLE);
<<<<<<< HEAD:app/src/main/res/drawable/user/join/JoinActivity.java
<<<<<<< HEAD:app/src/main/res/drawable/user/join/JoinActivity.java
                certCode = verificationCode;
=======
>>>>>>> 008feb5 (출퇴근관리 7/31):app/src/main/java/com/example/sstep/user/join/JoinActivity.java
=======
                certCode = verificationCode;
>>>>>>> 76835b5a06a845c06517ad3d0669c60a2e4e311c:app/src/main/java/com/example/sstep/user/join/JoinActivity.java
            } else {
                // 권한 요청
                ActivityCompat.requestPermissions(JoinActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            Toast.makeText(JoinActivity.this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(JoinActivity.this, "인증번호 발송에 실패했습니다." + verificationCode, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // 휴대폰 인증 완료 메서드
    private void checkSMS() {
        String enterCode = certNumEt.getText().toString();
        if (!enterCode.isEmpty()) {
            if (enterCode.equals(certCode)) {
                Toast.makeText(JoinActivity.this, "인증되었습니다.", Toast.LENGTH_SHORT).show();
                checkPhoneNum = 1;
                if (id.length()>0 && name.length()>0 && phonum.length()>0 && pass.length()>0 && checkPass.length()>0
                        && pass.equals(checkPass) && checkId==1 && checkPhoneNum == 1){
                    completeBtn.setEnabled(true);
                    completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
                    completeBtn_state = Boolean.TRUE;
                }
            } else {
                Toast.makeText(JoinActivity.this, "인증번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(JoinActivity.this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }

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


        try {

            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MemberApiService apiService = retrofit.create(MemberApiService.class);
// 회원가입에 필요한 데이터를 MemberRequestDto 객체로 생성
            MemberRequestDto memberRequestDto = new MemberRequestDto(
                    idEt.getText().toString().trim(),
                    nameEt.getText().toString().trim(),
                    passEt.getText().toString().trim(),
                    phonumEt.getText().toString().trim()
            );

// 회원가입 요청을 서버에 전송
            Call<MemberResponseDto> call = apiService.save(memberRequestDto);
            call.enqueue(new Callback<MemberResponseDto>() {
                @Override
                public void onResponse(Call<MemberResponseDto> call, Response<MemberResponseDto> response) {
                    if (response.isSuccessful()) {
                        // 회원가입 성공
                        MemberResponseDto memberResponseDto = response.body();
                        join_okdl_commentTv.setText("회원가입이 완료되었습니다.\n 로그인해 주세요.");
                        // 응답을 필요에 따라 처리하세요.
                    } else {
                        // 회원가입 실패
                        int statusCode = response.code();
                        join_okdl_commentTv.setText("회원가입이 실패했습니다.\n 오류코드: " + statusCode);
                        // 에러 응답을 처리하세요.
                    }
                }

                @Override
                public void onFailure(Call<MemberResponseDto> call, Throwable t) {
                    // 네트워크 오류나 기타 이유로 회원가입 실패
                    String errorMessage = t != null ? t.getMessage() : "Unknown error";
                    if (errorMessage.equals("End of input at line 1 column 1 path $")){
                        join_okdl_commentTv.setText("회원가입이 완료되었습니다.\n 로그인해 주세요.");

                    }else {
                        join_okdl_commentTv.setText("회원가입이 실패했습니다!!\n 오류메시지: " + errorMessage);
                        t.printStackTrace();
                    }
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

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
        try {

            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MemberApiService apiService = retrofit.create(MemberApiService.class);
            //적은 id를 기반으로 db에 검색
            Call<MemberModel> call = apiService.getDataFromServer(idEt.getText().toString().trim());
            call.enqueue(new Callback<MemberModel>() {
                @Override
                public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                    if (response.isSuccessful()) {
                        MemberModel data = response.body();
                        // 적은 id로 id 데이터 가져오기
                        testId =data.getMemberId();
                        if(testId.equals(idEt.getText().toString().trim())){
                            join_okdl_commentTv.setText("중복된 아이디가 있습니다.\n다른 아이디를 입력해 주세요.");
                            idEt.setText("");
                        }else if(idEt.getText().toString().trim().equals("")){
                            join_okdl_commentTv.setText("입력된 아이디가 없습니다.\n아이디를 입력해 주세요.");
                        }
                    } else {
                        // 오류 처리
                        int statusCode = response.code();
                        String errorMessage;
                        if(idEt.getText().toString().trim().equals("")) {
                            join_okdl_commentTv.setText("입력된 아이디가 없습니다.\n아이디를 입력해 주세요.");
                        } else if (statusCode == 404) {
                            join_okdl_commentTv.setText("사용 가능한 아이디 입니다.");
                            checkId = 1;
                            if (id.length()>0 && name.length()>0 && phonum.length()>0 && pass.length()>0 && checkPass.length()>0
                                    && pass.equals(checkPass) && checkId==1 && checkPhoneNum == 1){
                                completeBtn.setEnabled(true);
                                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
                                completeBtn_state = Boolean.TRUE;
                            }
                        } else if (statusCode == 500) {
                            join_okdl_commentTv.setText("사용 가능한 아이디 입니다.");
                            checkId = 1;
                            if (id.length()>0 && name.length()>0 && phonum.length()>0 && pass.length()>0 && checkPass.length()>0
                                    && pass.equals(checkPass) && checkId==1 && checkPhoneNum == 1){
                                completeBtn.setEnabled(true);
                                completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
                                completeBtn_state = Boolean.TRUE;
                            }
                        } else {
                            join_okdl_commentTv.setText("오류가 발생했습니다. 상태 코드: " + statusCode);
                        }

                    }
                }

                @Override
                public void onFailure(Call<MemberModel> call, Throwable t) {
                    // 실패 처리
                    join_okdl_commentTv.setText("요청 실패: " + t.getMessage());
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDialog_okCenter.dismiss();
            }
        });


    }
}