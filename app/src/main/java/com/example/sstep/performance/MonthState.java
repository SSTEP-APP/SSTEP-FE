package com.example.sstep.performance;

import android.os.Bundle;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sstep.R;

public class MonthState extends AppCompatActivity {

    private static final String TAG = "MonthState";
    private ViewPager viewpager;
    private MonthStateFragmentAdapter adapter;
    ScrollView scoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthstate);

        scoll = findViewById(R.id.monthstate_scoll);
        scoll.setFillViewport(true);
        viewpager = findViewById(R.id.monthstate_viewpager);
        adapter = new MonthStateFragmentAdapter(getSupportFragmentManager(),1);

        // AlarmFragmentAdapter 에 컬렉션 담기
        adapter.addFragment(new monthstate1Fragment());
        adapter.addFragment(new monthstate2Fragment());

        viewpager.setClipToPadding(false);
        viewpager.setPadding(0, 0, 80, 0);
        viewpager.setPageMargin(30);

        // ViewPager Fragment 연결
        viewpager.setAdapter(adapter);
    }
}