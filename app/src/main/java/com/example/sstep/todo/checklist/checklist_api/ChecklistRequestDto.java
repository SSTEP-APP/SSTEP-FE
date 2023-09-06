package com.example.sstep.todo.checklist.checklist_api;

import org.springframework.web.multipart.MultipartFile;

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
    private long id; //체크 리스트 고유번호
    private String title; //체크 리스트 제목
    private String date; //체크 리스트 작성 일자
    private String contents; //체크 리스트 내용
    private String endDay; //체크 리스트 마감 일자
    private boolean needPhoto; //체크 리스트 사진 필수 여부
    private boolean isComplete; //체크 리스트 완료 여부
    private String memo; //체크 리스트 완료 시 메모
    private MultipartFile[] multipartFiles; //사진 배열
    private long categoryId; //카테고리 고유번호
    private String categoryName; //카테고리 명
    private Set<CheckListManagerRequestDto> checkListManagersRequestDto; //체크 리스트 담당자 배열

    public ChecklistRequestDto(String title, String date, String contents ,String endDay, boolean needPhoto, boolean isComplete, String categoryName) {
        this.title = title;
        this.date = date;
        this.contents = contents;
        this.endDay = endDay;
        this.needPhoto = needPhoto;
        this.isComplete = isComplete;
        this.categoryName = categoryName;
    }
}
