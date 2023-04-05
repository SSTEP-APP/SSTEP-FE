package com.example.sstep.checklist;


import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.sstep.R;

public class CheckList_photo_dialog extends Dialog {

    private CheckList_photo_dialogListener photo_dialogListener;

    public CheckList_photo_dialog(Context context, CheckList_photo_dialogListener photo_dialogListener){
        super(context);
        this.photo_dialogListener = photo_dialogListener;
    }

    public interface CheckList_photo_dialogListener{
        void clickBtn(String data);
    }
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 바 삭제
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.checklist_photo_dialog);

        Button galleryBtn = (Button)findViewById(R.id.dia_checkPhoto_gallery);
        Button cameraBtn = (Button)findViewById(R.id.dia_checkPhoto_camera);
        Button cancelBtn = (Button)findViewById(R.id.dia_checkPhoto_cancel);
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo_dialogListener.clickBtn("gallery"); //보내는 값 설정
                dismiss();
            }
        });
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo_dialogListener.clickBtn("camera");
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

