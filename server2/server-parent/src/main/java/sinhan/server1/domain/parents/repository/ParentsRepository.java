package sinhan.server1.domain.parents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import sinhan.server1.domain.parents.entity.Parents;

import java.util.List;
import java.util.Optional;

public interface ParentsRepository extends JpaRepository<Parents, Integer> {

    Optional<Parents> findBySerialNum(long userSn);
    Optional<Parents> findByPhoneNum(String phoneNum);

    @Query("SELECT u.phoneNum FROM User u")
    List<String> findAllPhones();

    @Procedure(procedureName = "generate_serial_num")
    Long generateSerialNum();
}
