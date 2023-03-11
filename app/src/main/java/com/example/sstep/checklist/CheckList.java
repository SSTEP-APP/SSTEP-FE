package com.example.sstep.checklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.sstep.R;
import com.example.sstep.alarm.AlarmFragmentAdapter;
import com.example.sstep.alarm.alarm1Fragment;
import com.example.sstep.alarm.alarm2Fragment;
import com.example.sstep.alarm.alarm3Fragment;
import com.google.android.material.tabs.TabLayout;

public class CheckList extends AppCompatActivity {

    private static final String TAG = "CheckList";
    private TabLayout tabs_layout;
    private ViewPager viewpager;
    private CheckListFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist);

        tabs_layout = findViewById(R.id.checkList_tabs_layout);
        viewpager = findViewById(R.id.checkList_viewpager);
        adapter = new CheckListFragmentAdapter(getSupportFragmentManager(),1);

        // AlarmFragmentAdapter 에 컬렉션 담기
        adapter.addFragment(new checkList1Fragment());
        adapter.addFragment(new checkList2Fragment());

        // ViewPager Fragment 연결
        viewpager.setAdapter(adapter);

        // ViewPager TabLayout 연결
        tabs_layout.setupWithViewPager(viewpager);

        tabs_layout.getTabAt(0).setText("미완료");
        tabs_layout.getTabAt(1).setText("완료");
    }
}