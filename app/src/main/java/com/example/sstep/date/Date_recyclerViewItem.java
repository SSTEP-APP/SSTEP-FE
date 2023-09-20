package com.example.sstep.date;

public class Date_recyclerViewItem {
    private String staffName; // staffName
    private String startCalTime; // startCalTime
    private String endCalTime; // endCalTime

    public String getStaffName() {
        return staffName;
    }
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStartCalTime() {
        return startCalTime;
    }
    public void setStartCalTime(String startCalTime) {
        this.startCalTime = startCalTime;
    }

    public String getEndCalTime() {
        return endCalTime;
    }
    public void setEndCalTime(String endCalTime) {
        this.endCalTime = endCalTime;
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
