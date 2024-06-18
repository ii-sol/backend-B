package sinhan.server1.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FamilyFindOneResponse {

    private int id;
    @JsonProperty(value = "user_sn")
    private long userSn;
    @JsonProperty(value = "family_sn")
    private long familySn;
}