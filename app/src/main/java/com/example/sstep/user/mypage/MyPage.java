package com.example.sstep.user.mypage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.home.Home_Ceo;
import com.example.sstep.user.login.Login;
import com.example.sstep.user.member.MemberApiService;
import com.example.sstep.user.member.MemberModel;
import com.example.sstep.user.member.MemberResponseDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyPage extends AppCompatActivity implements View.OnClickListener {

    LinearLayout alarmHL1, pwdHL2, askHL3, logoutHL4, dropHL5;
    Button profileBtn;
    ImageButton backib;
    Dialog logoutdl, dropdl;
    Intent intent;
    String memberId, memberName;
    TextView nameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        alarmHL1 = findViewById(R.id.mypage_alarmHL1); alarmHL1.setOnClickListener(this);
        pwdHL2 = findViewById(R.id.mypage_pwdHL2); pwdHL2.setOnClickListener(this);
        askHL3 = findViewById(R.id.mypage_askHL3); askHL3.setOnClickListener(this);
        logoutHL4 = findViewById(R.id.mypage_logoutHL4); logoutHL4.setOnClickListener(this);
        dropHL5 = findViewById(R.id.mypage_dropHL5); dropHL5.setOnClickListener(this);
        profileBtn = findViewById(R.id.mypage_profileBtn); profileBtn.setOnClickListener(this);
        backib = findViewById(R.id.mypage_backib); backib.setOnClickListener(this);
        nameTv=findViewById(R.id.mypage_nameTv);

        logoutdl = new Dialog(MyPage.this); // Dialog 초기화
        logoutdl.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        logoutdl.setContentView(R.layout.mypage_logoutdl); // xml 레이아웃 파일과 연결

        dropdl = new Dialog(MyPage.this); // Dialog 초기화
        dropdl.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dropdl.setContentView(R.layout.mypage_dropdl); // xml 레이아웃 파일과 연결

        // memberId 가져오기
        try {
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MemberApiService apiService = retrofit.create(MemberApiService.class);
            memberId = "819id";
            //적은 id를 기반으로 db에 검색
            Call<MemberResponseDto> call = apiService.getMemberByUsername(memberId);
            call.enqueue(new Callback<MemberResponseDto>() {
                @Override
                public void onResponse(Call<MemberResponseDto> call, Response<MemberResponseDto> response) {
                    if (response.isSuccessful()) {
                        MemberResponseDto data = response.body();
                        // 적은 id로 패스워드 데이터 가져오기
                        memberName =data.getName(); // id에 id 설정
                        nameTv.setText(memberName);
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<MemberResponseDto> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = "요청 실패: " + t.getMessage();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.mypage_backib: // 뒤로 가기
                intent = new Intent(getApplicationContext(), Home_Ceo.class);
                startActivity(intent);
                finish();
                break;
            case R.id.mypage_profileBtn: // 프로필 수정
                intent = new Intent(getApplicationContext(), MyPage_Profile.class);
                startActivity(intent);
                finish();
                break;
            case R.id.mypage_alarmHL1: // 알림 설정
                intent = new Intent(getApplicationContext(), MyPage_Alarm.class);
                startActivity(intent);
                finish();
                break;
            case R.id.mypage_pwdHL2: // 비밀번호 변경
                intent = new Intent(getApplicationContext(), MyPage_Pwd.class);
                startActivity(intent);
                finish();
                break;
            case R.id.mypage_askHL3: // 문의하기
                intent = new Intent(getApplicationContext(), MyPage_Ask.class);
                startActivity(intent);
                finish();
                break;
            case R.id.mypage_logoutHL4: // 로그아웃
                showlogoutDl();
                break;
            case R.id.mypage_dropHL5: // 탈퇴하기
                showdropDl();
                break;
            default:
                break;
        }
    }

    // '로그아웃 dialog' 띄우기
    public void showlogoutDl() {
        logoutdl.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        logoutdl.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button logoutdl_cancleBtn,logoutdl_logoutBtn;
        logoutdl_cancleBtn = logoutdl.findViewById(R.id.mypage_logoutdl_cancleBtn); logoutdl_logoutBtn = logoutdl.findViewById(R.id.mypage_logoutdl_logoutBtn);
        // '로그아웃 dialog' _ 취소 버튼 클릭 시
        logoutdl_cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutdl.dismiss();
            }
        });
        // '로그아웃 dialog' _ 로그아웃 버튼 클릭 시
        logoutdl_logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // '탈퇴하기 dialog' 띄우기
    public void showdropDl() {
        dropdl.show();
        dropdl.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dropdl_cancleBtn, dropdl_dropBtn;
        dropdl_cancleBtn = dropdl.findViewById(R.id.mypage_dropdl_cancleBtn); dropdl_dropBtn = dropdl.findViewById(R.id.mypage_dropdl_dropBtn);

        // '탈퇴하기 dialog' _ 취소 버튼 클릭 시
        dropdl_cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropdl.dismiss();
            }
        });
        // '탈퇴하기 dialog' _ 탈퇴하기 버튼 클릭 시
        dropdl_dropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}