package com.example.sstep.date;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.user.staff.addSchedule;

public class Date_plus extends AppCompatActivity {

    Dialog timePickDialog;
    TextView workTimeText, homeTimeText;
    Button selectStaffBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_plus);

        workTimeText=findViewById(R.id.datePlus_workTimeText);
        homeTimeText=findViewById(R.id.datePlus_homeTimeText);
        selectStaffBtn=findViewById(R.id.datePlus_selectStaffBtn);


        timePickDialog = new Dialog(Date_plus.this);
        timePickDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        timePickDialog.setContentView(R.layout.dialog_time_setting);// xml 레이아웃 파일과 연결

        //시간을 누르면 다이얼로그 생성
        workTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickDialog.show();
                TimePicker timePicker = timePickDialog.findViewById(R.id.dialog_time_set_timePicker);

                timePicker.setIs24HourView(true);
                Button dialog_time_set_okBtn = timePickDialog.findViewById(R.id.dialog_time_set_okBtn);
                Button dialog_time_set_cancleBtn = timePickDialog.findViewById(R.id.dialog_time_set_cancleBtn);
                dialog_time_set_cancleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timePickDialog.dismiss();
                    }
                });
                dialog_time_set_okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        workTimeText.setText( timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                        timePickDialog.dismiss();

                    }
                });
            }
        });

        //시간을 누르면 다이얼로그 생성
        homeTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickDialog.show();
                TimePicker timePicker = timePickDialog.findViewById(R.id.dialog_time_set_timePicker);

                timePicker.setIs24HourView(true);
                Button dialog_time_set_okBtn1 = timePickDialog.findViewById(R.id.dialog_time_set_okBtn);
                Button dialog_time_set_cancleBtn1 = timePickDialog.findViewById(R.id.dialog_time_set_cancleBtn);
                //취소버튼 누르면 취소처리
                dialog_time_set_cancleBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timePickDialog.dismiss();
                    }
                });
                //확인버튼 누르면 시간을 텍스트에 기록
                dialog_time_set_okBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        homeTimeText.setText( timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                    }
                });
            }
        });
    }
}