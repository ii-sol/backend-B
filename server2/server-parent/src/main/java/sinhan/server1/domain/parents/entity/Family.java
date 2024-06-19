package sinhan.server1.domain.parents.entity;

import jakarta.persistence.*;
import lombok.*;
import sinhan.server1.domain.parents.dto.FamilyFindOneResponse;

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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parents_sn", referencedColumnName = "serial_num", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Parents parents;
    @Column(name = "parents_alias", length = 15)
    private String parentsAlias;

    public Family(Child child, Parents parents, String parentsAlias) {
        this.child = child;
        this.parents = parents;
        this.parentsAlias = parentsAlias;
    }

    public FamilyFindOneResponse convertToFamilyFindOneResponse() {
        return new FamilyFindOneResponse(id, child.getSerialNum(), parents.getSerialNum(), parentsAlias);
    }
}
