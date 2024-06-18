package sinhan.server1.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class FamilySaveRequest {

    private long userSn;
    private final String phoneNum;
}
