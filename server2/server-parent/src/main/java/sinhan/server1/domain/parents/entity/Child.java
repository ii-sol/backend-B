package sinhan.server1.domain.parents.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import sinhan.server1.domain.parents.dto.ChildFindOneResponse;

import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Check(constraints = "score >= 0 AND score <= 100")
public class Child {

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
    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED DEFAULT 50")
    private int score = 50;

    public Child(long serialNum, String phoneNum, String name, Date birthDate, String accountInfo, int profileId) {
        this.serialNum = serialNum;
        this.phoneNum = phoneNum;
        this.name = name;
        this.birthDate = birthDate;
        this.accountInfo = accountInfo;
        this.profileId = profileId;
    }

    @PrePersist
    @PreUpdate
    private void validateScore() {
        if (this.score < 0) {
            this.score = 0;
        } else if (this.score > 100) {
            this.score = 100;
        }
    }

    public ChildFindOneResponse convertToUserFindOneResponse() {
        return new ChildFindOneResponse(serialNum, phoneNum, name, birthDate, profileId, score);
    }
}