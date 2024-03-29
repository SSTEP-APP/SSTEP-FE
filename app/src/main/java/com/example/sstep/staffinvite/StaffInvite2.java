package com.example.sstep.staffinvite;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sstep.AppInData;
import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.store.store_api.StoreApiService;
import com.example.sstep.user.member.MemberApiService;
import com.example.sstep.user.member.MemberResponseDto;
import com.example.sstep.user.staff_api.StaffRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StaffInvite2 extends AppCompatActivity implements View.OnClickListener{

    ImageButton backib;
    EditText nameEt, numberEt;
    Button completeBtn;
    String userId, userName;
    long storeId, storecode;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 100;

    // 생성된 인증번호를 저장할 리스트 선언
    private List<String> generatedCodes = new ArrayList<>();

    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffinvite2);
        backib = findViewById(R.id.staffInvite2_backib); backib.setOnClickListener(this);
        nameEt = findViewById(R.id.staffInvite2_nameEt); numberEt = findViewById(R.id.staffInvite2_numberEt);
        completeBtn=findViewById(R.id.staffInvite2_completeBtn); completeBtn.setOnClickListener(this);

        baseDialog_okCenter = new BaseDialog_OkCenter(StaffInvite2.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(StaffInvite2.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        // 전화번호 입력시 자동 '-' 입력
        numberEt.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        AppInData appInData = (AppInData) getApplication(); // MyApplication 클래스의 인스턴스 가져오기
        userId = appInData.getUserId(); // 사용자 ID 가져오기
        storeId = appInData.getStoreId(); // storeId 가져오기
        storecode = appInData.getStoreCode();
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.staffInvite2_backib: // 뒤로가기
                intent = new Intent(getApplicationContext(), StaffInvite.class);
                startActivity(intent);
                finish();
                break;
            case R.id.staffInvite2_completeBtn: // 초대 완료 버튼
                try {

                    //네트워크 요청 구현
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    StoreApiService apiService = retrofit.create(StoreApiService.class);
                    MemberApiService apiService2 = retrofit.create(MemberApiService.class);

                    //userName받기 이름과 전화번호로 회원 찾기
                    Call<MemberResponseDto> call2 = apiService2.getMemberByNameAndPhoneNum(nameEt.getText().toString().trim(), numberEt.getText().toString().trim());
                    call2.enqueue(new Callback<MemberResponseDto>() {
                        @Override
                        public void onResponse(Call<MemberResponseDto> call, Response<MemberResponseDto> response) {
                            if (response.isSuccessful()) {
                                MemberResponseDto member = response.body();
                                userName = member.getUsername();
                                inviteStaffToStore(apiService, userName, storecode);

                                // 응답 성공 시 처리
                            } else {
                                Toast.makeText(getApplicationContext(), "이름과 전화번호를 확인하세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        @Override
                        public void onFailure(Call<MemberResponseDto> call, Throwable t) {
                            String errorMessage = t != null ? t.getMessage() : "Unknown error";
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });





                }catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }

    // 휴대폰 인증 처리 메서드
    private void sendSMS() {
        // 휴대폰 인증 처리 코드 작성

        // 휴대폰 인증번호 발송
        String name = nameEt.getText().toString().trim();
        String phoneNumber = numberEt.getText().toString().trim().replace("-","");
        if (!phoneNumber.isEmpty()) {
            // 권한 체크
            if (ContextCompat.checkSelfPermission(StaffInvite2.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                // 인증번호 생성 및 발송
                String verificationCode = generateUniqueVerificationCode();
                String message = name + " 님이 초대되었습니다. \n 사업장 코드는 " + storecode + " 입니다.";
                sendVerificationCode(phoneNumber, message);
                showCompleteDl(message); // 다이얼로그 표시
                int code = Integer.parseInt(verificationCode);


            } else {
                // 권한 요청
                ActivityCompat.requestPermissions(StaffInvite2.this, new String[]{android.Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            Toast.makeText(StaffInvite2.this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(StaffInvite2.this, "인증번호 발송에 실패했습니다." + verificationCode, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void showCompleteDl(String message){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        join_okdl_commentTv.setText("초대 완료");
        // '회원가입 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StaffInvite.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void inviteStaffToStore(StoreApiService apiService, String userName, long storecode) {
        // 새로운 StaffRequestDto 객체 생성
        StaffRequestDto staffRequestDto = new StaffRequestDto(
                userName,
                storecode,
                null,
                null,
                0,
                0,
                false,
                false,
                false
        );

        //적은 id를 기반으로 db에 검색
        Call<Void> call = apiService.inviteStaffToStore(staffRequestDto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    sendSMS();
                } else {
                    Toast.makeText(getApplicationContext(), "실패!!" + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // 실패 처리
                String errorMessage = t != null ? t.getMessage() : "Unknown error";
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });
    }

}