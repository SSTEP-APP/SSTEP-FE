package com.example.sstep.commute.commute_api;


import java.sql.Date;
import java.time.DayOfWeek;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommuteResponseDto {
    private long staffId; //직원 고유 번호
    private long commuteId; //출퇴근 고유 번호
    private String staffName; //직원 이름
    private String commuteDate; //출퇴근 일자
    private DayOfWeek dayOfWeek; //출퇴근 요일
    private String startTime; //출근 시간
    private String endTime; //퇴근 시간
    private boolean isLate; //지각 여부
    private String disputeMessage; //출퇴근 관련 이의 신청 메시지
    private String disputeStartTime; //정정 출근 시간
    private String disputeEndTime; //정정 퇴근 시간

    public CommuteResponseDto(long staffId, long commuteId, String staffName, String commuteDate, DayOfWeek dayOfWeek, String startTime, String endTime, boolean isLate, String disputeMessage, String disputeStartTime, String disputeEndTime) {
        this.staffId = staffId;
        this.commuteId = commuteId;
        this.staffName = staffName;
        this.commuteDate = commuteDate;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isLate = isLate;
        this.disputeMessage = disputeMessage;
        this.disputeStartTime = disputeStartTime;
        this.disputeEndTime = disputeEndTime;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public long getCommuteId() {
        return commuteId;
    }

    public void setCommuteId(long commuteId) {
        this.commuteId = commuteId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCommuteDate() {
        return commuteDate;
    }

    public void setCommuteDate(String commuteDate) {
        this.commuteDate = commuteDate;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isLate() {
        return isLate;
    }

    public void setLate(boolean late) {
        isLate = late;
    }

    public String getDisputeMessage() {
        return disputeMessage;
    }

    public void setDisputeMessage(String disputeMessage) {
        this.disputeMessage = disputeMessage;
    }

    public String getDisputeStartTime() {
        return disputeStartTime;
    }

    public void setDisputeStartTime(String disputeStartTime) {
        this.disputeStartTime = disputeStartTime;
    }

    public String getDisputeEndTime() {
        return disputeEndTime;
    }

    public void setDisputeEndTime(String disputeEndTime) {
        this.disputeEndTime = disputeEndTime;
    }
}
