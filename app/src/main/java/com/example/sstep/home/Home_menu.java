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
    String userName, storeName;

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
        userName = appInData.getUserName();
        storeName = appInData.getStoreName();

        storeNameTv.setText(storeName);
        nameTv.setText(userName);
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