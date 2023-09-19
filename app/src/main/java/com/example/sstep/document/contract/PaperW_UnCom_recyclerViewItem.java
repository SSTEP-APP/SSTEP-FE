package com.example.sstep.document.contract;

public class PaperW_UnCom_recyclerViewItem {
    private String unComName;
    private long unComStaffId;
    private String type;


    public String getUnComName() {
        return unComName;
    }

    public void setUnComName(String unComName) {
        this.unComName = unComName;
    }

    public long getUnComStaffId() {
        return unComStaffId;
    }

    public void setUnComStaffId(long unComStaffId) {
        this.unComStaffId = unComStaffId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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