package com.example.sstep.date;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.todo.notice.Notice_recyclerViewWordItemData;
import com.example.sstep.todo.notice.Notice_view;

import java.util.ArrayList;
import java.util.List;


public class Date_RecyclerViewAdpater extends RecyclerView.Adapter<Date_RecyclerViewAdpater.Holder> {
    private Context context;
    private List<Date_recyclerViewWordItemData> list = new ArrayList<>();

    // 어댑터 생성자
    public Date_RecyclerViewAdpater(Context context, List<Date_recyclerViewWordItemData> list) {
        this.context = context;
        this.list.addAll(list);
    }

    // 데이터 업데이트 메서드 추가
    public void updateData(List<Date_recyclerViewWordItemData> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    // ViewHolder 생성
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 아이템 뷰의 레이아웃을 인플레이션하여 Holder 객체 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_recycle_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    // 데이터를 아이템 뷰에 바인딩
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.userNameTv.setText(list.get(itemposition).staffName);
        holder.scheduleTimeTv.setText(list.get(itemposition).calendarDate);
        holder.workTimeTv.setText(list.get(itemposition).startCalTime);

        // 아이템 뷰 클릭 시 이벤트 처리
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Date_plus.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 오류 해결

                // 값 전달
                intent.putExtra("staffName", list.get(itemposition).staffName);
                intent.putExtra("calendarDate", list.get(itemposition).calendarDate);
                intent.putExtra("startCalTime", list.get(itemposition).startCalTime);

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
        public TextView dayTv, userNameTv, userPositionTv, statusTv, scheduleTimeTv, workTimeTv;
        public Button changeBtn, commuteDisputeBtn, deleteBtn;
        public LinearLayout layout, innerlayout;

        public Holder(View view){
            super(view);
            dayTv = (TextView) view.findViewById(R.id.date_recycle_dayTv);
            userNameTv = (TextView) view.findViewById(R.id.date_recycle_userNameTv);
            userPositionTv = (TextView) view.findViewById(R.id.date_recycle_userPositionTv);
            statusTv = (TextView) view.findViewById(R.id.date_recycle_statusTv);
            scheduleTimeTv = (TextView) view.findViewById(R.id.date_recycle_scheduleTimeTv);
            workTimeTv = (TextView) view.findViewById(R.id.date_recycle_workTimeTv);
            changeBtn = (Button) view.findViewById(R.id.date_recycle_changeBtn);
            commuteDisputeBtn = (Button) view.findViewById(R.id.date_recycle_commuteDisputeBtn);
            deleteBtn = (Button) view.findViewById(R.id.date_recycle_deleteBtn);
            layout = (LinearLayout) view.findViewById(R.id.date_recycle_layout);
        }
    }
}