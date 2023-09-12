package com.example.sstep.todo.notice;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.CalendarDialog;
import com.example.sstep.R;
import com.example.sstep.commute.Commute_map;
import com.example.sstep.store.store_api.NullOnEmptyConverterFactory;
import com.example.sstep.store.store_api.StoreApiService;
import com.example.sstep.store.store_api.StoreRegisterReqDto;
import com.example.sstep.todo.notice.notice_api.NoticeApiService;
import com.example.sstep.todo.notice.notice_api.NoticeRequestDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notice_input extends AppCompatActivity implements View.OnClickListener {

    LocalDate noticeDate; // 공지 작성 일자
    String noticeDateStr;
    ImageButton backib, cameraIbtn, photoIbtn, deleteIbtn;
    EditText titleEt, contentEt;
    TextView titleLimitTv, contentLimitTv, pictureNumTv; TextView join_okdl_commentTv;
    Button completeBtn;
    LinearLayout pictureHL;
    long photoId;

    private static final int REQ_CODE_SELECT_CAMERA = 100;
    private static final int REQ_CODE_GALLERY_IMAGE = 200;
    private ArrayList<Bitmap> bitmapList = new ArrayList<>(); // 이미지를 담을 리스트
    private ImageView[] pictureIv = new ImageView[4]; // 이미지뷰 배열
    private int currentImageView = 0; // 현재 채워진 이미지뷰 개수
    private int numPictures = 0;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_input);

        backib=findViewById(R.id.notice_input_backib); backib.setOnClickListener(this);
        cameraIbtn=findViewById(R.id.notice_input_cameraIbtn); cameraIbtn.setOnClickListener(this);
        photoIbtn=findViewById(R.id.notice_input_photoIbtn); photoIbtn.setOnClickListener(this);
        deleteIbtn=findViewById(R.id.notice_input_deleteIbtn); deleteIbtn.setOnClickListener(this);
        completeBtn=findViewById(R.id.notice_input_completeBtn); completeBtn.setOnClickListener(this);

        titleEt=findViewById(R.id.notice_input_titleEt);
        contentEt=findViewById(R.id.notice_input_contentEt);
        titleLimitTv=findViewById(R.id.notice_input_titleLimitTv);
        contentLimitTv=findViewById(R.id.notice_input_contentLimitTv);
        pictureNumTv=findViewById(R.id.notice_input_pictureNumTv);
        pictureIv[0]=findViewById(R.id.notice_input_pictureIv1);
        pictureIv[1]=findViewById(R.id.notice_input_pictureIv2);
        pictureIv[2]=findViewById(R.id.notice_input_pictureIv3);
        pictureIv[3]=findViewById(R.id.notice_input_pictureIv4);
        pictureHL=findViewById(R.id.notice_input_pictureHL);

        baseDialog_okCenter = new BaseDialog_OkCenter(Notice_input.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(Notice_input.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        // '제목' 글자 수 제한
        titleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String TitlewordLimit = titleEt.getText().toString();
                titleLimitTv.setText(TitlewordLimit.length()+"/40");
                if(TitlewordLimit.length() > 40) {
                    titleLimitTv.setTextColor(getResources().getColor(R.color.red));
                } else {
                    titleLimitTv.setTextColor(getResources().getColor(R.color.black));
                }
                checkInputValidity();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // '내용' 글자 수 제한
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ContentwordLimit = contentEt.getText().toString();
                contentLimitTv.setText(ContentwordLimit.length()+"/80");
                if(ContentwordLimit.length() > 80) {
                    contentLimitTv.setTextColor(getResources().getColor(R.color.red));
                } else {
                    contentLimitTv.setTextColor(getResources().getColor(R.color.black));
                }
                checkInputValidity();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 카메라 권한 요청
        ActivityCompat.requestPermissions(this, new String[]
                {android.Manifest.permission.CAMERA}, MODE_PRIVATE);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.notice_input_backib: // 뒤로가기
                intent = new Intent(getApplicationContext(), Notice.class);
                startActivity(intent);
                finish();
                break;
            case R.id.notice_input_cameraIbtn: // 카메라
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQ_CODE_SELECT_CAMERA);
                break;
            case R.id.notice_input_photoIbtn: // 갤러리
                intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, REQ_CODE_GALLERY_IMAGE);
                break;
            case R.id.notice_input_deleteIbtn: // 삭제
                onPictureDelete();
                break;
            case R.id.notice_input_completeBtn: // 등록하기
                // 현재 날짜 가져오기
                noticeDate = LocalDate.now(); // 출퇴근일자, yyyy-mm-dd
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                noticeDateStr = noticeDate.format(formatter);

                //레트로핏 작동
                try {

                    //네트워크 요청 구현
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    NoticeApiService noticeApiService = retrofit.create(NoticeApiService.class);

                    // 사업장등록에 필요한 데이터를 StoreRequestDto 객체로 생성
                    NoticeRequestDto noticeRequestDto = new NoticeRequestDto(
                            titleEt.getText().toString().trim(), //공지글 제목
                            noticeDateStr, //공지글 작성 일자
                            contentEt.getText().toString().trim(), //공지글 내용
                            0, //공지 조회수,
                            null //사진 정보
                    );

                    Call<Void> call = noticeApiService.registerNotice(2L, noticeRequestDto); // staffId

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                showCompleteDl();
                                Toast.makeText(Notice_input.this, "성공", Toast.LENGTH_SHORT).show();
                                // 성공적인 응답 처리
                            } else {
                                // 기타 다른 상태 코드 처리
                                try {
                                    String errorResponse = response.errorBody().string();
                                    Toast.makeText(Notice_input.this, "공지사항 등록 실패!! 에러 메시지: " + errorResponse, Toast.LENGTH_SHORT).show();
                                    // 에러 메시지를 사용하여 추가적인 처리 수행
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // 실패 처리
                            String errorMessage = t != null ? t.getMessage() : "Unknown error";
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });


                }catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }

    // 완료'버튼 클릭 시
    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        join_okdl_commentTv.setText("공지사항 작성 완료하였습니다.");

        // '공지사항 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Notice.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_SELECT_CAMERA && resultCode == RESULT_OK) { // 카메라
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if (imageBitmap != null) {
                addBitmapToList(imageBitmap); // 비트맵을 리스트에 추가
            }
        }

        if (requestCode == REQ_CODE_GALLERY_IMAGE && resultCode == RESULT_OK) { // 갤러리
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    addBitmapToList(bm); // 비트맵을 리스트에 추가
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 리스트에 있는 이미지뷰를 차례로 채워주기
        for (int i = 0; i < bitmapList.size(); i++) {
            pictureIv[i].setImageBitmap(bitmapList.get(i));
            pictureIv[i].setClipToOutline(true);
        }

        // 이미지뷰가 4개 다 채워졌으면 버튼 비활성화
        if (currentImageView == 4) {
            cameraIbtn.setEnabled(false);
            photoIbtn.setEnabled(false);
        }
    }

    private void addBitmapToList(Bitmap imageBitmap) {
        // 이미지뷰에 이미지가 있는 경우 이어서 추가하는 경우
        if (currentImageView < 4) {
            pictureIv[currentImageView].setImageBitmap(imageBitmap);
            pictureIv[currentImageView].setClipToOutline(true);
            currentImageView++;
            numPictures++;
        }
        // 이미지뷰가 4개 모두 채워진 경우
        if (currentImageView == 4) {
            cameraIbtn.setEnabled(false);
            photoIbtn.setEnabled(false);
        }
        updatePictureNumText();
    }

    public void onPictureDelete() {
        bitmapList.clear();

        // pictureIv 배열의 원소 초기화하기
        for (int i = 0; i < pictureIv.length; i++) {
            pictureIv[i] = (ImageView) pictureHL.getChildAt(i);
            pictureIv[i].setImageBitmap(null);
        }

        numPictures = 0;
        updatePictureNumText();
        // currentImageView 초기화하기
        currentImageView = 0;

        // 버튼 활성화하기
        cameraIbtn.setEnabled(true);
        photoIbtn.setEnabled(true);
    }

    private void updatePictureNumText() {
        String text = numPictures + "/4";
        pictureNumTv.setText(text);
    }

    // '제목'과 '내용' 이 모두 채워지면 버튼 활성화
    private void checkInputValidity() {
        String title = titleEt.getText().toString();
        String content = contentEt.getText().toString();
        boolean isTitleValid = title.length() > 0 && title.length() <= 40;
        boolean isContentValid = content.length() > 0 && content.length() <= 80;

        if(isTitleValid && isContentValid){
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
        }
    }
}
