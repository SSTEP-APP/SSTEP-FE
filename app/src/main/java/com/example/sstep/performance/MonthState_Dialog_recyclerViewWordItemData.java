package com.example.sstep.performance;

import java.util.ArrayList;

public class MonthState_Dialog_recyclerViewWordItemData {
    public String name, num;
    public String meaning;

    // 아이템 데이터 초기화
    public MonthState_Dialog_recyclerViewWordItemData(String name, String num) {
        this.name = name;
        this.num = num;
    }

    // 입력받은 숫자의 리스트 생성
    public static ArrayList<MonthState_Dialog_recyclerViewWordItemData> createContactsList(int numContacts) {
        ArrayList<MonthState_Dialog_recyclerViewWordItemData> contacts = new ArrayList<MonthState_Dialog_recyclerViewWordItemData>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new MonthState_Dialog_recyclerViewWordItemData("이름", "지각 : 3, 결근 : 4")); // DB에서 받아서 제목 넣기
        }

        return contacts;
    }
}
