package com.example.sstep.start;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sstep.R;
import com.example.sstep.alarm.AlarmFragmentAdapter;
import com.example.sstep.alarm.alarm1Fragment;
import com.example.sstep.alarm.alarm2Fragment;
import com.example.sstep.alarm.alarm3Fragment;
import com.example.sstep.joinlogin.Login;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Start extends AppCompatActivity {


    private static final String TAG = "Start";
    ViewPager viewPager;
    TabLayout tabLayout;
    StartFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

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