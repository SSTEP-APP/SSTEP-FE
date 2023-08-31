package com.example.sstep.document.healthdoc_api;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthDocRequestDto {
    private long id; //문서 고유번호
    private String checkUpDate; //보건증 검진일
    private String expirationDate; //보건증 만료일
    private RequestBody multipartFile; //사진 정보

    public HealthDocRequestDto(String checkUpDate, String expirationDate, RequestBody multipartFile) {
        this.checkUpDate = checkUpDate;
        this.expirationDate = expirationDate;
        this.multipartFile = multipartFile;
    }
}
