package com.example.sstep.commute;

import android.annotation.SuppressLint;
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
import com.example.sstep.date.Date_RecyclerViewAdpater;
import com.example.sstep.date.Date_recyclerViewItem;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;


public class DisputeStaff_RecyclerViewAdpater extends RecyclerView.Adapter<DisputeStaff_RecyclerViewAdpater.ViewHolder> {

    private Context context;
    private static OnItemClickListener onItemClickListener;

    public static void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTv, schNameTv, schTimeTv;
        Button disputeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = (TextView) itemView.findViewById(R.id.cdsl_recycle_dateTv);
            schNameTv = (TextView) itemView.findViewById(R.id.cdsl_recycle_schNameTv);
            schTimeTv = (TextView) itemView.findViewById(R.id.cdsl_recycle_schTimeTv);
            disputeBtn = (Button) itemView.findViewById(R.id.cdsl_recycle_disputeBtn);
        }
    }

    private List<DisputeStaff_recyclerViewItem> mList = null;

    public DisputeStaff_RecyclerViewAdpater(List<DisputeStaff_recyclerViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.disputestafflist_recycle_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // 요일을 문자열로 변환하는 메서드
    private String convertDayOfWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "월";
            case TUESDAY:
                return "화";
            case WEDNESDAY:
                return "수";
            case THURSDAY:
                return "목";
            case FRIDAY:
                return "금";
            case SATURDAY:
                return "토";
            case SUNDAY:
                return "일";
            default:
                return dayOfWeek.toString(); // 다른 경우에는 열거형 이름을 반환
        }
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DisputeStaff_recyclerViewItem item = mList.get(position);

        String dayOfWeekStr = convertDayOfWeek(item.getDayOfWeek());
        holder.dateTv.setText(item.getCommuteDate() + " (" + dayOfWeekStr + ")");
        holder.schTimeTv.setText(item.getStartTime() + "~" + item.getEndTime());

        // 이의신청 버튼에 대한 클릭 리스너 설정
        holder.disputeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }
                Intent intent = new Intent(context, Dispute_WriteStaff.class);

                intent.putExtra("commuteDate", item.getCommuteDate());
                intent.putExtra("dayOfWeek", convertDayOfWeek(item.getDayOfWeek()));
                intent.putExtra("startTime", item.getStartTime());
                intent.putExtra("endTime", item.getEndTime());


                context.startActivity(intent);
            }
        });

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