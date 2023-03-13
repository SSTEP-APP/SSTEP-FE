package com.example.sstep;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class StaffInvite2 extends AppCompatActivity {

    ImageButton backib;
    EditText nameet1, numberet2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffinvite2);
        backib = findViewById(R.id.staffInvite2_backib);
        nameet1 = findViewById(R.id.staffInvite2_nameet1); numberet2 = findViewById(R.id.staffInvite2_numberet2);

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
        numberet2.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }
}