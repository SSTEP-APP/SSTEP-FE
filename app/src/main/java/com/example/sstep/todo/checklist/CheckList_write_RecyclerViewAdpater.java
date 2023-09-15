package com.example.sstep.todo.checklist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.staffinvite.StaffInvite_InviteStaff_RecyclerViewAdpater;
import com.example.sstep.staffinvite.StaffInvite_RecyclerViewAdpater;

import java.util.ArrayList;

public class CheckList_write_RecyclerViewAdpater extends RecyclerView.Adapter<CheckList_write_RecyclerViewAdpater.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView staffImg;
        ImageButton staffCancelBtn;
        TextView staffName;

        private OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener  listener) {
            onItemClickListener = listener;
        }
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            staffImg = (ImageView) itemView.findViewById(R.id.checklist_write_staffImg);
            staffCancelBtn = (ImageButton) itemView.findViewById(R.id.checklist_write_cancelBtn);
            staffName = (TextView) itemView.findViewById(R.id.checklist_write_staffName);
            staffName.setVisibility(View.VISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    private ArrayList<CheckList_write_recyclerViewItem> mList = null;

    public CheckList_write_RecyclerViewAdpater(ArrayList<CheckList_write_recyclerViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.checklist_write_recycle_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckList_write_recyclerViewItem item = mList.get(position);

        holder.staffImg.setImageResource(R.drawable.j_femail_img2);   // db에서 스태프에게 설정된 프로필사진 받기
        holder.staffCancelBtn.setImageResource(R.drawable.j_x_btn_img); // 취소 버튼 고정
        // 스태프 이름 설정
        String staffName = item.getChecklist_write_staffName();
        holder.staffName.setText(staffName);

        Log.d("RecyclerViewAdapter", "스태프 이름: " + staffName); // 스태프 이름을 로그로 출력
        //Toast.makeText(holder.itemView.getContext(), "스태프 이름: " + staffName, Toast.LENGTH_SHORT).show();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(adapterPosition);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    //누르면 리스트에서 삭제
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    // onCreateViewHolder, onBindViewHolder, getItemCount 메서드는 그대로 둡니다.

    public void removeItem(int position) {
        if (position >= 0 && position < mList.size()) {
            mList.remove(position);
            notifyItemRemoved(position);
        }
    }


}
