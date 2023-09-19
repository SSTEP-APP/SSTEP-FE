package com.example.sstep.date;

public class DatePlus_recyclerViewItem {
    private String dateplus_recycle_staffImg;
    private String dateplus_recycle_staffCancelBtn;
    private String dateplus_recycle_staffNameText;
    private String dateplus_recycle_phoneNum;
    private long dateplus_recycle_staffId;

    // 클릭 이벤트 처리를 위한 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener onItemClickListener;


    public String getDateplus_recycle_staffNameText(String subText) {
        return dateplus_recycle_staffNameText;
    }

    public void setDateplus_recycle_staffNameText(String dateplus_recycle_staffNameText) {
        this.dateplus_recycle_staffNameText = dateplus_recycle_staffNameText;
    }

    public String getDateplus_recycle_phoneNum() {
        return dateplus_recycle_phoneNum;
    }

    public void setDateplus_recycle_phoneNum(String dateplus_recycle_phoneNum) {
        this.dateplus_recycle_phoneNum = dateplus_recycle_phoneNum;
    }

    public long getDateplus_recycle_staffId() {
        return dateplus_recycle_staffId;
    }

    public void setDateplus_recycle_staffId(long dateplus_recycle_staffId) {
        this.dateplus_recycle_staffId = dateplus_recycle_staffId;
    }

    // 클릭 이벤트 핸들러 설정 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // 클릭 이벤트를 호출하는 메서드
    public void performItemClick(int position) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(position);
        }
    }
}