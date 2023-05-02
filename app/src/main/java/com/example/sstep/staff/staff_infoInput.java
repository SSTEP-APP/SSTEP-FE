package com.example.sstep.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.checklist.CheckList_write_RecyclerViewAdpater;
import com.example.sstep.checklist.CheckList_write_recyclerViewItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class staff_infoInput extends AppCompatActivity {
    Button addBtn, chanBtn, delBtn, schedule_addBtn;
    ImageButton preMonthBtn, nextMonthBtn;
    TextView yearMonthText, termText, staff_info_local_text12; //오늘 날짜 표시 텍스트뷰

    SimpleDateFormat ymFormat = new SimpleDateFormat("yyyy년 MM월");
    SimpleDateFormat mdFormat = new SimpleDateFormat("MM월 dd일");

    static int changeDate = 0;

    Calendar calendar = new GregorianCalendar(); //오늘날짜 받기
    Calendar mCalendar = new GregorianCalendar(); //1달후 날짜 받기

    String ymDate = ymFormat.format(calendar.getTime());
    String mdDate = mdFormat.format(calendar.getTime());

    private RecyclerView mRecyclerView;
    private ArrayList<Staff_infoInput_recyclerViewItem> mList;
    private Staff_infoInput_RecyclerViewAdpater mRecyclerViewAdapter;
    private List<String> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_info_input);

        addBtn = findViewById(R.id.staff_info_addBtn);
        chanBtn = findViewById(R.id.staff_info_chanBtn);
        delBtn = findViewById(R.id.staff_info_delBtn);
        preMonthBtn = findViewById(R.id.staff_info_preMonthBtn);
        nextMonthBtn = findViewById(R.id.staff_info_nextMonthBtn);
        yearMonthText =findViewById(R.id.staff_info_yearMonthText);
        termText = findViewById(R.id.staff_info_termText);
        schedule_addBtn = findViewById(R.id.staff_info_schedule_addBtn);
        staff_info_local_text12 = findViewById(R.id.staff_info_local_text12);

        LinearLayout noworkScheduleLayout = findViewById(R.id.staff_info_noworkScheduleLayout);
        LinearLayout workCheckLayout = findViewById(R.id.staff_info_workCheckLayout);

        //1달후 날짜 구하기
        mCalendar.add(Calendar.MONTH, 1);
        String m1dDate = mdFormat.format(mCalendar.getTime());

        //날짜 기간 표시
        termText.setText(mdDate+"~"+m1dDate);
        yearMonthText.setText(ymDate);

        //이전달로 가기
        preMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDate -=1;
                calendar.add(Calendar.MONTH, -1);
                ymDate = ymFormat.format(calendar.getTime());
                yearMonthText.setText(ymDate);
            }
        });
        //다음달로 가기
        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDate +=1;
                calendar.add(Calendar.MONTH, 1);
                ymDate = ymFormat.format(calendar.getTime());
                yearMonthText.setText(ymDate);
            }
        });

        //이름, 전화번호, 날짜 받기

        //일정이 없을 때 일정 추가
        schedule_addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), addSchedule.class);
                startActivity(intent);
                finish();
            }
        });


        try {
            Intent intent = getIntent();
            ArrayList<String> listData = intent.getStringArrayListExtra("LIST_DATA");


            if (listData != null && listData.size() > 0) {
                noworkScheduleLayout.setVisibility(View.GONE);
                workCheckLayout.setVisibility(View.VISIBLE);

                //리사이클 뷰
                firstInit();
                for (int i = 0; i < listData.size(); i++) {
                    String[] dataList = listData.get(i).split(",");
                    addItem(dataList[0],dataList[1],dataList[2]);
                }

                mRecyclerViewAdapter = new Staff_infoInput_RecyclerViewAdpater(mList);
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            } else {
                noworkScheduleLayout.setVisibility(View.VISIBLE);
                workCheckLayout.setVisibility(View.GONE);
                Toast.makeText(this, "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(staff_infoInput.this,
                    e.toString(), Toast.LENGTH_SHORT).show();
        }




        //근무일정 추가
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), addSchedule.class);
                startActivity(intent);
                finish();
            }
        });
        //일정 수정
        //chanBtn.setOnClickListener(new View.OnClickListener() {
         //   @Override
        //    public void onClick(View view) {
        //        Intent intent = new Intent(getApplicationContext(), modifySchedule.class);
        //        startActivity(intent);
        //        finish();
        //    }
        //});
        //삭제 버튼
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstInit();
                mRecyclerViewAdapter = new Staff_infoInput_RecyclerViewAdpater(mList);
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(staff_infoInput.this));
                mRecyclerView.setLayoutManager(new LinearLayoutManager(staff_infoInput.this, RecyclerView.VERTICAL, false));

                noworkScheduleLayout.setVisibility(View.VISIBLE);
                workCheckLayout.setVisibility(View.GONE);
            }
        });

    }
    public void firstInit(){
        mRecyclerView = (RecyclerView) findViewById(R.id.staff_info_recyclerView);
        mList = new ArrayList<>();
    }

    public void addItem(String day_of_week, String time, String workTime){
        Staff_infoInput_recyclerViewItem item = new Staff_infoInput_recyclerViewItem();

        item.setStaff_infoInput_days(day_of_week);
        item.setStaff_infoInput_time(time);
        item.setStaff_infoInput_cancelImg(workTime);

        mList.add(item);
    }
}