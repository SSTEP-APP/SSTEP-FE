package drawable.user.member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private long id;
    private String memberId;

    private String name;

    private String phoneNum;

    private String password;

}
