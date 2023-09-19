package com.example.sstep.todo.checklist.checklist_api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequestDto {
    private long id; //카테고리 고유번호
    private String name; //카테고리 명

    public CategoryRequestDto(String name) {
        this.name = name;
    }
}
