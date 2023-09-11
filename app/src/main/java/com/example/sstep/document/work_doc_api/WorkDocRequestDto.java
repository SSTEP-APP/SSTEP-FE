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
    private long id;
    private String name;
    private String address;
    private String latitude;
    private String longitude;
    private boolean scale;
    private boolean plan;
    private long code;




}
