package com.example.sstep.store.store_api;

import com.example.sstep.user.staff_api.StaffInviteResponseDto;
import com.example.sstep.user.staff_api.StaffModel;
import com.example.sstep.user.staff_api.StaffRequestDto;
import com.example.sstep.user.staff_api.StaffResponseDto;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StoreApiService {

    //사업장 등록 => 등록한 사람은 바로 직원으로 추가, 사장으로 취급
    @POST("/store/register")
    Call<Void> registerStore(@Body StoreRegisterReqDto dto);


    //직원 목록 조회
    //해당 사업장의 직원 목록 조회
    @GET("/store/{storeId}/staffs")
    Call<Set<StaffResponseDto>> getStaffsByStoreId(@Path("storeId") Long storeId);


    //사업장 코드로 사업장 조회
    @GET("/store/{storeCode}")
    Call<StoreResponseDto> getStore(@Path("storeCode") Long storeCode); // 출퇴근 정보

    //직원 초대 => 사업장 코드 전송
    @POST("/store/invite/staff")
    Call<Void> inviteStaffToStore(@Body StaffRequestDto dto);

    //직원이 사업장 코드 입력시
    @POST("/store/{staffId}/input-code/staff")
    Call<Void> inputCode(@Path("staffId") long staffId);

    //직원 추가 => 사업장 코드 입력 후 사장이 승인을 받아줬을 경우
    @POST("/store/add/staff")
    Call<Void> addStaffToStore(@Body StaffRequestDto dto);

    //초대 여부가 true 직원 리스트 가져오기
    @GET("/store/{storeId}/invite-staffs")
    Call<Set<StaffInviteResponseDto>> getInviteStaffs(@Path("storeId") Long storeId);

    //코드 입력 여부가 true인 직원 리스트 가져오기
    @GET("/store/{storeId}/input-code/staffs")
    Call<Set<StaffInviteResponseDto>> getInputCodeStaffs(@Path("storeId") Long storeId);
}
