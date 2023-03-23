package com.example.sstep.circlemenu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sstep.R;
import com.example.sstep.SelectStore;
import com.example.sstep.StaffInvite2;
import com.example.sstep.alarm.Alarm;
import com.example.sstep.monthstate.MonthState;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class CircleMenu extends AppCompatActivity {

    PieChart circle;
    private ArrayList<PieEntry> datavalue;
    private PieDataSet pieDataSet;
    private PieData pieData;
    int[] colorArray = new int[] {Color.parseColor("#2375F2"), Color.parseColor("#DDDDDD")};
    Intent intent; float indexSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circlemenu);
        circle = findViewById(R.id.circle);
        setData();
        setUpData();

        changeIntent();
    }

    public void changeIntent() {
        circle.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                indexSelected =h.getX();
                switch ((int) indexSelected) {
                    case 0:
                        intent = new Intent(getApplicationContext(),StaffInvite2.class);
                        startActivity(intent);
//                        finish();
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(), SelectStore.class);
                        startActivity(intent);
//                        finish();
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), Alarm.class);
                        startActivity(intent);
//                        finish();
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(), MonthState.class);
                        startActivity(intent);
//                        finish();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    public void setData() {
        int [] sampledata = {20,20,20,20};
        Drawable[] iconsd = {getDrawable(R.drawable.yicon_bell),getDrawable(R.drawable.yicon_go),getDrawable(R.drawable.yicon_bell),
                getDrawable(R.drawable.yicon_bell)};
        datavalue = new ArrayList<>();

        for(int i = 0; i<sampledata.length;i++){
            datavalue.add(new PieEntry(sampledata[i], iconsd[i]));
        }
        pieDataSet = new PieDataSet(datavalue, "지각률");
        pieDataSet.setColors(colorArray);

        pieData = new PieData(pieDataSet);
    }

    public void setUpData() {
        circle.setData(pieData);
        circle.setDrawEntryLabels(true);
        circle.setUsePercentValues(false);
        circle.getDescription().setEnabled(false);
        circle.setCenterText("100%");
        circle.setCenterTextSize(20);
        circle.setTouchEnabled(true);
        pieDataSet.setDrawValues(false);

        // 범례 없애기
        Legend legend = circle.getLegend();
        legend.setEnabled(false);

        circle.invalidate();
    }
}