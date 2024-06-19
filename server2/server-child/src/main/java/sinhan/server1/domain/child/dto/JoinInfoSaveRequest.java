package sinhan.server1.domain.child.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import sinhan.server1.domain.child.entity.Child;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinInfoSaveRequest {

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNum;
    @NotBlank(message = "이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣]{2,5}$", message = "이름은 한글로 최소 2글자 최대 5글자까지 입력 가능합니다.")
    private String name;
    @NotNull(message = "생일을 입력해주세요.")
    @Past(message = "생일은 현재 날짜보다 이전이어야 합니다.")
    private Date birthDate;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "\\d{6}", message = "비밀번호는 6자리 숫자여야 합니다.")
    private String accountInfo;
    private Integer profileId;

    public Child convertToUser(long serialNum, PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(accountInfo);
        return new Child(serialNum, phoneNum, name, birthDate, encodedPassword, profileId);
    }
}
