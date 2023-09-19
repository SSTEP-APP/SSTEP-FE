package com.example.sstep.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.commute.DisputeStaff_recyclerViewItem;
import com.example.sstep.commute.Dispute_WriteStaff;
import com.example.sstep.commute.commute_api.CommuteApiService;
import com.example.sstep.commute.commute_api.CommuteResponseDto;
import com.example.sstep.store.store_api.NullOnEmptyConverterFactory;
import com.example.sstep.todo.notice.Notice_view;

import java.time.DayOfWeek;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeStaffNotice_RecyclerViewAdpater extends RecyclerView.Adapter<HomeStaffNotice_RecyclerViewAdpater.ViewHolder> {

    long noticeId;
    String writeDateStr, titleStr, writerNameStr;
    private Context context;
    private static OnItemClickListener onItemClickListener;

    public static void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView writeDateTv, titleTv, staffNameTv;
        LinearLayout goLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            writeDateTv = (TextView) itemView.findViewById(R.id.homestaff_notice_recycle_writeDateTv);
            titleTv = (TextView) itemView.findViewById(R.id.homestaff_notice_recycle_titleTv);
            staffNameTv = (TextView) itemView.findViewById(R.id.homestaff_notice_recycle_staffNameTv);
            goLayout = (LinearLayout) itemView.findViewById(R.id.homestaff_notice_recycle_goLayout);
        }
    }

    private List<HomeStaffNotice_recyclerViewItem> mList = null;

    public HomeStaffNotice_RecyclerViewAdpater(List<HomeStaffNotice_recyclerViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.homestaff_notice_recycle_item, parent, false);
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
        HomeStaffNotice_recyclerViewItem item = mList.get(position);

        writeDateStr = item.getWriteDate();
        titleStr = item.getTitle();
        writerNameStr = item.getWriterName();
        noticeId = item.getNoticeId();

        holder.writeDateTv.setText(writeDateStr);
        holder.titleTv.setText(titleStr);
        holder.staffNameTv.setText(writerNameStr);

        // 이의신청 버튼에 대한 클릭 리스너 설정
        holder.goLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }

                Intent intent = new Intent(context, Notice_view.class);

                intent.putExtra("noticeId", noticeId);

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