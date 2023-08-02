package com.example.sstep.store;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.sstep.R;

public class RegisterStore_calendarDialog extends Dialog {

    RadioGroup rg0, rg1, rg2, rg3, rg4, rg5, rg6;
    boolean isChecking = true;
    int mCheckedId = R.id.dialog_cal_undefined;
    int selectedId;  int previousSelectedId;
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
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_calendar);

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
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg1.clearCheck();
                    rg2.clearCheck();
                    rg3.clearCheck();
                    rg4.clearCheck();
                    rg5.clearCheck();
                    rg6.clearCheck();
                    mCheckedId = checkedId;
                    rg0.check(mCheckedId);
                }
                isChecking = true;
                selectedId = group.getCheckedRadioButtonId();

                if(selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    if(selectedRadioButton != null) {
                        selectedRadioButton.setTextColor(Color.WHITE);
                    }
                    data = selectedRadioButton.getText().toString();

                    // 이전에 선택된 라디오 버튼의 텍스트 색상을 검정색으로 바꾼다.
                    if(previousSelectedId != -1 && previousSelectedId != selectedId) {
                        RadioButton previousSelectedRadioButton = findViewById(previousSelectedId);
                        if(previousSelectedRadioButton != null) {
                            previousSelectedRadioButton.setTextColor(Color.BLACK);
                        }
                    }
                    previousSelectedId = selectedId;
                }

            }
        });

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg0.clearCheck();
                    rg2.clearCheck();
                    rg3.clearCheck();
                    rg4.clearCheck();
                    rg5.clearCheck();
                    rg6.clearCheck();
                    mCheckedId = checkedId;
                    rg1.check(mCheckedId);
                }
                isChecking = true;
                selectedId = group.getCheckedRadioButtonId();

                if(selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    if(selectedRadioButton != null) {
                        selectedRadioButton.setTextColor(Color.WHITE);
                    }
                    data = selectedRadioButton.getText().toString() + "일";

                    // 이전에 선택된 라디오 버튼의 텍스트 색상을 검정색으로 바꾼다.
                    if(previousSelectedId != -1 && previousSelectedId != selectedId) {
                        RadioButton previousSelectedRadioButton = findViewById(previousSelectedId);
                        if(previousSelectedRadioButton != null) {
                            previousSelectedRadioButton.setTextColor(Color.BLACK);
                        }
                    }
                    previousSelectedId = selectedId;
                }
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg0.clearCheck();
                    rg1.clearCheck();
                    rg3.clearCheck();
                    rg4.clearCheck();
                    rg5.clearCheck();
                    rg6.clearCheck();
                    mCheckedId = checkedId;
                    rg2.check(mCheckedId);
                }
                isChecking = true;
                selectedId = group.getCheckedRadioButtonId();

                if(selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    if(selectedRadioButton != null) {
                        selectedRadioButton.setTextColor(Color.WHITE);
                    }
                    data = selectedRadioButton.getText().toString() + "일";

                    // 이전에 선택된 라디오 버튼의 텍스트 색상을 검정색으로 바꾼다.
                    if(previousSelectedId != -1 && previousSelectedId != selectedId) {
                        RadioButton previousSelectedRadioButton = findViewById(previousSelectedId);
                        if(previousSelectedRadioButton != null) {
                            previousSelectedRadioButton.setTextColor(Color.BLACK);
                        }
                    }
                    previousSelectedId = selectedId;
                }
            }
        });

        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg0.clearCheck();
                    rg1.clearCheck();
                    rg2.clearCheck();
                    rg4.clearCheck();
                    rg5.clearCheck();
                    rg6.clearCheck();
                    mCheckedId = checkedId;
                    rg3.check(mCheckedId);
                }
                isChecking = true;
                selectedId = group.getCheckedRadioButtonId();
                if(selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    if(selectedRadioButton != null) {
                        selectedRadioButton.setTextColor(Color.WHITE);
                    }
                    data = selectedRadioButton.getText().toString() + "일";

                    // 이전에 선택된 라디오 버튼의 텍스트 색상을 검정색으로 바꾼다.
                    if(previousSelectedId != -1 && previousSelectedId != selectedId) {
                        RadioButton previousSelectedRadioButton = findViewById(previousSelectedId);
                        if(previousSelectedRadioButton != null) {
                            previousSelectedRadioButton.setTextColor(Color.BLACK);
                        }
                    }
                    previousSelectedId = selectedId;
                }
            }
        });

        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg0.clearCheck();
                    rg1.clearCheck();
                    rg2.clearCheck();
                    rg3.clearCheck();
                    rg5.clearCheck();
                    rg6.clearCheck();
                    mCheckedId = checkedId;
                    rg4.check(mCheckedId);
                }
                isChecking = true;
                selectedId = group.getCheckedRadioButtonId();
                if(selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    if(selectedRadioButton != null) {
                        selectedRadioButton.setTextColor(Color.WHITE);
                    }
                    data = selectedRadioButton.getText().toString() + "일";

                    // 이전에 선택된 라디오 버튼의 텍스트 색상을 검정색으로 바꾼다.
                    if(previousSelectedId != -1 && previousSelectedId != selectedId) {
                        RadioButton previousSelectedRadioButton = findViewById(previousSelectedId);
                        if(previousSelectedRadioButton != null) {
                            previousSelectedRadioButton.setTextColor(Color.BLACK);
                        }
                    }
                    previousSelectedId = selectedId;
                }
            }
        });

        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg0.clearCheck();
                    rg1.clearCheck();
                    rg2.clearCheck();
                    rg3.clearCheck();
                    rg4.clearCheck();
                    rg6.clearCheck();
                    mCheckedId = checkedId;
                    rg5.check(mCheckedId);
                }
                isChecking = true;
                selectedId = group.getCheckedRadioButtonId();
                if(selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    if(selectedRadioButton != null) {
                        selectedRadioButton.setTextColor(Color.WHITE);
                    }
                    data = selectedRadioButton.getText().toString() + "일";

                    // 이전에 선택된 라디오 버튼의 텍스트 색상을 검정색으로 바꾼다.
                    if(previousSelectedId != -1 && previousSelectedId != selectedId) {
                        RadioButton previousSelectedRadioButton = findViewById(previousSelectedId);
                        if(previousSelectedRadioButton != null) {
                            previousSelectedRadioButton.setTextColor(Color.BLACK);
                        }
                    }
                    previousSelectedId = selectedId;
                }
            }
        });

        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg0.clearCheck();
                    rg1.clearCheck();
                    rg2.clearCheck();
                    rg3.clearCheck();
                    rg4.clearCheck();
                    rg5.clearCheck();
                    mCheckedId = checkedId;
                    rg6.check(mCheckedId);
                }
                isChecking = true;
                selectedId = group.getCheckedRadioButtonId();

                if(selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    if(selectedRadioButton != null) {
                        selectedRadioButton.setTextColor(Color.WHITE);
                    }
                    if (selectedRadioButton.getText().toString().equals("말일")){
                        data = "말일";
                    }else{
                        data = selectedRadioButton.getText().toString() + "일";
                    }

                    // 이전에 선택된 라디오 버튼의 텍스트 색상을 검정색으로 바꾼다.
                    if(previousSelectedId != -1 && previousSelectedId != selectedId) {
                        RadioButton previousSelectedRadioButton = findViewById(previousSelectedId);
                        if(previousSelectedRadioButton != null) {
                            previousSelectedRadioButton.setTextColor(Color.BLACK);
                        }
                    }
                    previousSelectedId = selectedId;
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

