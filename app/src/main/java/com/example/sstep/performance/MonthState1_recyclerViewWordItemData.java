package com.example.sstep.performance;

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

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new MonthState1_recyclerViewWordItemData("이름", "지각 횟수")); // DB에서 받아서 제목 넣기
        }

        return contacts;
    }
}
