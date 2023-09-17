package com.example.sstep.date.date_api;


import com.example.sstep.photo_api.PhotoResponseDto;

import java.time.DayOfWeek;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CalendarResponseDto {
    private long id; //일정 고유번호
    private String calendarDate; //일자
    private DayOfWeek dayOfWeek; //요일
    private String startCalTime; //근무 시작 시간
    private String endCalTime; //근무 종료 시간
    private String staffName; //직원 이름

    public CalendarResponseDto(long id, String calendarDate, DayOfWeek dayOfWeek, String startCalTime, String endCalTime, String staffName) {
        this.id = id;
        this.calendarDate = calendarDate;
        this.dayOfWeek = dayOfWeek;
        this.startCalTime = startCalTime;
        this.endCalTime = endCalTime;
        this.staffName = staffName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
