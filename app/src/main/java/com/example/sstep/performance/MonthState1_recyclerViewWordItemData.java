package com.example.sstep.performance;

import com.example.sstep.alarm.Alarm1_recyclerViewWordItemData;

import java.util.ArrayList;

public class MonthState1_recyclerViewWordItemData {
    public String name, num;
    public String meaning;

    // 아이템 데이터 초기화
    public MonthState1_recyclerViewWordItemData(String name, String num) {
        this.name = name;
        this.num = num;
    }

    // 입력받은 숫자의 리스트 생성
    public static ArrayList<MonthState1_recyclerViewWordItemData> createContactsList(int numContacts) {
        ArrayList<MonthState1_recyclerViewWordItemData> contacts = new ArrayList<MonthState1_recyclerViewWordItemData>();

        contacts.add(new MonthState1_recyclerViewWordItemData("유지수", "2회"));
        contacts.add(new MonthState1_recyclerViewWordItemData("김종운", "1회"));
        contacts.add(new MonthState1_recyclerViewWordItemData("김유경", "0회"));


        return contacts;
    }
}
