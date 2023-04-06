package com.example.sstep.checklist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.MainActivity;
import com.example.sstep.R;

import java.util.ArrayList;
import java.util.List;

public class CheckList1_RecyclerViewAdpater extends RecyclerView.Adapter<CheckList1_RecyclerViewAdpater.Holder> {
    private Context context;
    private List<CheckList1_recyclerViewWordItemData> list = new ArrayList<>();
    public CheckList1_RecyclerViewAdpater(Context context, List<CheckList1_recyclerViewWordItemData> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_recycle_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    /*
     * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
     *
     * */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.wordText.setText(list.get(itemposition).word);
        holder.checkImg.setImageResource(R.drawable.j_check_img);
        //Log.e("StudyApp", "onBindViewHolder" + itemposition);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Checklist_detail.class);
                ((CheckList)context).startActivity(intent);
                ((CheckList)context).finish();

            }
        });
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public TextView wordText;
        public ImageView checkImg;
        public LinearLayout layout;

        public Holder(View view){
            super(view);
            wordText = (TextView) view.findViewById(R.id.checkList_recycle_text);
            checkImg = (ImageView) view.findViewById(R.id.checkList_recycle_checkBtn);
            layout = (LinearLayout) view.findViewById(R.id.checkList_recycle_layout);
        }
    }
}
