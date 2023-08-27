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

    /*
    //직원 목록 조회
    @GET("/store/{storeId}/staffs")
    Call<List<Staff>> getStaffsByStoreId(@Path("storeId") Long storeId);
     */

    //직원 조회
    @GET("/store/{staffId}")
    Call<StaffResponseDto> getStaffByStaffId(@Path("staffId") Long staffId);

    //사업장 조회
    @GET("/store/{storeCode}")
    Call<StoreResponseDto> getStore(@Path("storeCode") Long storeCode);

    //직원 초대 => 사업장 코드 전송
    @POST("/store/invite/staff")
    Call<Void> inviteStaffToStore(@Body StaffRequestDto dto);

    /*
    //직원이 사업장 코드 입력시
    @POST("/store/input-code/staff")
    Call<Void> inputCode(@Body StaffRequestDto dto);
     */
    @POST("/store/{staffId}/input-code/staff")
    Call<Void> inputCode(@Path("staffId") long staffId);

    //직원 추가 => 사업장 코드 입력 후 사장이 승인을 받아줬을 경우
    @POST("/store/add/staff")
    Call<Void> addStaffToStore(@Body StaffRequestDto dto);

    //초대 여부가 true 직원 리스트 가져오기
    @GET("/store/{storeId}/invite-staffs")
    Call<List<StaffInviteResponseDto>> getInviteStaffs(@Path("storeId") Long storeId);

    //코드 입력 여부가 true인 직원 리스트 가져오기
    @GET("/store/{storeId}/input-code/staffs")
    Call<List<StaffInviteResponseDto>> getInputCodeStaffs(@Path("storeId") Long storeId);

    /*
    //해당 날짜에 근무하는 직원 리스트 가져오기
    @GET("/store/{storeId}/day-work-staffs")
    Call<List<Staff>> getDayWorkStaffs(@Path("storeId") Long storeId, @Body CalendarRequestDto calendarRequestDto);

    //이의 신청한 직원 리스트 가져오기
    @GET("/store/{storeId}/dispute-staffs")
    Call<List<Staff>> getDisputeStaffs(@Path("storeId") Long storeId);

    //사업장 내 전체 공지사항 목록 조회
    @GET("/store/{storeId}/notices")
    Call<List<Notice>> getNotices(@Path("storeId") Long storeId);
     */
}



/*~~ 참고


    //직원 추가 => 사업장 코드 입력 후 사장이 승인을 받아줬을 경우
    @POST("/store/{code}/add/staff")
    Call<Void> addStaffToStore(@Path("code") Long code, @Body StaffRequestDto staffRequestDto);
    public ResponseEntity<Void> addStaffToStore(@PathVariable Long code, @RequestBody StaffRequestDto staffRequestDto) {
        storeService.addStaffToStore(code, staffRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @POST("/{code}/add/staff")
    Call<Void> addStaffToStore(@Path("code") Long code, @Body StaffRequestDto staffRequestDto);




    //사업장 등록 => 등록한 사람은 바로 직원으로 추가, 사장으로 취급
    @PostMapping("/register")
    public ResponseEntity<Void> registerStore(@RequestBody StoreRequestDto storeRequestDto, StaffRequestDto staffRequestDto) {
        storeService.saveStore(storeRequestDto); //사업장 등록 로직
        storeService.setOwner(storeRequestDto, staffRequestDto);//사업장 등록한 사람을 사장으로 취급하는 로직
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //직원 목록 조회
    @GetMapping("/{storeId}/staffs")
    public List<Staff> getStaffsByStoreId(@PathVariable Long storeId) {
        return storeService.getStaffsByStoreId(storeId);
    }

    //직원 추가 => 사업장 코드 입력 후 사장이 승인을 받아줬을 경우
    @PostMapping("/{code}/add/staff")
    public ResponseEntity<Void> addStaffToStore(@PathVariable Long code, @RequestBody StaffRequestDto staffRequestDto) {
        storeService.addStaffToStore(code, staffRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //합류 여부가 false인 직원 리스트 가져오기
    @GetMapping("/{storeId}/unregister-staffs")
    public List<Staff> getUnRegStaffs(@PathVariable Long storeId) {
        return storeService.getUnRegStaffs(storeId);
    }

    //해당 날짜에 근무하는 직원 리스트 가져오기
    @GetMapping("/{storeId}/daywork-staffs")
    public List<Staff> getDayWorkStaffs(@PathVariable Long storeId, @RequestBody CalendarRequestDto calendarRequestDto) {
        return storeService.getDayWorkStaffs(storeId, calendarRequestDto);
    }

    //이의 신청한 직원 리스트 가져오기
    @GetMapping("/{storeId}/dispute-staffs")
    public List<Staff> getDisputeStaffs(@PathVariable Long storeId) {
        return storeService.getDisputeStaffs(storeId);
    }

    //사업장 내 전체 공지사항 목록 조회
    @GetMapping("/{storeId}/notices")
    public List<Notice> getNotices(@PathVariable Long storeId) {
        return storeService.getNotices(storeId);
    }


*/