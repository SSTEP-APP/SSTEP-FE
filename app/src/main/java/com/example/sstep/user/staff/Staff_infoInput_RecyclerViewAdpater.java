package com.example.sstep.user.staff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;

import java.util.ArrayList;

public class Staff_infoInput_RecyclerViewAdpater extends RecyclerView.Adapter<Staff_infoInput_RecyclerViewAdpater.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView workScheduleDay, workScheduleTime, workScheduleOurs;
        //ImageView


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            workScheduleDay = (TextView) itemView.findViewById(R.id.staff_info_RI_workScheduleDay);
            workScheduleTime = (TextView) itemView.findViewById(R.id.staff_info_RI_workScheduleTime);
            workScheduleOurs = (TextView) itemView.findViewById(R.id.staff_info_RI_workScheduleOurs);
        }
    }

    private ArrayList<Staff_infoInput_recyclerViewItem> mList = null;

    public Staff_infoInput_RecyclerViewAdpater(ArrayList<Staff_infoInput_recyclerViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.staff_infoinput_recycle_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Staff_infoInput_recyclerViewItem item = mList.get(position);

        holder.workScheduleDay.setText(item.getStaff_infoInput_days());   // 요일 받기
        holder.workScheduleTime.setText(item.getStaff_infoInput_time()); // 시간 표시
        holder.workScheduleOurs.setText(item.getStaff_infoInput_cancelImg()); //취소 이미지 고정
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}