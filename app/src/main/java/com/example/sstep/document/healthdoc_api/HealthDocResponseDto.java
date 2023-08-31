package com.example.sstep.document.healthdoc_api;


import com.example.sstep.document.work_doc_api.PhotoResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthDocResponseDto {
    private long id; //문서 고유번호
    private String name; //직원명
    private String checkUpDate; //보건증 검진일
    private String expirationDate; //보건증 만료일
    private PhotoResponseDto photoResponseDto; //사진 정보

    public String getName() {
        return name;
    }

    public String getCheckUpDate() {
        return checkUpDate;
    }
}
