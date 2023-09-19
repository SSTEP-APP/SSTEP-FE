package com.example.sstep.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.date.Date_recyclerViewItem;

import java.util.List;

public class HomeDate_RecyclerViewAdpater extends RecyclerView.Adapter<HomeDate_RecyclerViewAdpater.ViewHolder> {

    private Context context;
    private static OnItemClickListener onItemClickListener;

    public static void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView calendarDateTv, staffNameTv, schTimeTv;
        LinearLayout goLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            calendarDateTv = (TextView) itemView.findViewById(R.id.home_date_recycle_calendarDateTv);
            staffNameTv = (TextView) itemView.findViewById(R.id.home_date_recycle_staffNameTv);
            schTimeTv = (TextView) itemView.findViewById(R.id.home_date_recycle_schTimeTv);
            goLayout = (LinearLayout) itemView.findViewById(R.id.home_date_recycle_goLayout);
        }
    }

    private List<HomeDate_recyclerViewItem> mList = null;

    public HomeDate_RecyclerViewAdpater(List<HomeDate_recyclerViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.home_date_recycle_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeDate_recyclerViewItem item = mList.get(position);

        holder.calendarDateTv.setText(item.getCalendarDate());
        holder.staffNameTv.setText(item.getStaffName());
        holder.schTimeTv.setText(item.getStartCalTime() + " ~ " + item.getEndCalTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}