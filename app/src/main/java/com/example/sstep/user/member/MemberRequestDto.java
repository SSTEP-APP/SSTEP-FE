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
}
