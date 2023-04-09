package com.example.sstep.staff;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.sstep.Login;
import com.example.sstep.R;
import com.example.sstep.checklist.CheckList_write;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class addSchedule extends AppCompatActivity {


    SimpleDateFormat ymFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
    Calendar calendar = new GregorianCalendar(); //오늘날짜 받기
    String mDate = ymFormat.format(calendar.getTime());
    ImageButton backBtn;
    Button selectDate;
    TextView startTimeText, endTimeText;
    Dialog timePickDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_schedule);

        backBtn = findViewById(R.id.addSch_backBtn);
        startTimeText = findViewById(R.id.addSch_startTimeText);
        endTimeText = findViewById(R.id.addSch_endTimeText);
        selectDate = findViewById(R.id.addSch_selectDate);
        timePickDialog = new Dialog(addSchedule.this);
        timePickDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        timePickDialog.setContentView(R.layout.dialog_time_setting);// xml 레이아웃 파일과 연결

        //뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), staff_infoInput.class);
                startActivity(intent);
                finish();
            }
        });

        //일정시작일 설정
        selectDate.setText(mDate);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        startTimeText.setOnClickListener(new View.OnClickListener() {
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

                    }
                });
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        dialog_time_set_okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_SHORT).show();


                                startTimeText.setText(hourOfDay + ":" + minute);
                                timePickDialog.dismiss();
                                // 시간이 변경될 때 호출되는 코드
                            }
                        });


                    }
                });
            }
        });


        endTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickDialog.show();
                TimePicker timePicker = timePickDialog.findViewById(R.id.dialog_time_set_timePicker);

                timePicker.setIs24HourView(true);
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        Button dialog_time_set_okBtn = timePickDialog.findViewById(R.id.dialog_time_set_okBtn);
                        dialog_time_set_okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_SHORT).show();


                                startTimeText.setText(hourOfDay + ":" + minute);
                                timePickDialog.dismiss();
                                // 시간이 변경될 때 호출되는 코드
                            }
                        });
                        Button dialog_time_set_cancleBtn = timePickDialog.findViewById(R.id.dialog_time_set_cancleBtn);
                        dialog_time_set_cancleBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                timePickDialog.dismiss();
                            }
                        });

                    }
                });
            }
        });
    }
}