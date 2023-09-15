package com.example.sstep.date;

import com.example.sstep.photo_api.PhotoResponseDto;

import java.util.Set;

public class Date_recyclerViewWordItemData{
    private String date; // calendarDate
    private String staffName; // staffName
    private String startCalTime; // startCalTime
    private String endCalTime; // endCalTime

    public Date_recyclerViewWordItemData(String date, String staffName, String startCalTime, String endCalTime) {
        this.date = date;
        this.staffName = staffName;
        this.startCalTime = startCalTime;
        this.endCalTime = endCalTime;
    }

    public String getDate() {
        return date;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStartCalTime() {
        return startCalTime;
    }

    public String getEndCalTime() {
        return endCalTime;
    }


}