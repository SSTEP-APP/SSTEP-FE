package com.example.sstep.monthstate;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.sstep.R;
import com.example.sstep.alarm.AlarmFragmentAdapter;
import com.example.sstep.alarm.alarm1Fragment;
import com.example.sstep.alarm.alarm2Fragment;
import com.example.sstep.alarm.alarm3Fragment;
import com.example.sstep.mypage.MyPage;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

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
        adapter.addFragment(new monthstate3Fragment());

        viewpager.setClipToPadding(false);
        viewpager.setPadding(0, 0, 80, 0);
        viewpager.setPageMargin(30);

        // ViewPager Fragment 연결
        viewpager.setAdapter(adapter);
    }
}