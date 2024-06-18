package sinhan.server1.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserInfoResponse {

    private long sn;
    private List<FamilyInfoResponse> familyInfo;
}
