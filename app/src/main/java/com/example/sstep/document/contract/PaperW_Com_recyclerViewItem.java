package com.example.sstep.document.contract;


public class PaperW_Com_recyclerViewItem {
    private String comName;
    private long comStaffId;
    private String type;

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public long getComStaffId() {
        return comStaffId;
    }

    public void setComStaffId(long comStaffId) {
        this.comStaffId = comStaffId;
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