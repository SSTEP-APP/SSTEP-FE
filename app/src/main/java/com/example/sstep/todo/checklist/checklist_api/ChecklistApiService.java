package com.example.sstep.todo.checklist.checklist_api;

import com.example.sstep.document.healthdoc_api.HealthDocRequestDto;
import com.example.sstep.document.healthdoc_api.HealthDocResponseDto;

import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChecklistApiService {

    //체크 리스트 작성
    @POST("/check-list/{staffId}/add")
    Call<Void> registerCheckList(@Path("staffId") Long staffId, @Body ChecklistRequestDto checkListRequestDto);

    //카테고리 별 체크 리스트 완료 목록
    @GET("/check-list/{storeId}/complete-checklists")
    Call<Set<ChecklistResponseDto>> getCompleteCheckListsByCategory(@Path("storeId") Long storeId, @Body CategoryRequestDto categoryRequestDto);

    //카테고리 별 체크 리스트 미완료 목록
    @GET("/check-list/{storeId}/uncompleted-checklists")
    Call<Set<ChecklistResponseDto>> getUnCompletedCheckListsByCategory(@Path("storeId") Long storeId, @Body CategoryRequestDto categoryRequestDto);

    //체크 리스트 상세 조회
    @GET("/check-list/{checklistId}/detail")
    Call<ChecklistResponseDto> getCheckList(@Path("checklistId") Long checklistId);

    //체크 리스트 완료 처리
    @POST("/check-list/{staffId}/{checklistId}/complete")
    Call<Void> completeCheckList(@Path("staffId") Long staffId, @Path("checklistId") Long checklistId, @Body ChecklistRequestDto checkListRequestDto);

}


