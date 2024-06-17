package sinhan.server1.domain.tempuser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempUser {
    @Id
    private int id;

    @Column(name="serial_num")
    private int serialNumber;

    public TempUser(int id, int serialNumber) {
        this.id = id;
        this.serialNumber = serialNumber;
    }
}
