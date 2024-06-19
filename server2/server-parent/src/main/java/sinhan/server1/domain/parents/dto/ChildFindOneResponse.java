package sinhan.server1.domain.parents.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import sinhan.server1.domain.parents.entity.Child;

import java.sql.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ChildFindOneResponse {

    private long serialNum;
    private String phoneNum;
    private String name;
    private Date birthDate;
    private int profileId;
    private int score;

    public static ChildFindOneResponse from(Child child) {
        return ChildFindOneResponse.builder()
                .serialNum(child.getSerialNum())
                .phoneNum(child.getPhoneNum())
                .name(child.getName())
                .birthDate(child.getBirthDate())
                .profileId(child.getProfileId())
                .score(child.getScore())
                .build();
    }
}
