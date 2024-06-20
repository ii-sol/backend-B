package sinhan.server1.domain.parents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.parents.entity.Child;

import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Integer> {

    Optional<Child> findBySerialNum(long userSn);
}
