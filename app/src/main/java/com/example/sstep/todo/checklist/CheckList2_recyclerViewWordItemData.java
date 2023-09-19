package com.example.sstep.todo.checklist;

import java.util.ArrayList;

public class CheckList2_recyclerViewWordItemData {
    public String word;

    // 화면에 표시될 문자열 초기화
    public CheckList2_recyclerViewWordItemData(String word) {
        this.word = word;
    }

    // 입력받은 숫자의 리스트생성
    public static ArrayList<CheckList2_recyclerViewWordItemData> createContactsList(int numContacts) {
        ArrayList<CheckList2_recyclerViewWordItemData> contacts = new ArrayList<CheckList2_recyclerViewWordItemData>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new CheckList2_recyclerViewWordItemData("마감시 에어컨 끄기")); //DB에서 받아서 제목 넣기
        }

        return contacts;
    }
}
