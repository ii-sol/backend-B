package shinhan.server_common.domain.tempuser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TempUserRepository extends JpaRepository<TempUser,Integer> {
    TempUser findBySerialNumber(Long serialNumber);
}
