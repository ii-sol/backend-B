package sinhan.server1.domain.parents.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sinhan.server1.domain.parents.entity.Child;
import sinhan.server1.domain.parents.entity.Family;
import sinhan.server1.domain.parents.entity.Parents;
import sinhan.server1.global.security.dto.FamilyInfoResponse;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Family f WHERE f.id = :id")
    void delete(@Param("id") int id);

    @Query("SELECT f.child.serialNum AS sn, f.child.name AS name " +
            "FROM Family f " +
            "WHERE f.parents.serialNum = :sn " +
            "ORDER BY sn")
    List<FamilyInfoResponse> findMyFamilyInfo(@Param("sn") long sn);

    Optional<Family> findByChildAndParents(Child child, Parents parents);
}
