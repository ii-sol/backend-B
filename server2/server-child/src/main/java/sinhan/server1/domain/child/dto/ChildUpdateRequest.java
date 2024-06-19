package sinhan.server1.domain.child.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class ChildUpdateRequest {

    private long serialNum;
    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다.")
    private  String phoneNum;
    @NotBlank(message = "이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣]{2,5}$", message = "이름은 한글로 최소 2글자 최대 5글자까지 입력 가능합니다.")
    private  String name;
    @NotNull(message = "생일을 입력해주세요.")
    @Past(message = "생일은 현재 날짜보다 이전이어야 합니다.")
    private  Date birthDate;
    @NotNull(message = "프로필 번호를 입력해주세요.")
    private  int profileId;

    public ChildUpdateRequest(String phoneNum, String name, Date birthDate, int profileId) {
        this.phoneNum = phoneNum;
        this.name = name;
        this.birthDate = birthDate;
        this.profileId = profileId;
    }
}
