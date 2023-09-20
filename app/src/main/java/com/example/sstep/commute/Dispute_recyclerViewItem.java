package com.example.sstep.commute;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class Dispute_recyclerViewItem {
    private String commuteDate, staffName;
    private DayOfWeek dayOfWeek;
    private long staffId;
    private long commuteId;

    public String getCommuteDate() {
        return commuteDate;
    }

    public void setCommuteDate(String commuteDate) {
        this.commuteDate = commuteDate;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public long getCommuteId() {
        return commuteId;
    }

    public void setCommuteId(long commuteId) {
        this.commuteId = commuteId;
    }

    public DisputeStaff_recyclerViewItem.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    // 클릭 이벤트 처리를 위한 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private DisputeStaff_recyclerViewItem.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(DisputeStaff_recyclerViewItem.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void performItemClick(int position) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(position);
        }
    }
}