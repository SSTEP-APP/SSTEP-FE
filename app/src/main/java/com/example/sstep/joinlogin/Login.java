package com.example.sstep.joinlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Dialog;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.BaseDialog_Bottom;
import com.example.sstep.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    String testId, testPassword;
    EditText idEt, passwordEt;
    BaseDialog_Bottom baseDialog_bottom;
    Button completeBtn, sighIn, searchId, searchPass;
    ImageButton logIn_kakaoBtn, logIn_naverBtn, logIn_googleBtn;
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
        testId="111";
        testPassword="111";

        TextView text = baseDialog_bottom.findViewById(R.id.dialog_okdown_commentTv);
        Button closeBtn = baseDialog_bottom.findViewById(R.id.dialog_okdown_okBtn);

        if(testPassword.equals(passwordEt.getText().toString())){

            //Intent intent = new Intent(getApplicationContext(), MainFage.class); //메인페이지로
            //startActivity(intent);
            // finish();
            Toast.makeText(getApplicationContext(),"로그인",Toast.LENGTH_SHORT).show();
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
}