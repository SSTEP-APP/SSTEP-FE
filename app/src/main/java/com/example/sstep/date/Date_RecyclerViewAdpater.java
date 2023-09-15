package com.example.sstep.date;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Date_RecyclerViewAdpater extends RecyclerView.Adapter<Date_RecyclerViewAdpater.ViewHolder> {

    private Context context;
    private List<Date_recyclerViewWordItemData> itemList;

    public Date_RecyclerViewAdpater(Context context, List<Date_recyclerViewWordItemData> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_recycle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Date_recyclerViewWordItemData itemData = itemList.get(position);

        // itemData에서 필요한 정보를 가져와서 뷰에 설정
        holder.dayTv.setText(itemData.getDate()); // calendarDate 설정
        holder.userNameTv.setText(itemData.getStaffName()); // staffName 설정
//        holder.scheduleTimeTv.setText(itemData.getStartCalTime() + " ~ " + itemData.getEndCalTime()); // startCalTime와 endCalTime 설정
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayTv, userNameTv;
        LinearLayout nodataLayout, dataLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTv = itemView.findViewById(R.id.date_recycle_dayTv);
            userNameTv = itemView.findViewById(R.id.date_recycle_userNameTv);
            nodataLayout = itemView.findViewById(R.id.date_recycle_nodataLayout);
            dataLayout = itemView.findViewById(R.id.date_recycle_dataLayout);
        }
    }

}