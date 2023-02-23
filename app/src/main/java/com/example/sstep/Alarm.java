package com.example.sstep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.sstep.fragments.AlarmFragmentAdapter;
import com.example.sstep.fragments.alarm1Fragment;
import com.example.sstep.fragments.alarm2Fragment;
import com.example.sstep.fragments.alarm3Fragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Alarm extends AppCompatActivity {

    private static final String TAG = "Alarm";
    private TabLayout tabs_layout;
    private ViewPager viewpager;
    private AlarmFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

        tabs_layout = findViewById(R.id.tabs_layout);
        viewpager = findViewById(R.id.viewpager);
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
    }
}