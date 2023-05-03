package com.example.sstep.paper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sstep.R;

import java.util.List;

public class PwiEndTimeAdapterSpinner extends BaseAdapter {

    Context mContext;
    List<String> Data;
    LayoutInflater Inflater;

    public PwiEndTimeAdapterSpinner(Context context, List<String> data) {
        this.mContext = context;
        this.Data = data;
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            convertView = Inflater.inflate(R.layout.paperwinput_spinner_text, parent, false);
        }
        if(Data!=null){
            String text = Data.get(position);
            ((TextView) convertView.findViewById(R.id.paperwinput_spinner_text)).setText(text);
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
        ((TextView) convertView.findViewById(R.id.paperwinput_spinner_text)).setText(text);

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
