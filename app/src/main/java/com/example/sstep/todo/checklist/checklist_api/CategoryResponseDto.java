package com.example.sstep.todo.checklist.checklist_api;


import com.example.sstep.document.work_doc_api.PhotoResponseDto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDto {
    private String name; //카테고리 명

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
