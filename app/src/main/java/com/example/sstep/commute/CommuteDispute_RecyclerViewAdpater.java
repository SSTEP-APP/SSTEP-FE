package com.example.sstep.commute;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.todo.notice.Notice_recyclerViewWordItemData;
import com.example.sstep.todo.notice.Notice_view;

import java.util.ArrayList;
import java.util.List;


public class CommuteDispute_RecyclerViewAdpater extends RecyclerView.Adapter<CommuteDispute_RecyclerViewAdpater.Holder> {
    private Context context;
    private List<CommuteDispute_recyclerViewWordItemData> list = new ArrayList<>();

    // 어댑터 생성자
    public CommuteDispute_RecyclerViewAdpater(Context context, List<CommuteDispute_recyclerViewWordItemData> list) {
        this.context = context;
        this.list.addAll(list);
    }

    // 데이터 업데이트 메서드 추가
    public void updateData(List<CommuteDispute_recyclerViewWordItemData> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    // ViewHolder 생성
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 아이템 뷰의 레이아웃을 인플레이션하여 Holder 객체 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commutedisputelist_recycle_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    // 데이터를 아이템 뷰에 바인딩
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.dateTv.setText(list.get(itemposition).date);
        holder.nameTv.setText(list.get(itemposition).name);

        // 아이템 뷰 클릭 시 이벤트 처리
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommuteDispute_WriteStaff.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 오류 해결

                // 값 전달
                intent.putExtra("date", list.get(itemposition).date);
                intent.putExtra("name", list.get(itemposition).name);

                context.startActivity(intent);
            }
        });
    }

    // 데이터 리스트 크기 반환
    @Override
    public int getItemCount() {
        return list.size();
    }

    // 아이템 뷰의 구성요소를 보유하는 뷰 홀더 클래스
    public class Holder extends RecyclerView.ViewHolder{
        public TextView dateTv, nameTv;
        public LinearLayout layout, innerlayout;

        public Holder(View view){
            super(view);
            dateTv = (TextView) view.findViewById(R.id.commutedisputelist_dateTv);
            nameTv = (TextView) view.findViewById(R.id.commutedisputelist_nameTv);
            layout = (LinearLayout) view.findViewById(R.id.commutedisputelist_recycle_layout);
            innerlayout = (LinearLayout) view.findViewById(R.id.commutedisputelist_recycle_innerLayout);
        }
    }
}