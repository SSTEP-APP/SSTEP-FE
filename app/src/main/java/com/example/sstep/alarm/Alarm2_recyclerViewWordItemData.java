package com.example.sstep.alarm;

import java.util.ArrayList;

public class Alarm2_recyclerViewWordItemData {
    public String name, content, category, date;
    public String meaning;

    // 아이템 데이터 초기화
    public Alarm2_recyclerViewWordItemData(String name, String content, String category, String date) {
        this.name = name;
        this.content = content;
        this.category = category;
        this.date = date;
    }

    // 입력받은 숫자의 리스트 생성
    public static ArrayList<Alarm2_recyclerViewWordItemData> createContactsList(int numContacts) {
        ArrayList<Alarm2_recyclerViewWordItemData> contacts = new ArrayList<Alarm2_recyclerViewWordItemData>();

        contacts.add(new Alarm2_recyclerViewWordItemData("이름1", "[청소] 카테고리가 추가되었습니다.", "", "2023-09-19"));
        contacts.add(new Alarm2_recyclerViewWordItemData("이름2", "[홀] 카테고리가 추가되었습니다.", "", "2023-09-20"));
        contacts.add(new Alarm2_recyclerViewWordItemData("이름3", "[주방] 카테고리가 추가되었습니다.", "", "2023-09-20"));

        return contacts;
    }
}
