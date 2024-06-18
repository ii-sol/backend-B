package sinhan.server1.domain.child.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScoreUpdateRequest {

    private long sn;
    private int change;
}
