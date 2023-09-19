package com.example.sstep.document.healthdoc_api;

import com.example.sstep.document.work_doc_api.PhotoResponseDto;
import com.example.sstep.document.work_doc_api.WorkDocResponseDto;

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

public interface HealthDocApiService {

    //보건증 등록
    @POST("/health-doc/{staffId}/add")
    Call<Void> registerHealthDoc(@Path("staffId") Long staffId, @Body HealthDocRequestDto healthDocRequestDto);

    //직원별 보건증 상세 정보 조회
    @GET("/health-doc/{staffId}/detail")
    Call<HealthDocResponseDto> getHealthDoc(@Path("staffId") Long staffId);

    //보건증을 등록한 직원 목록
    @GET("/health-doc/{storeId}/reg/health-doc/staffs")
    Call<Set<HealthDocResponseDto>> getRegHealthDocStaffs(@Path("storeId") Long storeId);

    //보건증을 미 등록한 직원 목록
    @GET("/health-doc/{storeId}/un-reg/health-doc/staffs")
    Call<Set<HealthDocResponseDto>> getUnRegHealthDocStaffs(@Path("storeId") Long storeId);



}


