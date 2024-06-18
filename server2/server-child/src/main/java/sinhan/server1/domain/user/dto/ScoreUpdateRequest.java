package sinhan.server1.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScoreUpdateRequest {

    private long sn;
    private int change;
}
