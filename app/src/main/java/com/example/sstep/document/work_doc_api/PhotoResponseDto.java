package com.example.sstep.document.work_doc_api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoResponseDto {
    private long id;
    private String fileName;
    private String contentType;
    private byte[] data;
}