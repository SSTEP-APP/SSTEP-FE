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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class monthstate2Fragment extends Fragment {

    private MonthState2_RecyclerViewAdpater mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<MonthState2_recyclerViewWordItemData> list = new ArrayList<>();
    private LinearLayout dataLayout;
    private TextView nodataTv;

    PieChart piechart2;
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
        datavalue.add(new PieEntry(0, "결근률"));
        datavalue.add(new PieEntry(100, "전체"));

        pieDataSet = new PieDataSet(datavalue, "결근률");
        pieDataSet.setColors(colorArray);

        pieData = new PieData(pieDataSet);
    }

    public void setUpData() {
        piechart2.setData(pieData);

        piechart2.setDrawEntryLabels(false);
        piechart2.setUsePercentValues(false);
        piechart2.getDescription().setEnabled(false);
        piechart2.setCenterText("100%");
        piechart2.setCenterTextSize(20);
        piechart2.animateY(2000, Easing.EaseInOutQuad);
        piechart2.setTouchEnabled(false);
        // 범례 없애기
        Legend legend = piechart2.getLegend();
        legend.setEnabled(false);

        piechart2.invalidate();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.monthstate2_fragment, container, false);
        piechart2 = v.findViewById(R.id.monthstate2_fragment_piechart2);
        setData();
        setUpData();

        // list dialog 띄우기
        LinearLayout absentL = v.findViewById(R.id.monthstate2_fragment_absentL);
        monstateDl = new Dialog(v.getContext()); // Dialog 초기화
        monstateDl.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        monstateDl.setContentView(R.layout.monthstate_listdl); // xml 레이아웃 파일과 연결
        // 다이얼로그의 배경을 투명으로 만든다.
        monstateDl.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        absentL.setOnClickListener(new View.OnClickListener() {
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
        list = MonthState2_recyclerViewWordItemData.createContactsList(3);// 리스트 갯수
        mRecyclerView = (RecyclerView) v.findViewById(R.id.monthstate2_fragment_recycleView);
        nodataTv = (TextView) v.findViewById(R.id.monthstate2_fragment_nodataTv);
        dataLayout = (LinearLayout)  v.findViewById(R.id.monthstate2_fragment_dataLayout);
        mRecyclerView.setHasFixedSize(true);

        try {
            if(list.isEmpty()){
                nodataTv.setVisibility(View.VISIBLE);
                dataLayout.setVisibility(View.GONE);
            }else{
                nodataTv.setVisibility(View.GONE);
                dataLayout.setVisibility(View.VISIBLE);
                mRecyclerViewAdapter = new MonthState2_RecyclerViewAdpater(getActivity(), list);
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