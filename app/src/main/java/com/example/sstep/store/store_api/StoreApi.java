package com.example.sstep.store.store_api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StoreApi {
    @POST("store/")
    Call<StoreRequestDto> saveMember(@Body StoreRequestDto memberRequestDto);
}