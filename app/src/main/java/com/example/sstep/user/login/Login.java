package com.example.sstep.user.login;

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
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.BaseDialog_Bottom;
import com.example.sstep.BaseDialog_OkCenter;
import com.example.sstep.R;
import com.example.sstep.document.certificate.Paper;
import com.example.sstep.store.SelectStore;
import com.example.sstep.todo.notice.Notice;
import com.example.sstep.user.join.JoinActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {

    String testId, testPassword;
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
        completeBtn = findViewById(R.id.logIn_completeBtn);

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
                intent = new Intent(getApplicationContext(), Find_password.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    //로그인 버튼
    public void onCompleteBtn() {
        //db구축시 서버에서 db받기
//                    ex)
//                    String rst = String.valueOf(new Task().execute(et.getText().toString().trim()).get());
//                    JSONObject json = new JSONObject(rst);
//                    String id = json.getString("id");
//                    String password = json.getString("password");
        testId="ididid";
        testPassword="00";

        TextView text = baseDialog_bottom.findViewById(R.id.dialog_okdown_commentTv);
        Button closeBtn = baseDialog_bottom.findViewById(R.id.dialog_okdown_okBtn);

        if(testPassword.equals(passwordEt.getText().toString())){

            //Intent intent = new Intent(getApplicationContext(), MainFage.class); //메인페이지로
            //startActivity(intent);
            // finish();
            showCompleteDl();

        }
        else if(testId.isEmpty()){
            baseDialog_bottom.show();
            text.setText("가입된 계정이 없습니다.\n" +"회원가입을 진행해 주세요");
        }
        else{
            baseDialog_bottom.show();
            text.setText("사용자 정보가 맞지 않습니다.");
        }
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseDialog_bottom.dismiss();
            }
        });
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
                startActivity(intent);
                finish();
            }
        });
    }
}