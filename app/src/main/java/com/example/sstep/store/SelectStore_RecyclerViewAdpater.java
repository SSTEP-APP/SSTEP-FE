package com.example.sstep.store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;

import java.util.ArrayList;
import java.util.List;

public class SelectStore_RecyclerViewAdpater extends RecyclerView.Adapter<SelectStore_RecyclerViewAdpater.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    LinearLayout selectStoreLayout; // selectstoreRI_onelistF 뷰를 참조할 변수

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView selectStoreName, selectStoreAddress, selectStorePerson;
        //ImageView


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            selectStoreName = (TextView) itemView.findViewById(R.id.selectstoreRI_storenameTv);
            selectStoreAddress = (TextView) itemView.findViewById(R.id.selectstoreRI_addressTv);
            selectStorePerson = (TextView) itemView.findViewById(R.id.selectstoreRI_personNumTv);
            selectStoreLayout = itemView.findViewById(R.id.selectstoreRI_onelistL); // 뷰 초기화

            selectStoreLayout.setOnClickListener(new View.OnClickListener() {
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

    private List<SelectStore_recyclerViewItem> mList = null;

    public SelectStore_RecyclerViewAdpater(List<SelectStore_recyclerViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.selectstore_recycle_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelectStore_recyclerViewItem item = mList.get(position);

        holder.selectStoreName.setText(item.getSelectStoreName());
        holder.selectStoreAddress.setText(item.getSelectStoreAddress());
        holder.selectStorePerson.setText(item.getSelectStorePerson());

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