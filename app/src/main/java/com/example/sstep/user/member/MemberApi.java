package com.example.sstep.user.member;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MemberApi {
    @POST("member/")
    Call<MemberResponseDto> saveMember(@Body MemberRequestDto memberRequestDto);
}