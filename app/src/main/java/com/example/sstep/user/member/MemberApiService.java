package com.example.sstep.user.member;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MemberApiService {

    @POST("/member/join")
    Call<MemberResponseDto> save(@Body MemberRequestDto memberRequestDto);
    @GET("member/{memberId}")
    Call<MemberModel> getDataFromServer(@Path("memberId") String memberId);

}
