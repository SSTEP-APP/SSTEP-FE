package com.example.sstep.date;

import com.example.sstep.photo_api.PhotoResponseDto;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Set;

public class Date_recyclerViewWordItemData {
    public String calendarDate, startCalTime, endCalTime, staffName;
    public DayOfWeek dayOfWeek;
    public String meaning;

    // 아이템 데이터 초기화
    public Date_recyclerViewWordItemData(String calendarDate, DayOfWeek dayOfWeek, String startCalTime, String endCalTime, String staffName) {
        this.calendarDate = calendarDate; //일자
        this.dayOfWeek = dayOfWeek; //요일
        this.startCalTime = startCalTime; //근무 시작 시간
        this.endCalTime = endCalTime; //근무 종료 시간
        this.staffName = staffName; //직원 이름
    }

    // 입력받은 숫자의 리스트 생성
    public static ArrayList<Date_recyclerViewWordItemData> createContactsList(int numContacts) {
        ArrayList<Date_recyclerViewWordItemData> contacts = new ArrayList<Date_recyclerViewWordItemData>();

        for (int i = 1; i <= numContacts; i++) {
            // 공지 사항 아이템 데이터 생성 및 초기화, 이 부분은 임시로 고정된 값을 사용하지만
            // 실제로는 DB에서 데이터를 받아와서 아이템을 생성하는 용도로 사용될 수 있습니다.
            contacts.add(new Date_recyclerViewWordItemData("일자", DayOfWeek.MONDAY, "근무 시작 시간", "근무 종료 시간", "직원 이름")); // DB에서 받아서 제목 넣기
        }

        return contacts;
    }
}