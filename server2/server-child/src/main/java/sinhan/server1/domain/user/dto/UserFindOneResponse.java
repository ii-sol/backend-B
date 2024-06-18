package sinhan.server1.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import sinhan.server1.domain.user.entity.User;

import java.sql.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserFindOneResponse {

    private int id;
    @JsonProperty(value = "serial_number")
    private long serialNumber;
    @JsonProperty(value = "phone_num")
    private String phoneNum;
    private String name;
    @JsonProperty(value = "birth_date")
    private Date birthDate;
    @JsonProperty(value = "profile_id")
    private int profileId;
    private int score;

    public static UserFindOneResponse from(User user) {
        return UserFindOneResponse.builder()
                .id(user.getId())
                .serialNumber(user.getSerialNum())
                .phoneNum(user.getPhoneNum())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .profileId(user.getProfileId())
                .score(user.getScore())
                .build();
    }

}
