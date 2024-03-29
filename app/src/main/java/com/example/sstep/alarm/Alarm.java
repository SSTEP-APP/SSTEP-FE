package com.example.sstep.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sstep.R;
import com.example.sstep.home.Home_Ceo;
import com.google.android.material.tabs.TabLayout;

public class Alarm extends AppCompatActivity {

    private static final String TAG = "Alarm";
    private TabLayout tabs_layout;
    private ViewPager viewpager;
    private AlarmFragmentAdapter adapter;
    ImageButton backib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

        backib=findViewById(R.id.alarm_backib);

        tabs_layout = findViewById(R.id.alarm_tabs_layout);
        viewpager = findViewById(R.id.alarm_viewpager);
        adapter = new AlarmFragmentAdapter(getSupportFragmentManager(),1);

        // AlarmFragmentAdapter 에 컬렉션 담기
        adapter.addFragment(new alarm1Fragment());
        adapter.addFragment(new alarm2Fragment());
        adapter.addFragment(new alarm3Fragment());

        // ViewPager Fragment 연결
        viewpager.setAdapter(adapter);

        // ViewPager TabLayout 연결
        tabs_layout.setupWithViewPager(viewpager);

        tabs_layout.getTabAt(0).setText("전체");
        tabs_layout.getTabAt(1).setText("해야할 일");
        tabs_layout.getTabAt(2).setText("공지사항");

        backib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home_Ceo.class);
                startActivity(intent);
                finish();
            }
        });
    }
}