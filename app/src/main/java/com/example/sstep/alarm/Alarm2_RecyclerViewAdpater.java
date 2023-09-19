package com.example.sstep.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;

import java.util.ArrayList;
import java.util.List;


public class Alarm2_RecyclerViewAdpater extends RecyclerView.Adapter<Alarm2_RecyclerViewAdpater.Holder> {
    private Context context;
    private List<Alarm2_recyclerViewWordItemData> list = new ArrayList<>();

    // 어댑터 생성자
    public Alarm2_RecyclerViewAdpater(Context context, List<Alarm2_recyclerViewWordItemData> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 아이템 뷰의 레이아웃을 인플레이션하여 Holder 객체 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_recycle_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    // 데이터를 아이템 뷰에 바인딩

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.nameTv.setText(list.get(itemposition).name);
        holder.alarmTv.setText(list.get(itemposition).content);
        holder.categoryTv.setText(list.get(itemposition).category);
        holder.dateTv.setText(list.get(itemposition).date);
        holder.calIv.setImageResource(R.drawable.yicon_calb);
        holder.closeIB.setImageResource(R.drawable.yicon_close2);


    }

    // 데이터 리스트 크기 반환
    @Override
    public int getItemCount() {
        return list.size();
    }

    // 아이템 뷰의 구성요소를 보유하는 뷰 홀더 클래스
    public class Holder extends RecyclerView.ViewHolder{
        public TextView nameTv, alarmTv, categoryTv, dateTv;
        public ImageView calIv;
        public ImageButton closeIB;
        public LinearLayout layout, HL;

        public Holder(View view){
            super(view);
            nameTv = (TextView) view.findViewById(R.id.alarm_recycle_nameTv);
            calIv = (ImageView) view.findViewById(R.id.alarm_recycle_calIv);
            alarmTv = (TextView) view.findViewById(R.id.alarm_recycle_alarmTv);
            categoryTv = (TextView) view.findViewById(R.id.alarm_recycle_categoryTv);
            dateTv = (TextView) view.findViewById(R.id.alarm_recycle_dateTv);
            closeIB = (ImageButton) view.findViewById(R.id.alarm_recycle_closeIB);
            layout = (LinearLayout) view.findViewById(R.id.alarm_recycle_layout);
            HL = (LinearLayout) view.findViewById(R.id.alarm_recycle_HL);
        }
    }
}
