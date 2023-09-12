package com.example.sstep.photo_api;

import com.example.sstep.store.store_api.StoreRegisterReqDto;
import com.example.sstep.store.store_api.StoreResponseDto;
import com.example.sstep.user.staff_api.StaffInviteResponseDto;
import com.example.sstep.user.staff_api.StaffRequestDto;
import com.example.sstep.user.staff_api.StaffResponseDto;

import java.util.Set;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PhotoApiService {
    //사진 등록
    @Multipart
    @POST("/photo/add")
    Call<Long> savePhoto(@Part MultipartBody.Part file);
}
