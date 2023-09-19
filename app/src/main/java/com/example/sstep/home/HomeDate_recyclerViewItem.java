package com.example.sstep.home;

import com.example.sstep.document.certificate.PaperH_Reg_recyclerViewItem;

import java.time.DayOfWeek;

public class HomeDate_recyclerViewItem {
    private String calendarDate; // 일자
    private DayOfWeek dayOfWeek; // 요일
    private String startCalTime; // 근무 시작 시간
    private String endCalTime; // 근무 종료 시간
    private String staffName; //직원 이름

    public String getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(String calendarDate) {
        this.calendarDate = calendarDate;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public HomeDate_recyclerViewItem.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    // 클릭 이벤트 처리를 위한 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private HomeDate_recyclerViewItem.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(HomeDate_recyclerViewItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void performItemClick(int position) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(position);
        }
    }

}