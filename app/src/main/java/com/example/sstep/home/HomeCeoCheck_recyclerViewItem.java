package com.example.sstep.home;

import java.util.ArrayList;

public class HomeCeoCheck_recyclerViewItem {
    public String title, staffCnt, date;
    public String meaning;

    // 아이템 데이터 초기화
    public HomeCeoCheck_recyclerViewItem(String title, String staffCnt, String date) {
        this.title = title;
        this.staffCnt = staffCnt;
        this.date = date;
    }

    // 입력받은 숫자의 리스트 생성
    public static ArrayList<HomeCeoCheck_recyclerViewItem> createContactsList(int numContacts) {
        ArrayList<HomeCeoCheck_recyclerViewItem> contacts = new ArrayList<HomeCeoCheck_recyclerViewItem>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new HomeCeoCheck_recyclerViewItem("할 일 제목임", "직원명 외 1명", "날짜")); // DB에서 받아서 제목 넣기
        }

        return contacts;
    }
}
