package sinhan.server1.domain.tempuser;

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
    @Column(name="id")
    private int id;

    @Column(name="serial_num" , unique=true)
    private Long serialNumber;

    private String name;

    @Builder
    public TempUser(Long serialNumber) {
        this.serialNumber = serialNumber;
    }
}
