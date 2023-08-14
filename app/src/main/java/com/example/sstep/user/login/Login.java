package com.example.sstep.user.login;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.BaseDialog_Bottom;
import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.store.SelectStore;
import com.example.sstep.user.join.JoinActivity;
import com.example.sstep.user.member.MemberApiService;
import com.example.sstep.user.member.MemberModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements View.OnClickListener {

    String checkPassword;
    EditText idEt, passwordEt;
    BaseDialog_Bottom baseDialog_bottom;
    Button completeBtn, sighIn, searchId, searchPass;
    ImageButton logIn_kakaoBtn, logIn_naverBtn, logIn_googleBtn;
    Dialog showComplete_dialog;
    BaseDialog_OkCenter baseDialog_okCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        idEt = findViewById(R.id.logIn_idEt);
        passwordEt = findViewById(R.id.logIn_passwordEt);

        baseDialog_bottom = new BaseDialog_Bottom(Login.this, R.layout.dialog_okdown);

        completeBtn = findViewById(R.id.logIn_completeBtn); completeBtn.setOnClickListener(this);
        sighIn = findViewById(R.id.logIn_sighIn); sighIn.setOnClickListener(this);
        searchId = findViewById(R.id.logIn_searchId); searchId.setOnClickListener(this);
        searchPass = findViewById(R.id.logIn_searchPass); searchPass.setOnClickListener(this);
        logIn_kakaoBtn=findViewById(R.id.logIn_kakaoBtn); logIn_kakaoBtn.setOnClickListener(this);
        logIn_naverBtn=findViewById(R.id.logIn_naverBtn); logIn_naverBtn.setOnClickListener(this);
        logIn_googleBtn=findViewById(R.id.logIn_googleBtn); logIn_googleBtn.setOnClickListener(this);

        baseDialog_okCenter = new BaseDialog_OkCenter(Login.this, R.layout.join_okdl);

        showComplete_dialog = new Dialog(Login.this);
        showComplete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        showComplete_dialog.setContentView(R.layout.join_okdl); // xml 레이아웃 파일과 연결


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.logIn_completeBtn: //로그인 버튼
                onCompleteBtn();
                break;
            case R.id.logIn_sighIn: //회원가입
                intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.logIn_searchId: //아이디찾기
                intent = new Intent(getApplicationContext(), Find_id.class);
                startActivity(intent);
                finish();
                break;
            case R.id.logIn_searchPass: //패스워드 찾기
                intent = new Intent(getApplicationContext(), Login_test.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    //로그인 버튼
    public void onCompleteBtn() {

        try {

            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MemberApiService apiService = retrofit.create(MemberApiService.class);
            //적은 id를 기반으로 db에 검색
            Call<MemberModel> call = apiService.getDataFromServer(idEt.getText().toString().trim());
            call.enqueue(new Callback<MemberModel>() {
                @Override
                public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                    if (response.isSuccessful()) {
                        MemberModel data = response.body();
                        // 적은 id로 패스워드 데이터 가져오기
                        checkPassword =data.getPassword(); // id에 id 설정
                        if(checkPassword.equals(passwordEt.getText().toString())){

                            //Intent intent = new Intent(getApplicationContext(), MainFage.class); //메인페이지로
                            //startActivity(intent);
                            // finish();
                            showCompleteDl();
                        }
                        else {
                            TextView text = baseDialog_bottom.findViewById(R.id.dialog_okdown_commentTv);
                            Button closeBtn = baseDialog_bottom.findViewById(R.id.dialog_okdown_okBtn);
                            text.setText("비밀번호가 틀렸습니다.");
                            baseDialog_bottom.show();
                            closeBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    baseDialog_bottom.dismiss();
                                }
                            });

                        }
                    } else {
                        // 오류 처리
                        int statusCode = response.code();
                        String errorMessage;
                        /*
                        if (statusCode == 404) {
                            errorMessage = "아이디가 없습니다. 회원가입하세요.";
                        } else if (statusCode == 500) {
                            errorMessage = "서버 내부 오류가 발생했습니다.";
                        } else {
                            errorMessage = "오류가 발생했습니다. 상태 코드: " + statusCode;
                        }
                        */
                        errorMessage = "아이디 또는 비밀번호가 틀렸습니다.";
                        TextView text = baseDialog_bottom.findViewById(R.id.dialog_okdown_commentTv);
                        Button closeBtn = baseDialog_bottom.findViewById(R.id.dialog_okdown_okBtn);
                        baseDialog_bottom.show();
                        text.setText(errorMessage);
                        closeBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                baseDialog_bottom.dismiss();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<MemberModel> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = "요청 실패: " + t.getMessage();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showCompleteDl(){
        showComplete_dialog.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        showComplete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView join_okdl_commentTv; Button join_okdl_okBtn;
        join_okdl_commentTv = showComplete_dialog.findViewById(R.id.join_okdl_commentTv);
        join_okdl_okBtn = showComplete_dialog.findViewById(R.id.join_okdl_okBtn);
        join_okdl_commentTv.setText("로그인 되었습니다.");
        // '로그인 dialog' _ 확인 버튼 클릭 시
        join_okdl_okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectStore.class);
                intent.putExtra("userId", idEt.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}