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
public class CheckListManagerRequestDto {
    private long id; //체크 리스트 담당자 고유번호
    private String name; //체크 리스트 담당자 이름
    private String phoneNum; //체크 리스트 담당자 연락처

    public CheckListManagerRequestDto(long id, String name, String phoneNum) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
    }
}
