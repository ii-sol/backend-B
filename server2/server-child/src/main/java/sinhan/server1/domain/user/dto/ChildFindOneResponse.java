package sinhan.server1.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import sinhan.server1.domain.user.entity.Child;

import java.sql.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ChildFindOneResponse {

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

    public static ChildFindOneResponse from(Child child) {
        return ChildFindOneResponse.builder()
                .id(child.getId())
                .serialNumber(child.getSerialNum())
                .phoneNum(child.getPhoneNum())
                .name(child.getName())
                .birthDate(child.getBirthDate())
                .profileId(child.getProfileId())
                .score(child.getScore())
                .build();
    }

}
