package com.example.sstep.commute.commute_api;

import com.example.sstep.store.store_api.StoreRegisterReqDto;
import com.example.sstep.store.store_api.StoreResponseDto;
import com.example.sstep.user.staff_api.StaffInviteResponseDto;
import com.example.sstep.user.staff_api.StaffRequestDto;
import com.example.sstep.user.staff_api.StaffResponseDto;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommuteApiService {

    //직원별 실시간 출근정보 저장
    @POST("/commute/{staffId}/add-commute")
    Call<Void> registerCommute(@Path("staffId") Long staffId, @Body CommuteRequestDto commuteRequestDto);

    //직원별 실시간 퇴근정보 저장
    @POST("/commute/{staffId}/{nowDate}/update-commute")
    Call<Void> updateCommute(@Path("staffId") Long staffId, @Path("nowDate") String nowDate, @Body CommuteRequestDto commuteRequestDto);

    //출퇴근 정보
    @GET("/commute/{staffId}/{date}")
    Call<CommuteResponseDto> getCommute(@Path("staffId") Long staffId, @Path("date") String date);

    //직원의 전체 출퇴근 정보
    @GET("/commute/{staffId}/commute-info")
    Call<Set<CommuteResponseDto>> getCommutes(@Path("staffId") Long staffId);

    //이의 신청 시 메시지 업데이트 + 정정시간 설정
    @POST("/commute/{commuteId}/dispute")
    Call<Void> disputeCommute(@Path("commuteId") Long commuteId, @Body CommuteRequestDto commuteRequestDto);

    //사장에게 이의신청 내용 보여주기
    @GET("/commute/{commuteId}/dispute")
    Call<CommuteResponseDto> getDispute(@Path("commuteId") Long commuteId);

    //이의 신청 사항 처리 완료시 메시지 삭제
    @POST("/commute/{staffId}/update-dispute")
    Call<Void> updateDisputeCommute(@Path("staffId") Long staffId, @Body CommuteRequestDto commuteRequestDto);

    //해당 사업장의 이의 신청 리스트 가져오기
    @GET("/commute/{storeId}/dispute-list")
    Call<Set<CommuteResponseDto>> getDisputeList(@Path("storeId") Long storeId);
}