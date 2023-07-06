package com.example.sstep.user.member;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MemberApiService {
    @FormUrlEncoded
    @POST("member")
    Call<String> join(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("password") String password
    );

    @GET("member/{memberId}")
    Call<MemberModel> getDataFromServer(@Path("memberId") String memberId);
}
