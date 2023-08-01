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
    private long id;
    private String name;
    private String phoneNum;
    private String memberId;
    private String password;
    public MemberRequestDto(String memberId, String name, String password, String phoneNum) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.phoneNum = phoneNum;
    }
}
