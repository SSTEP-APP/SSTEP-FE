package com.example.sstep.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;

import java.util.ArrayList;
import java.util.List;


public class HomeCeoCheck_RecyclerViewAdpater extends RecyclerView.Adapter<HomeCeoCheck_RecyclerViewAdpater.Holder> {
    private Context context;
    private List<HomeCeoCheck_recyclerViewItem> list = new ArrayList<>();

    // 어댑터 생성자
    public HomeCeoCheck_RecyclerViewAdpater(Context context, List<HomeCeoCheck_recyclerViewItem> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 아이템 뷰의 레이아웃을 인플레이션하여 Holder 객체 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeceo_checklist_recycle_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    // 데이터를 아이템 뷰에 바인딩

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.titleCb.setText(list.get(itemposition).title);
        holder.staffCntTv.setText(list.get(itemposition).staffCnt);
        holder.dateTv.setText(list.get(itemposition).date);


    }

    // 데이터 리스트 크기 반환
    @Override
    public int getItemCount() {
        return list.size();
    }

    // 아이템 뷰의 구성요소를 보유하는 뷰 홀더 클래스
    public class Holder extends RecyclerView.ViewHolder{
        public TextView staffCntTv, dateTv;
        private CheckBox titleCb;
        public LinearLayout layout, innerLayout;

        public Holder(View view){
            super(view);
            titleCb = (CheckBox) view.findViewById(R.id.homeceo_checklist_recycle_titleCb);
            staffCntTv = (TextView) view.findViewById(R.id.homeceo_checklist_recycle_staffCntTv);
            dateTv = (TextView) view.findViewById(R.id.homeceo_checklist_recycle_dateTv);
            layout = (LinearLayout) view.findViewById(R.id.homeceo_checklist_recycle_layout);
            innerLayout = (LinearLayout) view.findViewById(R.id.homeceo_checklist_recycle_innerLayout);
        }
    }
}
