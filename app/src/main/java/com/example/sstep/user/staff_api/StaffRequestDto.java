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
    private long id; //직원 고유번호
    private String startDay; //입사일
    private String paymentDate; //급여지급일

    private int hourMoney; //시급

    private long member_id;

    private long store_id;

    private int wageType; //급여 지급 방식 일급(1), 주급(2), 월급(3)
    private boolean ownerStatus; //사장 여부
    private boolean joinStatus; //합류 여부

    public void setJoinStatus(boolean joinStatus) {
        this.joinStatus = joinStatus;
    }

    public void setOwnerStatus(boolean ownerStatus) {
        this.ownerStatus = ownerStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public long getId() {
        return id;
    }


    public int getHourMoney() {
        return hourMoney;
    }

    public int getWageType() {
        return wageType;
    }

    public StaffRequestDto(String startDay, String paymentDate, int hourMoney, long member_id, long store_id, int wageType, boolean ownerStatus, boolean joinStatus) {
        this.startDay = startDay;
        this.paymentDate = paymentDate;
        this.hourMoney = hourMoney;
        this.member_id = member_id;
        this.store_id = store_id;
        this.wageType = wageType;
        this.ownerStatus = ownerStatus;
        this.joinStatus = joinStatus;
    }
}
