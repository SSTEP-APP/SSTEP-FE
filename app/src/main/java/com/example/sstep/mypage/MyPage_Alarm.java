package com.example.sstep.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;

public class MyPage_Alarm extends AppCompatActivity {

    ImageButton backib;
    Switch allsw1,inoutsw2,workcalsw3,todosw4,notisw5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_alarm);
        backib = findViewById(R.id.mypage_alarm_backib);
        allsw1 = findViewById(R.id.mypage_alarm_allsw1); inoutsw2 = findViewById(R.id.mypage_alarm_inoutsw2);
        workcalsw3 = findViewById(R.id.mypage_alarm_workcalsw3); todosw4 = findViewById(R.id.mypage_alarm_todosw4);
        notisw5 = findViewById(R.id.mypage_alarm_notisw5);

        // '뒤로가기' 선택 시
        backib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
                finish();
            }
        });

        // Switch '푸시 끄기/켜기' 클릭 시
        allsw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (ischecked) {
                    inoutsw2.setChecked(true); workcalsw3.setChecked(true);
                    todosw4.setChecked(true); notisw5.setChecked(true);
                } else {
                    inoutsw2.setChecked(false); workcalsw3.setChecked(false);
                    todosw4.setChecked(false); notisw5.setChecked(false);
                }
            }
        });
    }
}