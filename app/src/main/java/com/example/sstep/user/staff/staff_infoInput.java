package com.example.sstep.user.staff;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.document.certificate.Paper;
import com.example.sstep.document.certificate.PaperHinput;
import com.example.sstep.home.Home_Ceo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class staff_infoInput extends AppCompatActivity {

    Intent intent;
    Button addBtn, chanBtn, delBtn, schedule_addBtn, completeBtn;
    ImageButton preMonthBtn, nextMonthBtn, back_Btn;
    TextView yearMonthText, termText, staff_info_local_text12; //오늘 날짜 표시 텍스트뷰

    SimpleDateFormat ymFormat = new SimpleDateFormat("yyyy년 MM월");
    SimpleDateFormat mdFormat = new SimpleDateFormat("MM월 dd일");

    static int changeDate = 0;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;

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
        back_Btn = findViewById(R.id.staff_info_back_Btn);
        completeBtn =findViewById(R.id.staff_info_completeBtn);

        baseDialog_okCenter = new BaseDialog_OkCenter(staff_infoInput.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(staff_infoInput.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

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
                Toast.makeText(this, "일정이 없습니다.", Toast.LENGTH_SHORT).show();
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

        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), InputStaffInfo.class);
                startActivity(intent);
                finish();
            }
        });

        // 완료 버튼
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCompleteDl();
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

    // '계약서 작성완료'버튼 클릭 시
    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        join_okdl_commentTv.setText("직원 합류를 완료하였습니다.");
        // '회원가입 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home_Ceo.class);
                startActivity(intent);
                finish();
            }
        });
    }
}