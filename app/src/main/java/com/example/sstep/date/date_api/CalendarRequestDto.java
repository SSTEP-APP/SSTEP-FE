package com.example.sstep.date.date_api;


import java.time.DayOfWeek;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import okhttp3.MultipartBody;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CalendarRequestDto  {
    private long id; //일정 고유번호
    private String calendarDate; //일자
    private DayOfWeek dayOfWeek; //요일
    private String startCalTime; //근무 시작 시간
    private String endCalTime; //근무 종료 시간

    public CalendarRequestDto(String calendarDate, DayOfWeek dayOfWeek, String startCalTime, String endCalTime) {
        this.calendarDate = calendarDate;
        this.dayOfWeek = dayOfWeek;
        this.startCalTime = startCalTime;
        this.endCalTime = endCalTime;
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
}
