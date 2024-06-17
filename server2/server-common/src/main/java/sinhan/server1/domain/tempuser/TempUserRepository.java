package sinhan.server1.domain.tempuser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TempUserRepository extends JpaRepository<TempUser, Integer> {
    Optional<TempUser> findBySerialNumber(Long serialNumber);
}
