package com.example.sstep.performance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sstep.R;
import com.example.sstep.home.Home_Ceo;
import com.example.sstep.user.staff.DateYearMonth_NavigationClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MonthState extends AppCompatActivity {

    private static final String TAG = "MonthState";
    private ViewPager viewpager;
    private MonthStateFragmentAdapter adapter;
    ScrollView scoll;
    TextView dateTv;
    Button leftBtn, rightBtn;
    ImageButton backib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthstate);

        backib=findViewById(R.id.monthstate_backib);
        dateTv = findViewById(R.id.monthstate_dateTv);
        leftBtn = findViewById(R.id.monthstate_leftBtn);
        rightBtn = findViewById(R.id.monthstate_rightBtn);
        scoll = findViewById(R.id.monthstate_scoll);
        scoll.setFillViewport(true);
        viewpager = findViewById(R.id.monthstate_viewpager);
        adapter = new MonthStateFragmentAdapter(getSupportFragmentManager(),1);

        // AlarmFragmentAdapter 에 컬렉션 담기
        adapter.addFragment(new monthstate1Fragment());
        adapter.addFragment(new monthstate2Fragment());

        viewpager.setClipToPadding(false);
        viewpager.setPadding(0, 0, 80, 0);
        viewpager.setPageMargin(30);

        // ViewPager Fragment 연결
        viewpager.setAdapter(adapter);

        backib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home_Ceo.class);
                startActivity(intent);
                finish();
            }
        });

        // 년도, 월 날짜 이동
        DateYearMonth_NavigationClickListener dateYearMonth_NavigationClickListener = new DateYearMonth_NavigationClickListener(dateTv, leftBtn, rightBtn){
            @Override
            public void onClick(View view) {
                super.onClick(view);
                String data = dateTv.getText().toString().trim();
                String month = data.substring(data.indexOf(" ") + 1);  // 월 정보 추출
            }
        };
        leftBtn.setOnClickListener(dateYearMonth_NavigationClickListener);
        rightBtn.setOnClickListener(dateYearMonth_NavigationClickListener);

        setInitialDate();
    }

    // 초기 날짜 설정
    public void setInitialDate() {
        // 초기 상태로 오늘 날짜의 해당 월 설정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 M월", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy년", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        String currentMonth = sdf.format(currentDate);
        String currentYear = sdf2.format(currentDate);
        dateTv.setText(currentMonth);
        String data = dateTv.getText().toString().trim();
        String month = data.substring(data.indexOf(" ") + 1);  // 월 정보 추출
    }
}