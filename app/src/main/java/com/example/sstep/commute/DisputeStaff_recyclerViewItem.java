package com.example.sstep.commute;

import android.widget.Button;

import com.example.sstep.document.certificate.PaperH_Reg_recyclerViewItem;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class DisputeStaff_recyclerViewItem {
    private String commuteDate, startTime, endTime;
    private DayOfWeek dayOfWeek;

    public String getCommuteDate() {
        return commuteDate;
    }

    public void setCommuteDate(String commuteDate) {
        this.commuteDate = commuteDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public OnItemClickListener getOnItemClickListener() {
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