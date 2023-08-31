package com.example.sstep.document.work_doc_api;

import java.util.Set;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface WorkDocApiService {

    //근로 계약서 작성에 필요한 정보 가져오기
    @GET("/work-doc/{storeId}/{staffId}/info")
    Call<WorkDocResponseDto> getInfoForWorkDoc(
            @Path("staffId") Long staffId,
            @Path("storeId") Long storeId
    );

    //근로 계약서 1차 등록
    @Multipart

    @POST("/work-doc/{staffId}/add/first")
    Call<ResponseBody> registerWorkDocFirst(
            @Path("staffId") Long staffId,
            @Part MultipartBody.Part file
    );

    //1차 등록한 계약서(사진) 정보
    @GET("/{staffId}/first")
    Call<PhotoResponseDto> getFirstWorkDoc(@Path("staffId") Long staffId);

    //근로 계약서 2차(최종) 등록
    @POST("/{staffId}/add/second")
    Call<Void> registerWorkDocSecond(@Path("staffId") Long staffId, @Body MultipartBody.Part multipartFile);

    //근로 계약서 등록한 직원 목록
    @GET("/{storeId}/reg/work-doc/staffs")
    Call<Set<WorkDocResponseDto>> getRegWorkDocStaffs(@Path("storeId") Long storeId);

    //근로 계약서 미 등록한 직원 목록
    @GET("/{storeId}/un-reg/work-doc/staffs")
    Call<Set<WorkDocResponseDto>> getUnRegWorkDocStaffs(@Path("storeId") Long storeId);



}


