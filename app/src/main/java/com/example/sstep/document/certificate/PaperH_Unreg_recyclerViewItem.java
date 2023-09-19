package com.example.sstep.document.certificate;

public class PaperH_Unreg_recyclerViewItem {
    private String paperH_UnReg_Name;

    public String getPaperH_UnReg_Name() {
        return paperH_UnReg_Name;
    }

    public void setPaperH_UnReg_Name(String paperH_UnReg_Name) {
        this.paperH_UnReg_Name = paperH_UnReg_Name;
    }
    // 클릭 이벤트 처리를 위한 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

}