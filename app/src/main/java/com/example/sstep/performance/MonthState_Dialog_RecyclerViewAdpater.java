package com.example.sstep.performance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;

import java.util.ArrayList;
import java.util.List;


public class MonthState_Dialog_RecyclerViewAdpater extends RecyclerView.Adapter<MonthState_Dialog_RecyclerViewAdpater.Holder> {
    private Context context;
    private List<MonthState_Dialog_recyclerViewWordItemData> list = new ArrayList<>();

    // 어댑터 생성자
    public MonthState_Dialog_RecyclerViewAdpater(Context context, List<MonthState_Dialog_recyclerViewWordItemData> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 아이템 뷰의 레이아웃을 인플레이션하여 Holder 객체 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.monthstate_dialog_recycle_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    // 데이터를 아이템 뷰에 바인딩

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.personIv.setImageResource(R.drawable.yicon_stafffemale);
        holder.nameTv.setText(list.get(itemposition).name);
        holder.numTv.setText(list.get(itemposition).num);

    }

    // 데이터 리스트 크기 반환
    @Override
    public int getItemCount() {
        return list.size();
    }

    // 아이템 뷰의 구성요소를 보유하는 뷰 홀더 클래스
    public class Holder extends RecyclerView.ViewHolder{
        public TextView nameTv, numTv;
        public ImageView personIv;
        public LinearLayout layout, innerlayout;

        public Holder(View view){
            super(view);
            personIv = (ImageView) view.findViewById(R.id.monthstate_dialog_recycle_personIv);
            nameTv = (TextView) view.findViewById(R.id.monthstate_dialog_recycle_nameTv);
            numTv = (TextView) view.findViewById(R.id.monthstate_dialog_recycle_numTv);
            layout = (LinearLayout) view.findViewById(R.id.monthstate_dialog_recycle_layout);
            innerlayout = (LinearLayout) view.findViewById(R.id.monthstate_dialog_recycle_innerlayout);
        }
    }
}
