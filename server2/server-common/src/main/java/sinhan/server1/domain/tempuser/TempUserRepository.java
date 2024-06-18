package sinhan.server1.domain.tempuser;

import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.notification.entity.Notification;

public interface TempUserRepository extends JpaRepository<TempUser,Integer> {
    TempUser findBySerialNumber(Long serialNumber);
}
