package sinhan.server1.domain.parents.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT f.family AS sn, f.family.name AS name " +
            "FROM Family f " +
            "WHERE f.user.serialNum = :sn " +
            "ORDER BY sn")
    List<FamilyInfoResponse> findMyFamilyInfo(@Param("sn") long sn);

    Optional<Family> findByUserSerialNumAndFamilySn(Parents parents, long familySn);
}
