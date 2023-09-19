package com.example.sstep.user.staff_api;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class StaffModel {

    @SerializedName("id")//직원 고유번호
    private long id;

    @SerializedName("startDay")
    private Date startDay;

    @SerializedName("paymentDate")
    private String paymentDate;

    @SerializedName("hourMoney")
    private int hourMoney;

    @SerializedName("wageType")
    private int wageType;

    @SerializedName("ownerStatus")
    private boolean ownerStatus;

    @SerializedName("joinStatus")
    private boolean joinStatus;



    // 생성자, getter, setter 등 필요한 메서드들을 추가할 수 있습니다.


}
/*~~
    private long id; //직원 고유번호
    private Date startDay; //입사일
    private String paymentDate; //급여지급일

    private int hourMoney; //시급
    private int wageType; //급여 지급 방식 일급(1), 주급(2), 월급(3)
    private boolean ownerStatus; //사장 여부
    private boolean joinStatus; //합류 여부
 */