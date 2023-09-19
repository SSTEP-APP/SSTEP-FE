package com.example.sstep.document.contract;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sstep.AppInData;
import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.CalendarDialog;
import com.example.sstep.CustomTextWatcher_Comma;
import com.example.sstep.R;
import com.example.sstep.document.certificate.Paper;
import com.example.sstep.document.certificate.PaperHview;
import com.example.sstep.document.healthdoc_api.HealthDocApiService;
import com.example.sstep.document.healthdoc_api.HealthDocResponseDto;
import com.example.sstep.document.work_doc_api.ByteArrayTypeAdapter;
import com.example.sstep.document.work_doc_api.PhotoApiService;
import com.example.sstep.document.work_doc_api.PhotoResponseDto;
import com.example.sstep.document.work_doc_api.WorkDocApiService;
import com.example.sstep.document.work_doc_api.WorkDocRequestDto;
import com.example.sstep.document.work_doc_api.WorkDocResponseDto;
import com.example.sstep.user.member.NullOnEmptyConverterFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaperWsecondInput extends AppCompatActivity implements View.OnClickListener{

    ScrollView scrollView;
    CustomDrawingView customDrawingView;
    PwiStartTimeAdapterSpinner startTimeAdapter;
    PwiEndTimeAdapterSpinner endTimeAdapter;
    com.example.sstep.document.contract.PwiWorkdayAdapterSpinner PwiWorkdayAdapterSpinner;
    String[] startTimeSpDatas, endTimeSpDatas, workTerms_workDaySpDatas;
    EditText workTerms_workPlaceEt,workTerms_workContentEt,wageEt,
            storeNameEt,storeAddressEt,storePhoneEt;
    TextView workTerms_workPlaceLimitTv, workTerms_workContentLimitTv, restdaySelectTv,
            restdayTv1,restdayTv2, workTerms_workstartDateTv,writeDayTv;
    ImageView workTerms_updownIv,wage_updownIv;
    LinearLayout workTerms_hidL,wage_hidL;
    ImageButton backib, workTerms_workstartDateIb, storeCeoSignRefreshIb;
    Button completeBtn;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;

    File file;

    long staffId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paper_wsecond_input);
        scrollView=findViewById(R.id.paperwinputsecond_scroll);
        storeCeoSignRefreshIb = findViewById(R.id.paperwinputsecond_storeInfo_storeCeoSignRefreshIb);
        customDrawingView=findViewById(R.id.customDrawingView);

        storeNameEt=findViewById(R.id.paperwinputsecond_storeInfo_storeNameEt);
        storeAddressEt=findViewById(R.id.paperwinputsecond_storeInfo_storeAddressEt);
        storePhoneEt=findViewById(R.id.paperwinputsecond_storeInfo_storePhoneEt);
        backib=findViewById(R.id.paperwinputsecond_backib); backib.setOnClickListener(this);
        completeBtn=findViewById(R.id.paperwinputsecond_completeBtn); completeBtn.setOnClickListener(this);


        setupEditTextListeners();
        checkCompleteBtnState();

        baseDialog_okCenter = new BaseDialog_OkCenter(PaperWsecondInput.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(PaperWsecondInput.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결


        //리스트에서 보낸 staffId받기
        Intent intent = getIntent();
        staffId = intent.getLongExtra("staffId", 0);
        // 전화번호 입력시 자동 '-' 입력
        storePhoneEt.addTextChangedListener(new PhoneNumberFormattingTextWatcher());


        // 서명 새로고침 버튼
        storeCeoSignRefreshIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDrawingView.clearCanvas();
                checkCompleteBtnState();
            }
        });

        // 서명할때는 스크롤 방지
        customDrawingView.setParentScrollView(scrollView);

        scrollView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                customDrawingView.getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                customDrawingView.getParent().requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });

        // 그림 변경될때마다 '계약서 작성완료 버튼' 상태 업데이트
        customDrawingView.setOnDrawingChangeListener(new CustomDrawingView.OnDrawingChangeListener() {
            @Override
            public void onDrawingChanged(boolean hasDrawing) {
                checkCompleteBtnState();
            }
        });


        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY) // 대소문자 구분
                .registerTypeAdapter(byte[].class, new ByteArrayTypeAdapter())
                .create();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                    WorkDocApiService apiService = retrofit.create(WorkDocApiService.class);

                    Call<PhotoResponseDto> call = apiService.getFirstWorkDoc(staffId);
                    retrofit2.Response<PhotoResponseDto> response = call.execute();

                    if (response.isSuccessful()) {
                        final PhotoResponseDto codeStaffs = response.body();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // responseBody를 바이트 배열로 변환
                                    // photoDto에서 data 가져오기

                                    if (codeStaffs != null) {
                                        byte[] imageData = codeStaffs.getData();
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                        ImageView photo = findViewById(R.id.paperwinputsecond_firstIv);
                                        photo.setImageBitmap(bitmap);
                                    } else {
                                        Toast.makeText(PaperWsecondInput.this, "null", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (NullPointerException e) {
                                    // 널 포인터 예외 처리
                                    // 예외 발생 시 오류 메시지 출력 또는 다른 예외 처리 작업 수행
                                    e.printStackTrace();
                                    // 또는 기본 이미지 설정
                                    Toast.makeText(PaperWsecondInput.this, "널포인터"+e, Toast.LENGTH_SHORT).show();
                                    // 예외 로그 출력
                                    Log.e("MyApp", "NullPointerException 발생", e);
                                }
                            }
                        });
                    } else {
                        // 서버 응답 코드 확인
                        int responseCode = response.code();
                        // 서버 응답 데이터 확인
                        String responseData = response.errorBody().string();
                        // 에러 메시지 출력 또는 처리
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                handleError("서버 응답 오류 - 코드: " + responseCode + ", 데이터: " + responseData);
                            }
                        });
                        // 예외 로그 출력
                        Log.e("MyApp", "서버 응답 오류 - 코드: " + responseCode + ", 데이터: " + responseData);
                    }
                } catch (Exception e) {
                    final String errorMsg = e.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handleError(errorMsg);
                        }
                    });
                    // 예외 로그 출력
                    Log.e("MyApp", "예외 발생", e);
                }
            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.paperwinputsecond_backib: // 뒤로가기
                intent = new Intent(getApplicationContext(), PaperW.class);
                startActivity(intent);
                finish();
                break;

            case R.id.paperwinputsecond_completeBtn:
                showCompleteDl();
                break;
            default:
                break;
        }
    }

    // '계약서 작성완료'버튼 클릭 시
    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);

        join_okdl_commentTv.setText("로딩중");

        try {
            PhotoTask photoTask = new PhotoTask();

            long photoId = photoTask.execute().get();
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WorkDocRequestDto workDocRequestDto = new WorkDocRequestDto(photoId);

            WorkDocApiService apiService = retrofit.create(WorkDocApiService.class);

            Call<Void> call = apiService.registerWorkDocSecond(staffId, workDocRequestDto);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        join_okdl_commentTv.setText("작성에 성공했습니다.");
                    }else {
                        join_okdl_commentTv.setText("작성 실패"+response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
        // '회원가입 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Paper.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void setupEditTextListeners() {
        EditTextValidator.ValidationListener validationListener = new EditTextValidator.ValidationListener() {
            @Override
            public void onValidationResult(boolean hasContent) {
                checkCompleteBtnState();
            }
        };

        EditTextValidator storeNameValidator = new EditTextValidator(storeNameEt, validationListener);
        storeNameEt.addTextChangedListener(storeNameValidator);

        EditTextValidator storeAddressValidator = new EditTextValidator(storeAddressEt, validationListener);
        storeAddressEt.addTextChangedListener(storeAddressValidator);

        EditTextValidator storePhoneValidator = new EditTextValidator(storePhoneEt, validationListener);
        storePhoneEt.addTextChangedListener(storePhoneValidator);

    }

    // 계약서 작성완료 버튼' 활성화/비활성화
    public void checkCompleteBtnState() {
        boolean isStoreNameEt = !storeNameEt.getText().toString().trim().isEmpty();
        boolean isStoreAddressEt = !storeAddressEt.getText().toString().trim().isEmpty();
        boolean isStorePhoneEt = !storePhoneEt.getText().toString().trim().isEmpty();

        if(isStoreNameEt && isStoreAddressEt && isStorePhoneEt &&
                customDrawingView.isCeoSign() /*&& isworkDaySp && isprivacyCb*/){
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
        }
    }

    private class PhotoTask extends AsyncTask<Void, Void, Long> {
        @Override
        protected Long doInBackground(Void... params) {

            scrollView = findViewById(R.id.paperwinputsecond_scroll);
            int totalHeight = scrollView.getChildAt(0).getHeight();
            int totalWidth = scrollView.getChildAt(0).getWidth();
            Bitmap bitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            scrollView.draw(canvas);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); // Bitmap을 JPEG 바이트 배열로 압축
            byte[] byteArray = stream.toByteArray();
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", "paperWsecond.jpeg", requestFile); // 파일 이름을 "19hdoc.jpeg"로 변경

            // 네트워크 요청을 백그라운드 스레드에서 수행
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PhotoApiService apiService2 = retrofit.create(PhotoApiService.class);
            Call<Long> call2 = apiService2.savePhoto(body);

            try {
                Response<Long> response = call2.execute();

                if (response.isSuccessful()) {
                    return response.body(); // 성공한 경우 이미지 ID 반환
                } else {

                    return response.body();

                }
            } catch (IOException e) {
                e.printStackTrace();
                return -1L; // 예외 발생한 경우 -1 반환 또는 다른 실패 코드 반환
            }
        }

        @Override
        protected void onPostExecute(Long result) {
            showComplete_dialog.show();
            // 다이얼로그의 배경을 투명으로 만든다.
            showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView join_okdl_commentTv;
            Button join_okdl_okBtn;
            join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
            join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
            join_okdl_commentTv.setText("로딩중");

            if (result != null) {
                if (result != -1L) {
                    String message = "사진 저장을 성공했습니다.";
                    join_okdl_commentTv.setText(message);
                    // 이미지 ID를 사용하여 이미지를 가져오거나 다른 작업 수행

                } else {
                    String errorMessage = "보건증 작성이 실패했습니다!";
                    join_okdl_commentTv.setText(errorMessage);

                    // Toast로 실패 메시지 표시
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            } else {
                String nullMessage = "서버 응답이 null입니다.";
                join_okdl_commentTv.setText(nullMessage);

                // Toast로 null 메시지 표시
                Toast.makeText(getApplicationContext(), nullMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void handleError(String errorMsg) {
        Toast.makeText(this, errorMsg + "!!", Toast.LENGTH_SHORT).show();

    }
}