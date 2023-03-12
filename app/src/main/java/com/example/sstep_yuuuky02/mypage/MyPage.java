package com.example.sstep_yuuuky02.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.sstep_yuuuky02.R;

public class MyPage extends AppCompatActivity {

    LinearLayout mypage_HL1, mypage_HL3, mypage_HL4, mypage_HL5, mypage_HL6, mypage_HL7, mypage_HL8, mypage_HL9;
    Dialog logoutdl, outdl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        mypage_HL1 = findViewById(R.id.mypage_HL1);mypage_HL3 = findViewById(R.id.mypage_HL3);
        mypage_HL4 = findViewById(R.id.mypage_HL4);mypage_HL5 = findViewById(R.id.mypage_HL5);
        mypage_HL6 = findViewById(R.id.mypage_HL6);mypage_HL7 = findViewById(R.id.mypage_HL7);
        mypage_HL8 = findViewById(R.id.mypage_HL8);mypage_HL9 = findViewById(R.id.mypage_HL9);

        logoutdl = new Dialog(MyPage.this); // Dialog 초기화
        logoutdl.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        logoutdl.setContentView(R.layout.mypage_logoutdl); // xml 레이아웃 파일과 연결

        outdl = new Dialog(MyPage.this); // Dialog 초기화
        outdl.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        outdl.setContentView(R.layout.mypage_outdl); // xml 레이아웃 파일과 연결


        // '프로필' 선택 시
        mypage_HL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPage_Profile.class);
                startActivity(intent);
                finish();
            }
        });
        // '알림설정' 선택 시
        mypage_HL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPage_Alarm.class);
                startActivity(intent);
                finish();
            }
        });
        // '비밀번호 변경' 선택 시
        mypage_HL4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPage_Pwd.class);
                startActivity(intent);
                finish();
            }
        });
        // '사업주-직원 전환' 선택 시
        mypage_HL5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPage_Change.class);
                startActivity(intent);
                finish();
            }
        });
        // '문자 수신 설정' 선택 시
        mypage_HL6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPage_Message.class);
                startActivity(intent);
                finish();
            }
        });
        // '문의하기' 선택 시
        mypage_HL7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPage_Ask.class);
                startActivity(intent);
                finish();
            }
        });
        // '로그아웃' 선택 시
        mypage_HL8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showlogoutDl();
            }
        });
        // '탈퇴하기' 선택 시
        mypage_HL9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showoutDl();
            }
        });
    }

    // '로그아웃 dialog' 띄우기
    public void showlogoutDl() {
        logoutdl.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        logoutdl.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button logoutdl_canclebtn1,logoutdl_okbtn2;
        logoutdl_canclebtn1 = logoutdl.findViewById(R.id.mypage_logoutdl_canclebtn1); logoutdl_okbtn2 = logoutdl.findViewById(R.id.mypage_logoutdl_okbtn2);
        // '로그아웃 dialog' _ 취소 버튼 클릭 시
        logoutdl_canclebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutdl.dismiss();
            }
        });
        // '로그아웃 dialog' _ 로그아웃 버튼 클릭 시
        logoutdl_okbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutdl.dismiss();
            }
        });
    }

    // '탈퇴하기 dialog' 띄우기
    public void showoutDl() {
        outdl.show();
        outdl.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button outdl_canclebtn1, outdl_okbtn2;
        outdl_canclebtn1 = outdl.findViewById(R.id.mypage_outdl_canclebtn1); outdl_okbtn2 = outdl.findViewById(R.id.mypage_outdl_okbtn2);

        // '탈퇴하기 dialog' _ 취소 버튼 클릭 시
        outdl_canclebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outdl.dismiss();
            }
        });
        // '탈퇴하기 dialog' _ 탈퇴하기 버튼 클릭 시
        outdl_okbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outdl.dismiss();
            }
        });
    }
}