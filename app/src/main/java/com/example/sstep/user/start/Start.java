package com.example.sstep.user.start;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.sstep.R;
import com.google.android.material.tabs.TabLayout;

public class Start extends AppCompatActivity {


    private static final String TAG = "Start";
    ViewPager viewPager;
    TabLayout tabLayout;
    StartFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        // 카메라 권한 요청
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 1);

        tabLayout = findViewById(R.id.start_tablayout);
        viewPager = findViewById(R.id.start_viewpager);
        adapter = new StartFragmentAdapter(getSupportFragmentManager(),1);

        // AlarmFragmentAdapter 에 컬렉션 담기
        adapter.addFragment(new Start1());
        adapter.addFragment(new Start2());
        adapter.addFragment(new Start3());
        adapter.addFragment(new Start4());

        // ViewPager Fragment 연결
        viewPager.setAdapter(adapter);

        // ViewPager TabLayout 연결
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0);
        tabLayout.getTabAt(1);
        tabLayout.getTabAt(2);
        tabLayout.getTabAt(3);


        ViewGroup tabs = (ViewGroup) tabLayout.getChildAt(0);

        for (int i = 0; i < tabs.getChildCount(); i++) {
            View tab = tabs.getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();
            layoutParams.weight = 0;
            layoutParams.setMarginEnd(15);
            layoutParams.setMarginStart(15);
            layoutParams.width = 13;
            tab.setLayoutParams(layoutParams);
            tabLayout.requestLayout();
        }
    }

}