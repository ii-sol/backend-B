package sinhan.server1.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import sinhan.server1.domain.user.entity.User;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class JoinInfoSaveRequest {

    @JsonProperty(value = "phone_num")
    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNum;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @JsonProperty(value = "birth_date")
    @NotNull(message = "생일을 입력해주세요.")
    @Past(message = "생일은 현재 날짜보다 이전이어야 합니다.")
    private Date birthDate;
    @JsonProperty(value = "account_info")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "\\d{6}", message = "비밀번호는 6자리 숫자여야 합니다.")
    private String accountInfo;
    @JsonProperty(value = "profile_id")
    private Integer profileId;

    public User convertToUser(long serialNum, PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(accountInfo);
        return new User(serialNum, phoneNum, name, birthDate, encodedPassword, profileId);
    }
}
