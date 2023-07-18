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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sstep.R;

public class InputStaffInfo_wselectDialog extends Dialog {

    RadioGroup Rg;
    RadioButton autoRb1, manualRb2;
    EditText manualEt;
    LinearLayout HL1;

    private InputStaffInfo_wselectDialogListener wselectDialogListener;

    public InputStaffInfo_wselectDialog(Context context, InputStaffInfo_wselectDialogListener wselectDialogListener){
        super(context);
        this.wselectDialogListener = wselectDialogListener;
    }

    String data ="";
    public interface InputStaffInfo_wselectDialogListener{
        void clickBtn(String data);
    }
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 바 삭제
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.inputstaffinfo_wselectdl);

        Rg = (RadioGroup) findViewById(R.id.inputStaffInfo_wselectdl_Rg);
        autoRb1 = (RadioButton) findViewById(R.id.inputStaffInfo_wselectdl_autoRb1);
        manualRb2 = (RadioButton)findViewById(R.id.inputStaffInfo_wselectdl_manualRb2);
        manualEt = (EditText)findViewById(R.id.inputStaffInfo_wselectdl_manualEt);
        HL1 = (LinearLayout)findViewById(R.id.inputStaffInfo_wselectdl_HL1);

        Button okBtn = (Button)findViewById(R.id.inputStaffInfo_wselectdl_okBtn);

        Rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.inputStaffInfo_wselectdl_autoRb1) {
                    HL1.setVisibility(View.INVISIBLE);
                    manualEt.setText("");
                } else if (checkedId == R.id.inputStaffInfo_wselectdl_manualRb2) {
                    HL1.setVisibility(View.VISIBLE);
                    manualEt.setText("");
                }

            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editTextData = manualEt.getText().toString().trim();
                if(data.equals("")){
                    if(!editTextData.isEmpty()){
                        data=editTextData + "시간";
                    }else{
                        data = "설정";
                    }
                }
                wselectDialogListener.clickBtn(data);  //보내는 값 설정
                dismiss();
            }
        });
    }
    
    
}

