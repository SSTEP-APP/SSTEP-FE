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

        contacts.add(new Alarm1_recyclerViewWordItemData("이름1", "[식기 세척하기] 해야할 일 마감기한까지 1일 남았습니다.", "해야할 일", "2023-09-19"));
        contacts.add(new Alarm1_recyclerViewWordItemData("이름2", "[홀] 카테고리가 추가되었습니다.", "알림", "2023-09-20"));
        contacts.add(new Alarm1_recyclerViewWordItemData("이름3", "[새 레시피 숙지 바람] 공지가 추가되었습니다.", "공지사항", "2023-09-20"));


        return contacts;
    }
}
