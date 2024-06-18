package sinhan.server1.domain.parents.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@RequiredArgsConstructor
@Getter
@Setter
public class ParentsUpdateRequest {

    private long serialNum;
    private final String phoneNum;
    private final String name;
    private final Date birthdate;
    private final int profileId;
}
