package com.example.sstep.document.certificate;

import com.example.sstep.todo.checklist.CheckList_write_recyclerViewItem;

public class PaperH_Reg_recyclerViewItem {
    private String paperH_reg_name;
    private String paperH_reg_date;
    private long paperH_reg_staffId;

    public String getPaperH_reg_name() {
        return paperH_reg_name;
    }

    public void setPaperH_reg_name(String paperH_reg_name) {
        this.paperH_reg_name = paperH_reg_name;
    }

    public String getPaperH_reg_date() {
        return paperH_reg_date;
    }

    public void setPaperH_reg_date(String paperH_reg_date) {
        this.paperH_reg_date = paperH_reg_date;
    }

    public long getPaperH_reg_staffId() {
        return paperH_reg_staffId;
    }

    public void setPaperH_reg_staffId(long paperH_reg_staffId) {
        this.paperH_reg_staffId = paperH_reg_staffId;
    }

    // 클릭 이벤트 처리를 위한 인터페이스 정의

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void performItemClick(int position) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(position);
        }
    }
}