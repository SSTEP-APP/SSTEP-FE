package com.example.sstep.user.mypage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class MyPage extends AppCompatActivity implements View.OnClickListener {

    LinearLayout alarmHL1, pwdHL2, askHL3, logoutHL4, dropHL5;
    Button profileBtn;
    Dialog logoutdl, dropdl;
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

        logoutdl = new Dialog(MyPage.this); // Dialog 초기화
        logoutdl.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        logoutdl.setContentView(R.layout.mypage_logoutdl); // xml 레이아웃 파일과 연결

        dropdl = new Dialog(MyPage.this); // Dialog 초기화
        dropdl.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dropdl.setContentView(R.layout.mypage_dropdl); // xml 레이아웃 파일과 연결

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
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
                logoutdl.dismiss();
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
                dropdl.dismiss();
            }
        });
    }
}