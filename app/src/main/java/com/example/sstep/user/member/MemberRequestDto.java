package com.example.sstep.user.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    private String name;
    private String phoneNum;
    private String username;
    private String password;
    public MemberRequestDto(String name, String password, String phoneNum, String username) {
        this.name = name;
        this.password = password;
        this.phoneNum = phoneNum;
        this.username = username;
    }

    public MemberRequestDto(String name, String phoneNum) {
        this.name = name;
        this.phoneNum = phoneNum;
    }
}
