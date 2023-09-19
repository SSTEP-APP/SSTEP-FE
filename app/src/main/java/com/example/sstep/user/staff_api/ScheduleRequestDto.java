package com.example.sstep.user.staff_api;


import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@Builder
public class ScheduleRequestDto {
    private long id;
    private DayOfWeek weekDay;
    private String startTime;
    private String endTime;

    public ScheduleRequestDto(DayOfWeek weekDay, String startTime, String endTime) {
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
