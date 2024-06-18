package sinhan.server2.domain.tempuser;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "serial_num", unique = true)
    Long serialNumber;

    String name;

    @Builder
    public TempUser( Long serialNumber, String name) {
        this.serialNumber = serialNumber;
        this.name = name;
    }
}
