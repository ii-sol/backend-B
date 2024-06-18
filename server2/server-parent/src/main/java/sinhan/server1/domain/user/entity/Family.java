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
    @JoinColumn(name = "child_sn", referencedColumnName = "serial_num", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Child child;
    @JoinColumn(name = "parents_sn", referencedColumnName = "serial_num", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Parents parents;

    public Family(Child child, Parents parents) {
        this.child = child;
        this.parents = parents;
    }

    public FamilyFindOneResponse convertToFamilyFindOneResponse() {
        return new FamilyFindOneResponse(id, child.getSerialNum(), parents.getSerialNum());
    }
}
