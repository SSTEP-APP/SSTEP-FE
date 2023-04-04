package com.example.sstep;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterStore_calendarDialog extends Dialog {

    RadioGroup rg0, rg1, rg2, rg3, rg4, rg5, rg6;
    private RegisterStore_calendarDialogListener calendarDialogListener;

    public RegisterStore_calendarDialog(Context context, RegisterStore_calendarDialogListener calendarDialogListener){
        super(context);
        this.calendarDialogListener = calendarDialogListener;
    }

    String data ="";
    public interface RegisterStore_calendarDialogListener{
        void clickBtn(String data);
    }
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 바 삭제
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_calendar);
        RadioGroup calendarRG = (RadioGroup)findViewById(R.id.dialog_cal_calendarRG);
        rg0 = (RadioGroup)findViewById(R.id.dialog_cal_calendarRG0);
        rg1 = (RadioGroup)findViewById(R.id.dialog_cal_calendarRG1);
        rg2 = (RadioGroup)findViewById(R.id.dialog_cal_calendarRG2);
        rg3 = (RadioGroup)findViewById(R.id.dialog_cal_calendarRG3);
        rg4 = (RadioGroup)findViewById(R.id.dialog_cal_calendarRG4);
        rg5 = (RadioGroup)findViewById(R.id.dialog_cal_calendarRG5);
        rg6 = (RadioGroup)findViewById(R.id.dialog_cal_calendarRG6);
        Button okBtn = (Button)findViewById(R.id.dialog_cal_okBtn);
        Button cancelBtn = (Button)findViewById(R.id.dialog_cal_cancleBtn);
            rg0.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.dialog_cal_undefined:
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "미정";
                            break;
                    }
                }
            });

            rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.dialog_cal_1btn:
                            if (i != -1) {
                                rg0.setOnCheckedChangeListener(null);
                                rg0.clearCheck();
                                rg2.clearCheck();
                                rg3.clearCheck();
                                rg4.clearCheck();
                                rg5.clearCheck();
                                rg6.clearCheck();
                                //rg0.setOnCheckedChangeListener(rg0.getCheckedRadioButtonId());

                            }
                            data = "1일";
                            break;
                        case R.id.dialog_cal_2btn:
                            rg0.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "2일";
                            break;
                        case R.id.dialog_cal_3btn:
                            rg0.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "3일";
                            break;
                        case R.id.dialog_cal_4btn:
                            rg0.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "4일";
                            break;
                        case R.id.dialog_cal_5btn:
                            rg0.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "5일";
                            break;

                    }
                }
            });
            rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.dialog_cal_6btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "6일";
                            break;
                        case R.id.dialog_cal_7btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "7일";
                            break;
                        case R.id.dialog_cal_8btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "8일";
                            break;
                        case R.id.dialog_cal_9btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "9일";
                            break;
                        case R.id.dialog_cal_10btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "10일";
                            break;
                    }
                }
            });
            rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.dialog_cal_11btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "11일";
                            break;
                        case R.id.dialog_cal_12btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "12일";
                            break;
                        case R.id.dialog_cal_13btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "13일";
                            break;
                        case R.id.dialog_cal_14btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "14일";
                            break;
                        case R.id.dialog_cal_15btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "15일";
                            break;
                    }
                }
            });
            rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.dialog_cal_16btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "16일";
                            break;
                        case R.id.dialog_cal_17btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "17일";
                            break;
                        case R.id.dialog_cal_18btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "18일";
                            break;
                        case R.id.dialog_cal_19btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "19일";
                            break;
                        case R.id.dialog_cal_20btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg5.clearCheck();
                            rg6.clearCheck();
                            data = "20일";
                            break;
                    }
                }
            });
            rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.dialog_cal_21btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg6.clearCheck();
                            data = "21일";
                            break;
                        case R.id.dialog_cal_22btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg6.clearCheck();
                            data = "22일";
                            break;
                        case R.id.dialog_cal_23btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg6.clearCheck();
                            data = "23일";
                            break;
                        case R.id.dialog_cal_24btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg6.clearCheck();
                            data = "24일";
                            break;
                        case R.id.dialog_cal_25btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg6.clearCheck();
                            data = "25일";
                            break;
                    }
                }
            });
            rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.dialog_cal_26btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            data = "26일";
                            break;
                        case R.id.dialog_cal_27btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            data = "27일";
                            break;
                        case R.id.dialog_cal_28btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            data = "28일";
                            break;
                        case R.id.dialog_cal_29btn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            data = "29일";
                            break;
                        case R.id.dialog_cal_lastBtn:
                            rg0.clearCheck();
                            rg1.clearCheck();
                            rg2.clearCheck();
                            rg3.clearCheck();
                            rg4.clearCheck();
                            rg5.clearCheck();
                            data = "말일";
                            break;
                    }
                }
            });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.equals("")){
                    calendarDialogListener.clickBtn("급여날 설정");
                }else {
                    calendarDialogListener.clickBtn(data);  //보내는 값 설정
                }
                dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
    
    
}

