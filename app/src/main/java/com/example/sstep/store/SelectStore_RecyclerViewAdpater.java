package com.example.sstep.store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;

import java.util.ArrayList;

public class SelectStore_RecyclerViewAdpater extends RecyclerView.Adapter<SelectStore_RecyclerViewAdpater.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView selectStoreName, selectStoreAddress, selectStorePerson;
        //ImageView


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            selectStoreName = (TextView) itemView.findViewById(R.id.selectstoreRI_storenameTv);
            selectStoreAddress = (TextView) itemView.findViewById(R.id.selectstoreRI_addressTv);
            selectStorePerson = (TextView) itemView.findViewById(R.id.selectstoreRI_personNumTv);
        }
    }

    private ArrayList<SelectStore_recyclerViewItem> mList = null;

    public SelectStore_RecyclerViewAdpater(ArrayList<SelectStore_recyclerViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slectstore_recycle_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelectStore_recyclerViewItem item = mList.get(position);

        holder.selectStoreName.setText(item.getSelectStoreName());   // 사업장 이름 받기
        holder.selectStoreAddress.setText(item.getSelectStoreAddress()); // 주소 받기
        holder.selectStorePerson.setText(item.getSelectStorePerson()); // 사람 몇명인지 받기
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}