package com.example.sstep.staffinvite;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;

import java.util.List;

public class StaffInvite_InviteStaff_RecyclerViewAdpater extends RecyclerView.Adapter<StaffInvite_InviteStaff_RecyclerViewAdpater.ViewHolder> {
    Dialog staffInviteYes_dialog;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView staffInviteISName, staffInviteISUserName;
        Button reBtn, delBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            staffInviteISName = (TextView) itemView.findViewById(R.id.staffInvite1_is_nameTv2);
            staffInviteISUserName = (TextView) itemView.findViewById(R.id.staffInvite1_is_idTv2);
            //reBtn = (Button) itemView.findViewById(R.id.staffInvite1_is_resendBtn);
            //delBtn = (Button) itemView.findViewById(R.id.staffInvite1_is_deleteBtn);


            /* 재전송 버튼  후순위 개발
            reBtn.setOnClickListener(new View.OnClickListener() {
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

             */
        }
    }

    private List<StaffInvite_InviteStaff_recyclerViewItem> mList = null;

    public StaffInvite_InviteStaff_RecyclerViewAdpater(List<StaffInvite_InviteStaff_recyclerViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.staffinvite_is_recycle_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StaffInvite_InviteStaff_recyclerViewItem item = mList.get(position);

        holder.staffInviteISName.setText(item.getStaffInviteISName());
        holder.staffInviteISUserName.setText(item.getStaffInviteISUserName());

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