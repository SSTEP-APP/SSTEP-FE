package com.example.sstep.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sstep.R;
import com.example.sstep.alarm.Alarm;
import com.example.sstep.document.contract.PaperWlist;
import com.example.sstep.performance.MonthState;
import com.example.sstep.store.SelectStore;
import com.example.sstep.todo.checklist.CheckList;
import com.example.sstep.user.login.Find_password;
import com.example.sstep.user.mypage.MyPage;

public class Home_Ceo extends AppCompatActivity implements View.OnClickListener {

    ImageButton menuIBtn, alarmIBtn;
    Button selectStoreBtn, mypageBtn, checklistBtn;;
    TextView monthstateTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_ceo);
        menuIBtn=findViewById(R.id.homeceo_menuIBtn); menuIBtn.setOnClickListener(this);
        alarmIBtn=findViewById(R.id.homeceo_alarmIBtn); alarmIBtn.setOnClickListener(this);
        selectStoreBtn=findViewById(R.id.homeceo_selectStoreBtn); selectStoreBtn.setOnClickListener(this);
        mypageBtn=findViewById(R.id.homeceo_mypageBtn); mypageBtn.setOnClickListener(this);
        monthstateTv=findViewById(R.id.homeceo_monthstateTv); monthstateTv.setOnClickListener(this);
        checklistBtn=findViewById(R.id.homeceo_checklistBtn); checklistBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.homeceo_menuIBtn: // 메뉴
                Animation slideInLeft = AnimationUtils.loadAnimation(Home_Ceo.this, R.anim.slide_in_left);
                menuIBtn.startAnimation(slideInLeft);

                intent = new Intent(Home_Ceo.this, Home_menu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                break;
            case R.id.homeceo_alarmIBtn: // 알림
                intent = new Intent(getApplicationContext(), Alarm.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homeceo_selectStoreBtn: // 사업장 월별 현황
                intent = new Intent(getApplicationContext(), SelectStore.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homeceo_mypageBtn: // 마이페이지
                intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homeceo_checklistBtn: // 해야할 일
                intent = new Intent(getApplicationContext(), CheckList.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homeceo_monthstateTv: // 사업장 월별 현황
                intent = new Intent(getApplicationContext(), MonthState.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}