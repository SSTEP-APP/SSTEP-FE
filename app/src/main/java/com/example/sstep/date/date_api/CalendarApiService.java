package com.example.sstep.date.date_api;

import com.example.sstep.todo.notice.notice_api.NoticeRequestDto;
import com.example.sstep.todo.notice.notice_api.NoticeResponseDto;

import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CalendarApiService {

    @POST("/calendar/{staffId}/add-calendar")
    Call<Void> registerCalendar(@Path("staffId") Long staffId, @Body CalendarRequestDto calendarRequestDto);

    @GET("/calendar/{storeId}/day-work-staffs")
    Call<Set<CalendarResponseDto>> getDayWorkStaffs(@Path("storeId") Long storeId, @Body CalendarRequestDto calendarRequestDto);
}