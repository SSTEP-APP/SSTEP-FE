package com.example.sstep.todo.checklist;

import java.util.ArrayList;

public class CheckList1_recyclerViewWordItemData {
    public String word;
    public String meaning;
    public long ctId;

    // 화면에 표시될 문자열 초기화
    public CheckList1_recyclerViewWordItemData(String word, long ctId) {
        this.word = word;
        this.ctId = ctId;
    }

    // 입력받은 숫자의 리스트생성
    public static ArrayList<CheckList1_recyclerViewWordItemData> createContactsList(int numContacts, String word, long id) {
        ArrayList<CheckList1_recyclerViewWordItemData> contacts = new ArrayList<CheckList1_recyclerViewWordItemData>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new CheckList1_recyclerViewWordItemData(word, id)); // DB에서 받아서 제목과 id 넣기
        }

        return contacts;
    }
}
