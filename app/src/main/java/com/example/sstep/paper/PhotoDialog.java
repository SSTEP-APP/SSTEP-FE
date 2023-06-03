package com.example.sstep.paper;

import static android.app.appsearch.AppSearchResult.RESULT_OK;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.sstep.R;
import com.example.sstep.checklist.CheckList_photo_dialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class PhotoDialog extends Dialog implements View.OnClickListener {

    private Button galleryBtn,cameraBtn,cancelBtn;
    private static final int REQ_CODE_GALLERY_IMAGE = 100;
    private static final int REQ_CODE_SELECT_CAMERA = 200;

    private final Activity activity;
    private final PhotoDialogListener listener;

    public PhotoDialog(@NonNull Activity activity, PhotoDialogListener listener) {
        super(activity);
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 바 삭제
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.checklist_photo_dialog);

        galleryBtn=findViewById(R.id.dia_checkPhoto_gallery); galleryBtn.setOnClickListener(this);
        cameraBtn=findViewById(R.id.dia_checkPhoto_camera); cameraBtn.setOnClickListener(this);
        cancelBtn=findViewById(R.id.dia_checkPhoto_cancel); cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.dia_checkPhoto_gallery: // 갤러리
                intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                activity.startActivityForResult(intent, REQ_CODE_GALLERY_IMAGE);
                dismiss();
                break;
            case R.id.dia_checkPhoto_camera: // 카메라
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activity.startActivityForResult(intent, REQ_CODE_SELECT_CAMERA);
                dismiss();
                break;
            case R.id.dia_checkPhoto_cancel: // 취소
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface PhotoDialogListener {
        void onPhotoSelected(Bitmap bitmap);
    }
}

