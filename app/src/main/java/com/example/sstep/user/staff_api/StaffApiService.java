package com.example.sstep.user.staff_api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StaffApiService {

    //직원 정보 입력 시 직원 등록 => null로 저장되어있던 직원 정보를 입력된 정보로 update 과정
    @POST("/{storeId}/{staffId}/update-staff")
    Call<Void> updateStaff(
                @Path("storeId") Long storeId,
                @Path("staffId") Long staffId,
                @Body StaffRequestDto staffRequestDto
    );
    //직원 목록 조회
    @GET("/store/{storeId}/staffs")
    Call<StaffModel> getStaffsByStoreId(@Path("storeId") Long storeId);



}



/*~~ 참고

    //직원 정보 입력 시 직원 등록 => null로 저장되어있던 직원 정보를 입력된 정보로 update 과정
    @PostMapping("/{storeId}/{staffId}/update-staff")
    public ResponseEntity<Void> updateStaff(@PathVariable Long storeId, @PathVariable Long staffId, @RequestBody StaffRequestDto staffRequestDto) {
        staffService.updateStaff(storeId, staffId, staffRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //직원별 스케줄 저장
    @PostMapping("/{staffId}}/add-schedule")
    public ResponseEntity<Void> registerSchedule(@PathVariable Long staffId, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        staffService.saveSchedule(scheduleRequestDto, staffId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //직원별 캘린더(일정) 저장
    @PostMapping("/{staffId}/add-calendar")
    public ResponseEntity<Void> registerCalendar(@PathVariable Long staffId, @RequestBody CalendarRequestDto calendarRequestDto) {
        staffService.saveCalendar(calendarRequestDto, staffId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //직원별 실시간 출근정보 저장
    @PostMapping("/{staffId}/add-commute")
    public ResponseEntity<Void> registerCommute(@PathVariable Long staffId, @RequestBody CommuteRequestDto commuteRequestDto) {
        staffService.saveCommute(commuteRequestDto, staffId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //직원별 실시간 퇴근정보 저장
    @PostMapping("/{staffId}/{commuteId}/update-commute")
    public ResponseEntity<Void> updateCommute(@PathVariable Long staffId, @PathVariable Long commuteId, @RequestBody CommuteRequestDto commuteRequestDto) {
        staffService.updateCommute(staffId, commuteId, commuteRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //이의 신청 시 메시지 업데이트
    @PostMapping("/{staffId}/{commuteId}/dispute")
    public ResponseEntity<Void> DisputeCommute(@PathVariable Long staffId, @PathVariable Long commuteId, @RequestBody CommuteRequestDto commuteRequestDto) {
        staffService.disputeCommute(staffId, commuteId, commuteRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //이의 신청 사항 처리 완료시 메시지 삭제
    @PostMapping("/{staffId}/{commuteId}/update-dispute")
    public ResponseEntity<Void> UpdateDisputeCommute(@PathVariable Long staffId, @PathVariable Long commuteId, @RequestBody CommuteRequestDto commuteRequestDto) {
        staffService.UpdateDisputeCommute(staffId, commuteId, commuteRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //해당 직원의 이의 신청 리스트 가져오기
    @GetMapping("/{storeId}/{staffId}/disputelist")
    public List<Commute> getDisputeList(@PathVariable Long storeId, @PathVariable Long staffId) {
        return staffService.getDisputeList(storeId, staffId);
    }

    //공지사항 등록
    @PostMapping("/{staffId}/add-notice")
    public ResponseEntity<Void> registerNotice(@PathVariable Long staffId,
                                               @RequestBody NoticeRequestDto noticeRequestDto, @RequestBody MultipartFile[] multipartFile) throws IOException {
        staffService.saveNotice(staffId, noticeRequestDto, multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


*/