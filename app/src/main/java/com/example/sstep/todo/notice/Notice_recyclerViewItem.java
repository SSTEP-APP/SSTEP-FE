package com.example.sstep.todo.notice;

import java.util.ArrayList;

public class Notice_recyclerViewItem {
    long noticeId;
    public String title, writeDate, contents, writerName;
    int hits;
    public String meaning;

    // 아이템 데이터 초기화
    public Notice_recyclerViewItem(long noticeId, String title, String writeDate, String contents, String writerName, int hits) {
        this.noticeId = noticeId;
        this.title = title;
        this.writeDate = writeDate;
        this.contents = contents;
        this.writerName = writerName;
        this.hits = hits;
    }

    // 입력받은 숫자의 리스트 생성
    public static ArrayList<Notice_recyclerViewItem> createContactsList(int numContacts) {
        ArrayList<Notice_recyclerViewItem> contacts = new ArrayList<Notice_recyclerViewItem>();

        for (int i = 1; i <= numContacts; i++) {
            // 공지 사항 아이템 데이터 생성 및 초기화, 이 부분은 임시로 고정된 값을 사용하지만
            // 실제로는 DB에서 데이터를 받아와서 아이템을 생성하는 용도로 사용될 수 있습니다.
            contacts.add(new Notice_recyclerViewItem(0L, "공지 제목", "작성일", "공지 내용", "작성자", 0)); // DB에서 받아서 제목 넣기
        }

        return contacts;
    }
}