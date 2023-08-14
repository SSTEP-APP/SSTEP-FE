package com.example.sstep.alarm;

import java.util.ArrayList;

public class Alarm1_recyclerViewWordItemData {
    public String name, content, category, date;
    public String meaning;

    // 아이템 데이터 초기화
    public Alarm1_recyclerViewWordItemData(String name, String content, String category, String date) {
        this.name = name;
        this.content = content;
        this.category = category;
        this.date = date;
    }

    // 입력받은 숫자의 리스트 생성
    public static ArrayList<Alarm1_recyclerViewWordItemData> createContactsList(int numContacts) {
        ArrayList<Alarm1_recyclerViewWordItemData> contacts = new ArrayList<Alarm1_recyclerViewWordItemData>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Alarm1_recyclerViewWordItemData("이름", "내용", "카테고리", "날짜")); // DB에서 받아서 제목 넣기
        }

        return contacts;
    }
}
