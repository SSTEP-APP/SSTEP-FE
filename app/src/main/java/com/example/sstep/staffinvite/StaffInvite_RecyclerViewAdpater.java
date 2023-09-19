package com.example.sstep.staffinvite;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.user.staff.InputStaffInfo;

import java.util.List;

public class StaffInvite_RecyclerViewAdpater extends RecyclerView.Adapter<StaffInvite_RecyclerViewAdpater.ViewHolder> {
    Dialog staffInviteYes_dialog;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView staffInviteName, staffInviteUserName, staffInviteId;
        Button yesBtn, noBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            staffInviteName = (TextView) itemView.findViewById(R.id.staffInvite1_RI_nameTv);
            staffInviteUserName = (TextView) itemView.findViewById(R.id.staffInvite1_RI_idTv);
            yesBtn = (Button) itemView.findViewById(R.id.staffInvite1_RI_yesBtn);
            noBtn = (Button) itemView.findViewById(R.id.staffInvite1_RI_noBtn);

            yesBtn.setOnClickListener(new View.OnClickListener() {
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

    private List<StaffInvite_recyclerViewItem> mList = null;

    public StaffInvite_RecyclerViewAdpater(List<StaffInvite_recyclerViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.staffinvite_recycle_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StaffInvite_recyclerViewItem item = mList.get(position);

        holder.staffInviteName.setText(item.getStaffInviteName());
        holder.staffInviteUserName.setText(item.getStaffInviteUserName());
        long staffId = item.getStaffInviteId();

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