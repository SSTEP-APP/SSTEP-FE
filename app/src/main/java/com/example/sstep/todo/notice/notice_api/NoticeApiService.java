package com.example.sstep.todo.notice.notice_api;

import com.example.sstep.user.staff_api.ScheduleRequestDto;
import com.example.sstep.user.staff_api.StaffInviteResponseDto;
import com.example.sstep.user.staff_api.StaffRequestDto;

import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NoticeApiService {

    //공지사항 등록
    @POST("/notice/{staffId}/add-notice")
    Call<Void> registerNotice(@Path("staffId") Long staffId, @Body NoticeRequestDto noticeRequestDto);

    //공지사항 상세 정보 조회
    @GET("/notice/{noticeId}/detail")
    Call<NoticeResponseDto> getNotice(@Path("noticeId") Long noticeId);

    //사업장 내 전체 공지사항 목록 조회
    @GET("/notice/{storeId}/notices")
    Call<Set<NoticeResponseDto>> getNotices(@Path("storeId") Long storeId);
}