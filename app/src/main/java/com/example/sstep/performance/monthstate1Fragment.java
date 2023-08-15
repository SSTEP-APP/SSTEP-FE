package com.example.sstep.performance;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.alarm.Alarm1_RecyclerViewAdpater;
import com.example.sstep.alarm.Alarm1_recyclerViewWordItemData;
import com.example.sstep.todo.checklist.CheckList1_RecyclerViewAdpater;
import com.example.sstep.todo.checklist.CheckList1_recyclerViewWordItemData;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class monthstate1Fragment extends Fragment {

    private MonthState1_RecyclerViewAdpater mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<MonthState1_recyclerViewWordItemData> list = new ArrayList<>();
    private LinearLayout dataLayout;
    private TextView nodataTv;


    // 다이얼로그


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
            public void onClick(View view) {
                // RecyclerView를 위한 어댑터 및 데이터 설정
                ArrayList<MonthState_Dialog_recyclerViewWordItemData> dialogList = MonthState_Dialog_recyclerViewWordItemData.createContactsList(10);
                MonthState_Dialog_RecyclerViewAdpater dialogAdapter = new MonthState_Dialog_RecyclerViewAdpater(getActivity(), dialogList);

                // 다이얼로그 내부의 RecyclerView 설정
                RecyclerView dialogRecyclerView = monstateDl.findViewById(R.id.monthstate_listdl_recycleView);
                dialogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                dialogRecyclerView.setAdapter(dialogAdapter);

                // 리사이클러뷰 높이 제한 설정
                ViewGroup.LayoutParams layoutParams = dialogRecyclerView.getLayoutParams();
                layoutParams.height = (int) getResources().getDimension(R.dimen.max_recyclerview_height); // 이 값을 300dp에 해당하는 값으로 변경해야 합니다.
                dialogRecyclerView.setLayoutParams(layoutParams);

                // 다이얼로그 보이기
                monstateDl.show();
            }
        });

        // 리사이클 뷰
        list = MonthState1_recyclerViewWordItemData.createContactsList(3);// 리스트 갯수
        mRecyclerView = (RecyclerView) v.findViewById(R.id.monthstate1_fragment_recycleView);
        nodataTv = (TextView) v.findViewById(R.id.monthstate1_fragment_nodataTv);
        dataLayout = (LinearLayout)  v.findViewById(R.id.monthstate1_fragment_dataLayout);
        mRecyclerView.setHasFixedSize(true);

        try {
            if(list.isEmpty()){
                nodataTv.setVisibility(View.VISIBLE);
                dataLayout.setVisibility(View.GONE);
            }else{
                nodataTv.setVisibility(View.GONE);
                dataLayout.setVisibility(View.VISIBLE);
                mRecyclerViewAdapter = new MonthState1_RecyclerViewAdpater(getActivity(), list);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("error", e.toString());
        }

        return v;
    }

}