package com.example.sstep.joinlogin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class Login extends AppCompatActivity {
    String testId, testPassword;
    EditText idEt, passwordEt;
    Button completeBtn;
    Dialog textDialog;
    TextView sighIn, searchId, searchPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        idEt = findViewById(R.id.logIn_idEt);
        passwordEt = findViewById(R.id.logIn_passwordEt);
        completeBtn = findViewById(R.id.logIn_completeBtn);
        sighIn = findViewById(R.id.logIn_sighIn);
        searchId = findViewById(R.id.logIn_searchId);
        searchPass = findViewById(R.id.logIn_searchPass);
        textDialog = new Dialog(Login.this);
        textDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        textDialog.setContentView(R.layout.dialog_text_test);// xml 레이아웃 파일과 연결

        //db구축시 서버에서 db받기
//                    ex)
//                    String rst = String.valueOf(new Task().execute(et.getText().toString().trim()).get());
//                    JSONObject json = new JSONObject(rst);
//                    String id = json.getString("id");
//                    String password = json.getString("password");
        testId="111";
        testPassword="111";

        //회원가입
        sighIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class); //회원가입으로
                startActivity(intent);
                finish();
            }
        });

        //아이디찾기
        searchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Find_id.class); //아이디 찾기로
                startActivity(intent);
                finish();
            }
        });

        //패스워드 찾기
        searchPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Find_password.class); //패스워드 찾기로
                    startActivity(intent);
                    finish();
            }
        });





        //로그인 버튼
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(testPassword.equals(passwordEt.getText().toString())){

                    //Intent intent = new Intent(getApplicationContext(), MainFage.class); //메인페이지로
                    //startActivity(intent);
                    // finish();
                    Toast.makeText(getApplicationContext(),"로그인",Toast.LENGTH_SHORT).show();
                }
                else if(testId.isEmpty()){
                    textDialog.show();
                    TextView text = textDialog.findViewById(R.id.dialog_text_textView);
                    text.setText("가입된 계정이 없습니다.\n" +"회원가입을 진행해 주세요");
                    Button closeBtn = textDialog.findViewById(R.id.dialog_text_cancleBtn);
                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            textDialog.dismiss();
                        }
                    });

                }
                else{
                    textDialog.show();
                    TextView text = textDialog.findViewById(R.id.dialog_text_textView);
                    text.setText("사용자 정보가 맞지 않습니다.");
                    Button closeBtn = textDialog.findViewById(R.id.dialog_text_cancleBtn);
                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            textDialog.dismiss();
                        }
                    });
                }

            }
        });


    }
}