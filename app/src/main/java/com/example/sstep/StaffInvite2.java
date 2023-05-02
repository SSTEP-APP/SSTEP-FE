package com.example.sstep;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class StaffInvite2 extends AppCompatActivity {

    ImageButton backib;
    EditText nameEt, numberEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffinvite2);
        backib = findViewById(R.id.staffInvite2_backib);
        nameEt = findViewById(R.id.staffInvite2_nameEt); numberEt = findViewById(R.id.staffInvite2_numberEt);

        // '뒤로가기' 선택 시
        backib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StaffInvite.class);
                startActivity(intent);
                finish();
            }
        });

        // 전화번호 입력시 자동 '-' 입력
        numberEt.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }
}