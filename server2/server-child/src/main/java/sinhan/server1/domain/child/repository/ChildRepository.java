package sinhan.server1.domain.child.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import sinhan.server1.domain.child.entity.Child;

import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Integer> {

    Optional<Child> findBySerialNum(long userSn);
    Optional<Child> findByPhoneNum(String phoneNum);

    @Query("SELECT u.phoneNum FROM User u")
    List<String> findAllPhones();

    @Procedure(procedureName = "generate_serial_num")
    Long generateSerialNum();
}
