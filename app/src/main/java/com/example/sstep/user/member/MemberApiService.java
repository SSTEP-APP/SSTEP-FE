package com.example.sstep.user.member;

import com.example.sstep.store.store_api.StoreResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MemberApiService {

    @POST("member/join")
    Call<MemberResponseDto> save(@Body MemberRequestDto memberRequestDto);
    @GET("member/{username}")
    Call<MemberModel> getDataFromServer(@Path("username") String memberId);

    //회원이 소속된 사업장 리스트
    @GET("member/{username}/stores")
    Call<List<StoreResponseDto>> getStoresBelongMember(@Path("username") String username);

    //이름 & 전화번호로 회원 조회
    @GET("member/{name}/{phoneNum}")
    Call<MemberResponseDto> getMemberByNameAndPhoneNum(
            @Path("name") String name,
            @Path("phoneNum") String phoneNum
    );

    // 추가
    //회원가입
    @POST("/member/join")
    Call<Void> joinMember(@Body MemberRequestDto memberRequestDto);

    //아이디로 회원 조회
    @GET("/member/{username}")
    Call<MemberResponseDto> getMemberByUsername(@Path("username") String username);

    //아이디를 통한 중복 체크
    @POST("/member/check/duplicate")
    Call<String> checkDuplicateUsername(@Body MemberRequestDto memberRequestDto);
}
