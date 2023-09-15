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
import androidx.recyclerview.widget.LinearLayoutManager;
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

    private LinearLayout nodataLayout, dataLayout;
    private TextView currentWeekTv;
    private Button prevWeekBtn, nextWeekBtn;
    private LinearLayout dateContainer;
    private List<java.util.Date> weekDates = new ArrayList<java.util.Date>();
    private Calendar calendar;
    private SimpleDateFormat sdf, sdf_day, sdf_date, sdf_ymd;
    ImageButton plusBtn;
    String day, date, ymd, day2;
    TextView dayTextView, dateTextView;
    int[] dayTextIds, dateTextIds;

    private RecyclerView dateRecyclerView; // RecyclerView 추가
    private List<Date_recyclerViewWordItemData> dateItemList; // RecyclerView 데이터를 담을 리스트
    private Date_RecyclerViewAdpater dateRecyclerViewAdapter; // RecyclerView 어댑터

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

        sdf = new SimpleDateFormat("YYYY년 MMM", Locale.getDefault());
        sdf_day = new SimpleDateFormat("EEE", Locale.getDefault());
        sdf_date = new SimpleDateFormat("d", Locale.getDefault());
        sdf_ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 일요일부터 시작

        dateRecyclerView=findViewById(R.id.date_recycleView);

        // RecyclerView 데이터 초기화
        dateItemList = new ArrayList<>();
        dateRecyclerViewAdapter = new Date_RecyclerViewAdpater(this, dateItemList);
        dateRecyclerView.setAdapter(dateRecyclerViewAdapter);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        // 날짜 텍스트뷰 클릭 이벤트 처리
        for (int i = 0; i < 7; i++) {
            dateTextView = findViewById(dateTextIds[i]);

            // 날짜 클릭 이벤트 리스너 추가
            final int clickedPosition = i; // 클릭된 텍스트뷰의 인덱스를 저장

            dateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭된 날짜의 위치(index)를 사용하여 RecyclerView를 스크롤
                    dateRecyclerView.scrollToPosition(clickedPosition);
                }
            });
        }
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
        dayTextIds = new int[]{
                R.id.date_dayTv1, R.id.date_dayTv2, R.id.date_dayTv3,
                R.id.date_dayTv4, R.id.date_dayTv5, R.id.date_dayTv6, R.id.date_dayTv7
        };
        dateTextIds = new int[]{
                R.id.date_dateTv1, R.id.date_dateTv2, R.id.date_dateTv3,
                R.id.date_dateTv4, R.id.date_dateTv5, R.id.date_dateTv6, R.id.date_dateTv7
        };

        // 주간 날짜를 동적으로 생성하고 추가
        // 요일과 날짜를 설정
        for (int i = 0; i < 7; i++) {
            dayTextView = findViewById(dayTextIds[i]);
            dateTextView = findViewById(dateTextIds[i]);

            day = sdf_day.format(weekDates.get(i)); // 요일 설정
            date = sdf_date.format(weekDates.get(i)); // 날짜 설정

            dayTextView.setText(day); // 요일 설정
            dateTextView.setText(date); // 날짜 설정
        }

        // RecyclerView 데이터 업데이트
        dateItemList.clear();
        for (java.util.Date date : weekDates) {
            ymd = sdf_ymd.format(date); // "yyyy-MM-dd" 형식
            day2 = sdf_day.format(date); // 요일 "EEE"
            dateItemList.add(new Date_recyclerViewWordItemData(ymd + ", " + day2 + "요일", null, null, null));
        }

        dateRecyclerViewAdapter.notifyDataSetChanged();
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