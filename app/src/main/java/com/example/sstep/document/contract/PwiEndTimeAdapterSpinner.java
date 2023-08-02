package com.example.sstep.document.contract;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sstep.R;

import java.util.ArrayList;
import java.util.List;

public class PwiEndTimeAdapterSpinner extends BaseAdapter {

    Context mContext;
    List<String> Data;
    LayoutInflater Inflater;
    private String SelectedStartTime;


    public PwiEndTimeAdapterSpinner(Context context, List<String> data) {
        this.mContext = context;
        this.Data = data;
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.SelectedStartTime = "";
    }

    public void updateEndTimeList(String selectedStartTime) {
        List<String> newData = new ArrayList<>(); // 새로운 데이터 리스트 생성
        SelectedStartTime = selectedStartTime;

        boolean addData = false;

        // 종료시간 항목을 항상 맨 처음으로 추가
        newData.add("종료시간");

        int startIndex = Data.indexOf(selectedStartTime);

        // 선택된 시작시간 이후의 데이터 추가
        for (int i = startIndex + 1; i < Data.size(); i++) {
            String time = Data.get(i);
            if (time.equals("종료시간")) {
                continue; // 종료시간을 만나면 건너뛰기
            }
            newData.add(time);
        }

        // 선택된 시작시간 이전의 데이터 추가
        for (int i = 0; i <= startIndex; i++) {
            String time = Data.get(i);
            if (time.equals("종료시간")) {
                continue; // 종료시간을 만나면 건너뛰기
            }
            newData.add(time);
        }

        Data.clear(); // 기존 데이터 초기화
        Data.addAll(newData); // 새로운 데이터로 업데이트

        notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
    }

    @Override
    public int getCount() {
        if(Data!=null) return Data.size();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //처음에 클릭전에 보여지는 레이아웃
        if(convertView==null){
            convertView = Inflater.inflate(R.layout.paperwinput_spinner_view, parent, false);
        }
        if(Data!=null){
            String text = Data.get(position);
            if(text.equals("종료시간")) {
                ((TextView) convertView.findViewById(R.id.paperwinput_spinner_view_text)).setHint(text);
            }else{
                ((TextView) convertView.findViewById(R.id.paperwinput_spinner_view_text)).setText(text);
            }
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // 클릭 후 보여지는 레이아웃
        if(convertView==null) {
            convertView = Inflater.inflate(R.layout.paperwinput_spinner_view, parent, false);
        }
        String text = Data.get(position);
        ((TextView) convertView.findViewById(R.id.paperwinput_spinner_view_text)).setText(text);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
