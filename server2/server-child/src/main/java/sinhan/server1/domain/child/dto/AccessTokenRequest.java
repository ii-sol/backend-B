package sinhan.server1.domain.child.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccessTokenRequest {

    @NotBlank(message = "refreshToken을 입력해주세요.")
    private String refreshToken;
}
