package com.example.sstep.alarm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.todo.checklist.CheckList1_RecyclerViewAdpater;
import com.example.sstep.todo.checklist.CheckList1_recyclerViewWordItemData;

import java.util.ArrayList;

public class alarm3Fragment extends Fragment {

    private Alarm3_RecyclerViewAdpater mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Alarm3_recyclerViewWordItemData> list = new ArrayList<>();
    private LinearLayout nodataLayout, dataLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.alarm3_fragment, container, false);

        // 리사이클 뷰
        list = Alarm3_recyclerViewWordItemData.createContactsList(0);// 리스트 갯수
        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_alarm3_recycleView);
        nodataLayout = (LinearLayout) v.findViewById(R.id.fragment_alarm3_nodataLayout);
        dataLayout = (LinearLayout)  v.findViewById(R.id.fragment_alarm3_dataLayout);
        mRecyclerView.setHasFixedSize(true);

        try {
            if(list.isEmpty()){
                nodataLayout.setVisibility(View.VISIBLE);
                dataLayout.setVisibility(View.GONE);
            }else{
                nodataLayout.setVisibility(View.GONE);
                dataLayout.setVisibility(View.VISIBLE);
                mRecyclerViewAdapter = new Alarm3_RecyclerViewAdpater(getActivity(), list);
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