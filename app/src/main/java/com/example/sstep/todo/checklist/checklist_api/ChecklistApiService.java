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
    Call<Long> registerCheckList(@Path("staffId") Long staffId, @Body ChecklistRequestDto checkListRequestDto);

    //체크 리스트 완료 처리
    @POST("/check-list/{staffId}/{checklistId}/complete")
    Call<Void> completeCheckList(
            @Path("staffId") Long staffId,
            @Path("checklistId") Long checklistId,
            @Body ChecklistRequestDto checkListRequestDto
    );

    //체크 리스트 상세 조회
    @GET("/check-list/{checklistId}/detail")
    Call<CheckListResponseDto> getCheckList(@Path("checklistId") Long checklistId);

    //카테고리 별 체크 리스트 완료 목록
    @GET("/check-list/{staffId}/complete-checklists")
    Call<Set<CheckListResponseDto>> getCompleteCheckListsByCategory(
            @Path("staffId") Long staffId,
            @Body ChecklistRequestDto checkListRequestDto
    );

    //카테고리 별 체크 리스트 미완료 목록
    @GET("/check-list/{staffId}/uncompleted-checklists")
    Call<Set<CheckListResponseDto>> getUnCompletedCheckListsByCategory(
            @Path("staffId") Long staffId,
            @Body ChecklistRequestDto checkListRequestDto
    );

}


