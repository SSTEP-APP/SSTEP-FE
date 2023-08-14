package com.example.sstep.user.staff_api;


import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffInviteResponseDto {
    private String name; //회원 이름
    private String username; //회원 아이디
}
