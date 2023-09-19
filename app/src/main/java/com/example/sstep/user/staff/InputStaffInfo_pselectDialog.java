package com.example.sstep.user.staff;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.core.content.ContextCompat;

import com.example.sstep.R;

public class InputStaffInfo_pselectDialog extends Dialog {

    RadioGroup Rg1, Rg2;
    EditText writeEt;
    boolean isChecking = true;
    int mCheckedId = R.id.inputStaffInfo_pselectdl_Rb100;
    int selectedId;  int previousSelectedId;
    private InputStaffInfo_pselectDialogListener pselectDialogListener;

    public InputStaffInfo_pselectDialog(Context context, InputStaffInfo_pselectDialogListener pselectDialogListener){
        super(context);
        this.pselectDialogListener = pselectDialogListener;
    }

    String data ="";
    public interface InputStaffInfo_pselectDialogListener{
        void clickBtn(String data);
    }
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 바 삭제
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.inputstaffinfo_pselectdl);

        Rg1 = (RadioGroup)findViewById(R.id.inputStaffInfo_pselectdl_Rg1);
        Rg2 = (RadioGroup)findViewById(R.id.inputStaffInfo_pselectdl_Rg2);
        writeEt = (EditText)findViewById(R.id.inputStaffInfo_pselectdl_writeEt);

        Button okBtn = (Button)findViewById(R.id.inputStaffInfo_pselectdl_okBtn);

        Rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    Rg2.clearCheck();
                    mCheckedId = checkedId;
                    Rg1.check(mCheckedId);
                }
                isChecking = true;
                selectedId = group.getCheckedRadioButtonId();

                if(selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    if(selectedRadioButton != null) {
                        selectedRadioButton.setTextColor(ContextCompat.getColor(getContext(), R.color.blue));
                    }
                    data = selectedRadioButton.getText().toString() + "%";

                    // Edittext 초기화
                    writeEt.setText("");
                    writeEt.setBackgroundResource(R.drawable.yradiobtn_table);

                    // 이전에 선택된 라디오 버튼의 텍스트 색상을 검정색으로 바꾼다.
                    if(previousSelectedId != -1 && previousSelectedId != selectedId) {
                        RadioButton previousSelectedRadioButton = findViewById(previousSelectedId);
                        if(previousSelectedRadioButton != null) {
                            previousSelectedRadioButton.setTextColor(ContextCompat.getColor(getContext(), R.color.yradiobtn_tabletext));
                        }
                    }
                    previousSelectedId = selectedId;
                }
            }
        });

        Rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    Rg1.clearCheck();
                    mCheckedId = checkedId;
                    Rg2.check(mCheckedId);
                }
                isChecking = true;
                selectedId = group.getCheckedRadioButtonId();

                if(selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    if(selectedRadioButton != null) {
                        selectedRadioButton.setTextColor(ContextCompat.getColor(getContext(), R.color.blue));
                    }
                    data = selectedRadioButton.getText().toString() + "%";

                    // Edittext 초기화
                    writeEt.setText("");
                    writeEt.setBackgroundResource(R.drawable.yradiobtn_table);

                    // 이전에 선택된 라디오 버튼의 텍스트 색상을 검정색으로 바꾼다.
                    if(previousSelectedId != -1 && previousSelectedId != selectedId) {
                        RadioButton previousSelectedRadioButton = findViewById(previousSelectedId);
                        if(previousSelectedRadioButton != null) {
                            previousSelectedRadioButton.setTextColor(ContextCompat.getColor(getContext(), R.color.yradiobtn_tabletext));
                        }
                    }
                    previousSelectedId = selectedId;
                }
            }
        });

        writeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecking = false;
                Rg1.clearCheck();
                Rg2.clearCheck();
                data="";

                writeEt.setBackgroundResource(R.drawable.yradiobtn_table_blue);

                // 이전에 선택된 라디오 버튼의 텍스트 색상을 검정색으로 바꾼다.
                if(previousSelectedId != -1 && previousSelectedId != selectedId) {
                    RadioButton previousSelectedRadioButton = findViewById(previousSelectedId);
                    if(previousSelectedRadioButton != null) {
                        previousSelectedRadioButton.setTextColor(ContextCompat.getColor(getContext(), R.color.yradiobtn_tabletext));
                    }
                }
                // Edittext를 클릭하면 이전에 선택된 라디오 버튼 초기화
                if (selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    if (selectedRadioButton != null) {
                        selectedRadioButton.setTextColor(ContextCompat.getColor(getContext(), R.color.yradiobtn_tabletext));
                    }
                }
                selectedId = -1;
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editTextData = writeEt.getText().toString().trim();
                if(data.equals("")){
                    if(!editTextData.isEmpty()){
                        data=editTextData + "%";
                    }else{
                        data = "설정";
                    }
                }
                pselectDialogListener.clickBtn(data);  //보내는 값 설정
                dismiss();
            }
        });
    }
    
    
}

