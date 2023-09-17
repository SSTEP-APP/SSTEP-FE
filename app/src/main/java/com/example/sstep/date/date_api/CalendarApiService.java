package com.example.sstep.date.date_api;

import com.example.sstep.todo.notice.notice_api.NoticeRequestDto;
import com.example.sstep.todo.notice.notice_api.NoticeResponseDto;

import java.time.DayOfWeek;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CalendarApiService {

    //직원별 캘린더(일정) 저장
    @POST("/calendar/{staffId}/add-calendar")
    Call<Void> registerCalendar(@Path("staffId") Long staffId, @Body CalendarRequestDto calendarRequestDto);

    //해당 날짜에 근무하는 직원 리스트 가져오기
    @GET("/calendar/{storeId}/{date}/{day}/day-work-staffs")
    Call<Set<CalendarResponseDto>> getDayWorkStaffs(
            @Path("storeId") Long storeId,
            @Path("date") String date,
            @Path("day") DayOfWeek day
    );
}