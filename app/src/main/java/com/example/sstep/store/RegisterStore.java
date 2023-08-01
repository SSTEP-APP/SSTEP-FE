package com.example.sstep.store;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.document.contract.EditTextValidator;
import com.example.sstep.store.store_api.NullOnEmptyConverterFactory;
import com.example.sstep.store.store_api.StoreApiService;
import com.example.sstep.store.store_api.StoreRequestDto;
import com.example.sstep.store.store_api.StoreResponseDto;
import com.example.sstep.user.login.Login;
import com.example.sstep.user.member.MemberResponseDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterStore extends AppCompatActivity {
    ImageButton backBtn;
    EditText nameEt, addressEt, detailEt;
    Button addressBtn, completeBtn;
    RadioGroup scaleRadioGroup, planRadioGroup;
    RadioButton under5, over5, pay, free;
    Boolean ispaydayBtn=false;
    BaseDialog_OkCenter baseDialog_okCenter;
    Dialog showComplete_dialog;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_store);
        backBtn = findViewById(R.id.regiStore_back_Btn);
        nameEt = findViewById(R.id.regiStore_nameEt);
        addressEt = findViewById(R.id.regiStore_addressEt);
        detailEt = findViewById(R.id.regiStore_detailEt);
        addressBtn = findViewById(R.id.regiStore_addressbtn);
        completeBtn = findViewById(R.id.regiStore_completeBtn);
        scaleRadioGroup = findViewById(R.id.regiStore_scaleRadioGroup);
        planRadioGroup = findViewById(R.id.regiStore_planRadioGroup);
        under5 = findViewById(R.id.regiStore_under5);
        over5 = findViewById(R.id.regiStore_over5);
        pay = findViewById(R.id.regiStore_pay);
        free = findViewById(R.id.regiStore_free);
        EditText edit_addr;

        baseDialog_okCenter = new BaseDialog_OkCenter(RegisterStore.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(RegisterStore.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결

        setupEditTextListeners();
        checkCompleteBtnState();

        //주소등록
        addressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("주소설정페이지", "주소입력창 클릭");


                Log.i("주소설정페이지", "주소입력창 클릭");
                Intent i = new Intent(getApplicationContext(), WebViewActivity.class);
                // 화면전환 애니메이션 없애기
                overridePendingTransition(0, 0);
                // 주소결과
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);

            }

        });



        //사업장 규모 라디오 버튼
        scaleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.regiStore_under5:
                        free.setEnabled(true);  //버튼 활성화
                        pay.setEnabled(true);  //버튼 활성화
                        break;
                    case R.id.regiStore_over5:
                        pay.setChecked(true);   //유료로 선택
                        free.setEnabled(false); //버튼 비활성화
                        break;
                }
            }
        });

        //뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectStore.class);
                startActivity(intent);
                finish();
            }
        });

        // '등록 완료'버튼
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComplete_dialog.show();
                // 다이얼로그의 배경을 투명으로 만든다.
                showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView join_okdl_commentTv; Button join_okdl_okBtn;
                join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
                join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
                join_okdl_commentTv.setText("사업장 등록이 완료되었습니다.");
                //레트로핏 작동
                try {

                    //네트워크 요청 구현
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    StoreApiService apiService = retrofit.create(StoreApiService.class);
                    // 사업장등록에 필요한 데이터를 StoreRequestDto 객체로 생성
                    StoreRequestDto storeRequestDto = new StoreRequestDto(
                            nameEt.getText().toString().trim(),
                            addressEt.getText().toString().trim()+detailEt.getText().toString().trim(),
                            "latitude",
                            "longitude",
                            true,
                            true,
                            "payday",
                            12345
                    );

// 회원가입 요청을 서버에 전송
                    Call<StoreResponseDto> call = apiService.saveStore(storeRequestDto);
                    call.enqueue(new Callback<StoreResponseDto>() {
                        @Override
                        public void onResponse(Call<StoreResponseDto> call, Response<StoreResponseDto> response) {
                            if (response.isSuccessful()) {
                                // 사업장드록 성공
                                StoreResponseDto storeResponseDto = response.body();
                                join_okdl_commentTv.setText("사업장등록이 완료되었습니다.\n 로그인해 주세요.");
                                // 응답을 필요에 따라 처리하세요.
                            } else {
                                // 사업장 등록 실패
                                int statusCode = response.code();
                                join_okdl_commentTv.setText("사업장등록이 실패했습니다.\n 오류코드: " + statusCode);
                            }
                        }

                        @Override
                        public void onFailure(Call<StoreResponseDto> call, Throwable t) {
                            // 네트워크 오류나 기타 이유로 등록 실패
                            String errorMessage = t != null ? t.getMessage() : "Unknown error";
                            if (errorMessage.equals("End of input at line 1 column 1 path $")){
                                //join_okdl_commentTv.setText("사업장등록이 완료되었습니다.\n 로그인해 주세요.");

                            }else {
                                //join_okdl_commentTv.setText("사업장등록이 실패했습니다!!\n 오류메시지: " + errorMessage);
                                t.printStackTrace();
                            }
                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }
                // 'dialog' _ 확인 버튼 클릭 시
                join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), SelectStore.class);
                        startActivity(intent);
                        finish();
                    }
                });
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

        EditTextValidator storeNameValidator = new EditTextValidator(nameEt, validationListener);
        nameEt.addTextChangedListener(storeNameValidator);

        EditTextValidator storeAddressValidator = new EditTextValidator(addressEt, validationListener);
        addressEt.addTextChangedListener(storeAddressValidator);

        EditTextValidator storePhoneValidator = new EditTextValidator(detailEt, validationListener);
        detailEt.addTextChangedListener(storePhoneValidator);
    }

    // 계약서 작성완료 버튼' 활성화/비활성화
    public void checkCompleteBtnState() {
        boolean isnameEt = !nameEt.getText().toString().trim().isEmpty();
        boolean isaddressEt = !addressEt.getText().toString().trim().isEmpty();
        boolean isdetailEt = !detailEt.getText().toString().trim().isEmpty();

        if(isnameEt && isaddressEt && isdetailEt){
            completeBtn.setEnabled(true);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnon);
        }else{
            completeBtn.setEnabled(false);
            completeBtn.setBackgroundResource(R.drawable.yroundrec_bottombtnoff);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.i("test", "onActivityResult");

        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        Log.i("test", "data:" + data);
                        addressEt.setText(data);
                    }
                }
                break;
        }
    }
}