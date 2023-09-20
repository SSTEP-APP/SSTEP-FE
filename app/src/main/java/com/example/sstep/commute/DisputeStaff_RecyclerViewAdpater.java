package com.example.sstep.commute;

import android.annotation.SuppressLint;
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

import com.example.sstep.AppInData;
import com.example.sstep.R;
import com.example.sstep.commute.commute_api.CommuteApiService;
import com.example.sstep.commute.commute_api.CommuteResponseDto;
import com.example.sstep.date.Date_RecyclerViewAdpater;
import com.example.sstep.date.Date_recyclerViewItem;
import com.example.sstep.store.store_api.NullOnEmptyConverterFactory;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DisputeStaff_RecyclerViewAdpater extends RecyclerView.Adapter<DisputeStaff_RecyclerViewAdpater.ViewHolder> {

    AppInData appInData;
    String commuteDateStr, dayOfWeekStr, startTimeStr, endTimeStr;
    long commuteId, staffId;
    private Context context;
    private static OnItemClickListener onItemClickListener;

    public static void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTv, homeTimeTv, workTimeTv;
        Button disputeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = (TextView) itemView.findViewById(R.id.cdsl_recycle_dateTv);
            homeTimeTv = (TextView) itemView.findViewById(R.id.cdsl_recycle_homeTimeTv);
            workTimeTv = (TextView) itemView.findViewById(R.id.cdsl_recycle_workTimeTv);
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

        dayOfWeekStr = convertDayOfWeek(item.getDayOfWeek());
        commuteDateStr = item.getCommuteDate();
        startTimeStr = item.getStartTime();

        if (item.getEndTime() == null) {
            endTimeStr="미퇴근";
        }else{
            endTimeStr = item.getEndTime();
        }

        holder.dateTv.setText(item.getCommuteDate() + " (" + dayOfWeekStr + ")");
        holder.homeTimeTv.setText(item.getStartTime());
        holder.workTimeTv.setText(endTimeStr);

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

                fetchCommuteInfo(commuteDateStr);
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

    // fetchCommuteInfo 메서드를 정의하여 Retrofit을 사용하여 출퇴근 정보를 가져오도록 합니다.
    private void fetchCommuteInfo(String commuteDateStr) {
        // ID값 가지고 오기
        appInData = (AppInData) context.getApplicationContext(); // MyApplication 클래스의 인스턴스 가져오기
        staffId = appInData.getStaffId();
        try {
            // Retrofit 코드 작성
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-35-10-138.ap-northeast-2.compute.amazonaws.com:3306/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CommuteApiService apiService = retrofit.create(CommuteApiService.class);

            Call<CommuteResponseDto> call = apiService.getCommute(staffId, commuteDateStr); // staffId, date

            call.enqueue(new Callback<CommuteResponseDto>() {
                @Override
                public void onResponse(Call<CommuteResponseDto> call, Response<CommuteResponseDto> response) {
                    if (response.isSuccessful()) {
                        CommuteResponseDto commuteInfo = response.body();
                        commuteId = commuteInfo.getCommuteId();
                        Intent intent = new Intent(context, Dispute_WriteStaff.class);

                        intent.putExtra("commuteDate", commuteDateStr);
                        intent.putExtra("dayOfWeek", dayOfWeekStr);
                        intent.putExtra("startTime", startTimeStr);
                        intent.putExtra("endTime", endTimeStr);
                        intent.putExtra("commuteId", commuteId);
                        intent.putExtra("staffId", staffId);

                        context.startActivity(intent);
                    } else {
                        // 처리할 실패 시나리오 작성
                        handleError("데이터 가져오기 실패: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<CommuteResponseDto> call, Throwable t) {
                    // 에러 처리 코드 작성
                    handleError("네트워크 오류: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            // 예외 처리 코드 작성
            handleError("예외 발생: " + e.getMessage());
        }
    }

    // 네트워크 요청 실패 또는 예외 발생 시 호출되는 오류 처리 메서드
    private void handleError(String errorMessage) {
        Log.e("API Error", errorMessage); // 로그에 오류 메시지 기록
        Toast.makeText(context, "API 오류: " + errorMessage, Toast.LENGTH_SHORT).show();

        // 다른 오류 처리 동작 추가 가능
    }
}