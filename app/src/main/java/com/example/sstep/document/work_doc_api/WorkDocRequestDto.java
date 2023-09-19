package com.example.sstep.document.work_doc_api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkDocRequestDto {
    private long photoId;

    public WorkDocRequestDto(long photoId) {
        this.photoId = photoId;
    }
}
