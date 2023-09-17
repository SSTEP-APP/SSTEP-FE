package com.example.sstep.commute.commute_api;


import java.time.DayOfWeek;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommuteRequestDto {
    private String commuteDate; //출퇴근 일자
    private DayOfWeek dayOfWeek; //출퇴근 요일
    private String startTime; //출근 시간
    private String endTime; //근무 종료 시간
    private boolean isLate; //지각 여부
    private String disputeMessage; //출퇴근 관련 이의 신청 메시지
    private String disputeStartTime; //정정 출근 시간
    private String disputeEndTime; //정정 퇴근 시간

    public CommuteRequestDto(String commuteDate, DayOfWeek dayOfWeek, String startTime, String endTime, boolean isLate, String disputeMessage, String disputeStartTime, String disputeEndTime) {
        this.commuteDate = commuteDate;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isLate = isLate;
        this.disputeMessage = disputeMessage;
        this.disputeStartTime = disputeStartTime;
        this.disputeEndTime = disputeEndTime;
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
