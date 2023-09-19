package com.example.sstep.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.AppInData;
import com.example.sstep.R;
import com.example.sstep.document.certificate.Paper;
import com.example.sstep.store.store_api.StoreApiService;
import com.example.sstep.store.store_api.StoreResponseDto;
import com.example.sstep.todo.checklist.CheckList;
import com.example.sstep.todo.notice.Notice;
import com.example.sstep.user.login.Login;
import com.example.sstep.user.member.MemberApiService;
import com.example.sstep.user.member.MemberResponseDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home_menu extends AppCompatActivity implements View.OnClickListener {

    ImageButton closeIb;
    TextView storeNameTv, nameTv, paperTv, noticeTv, checklistTv, logoutTv;
    Dialog logoutdl;
    AppInData appInData;
    String userId, userName, storeName;
    long storeCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_menu);

        closeIb=findViewById(R.id.homemenu_closeIb); closeIb.setOnClickListener(this);
        paperTv=findViewById(R.id.homemenu_paperTv); paperTv.setOnClickListener(this);
        noticeTv=findViewById(R.id.homemenu_noticeTv); noticeTv.setOnClickListener(this);
        checklistTv=findViewById(R.id.homemenu_checklistTv); checklistTv.setOnClickListener(this);
        logoutTv=findViewById(R.id.homemenu_logoutTv); logoutTv.setOnClickListener(this);
        storeNameTv=findViewById(R.id.homemenu_storeNameTv);
        nameTv=findViewById(R.id.homemenu_nameTv);

        logoutdl = new Dialog(Home_menu.this); // Dialog 초기화
        logoutdl.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        logoutdl.setContentView(R.layout.mypage_logoutdl); // xml 레이아웃 파일과 연결

        // ID값 가지고 오기
        appInData = (AppInData) getApplication(); // MyApplication 클래스의 인스턴스 가져오기
        userId = appInData.getUserId();
        storeCode = appInData.getStoreCode();

        // userId 를 통해 name 가져오기
        try {
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MemberApiService apiService = retrofit.create(MemberApiService.class);
            //적은 id를 기반으로 db에 검색
            Call<MemberResponseDto> call = apiService.getMemberByUsername(userId); //username 아이디
            call.enqueue(new Callback<MemberResponseDto>() {
                @Override
                public void onResponse(Call<MemberResponseDto> call, Response<MemberResponseDto> response) {
                    if (response.isSuccessful()) {
                        MemberResponseDto data = response.body();
                        // 적은 id로 패스워드 데이터 가져오기
                        userName =data.getName(); // id에 id 설정
                        nameTv.setText(userName);
                    } else {
                        // 오류 처리
                    }
                }

                @Override
                public void onFailure(Call<MemberResponseDto> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = "요청 실패: " + t.getMessage();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

        // storecode 를 통해 storeName 가져오기
        try {
            //네트워크 요청 구현
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            StoreApiService apiService = retrofit.create(StoreApiService.class);
            //적은 id를 기반으로 db에 검색
            Call<StoreResponseDto> call = apiService.getStore(storeCode); //username 아이디
            call.enqueue(new Callback<StoreResponseDto>() {
                @Override
                public void onResponse(Call<StoreResponseDto> call, Response<StoreResponseDto> response) {
                    if (response.isSuccessful()) {
                        StoreResponseDto data = response.body();
                        // 적은 id로 패스워드 데이터 가져오기
                        storeName =data.getName(); // id에 id 설정
                        storeNameTv.setText(storeName);
                    } else {
                        // 오류 처리
                    }
                }

                @Override
                public void onFailure(Call<StoreResponseDto> call, Throwable t) {
                    // 실패 처리
                    String errorMessage = "요청 실패: " + t.getMessage();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.homemenu_closeIb: // 닫기 버튼
                intent = new Intent(Home_menu.this, Home_Ceo.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homemenu_checklistTv: // 해야할 일
                intent = new Intent(getApplicationContext(), CheckList.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homemenu_noticeTv: // 공지사항
                intent = new Intent(getApplicationContext(), Notice.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homemenu_paperTv: // 문서 관리
                intent = new Intent(getApplicationContext(), Paper.class);
                startActivity(intent);
                finish();
                break;
            case R.id.homemenu_logoutTv: // 로그아웃
                showlogoutDl();
                break;
            default:
                break;
        }
    }

    public void showlogoutDl() {
        logoutdl.show();
        // 다이얼로그의 배경을 투명으로 만든다.
        logoutdl.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button logoutdl_cancleBtn,logoutdl_logoutBtn;
        logoutdl_cancleBtn = logoutdl.findViewById(R.id.mypage_logoutdl_cancleBtn); logoutdl_logoutBtn = logoutdl.findViewById(R.id.mypage_logoutdl_logoutBtn);
        // '로그아웃 dialog' _ 취소 버튼 클릭 시
        logoutdl_cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutdl.dismiss();
            }
        });
        // '로그아웃 dialog' _ 로그아웃 버튼 클릭 시
        logoutdl_logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}