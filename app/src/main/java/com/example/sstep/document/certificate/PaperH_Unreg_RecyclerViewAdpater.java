package com.example.sstep.document.certificate;

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
import com.example.sstep.staffinvite.StaffInvite_InviteStaff_recyclerViewItem;

import java.util.List;

public class PaperH_Unreg_RecyclerViewAdpater extends RecyclerView.Adapter<PaperH_Unreg_RecyclerViewAdpater.ViewHolder> {

    private Context context;

    private static OnItemClickListener onItemClickListener;

    public static void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.paperh_unregnameTv);

        }
    }

    private List<PaperH_Unreg_recyclerViewItem> mList = null;

    public PaperH_Unreg_RecyclerViewAdpater(List<PaperH_Unreg_recyclerViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.paperh_unreg_recycle_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaperH_Unreg_recyclerViewItem item = mList.get(position);

        holder.name.setText(item.getPaperH_UnReg_Name());

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