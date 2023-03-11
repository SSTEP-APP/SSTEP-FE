package com.example.sstep.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.sstep.R;

import java.util.ArrayList;

public class Start extends AppCompatActivity {

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pager = findViewById(R.id.start_pagers);
        pager.setOffscreenPageLimit(4); // 페이지 개수

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());

        // 프래그먼트를 어댑터의 아이템으로 추가
        Start1 fragment1 = new Start1();
        adapter.addItem(fragment1);

        Start2 fragment2 = new Start2();
        adapter.addItem(fragment2);

        Start3 fragment3 = new Start3();
        adapter.addItem(fragment3);

        Start4 fragment4 = new Start4();
        adapter.addItem(fragment4);

        // 어댑터 객체를 페이저쪽에 등록해야 화면에 보임
        pager.setAdapter(adapter);

    }


    // Adapter : 프래그먼트를 관리하는 기능을 함
    class MyPagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment item) {
            items.add(item);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }
}