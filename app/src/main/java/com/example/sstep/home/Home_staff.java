package com.example.sstep.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.alarm.Alarm;
import com.example.sstep.commute.Commute_map;
import com.example.sstep.store.SelectStore;
import com.example.sstep.user.mypage.MyPage;

public class Home_staff extends AppCompatActivity implements View.OnClickListener {

    Button commuteBtn, mypageBtn, selectStoreBtn;
    ImageButton menuIBtn, alarmIBtn;

    boolean isGoingToWork = true; // 초기값 설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_staff);

        alarmIBtn=findViewById(R.id.homestaff_alarmIBtn); alarmIBtn.setOnClickListener(this);
        selectStoreBtn=findViewById(R.id.homestaff_selectStoreBtn); selectStoreBtn.setOnClickListener(this);
        mypageBtn=findViewById(R.id.homestaff_mypageBtn); mypageBtn.setOnClickListener(this);
        commuteBtn=findViewById(R.id.homestaff_commuteBtn); commuteBtn.setOnClickListener(this);
        menuIBtn=findViewById(R.id.homestaff_menuIBtn); menuIBtn.setOnClickListener(this);

        // Commute_map으로부터 전달된 값 읽기
        isGoingToWork = getIntent().getBooleanExtra("isGoingToWork", true);
        if (isGoingToWork) {
            commuteBtn.setText("출근하기");
        } else {
            commuteBtn.setText("퇴근하기");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.homestaff_menuIBtn: // 메뉴
                Animation slideInLeft = AnimationUtils.loadAnimation(Home_staff.this, R.anim.slide_in_left);
                menuIBtn.startAnimation(slideInLeft);

                intent = new Intent(Home_staff.this, Home_menu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                break;
            case R.id.homestaff_commuteBtn: // 출근하기
                if (commuteBtn.getText().toString().equals("출근하기")) {
                    intent = new Intent(getApplicationContext(), Commute_map.class);
                    intent.putExtra("inout", "in");
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(getApplicationContext(), Commute_map.class);
                    intent.putExtra("inout", "out");
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.homestaff_selectStoreBtn: // 사업장 선택
                intent = new Intent(getApplicationContext(), SelectStore.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homestaff_mypageBtn: // 마이페이지
                intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homestaff_alarmIBtn: // 알림
                intent = new Intent(getApplicationContext(), Alarm.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

}