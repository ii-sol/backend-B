package sinhan.server1.domain.parents.entity;

import jakarta.persistence.*;
import lombok.*;
import sinhan.server1.domain.parents.dto.ParentsFindOneResponse;

import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Parents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "serial_num", nullable = false, unique = true)
    private long serialNum;
    @Column(name = "phone_num", nullable = false, unique = true)
    private String phoneNum;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Date birthDate;
    @Column(name = "account_info", nullable = false)
    private String accountInfo;
    @Column(name = "profile_id", nullable = false, columnDefinition = "TINYINT UNSIGNED DEFAULT 1")
    private int profileId = 1;

    public Parents(long serialNum, String phoneNum, String name, Date birthDate, String accountInfo, int profileId) {
        this.serialNum = serialNum;
        this.phoneNum = phoneNum;
        this.name = name;
        this.birthDate = birthDate;
        this.accountInfo = accountInfo;
        this.profileId = profileId;
    }

    public ParentsFindOneResponse convertToUserFindOneResponse() {
        return new ParentsFindOneResponse(id, serialNum, phoneNum, name, birthDate, profileId);
    }
}