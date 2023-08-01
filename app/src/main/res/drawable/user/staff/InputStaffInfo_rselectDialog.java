package drawable.user.staff;

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

public class InputStaffInfo_rselectDialog extends Dialog {

    RadioGroup Rg;
    RadioButton autoRb1, manualRb2;
    EditText manualEt;
    LinearLayout HL1;

    private InputStaffInfo_rselectDialogListener rselectDialogListener;

    public InputStaffInfo_rselectDialog(Context context, InputStaffInfo_rselectDialogListener rselectDialogListener){
        super(context);
        this.rselectDialogListener = rselectDialogListener;
    }

    String data ="";
    public interface InputStaffInfo_rselectDialogListener{
        void clickBtn(String data);
    }
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 바 삭제
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.inputstaffinfo_rselectdl);

        Rg = (RadioGroup) findViewById(R.id.inputStaffInfo_rselectdl_Rg);
        autoRb1 = (RadioButton) findViewById(R.id.inputStaffInfo_rselectdl_autoRb1);
        manualRb2 = (RadioButton)findViewById(R.id.inputStaffInfo_rselectdl_manualRb2);
        manualEt = (EditText)findViewById(R.id.inputStaffInfo_rselectdl_manualEt);
        HL1 = (LinearLayout)findViewById(R.id.inputStaffInfo_rselectdl_HL1);


        Button okBtn = (Button)findViewById(R.id.inputStaffInfo_rselectdl_okBtn);

        Rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.inputStaffInfo_rselectdl_autoRb1) {
                    HL1.setVisibility(View.INVISIBLE);
                    manualEt.setText("");
                } else if (checkedId == R.id.inputStaffInfo_rselectdl_manualRb2) {
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
                        data=editTextData + "분";
                    }else{
                        data = "설정";
                    }
                }
                rselectDialogListener.clickBtn(data);  //보내는 값 설정
                dismiss();
            }
        });
    }
    
    
}

