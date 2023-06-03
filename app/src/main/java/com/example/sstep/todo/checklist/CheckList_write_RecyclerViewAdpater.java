package com.example.sstep.todo.checklist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sstep.R;

import java.util.ArrayList;

public class CheckList_write_RecyclerViewAdpater extends RecyclerView.Adapter<CheckList_write_RecyclerViewAdpater.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView staffImg;
        ImageButton staffCancelBtn;
        TextView staffName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            staffImg = (ImageView) itemView.findViewById(R.id.checklist_write_staffImg);
            staffCancelBtn = (ImageButton) itemView.findViewById(R.id.checklist_write_cancelBtn);
            staffName = (TextView) itemView.findViewById(R.id.checklist_write_staffName);
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
        CheckList_write_RecyclerViewAdpater.ViewHolder vh = new CheckList_write_RecyclerViewAdpater.ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull CheckList_write_RecyclerViewAdpater.ViewHolder holder, int position) {
        CheckList_write_recyclerViewItem item = mList.get(position);

        holder.staffImg.setImageResource(R.drawable.j_femail_img2);   // db에서 스태프에게 설정된 프로필사진 받기
        holder.staffCancelBtn.setImageResource(R.drawable.j_x_btn_img); // 취소 버튼 고정
        holder.staffName.setText(item.getChecklist_write_staffName()); //스태프 이름  받기
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}