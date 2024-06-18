package sinhan.server1.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FamilyInfoResponse{

    long sn;
    String name;

    public FamilyInfoResponse(long sn) {
        this.sn = sn;
    }
}
