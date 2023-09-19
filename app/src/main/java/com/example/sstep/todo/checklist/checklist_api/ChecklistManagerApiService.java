package com.example.sstep.todo.checklist.checklist_api;


import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChecklistManagerApiService {

    //카테고리 목록 조회
    @POST("/check-manager/{checkListId}/add")
    Call<Void> registerCheckListManager(
            @Path("checkListId") Long checkListId,
            @Body CheckListManagerRequestDto checkListManagerRequestDto
    );

}