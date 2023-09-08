package com.example.sstep.todo.checklist.checklist_api;


import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChecklistRequestDto {
    private String title; //체크 리스트 제목
    private String date; //체크 리스트 작성 일자
    private String contents; //체크 리스트 내용
    private String endDay; //체크 리스트 마감 일자
    private long categoryId; //카테고리 고유번호
    private String categoryName; //카테고리 명
    private boolean needPhoto; //체크 리스트 사진 필수 여부
    private boolean isComplete; //체크 리스트 완료 여부
    private String memo; //체크 리스트 완료 시 메모
    private long photoId; //사진 고유 번호

    public ChecklistRequestDto(String title, String date, String contents ,String endDay, boolean needPhoto, boolean isComplete, String categoryName, long categoryId) {
        this.title = title;
        this.date = date;
        this.contents = contents;
        this.endDay = endDay;
        this.needPhoto = needPhoto;
        this.isComplete = isComplete;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isNeedPhoto() {
        return needPhoto;
    }

    public void setNeedPhoto(boolean needPhoto) {
        this.needPhoto = needPhoto;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }
}
