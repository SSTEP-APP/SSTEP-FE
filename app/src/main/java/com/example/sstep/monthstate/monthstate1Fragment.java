package com.example.sstep.monthstate;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.sstep.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class monthstate1Fragment extends Fragment {

    PieChart piechart1;
    private ArrayList<PieEntry> datavalue;
    private PieDataSet pieDataSet;
    private PieData pieData;
    int[] colorArray = new int[] {Color.parseColor("#2375F2"), Color.parseColor("#DDDDDD")};
    Dialog monstateDl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void monstateDl() {
        monstateDl.show();
    }
    public void setData() {
        datavalue = new ArrayList<>();
        datavalue.add(new PieEntry(30, "지각률"));
        datavalue.add(new PieEntry(70, "전체"));

        pieDataSet = new PieDataSet(datavalue, "지각률");
        pieDataSet.setColors(colorArray);

        pieData = new PieData(pieDataSet);
    }

    public void setUpData() {
        piechart1.setData(pieData);

        piechart1.setDrawEntryLabels(false);
        piechart1.setUsePercentValues(false);
        piechart1.getDescription().setEnabled(false);
        piechart1.setCenterText("100%");
        piechart1.setCenterTextSize(20);
        piechart1.animateY(2000, Easing.EaseInOutQuad);
        piechart1.setTouchEnabled(false);
        // 범례 없애기
        Legend legend = piechart1.getLegend();
        legend.setEnabled(false);

        piechart1.invalidate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.monthstate1_fragment, container, false);
        piechart1 = v.findViewById(R.id.monthstate1_fragment_piechart1);
        setData();
        setUpData();

        // list dialog 띄우기
        LinearLayout lateL = v.findViewById(R.id.monthstate1_fragment_lateL);
        monstateDl = new Dialog(v.getContext()); // Dialog 초기화
        monstateDl.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        monstateDl.setContentView(R.layout.monthstate_listdl); // xml 레이아웃 파일과 연결
        // 다이얼로그의 배경을 투명으로 만든다.
        monstateDl.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lateL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { monstateDl(); }
        });
        return v;
    }

}