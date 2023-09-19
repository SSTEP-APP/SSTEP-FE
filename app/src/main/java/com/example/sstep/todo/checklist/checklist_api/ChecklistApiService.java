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
    @GET("/check-list/{staffId}/{categoryId}/{date}/complete-checklists")
    Call<Set<CheckListResponseDto>> getCompleteCheckListsByCategory(
            @Path("staffId") Long staffId,
            @Path("categoryId") Long categoryId,
            @Path("date") String date
    );

    //카테고리 별 체크 리스트 미완료 목록
    @GET("/check-list/{staffId}/{categoryId}/{date}/uncompleted-checklists")
    Call<Set<CheckListResponseDto>> getUnCompletedCheckListsByCategory(
            @Path("staffId") Long staffId,
            @Path("categoryId") Long categoryId,
            @Path("date") String date
    );

    // 사업장 전체에 해당하는 체크리스트 목록을 가져오는 엔드포인트
    @GET("/check-list/{storeId}/store-checklists")
    Call<Set<CheckListResponseDto>> getStoreCheckLists(@Path("storeId") Long storeId);

    // 사업장 전체에 해당하는 체크리스트 미완료 목록을 가져오는 엔드포인트
    @GET("/check-list/{storeId}/{categoryId}/{date}/store/uncompleted-checklists")
    Call<Set<CheckListResponseDto>> getStoreUnCompletedCheckListsByCategory(
            @Path("storeId") Long storeId,
            @Path("categoryId") Long categoryId,
            @Path("date") String date
    );

    // 사업장 전체에 해당하는 체크리스트 완료 목록을 가져오는 엔드포인트
    @GET("/check-list/{storeId}/{categoryId}/{date}/store/complete-checklists")
        Call<Set<CheckListResponseDto>> getStoreCompleteCheckListsByCategory(
            @Path("storeId") Long storeId,
            @Path("categoryId") Long categoryId,
            @Path("date") String date
    );
    //사업장 전체에 해당하는 직원 별 체크리스트 미완료 목록
    @GET("/check-list/{staffId}/store-staff/uncompleted-checklists")
    Call<Set<CheckListResponseDto>> getStoreByStaffUnCompletedCheckListsByCategory(@Path("staffId") Long staffId);

}

