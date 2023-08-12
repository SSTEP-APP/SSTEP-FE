package com.example.sstep.todo.notice;

import java.util.ArrayList;

public class Notice_recyclerViewWordItemData {
    public String title, content, name, date;
    public String meaning;

    // 아이템 데이터 초기화
    public Notice_recyclerViewWordItemData(String title, String content, String name, String date) {
        this.title = title;
        this.content = content;
        this.name = name;
        this.date = date;
    }

    // 입력받은 숫자의 리스트 생성
    public static ArrayList<Notice_recyclerViewWordItemData> createContactsList(int numContacts) {
        ArrayList<Notice_recyclerViewWordItemData> contacts = new ArrayList<Notice_recyclerViewWordItemData>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Notice_recyclerViewWordItemData("공지 제목", "공지 내용", "작성자명", "날짜")); // DB에서 받아서 제목 넣기
        }

        return contacts;
    }
}
