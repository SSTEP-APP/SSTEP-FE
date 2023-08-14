package com.example.sstep.user.staff_api;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffRequestDto {
    private String username; //회원 아이디
    private long code; //사업장 초대 코드 번호
    private long id; //직원 고유번호
    private Date startDay; //입사일
    private String paymentDate; //급여지급일

    private int hourMoney; //시급

    private int wageType; //급여 지급 방식 일급(1), 주급(2), 월급(3)
    private boolean ownerStatus; //사장 여부
    private boolean joinStatus; //합류 여부
    private boolean submitStatus; //코드 입력 여부


    public void setJoinStatus(boolean joinStatus) {
        this.joinStatus = joinStatus;
    }

    public void setOwnerStatus(boolean ownerStatus) {
        this.ownerStatus = ownerStatus;
    }

    public void setSubmitStatus(boolean submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public long getId() {
        return id;
    }

    public Date getStartDay() {
        return startDay;
    }

    public int getHourMoney() {
        return hourMoney;
    }

    public int getWageType() {
        return wageType;
    }

    public String getUsername() {
        return username;
    }

    public long getCode() {
        return code;
    }

    public boolean isOwnerStatus() {
        return ownerStatus;
    }

    public boolean isJoinStatus() {
        return joinStatus;
    }

    public boolean isSubmitStatus() {
        return submitStatus;
    }

    public StaffRequestDto(String username, long code, Date startDay, String paymentDate, int hourMoney, int wageType, boolean ownerStatus, boolean joinStatus, boolean submitStatus) {
        this.username = username;
        this.code = code;
        this.startDay = startDay;
        this.paymentDate = paymentDate;
        this.hourMoney = hourMoney;
        this.wageType = wageType;
        this.ownerStatus = ownerStatus;
        this.joinStatus = joinStatus;
        this.submitStatus = submitStatus;
    }
}
