package com.example.sstep.date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.todo.notice.Notice_RecyclerViewAdpater;
import com.example.sstep.todo.notice.Notice_input;
import com.example.sstep.todo.notice.Notice_recyclerViewWordItemData;

import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Date extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "Date";
    private RecyclerView mRecyclerView;
    private Date_RecyclerViewAdpater mRecyclerViewAdapter;
    private Set<Date_recyclerViewWordItemData> set = new HashSet<>();
    private LinearLayout nodataLayout, dataLayout;
    private TextView currentWeekTv;
    private Button prevWeekBtn, nextWeekBtn;
    private LinearLayout dateContainer;
    private List<java.util.Date> weekDates = new ArrayList<java.util.Date>();
    private Calendar calendar;
    private SimpleDateFormat sdf, sdf_day, sdf_date;
    ImageButton plusBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        currentWeekTv = findViewById(R.id.date_currentWeekTv);
        prevWeekBtn = findViewById(R.id.date_prevWeekBtn);
        nextWeekBtn = findViewById(R.id.date_nextWeekBtn);
        dateContainer = findViewById(R.id.date_dateContainer);
        plusBtn = findViewById(R.id.date_plusBtn); plusBtn.setOnClickListener(this);


        // 리사이클 뷰
        mRecyclerView = (RecyclerView) findViewById(R.id.date_recycleView);
        nodataLayout = (LinearLayout) findViewById(R.id.date_recycle_nodataLayout);
        dataLayout = (LinearLayout)  findViewById(R.id.date_recycle_dataLayout);
        mRecyclerView.setHasFixedSize(true); // 리사이클러뷰의 크기가 고정됨을 설정

        sdf = new SimpleDateFormat("YYYY년 MMM", Locale.getDefault());
        sdf_day = new SimpleDateFormat("EEE", Locale.getDefault());
        sdf_date = new SimpleDateFormat("d", Locale.getDefault());

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 일요일부터 시작

        updateWeek();

        prevWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, -7);
                updateWeek();
            }
        });

        nextWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, 7);
                updateWeek();
            }
        });
    }

    private void updateWeek() {
        weekDates.clear();
        for (int i = 0; i < 7; i++) {
            weekDates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -7); // 시작 요일로 되돌리기

        currentWeekTv.setText(sdf.format(weekDates.get(0))); // 주의 시작 날짜 표시

        dateContainer.removeAllViews(); // 기존에 있던 날짜 뷰들 제거

        // 요일과 날짜를 표시하는 TextView의 ID 배열
        int[] dayTextIds = {
                R.id.date_dayTv1, R.id.date_dayTv2, R.id.date_dayTv3,
                R.id.date_dayTv4, R.id.date_dayTv5, R.id.date_dayTv6, R.id.date_dayTv7
        };
        int[] dateTextIds = {
                R.id.date_dateTv1, R.id.date_dateTv2, R.id.date_dateTv3,
                R.id.date_dateTv4, R.id.date_dateTv5, R.id.date_dateTv6, R.id.date_dateTv7
        };

        // 주간 날짜를 동적으로 생성하고 추가
        // 요일과 날짜를 설정
        for (int i = 0; i < 7; i++) {
            TextView dayTextView = findViewById(dayTextIds[i]);
            TextView dateTextView = findViewById(dateTextIds[i]);

            dayTextView.setText(sdf_day.format(weekDates.get(i))); // 요일 설정
            dateTextView.setText(sdf_date.format(weekDates.get(i))); // 날짜 설정
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.date_plusBtn: // +추가 버튼
                intent = new Intent(getApplicationContext(), Date_plus.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}