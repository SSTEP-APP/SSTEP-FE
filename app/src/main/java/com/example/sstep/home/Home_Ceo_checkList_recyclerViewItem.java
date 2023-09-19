package com.example.sstep.home;

public class Home_Ceo_checkList_recyclerViewItem {
    private String home_Check_title;
    private String home_Check_date;
    private long home_Check_checkListId;


    public String getHome_Check_title() {
        return home_Check_title;
    }

    public void setHome_Check_title(String home_Check_title) {
        this.home_Check_title = home_Check_title;
    }

    public String getHome_Check_date() {
        return home_Check_date;
    }

    public void setHome_Check_date(String home_Check_date) {
        this.home_Check_date = home_Check_date;
    }

    public long getHome_Check_checkListId() {
        return home_Check_checkListId;
    }

    public void setHome_Check_checkListId(long home_Check_checkListId) {
        this.home_Check_checkListId = home_Check_checkListId;
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