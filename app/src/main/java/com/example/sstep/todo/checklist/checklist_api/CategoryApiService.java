package com.example.sstep.todo.checklist.checklist_api;


import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoryApiService {

    //카테고리 목록 조회
    @GET("/category/{storeId}/categories")
    Call<Set<CategoryResponseDto>> getCategories(@Path("storeId") Long storeId);

    //카테고리 등록
    @POST("/category/{storeId}/add")
    Call<Void> saveCategory(@Path("storeId") Long storeId, @Body CategoryRequestDto categoryRequestDto);
}