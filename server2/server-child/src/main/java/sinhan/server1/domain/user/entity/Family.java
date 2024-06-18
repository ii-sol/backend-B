package sinhan.server1.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import sinhan.server1.domain.user.dto.FamilyFindOneResponse;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"parents_sn", "child_sn"})})
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_sn", referencedColumnName = "serial_num", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Child child;
    @Column(name = "family_sn", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private long familySn;

    public Family(Child child, long familySn) {
        this.child = child;
        this.familySn = familySn;
    }

    public FamilyFindOneResponse convertToFamilyFindOneResponse() {
        return new FamilyFindOneResponse(id, child.getSerialNum(), familySn);
    }
}