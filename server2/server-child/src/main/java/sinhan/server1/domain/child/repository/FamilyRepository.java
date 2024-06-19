package sinhan.server1.domain.child.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sinhan.server1.domain.child.entity.Child;
import sinhan.server1.domain.child.entity.Family;
import sinhan.server1.domain.child.entity.Parents;
import sinhan.server1.global.security.dto.FamilyInfoResponse;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Family f WHERE f.id = :id")
    void delete(@Param("id") int id);

    @Query("SELECT f.parents.serialNum AS sn, f.parentsAlias AS name " +
            "FROM Family f " +
            "WHERE f.child.serialNum = :sn " +
            "ORDER BY sn")
    List<FamilyInfoResponse> findMyFamilyInfo(@Param("sn") long sn);

    Optional<Family> findByChildAndParents(Child child, Parents parents);
}
